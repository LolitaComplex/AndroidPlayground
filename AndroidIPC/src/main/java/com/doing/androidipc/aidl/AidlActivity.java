package com.doing.androidipc.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.doing.androidipc.R;

import java.util.ArrayList;
import java.util.List;

public class AidlActivity extends AppCompatActivity {

    private static final String TAG = "AidlActivity";

    private ServiceConnection mAidlConnection;
    private Intent mServiceIntent;
    private AidlManager mAidlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("AidlActivity");
        setContentView(R.layout.activity_aidl);

        mServiceIntent = new Intent(this, AidlService.class);
        startService(mServiceIntent);
        mAidlConnection = new AidlConnection();
        bindService(mServiceIntent, mAidlConnection, BIND_AUTO_CREATE);
    }

    public void sendToService(View view) {
        List<String> list = new ArrayList<>();
        list.add("aaaa");
        boolean flag = false;
        try {
            flag = mAidlManager.addUser("这是个啥？", list, new User("芙兰朵露", 22, 3));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(TAG, list.get(0) + "-------" + flag);
    }

    public void bindCallback(View view) {
        try {
            mAidlManager.registCallback(mAidlCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unbindCallback(View view) {
        try {
            mAidlManager.unRegistCallback(mAidlCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class AidlConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAidlManager = AidlManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    private Callback mAidlCallback = new Callback.Stub(){
        @Override
        public void activityCallback() throws RemoteException {
            Log.e(TAG, "更新了用户");
        }
    };


    @Override
    protected void onDestroy() {
        unbindService(mAidlConnection);
        stopService(mServiceIntent);
        try {
            mAidlManager.unRegistCallback(mAidlCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
