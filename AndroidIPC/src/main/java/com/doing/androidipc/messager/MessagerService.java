package com.doing.androidipc.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessagerService extends Service {

    private static final String TAG = "MessagerService";

    public static final int MESSAGER_SERVICE_WHAT = 99;
    public static final int MESSAGER_SERVICE_GET = 98;


    private Messenger mMessenger;

    private static class ServieHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGER_SERVICE_WHAT:
                    Log.e(TAG, "服务端接收到了数据");
                    break;

                case MESSAGER_SERVICE_GET:
                    Log.e(TAG, "我接受到信息了呦：" + msg.getData().getString(MESSAGER_SERVICE_GET + ""));
                    Messenger replyTo = msg.replyTo;
                    Message message = Message.obtain();
//                    message.obj = "Server：【你去屎！】";
                    try {
                        replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.w(TAG, "onBind: ");
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMessenger = new Messenger(new ServieHandler());
        Log.w(TAG, "onCreate: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.w(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w(TAG, "onUnbind: ");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.w(TAG, "onRebind: ");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
