package com.example.wifip2pmulticlient

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.ref.PhantomReference
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {

    lateinit var serverButton: Button
    lateinit var discoverButton: Button
    lateinit var listView: ListView
    lateinit var messageET: EditText
    lateinit var sendButton: Button

    lateinit var adapter: ArrayAdapter<String>

    var peers: ArrayList<WifiP2pDevice> = ArrayList()
    var deviceNameArray: ArrayList<String> = ArrayList()
    var deviceArray: ArrayList<WifiP2pDevice> = ArrayList()

    val manager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE) {
        getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }

    var mChannel: WifiP2pManager.Channel? = null
    var receiver: BroadcastReceiver? = null

    val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    val peerListListener = WifiP2pManager.PeerListListener { peerList ->
        if (!peerList.deviceList.equals(peers)) {
            peers.clear()
            deviceNameArray.clear()
            deviceArray.clear()
            peers.addAll(peerList.deviceList)

            for (device in peerList.deviceList) {
                deviceNameArray.add(device.deviceName)
                deviceArray.add(device)
            }
            adapter.notifyDataSetChanged()
        }
    }

    val connectionListener = WifiP2pManager.ConnectionInfoListener { info ->

        // InetAddress from WifiP2pInfo struct.
        val groupOwnerAddress: InetAddress = info.groupOwnerAddress

        // After the group negotiation, we can determine the group owner
        // (server).
        if (info.isGroupOwner) {
            // Do whatever tasks are specific to the group owner.
            // One common case is creating a group owner thread and accepting
            // incoming connections.
            serverClass = ServerClass()
            serverClass!!.start()
            statusText.setText("Server")
        } else {
            // The other device acts as the peer (client). In this case,
            // you'll want to create a peer thread that connects
            // to the group owner.
            clientClass = ClientClass(groupOwnerAddress)
            clientClass.start()
            statusText.setText("Client")
        }
    }

    companion object {
        var first = 1234
        var serverSocket: ServerSocket? = null
        var sendRecieve: SendRecieve? = null
        var listOfSendRecieves: ArrayList<SendRecieve> = ArrayList()
        val MESSAGE_READ = 1
        var serverClass: ServerClass? = null
        lateinit var clientClass: ClientClass
        lateinit var msgTV: TextView
        lateinit var mcontext: Context
        lateinit var statusText: TextView

        fun setCon(c: Context) {
            mcontext = c
        }

        val handler: Handler = Handler(Handler.Callback {
            if (it.what == MESSAGE_READ) {
                val readBuffer = it.obj as ByteArray
                val tmpMsg = String(readBuffer, 0, it.arg1)
                msgTV.setText(tmpMsg)
            }
            return@Callback true
        })

        val disco: Handler = Handler(Handler.Callback {
            statusText.setText("Connection Lost")
            return@Callback true
        })

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serverButton = findViewById(R.id.server)
        discoverButton = findViewById(R.id.discover)
        listView = findViewById(R.id.list)
        messageET = findViewById(R.id.message)
        sendButton = findViewById(R.id.send)
        statusText = findViewById(R.id.status)
        msgTV = findViewById(R.id.msg)

        init()
        checkPermissions()
    }

    fun init() {
        setCon(this)
        adapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, deviceNameArray)
        listView.adapter = adapter

        mChannel = manager?.initialize(this, mainLooper, null)
        mChannel?.also { channel ->
            receiver = manager?.let { WiFiDirectBroadcastReceiver(it, channel, this) }
        }

        discoverButton.setOnClickListener {
            manager?.discoverPeers(mChannel, object : WifiP2pManager.ActionListener {

                override fun onSuccess() {
                    statusText.setText("Discovery Started")
                }

                override fun onFailure(reasonCode: Int) {
                    statusText.setText("Discovery Starting Failed")
                }
            })
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val device = peers[position]

            val config = WifiP2pConfig().apply {
                deviceAddress = device.deviceAddress
                wps.setup = WpsInfo.PBC
            }

            manager?.connect(mChannel, config, object : WifiP2pManager.ActionListener {

                override fun onSuccess() {
                    // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
                    Toast.makeText(
                        this@MainActivity,
                        "Connected to " + device.deviceName,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(reason: Int) {
                    Toast.makeText(
                        this@MainActivity,
                        "Connect failed. Retry.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
        sendButton.setOnClickListener {
            val msg = messageET.text.toString()
            if (!listOfSendRecieves.isEmpty()) {
                Log.i("SENDING...", "sending to multi device")
                for (sr in listOfSendRecieves) {
                    sr.write(msg.toByteArray())
                }
            } else {
                Log.i("SENDING...", "sending to single device")
                sendRecieve?.write(msg.toByteArray())
            }
        }

    }

    /* register the broadcast receiver with the intent values to be matched */
    override fun onResume() {
        super.onResume()
        receiver?.also { receiver ->
            registerReceiver(receiver, intentFilter)
        }
    }

    /* unregister the broadcast receiver */
    override fun onPause() {
        super.onPause()
        receiver?.also { receiver ->
            unregisterReceiver(receiver)
        }
    }

    fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_WIFI_STATE)
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_WIFI_STATE),
                    1
                )
            }
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CHANGE_WIFI_STATE
            )
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CHANGE_WIFI_STATE),
                    2
                )
            }
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CHANGE_NETWORK_STATE
            )
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CHANGE_NETWORK_STATE),
                    3
                )
            }
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            )
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.INTERNET),
                    4
                )
            }
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            )
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_NETWORK_STATE),
                    5
                )
            }
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    6
                )
            }
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    7
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions()
                }
            }
        }
    }

    public class ServerClass : Thread() {
        lateinit var socket: Socket

        override fun run() {
            Log.i("THIS", "Creating server socket.............")
            try {
                if (serverSocket == null) {
                    serverSocket = ServerSocket(first)
                }
                socket = serverSocket!!.accept()
                sendRecieve = SendRecieve(socket)
                listOfSendRecieves.add(sendRecieve!!)
                sendRecieve!!.start()
            }catch (e:IOException){
                e.printStackTrace()
            }

        }
    }

    class SendRecieve(socket: Socket) : Thread() {
        var socket: Socket
        var inputStream: InputStream
        var outputStream: OutputStream

        init {
            this.socket = socket
            inputStream = socket.getInputStream()
            outputStream = socket.getOutputStream()
        }

        override fun run() {
            Log.i("THIS", "running send recieve")
            val buffer: ByteArray = ByteArray(1024)
            var bytes: Int
            try {
                while (true) {
                    bytes = inputStream.read(buffer)
                    if (bytes > 0) {
                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget()
                    }
                }
            } catch (e: IOException) {
                disco.obtainMessage().sendToTarget()
                e.printStackTrace()
            }
        }

        public fun write(bytes: ByteArray) {
            WriteBytes(outputStream, bytes).start()
        }
    }

    public class WriteBytes(outputStream: OutputStream, bytes: ByteArray) : Thread() {
        var outputStream: OutputStream
        var bytes: ByteArray

        init {
            this.outputStream = outputStream
            this.bytes = bytes
        }

        override fun run() {
            super.run()
            try {
                outputStream.write(bytes)
            }catch (e:IOException){
                e.printStackTrace()
            }

        }
    }

    public class ClientClass(hostAddress: InetAddress) : Thread() {
        var socket: Socket
        var hostAddress: String

        init {
            this.hostAddress = hostAddress.hostAddress
            this.socket = Socket()
            socket.bind(null)
        }

        override fun run() {
            super.run()
            try {
                Log.i("THIS", "Connecting client socket.............")
                socket.connect(InetSocketAddress(hostAddress, first), 10000)
                sendRecieve = SendRecieve(socket)
                sendRecieve!!.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

