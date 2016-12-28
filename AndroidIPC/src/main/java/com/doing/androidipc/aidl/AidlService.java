package com.doing.androidipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static android.os.Build.VERSION_CODES.M;

public class AidlService extends Service {

    private final static String TAG = "AidlService";
    private RemoteCallbackList<Callback> mCallbackBuf = new RemoteCallbackList<>();

    private Binder mBinder = new AidlManager.Stub(){

        @Override
        public boolean addUser(String text, String name, List<String> arg, User user) throws RemoteException {
            Log.e(TAG, "Server：接收" + text);
            Log.e(TAG, "Server：接收" + user.toString());
//            arg = new ArrayList<>();
            arg.add("一个奇怪的输出函数- -就是能够返回多个值呗");

//            if (mCallbackBuf.size() > 0) {
//                mCallbackBuf.get(0).activityCallback();
//            }
            int length = mCallbackBuf.beginBroadcast();
            for (int x = 0; x < length; x++) {
                Callback broadcastItem = mCallbackBuf.getBroadcastItem(x);
                if (broadcastItem != null) {
                    broadcastItem.activityCallback();
                }
            }
            mCallbackBuf.finishBroadcast();
            return true;
        }

        @Override
        public void registCallback(Callback callback) throws RemoteException {
            mCallbackBuf.register(callback);

        }

        @Override
        public void unRegistCallback(Callback callback) throws RemoteException {
            mCallbackBuf.unregister(callback);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
