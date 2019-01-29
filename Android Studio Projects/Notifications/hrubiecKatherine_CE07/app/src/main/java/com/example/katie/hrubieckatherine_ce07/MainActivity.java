package com.example.katie.hrubieckatherine_ce07;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListFrag.SelectedArticleListener{

    public static ArrayList<Article> articles = new ArrayList<>();
    public static Toast mToast;
    public static final int EXPANDED_NOTIFICATION = 0x01002;
    public static final String ACTION_SAVE_ARTICLE =
            "com.example.katie.hrubieckatherine_ce07.ACTION_SAVE_ARTICLE";
    public static final String ACTION_SAVE_ARRAY =
            "com.example.katie.hrubieckatherine_ce07.ACTION_SAVE_ARRAY";
    public static final String ACTION_PUSH_NOTIFICATION =
            "com.example.katie.hrubieckatherine_ce07.ACTION_PUSH_NOTIFICATION";
    private SaveReciever mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = Toast.makeText(getApplicationContext(), R.string.no_int, Toast.LENGTH_SHORT);

        if(readObjectFromCache(this, ACTION_SAVE_ARRAY) != null){
            articles = (ArrayList<Article>) readObjectFromCache(this, ACTION_SAVE_ARRAY);
        }
        setList();
        setAlarm();
    }

    private void setAlarm(){
        AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext() ,ArticleIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        if(mgr != null){
            mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
        }
    }

    private void setList() {
        getFragmentManager().beginTransaction().replace(R.id.frame, ListFrag.newInstance(articles)).commit();
    }

    @Override
    public void selectedArticle(int position) {
        //navigate to webpage
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.get(position).urlToArticle));
        startActivity(browserIntent);
    }

    public static void writeObjectInCache(Context context, String key, Object object) {
        try {
            FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Object readObjectFromCache(Context context, String key) {
        try {
            FileInputStream fis = context.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeObjectInCache(this, ACTION_SAVE_ARRAY, articles);
        Intent stopIntent = new Intent(ACTION_PUSH_NOTIFICATION);
        sendBroadcast(stopIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new SaveReciever();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SAVE_ARTICLE);
        registerReceiver(mReceiver, filter);
        writeObjectInCache(this, ACTION_SAVE_ARRAY, articles);
        articles = (ArrayList<Article>) readObjectFromCache(this, ACTION_SAVE_ARRAY);
        setList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeObjectInCache(this, ACTION_SAVE_ARRAY, articles);
        Intent stopIntent = new Intent(ACTION_PUSH_NOTIFICATION);
        sendBroadcast(stopIntent);
        unregisterReceiver(mReceiver);
    }


    public class SaveReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() != null){
                if(intent.getAction().equals(MainActivity.ACTION_SAVE_ARTICLE)){
                    articles = (ArrayList<Article>) readObjectFromCache(context, ACTION_SAVE_ARRAY);
                    setList();
                }
            }
        }
    }


}
