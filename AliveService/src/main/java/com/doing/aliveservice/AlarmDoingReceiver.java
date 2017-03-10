package com.doing.aliveservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class AlarmDoingReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmDoingReceiver";

    public AlarmDoingReceiver() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void start(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmDoingReceiver.class);
        intent.setAction("AlarmDoingReceiver");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.ELAPSED_REALTIME, 3000, pendingIntent);
//        manager.setExact(AlarmManager.ELAPSED_REALTIME, 3000, pendingIntent);
        Log.w(TAG, "start: AlarmManager start");
    }

    public static void destory(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmDoingReceiver.class);
        PackageManager manager = context.getPackageManager();
        manager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                , PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
    }
}
