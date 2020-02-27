package com.example.wifip2pmulticlient

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast

/*
*Created by Katherine.Rogers on 11,February,2020
*/
class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: MainActivity
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action: String = intent.action!!
        when (action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Check to see if Wi-Fi is enabled and notify appropriate activity
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                when (state) {
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
                        // Wifi P2P is enabled
                        Toast.makeText(context,"WiFi is ON", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context,"WiFi is OFF", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // Call WifiP2pManager.requestPeers() to get a list of current peers
                manager.requestPeers(channel,activity.peerListListener)
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                // Respond to new connection or disconnections
                manager.let { manager ->

                    val networkInfo: NetworkInfo? = intent
                        .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO) as NetworkInfo

                    if (networkInfo?.isConnected == true) {

                        // We are connected with the other device, request connection
                        // info to find group owner IP

                        manager.requestConnectionInfo(channel, activity.connectionListener)
                    }
                }
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {

            }
        }
    }
}
