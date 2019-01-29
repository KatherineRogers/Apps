package com.example.katie.hrubieckatherine_ce07;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction() != null){
            if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) || intent.getAction().equals(MainActivity.ACTION_PUSH_NOTIFICATION)){
                AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent alarmIntent = new Intent(context ,ArticleIntentService.class);
                PendingIntent pendingIntent = PendingIntent.getService(context, 0, alarmIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
                if(mgr != null){
                    mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
                }
            }
        }
    }
}
