package com.doing.androidipc.messager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.doing.androidipc.R;

public class MessagerActivity extends AppCompatActivity {

    private static final String TAG = "MessagerActivity";

    private Messenger mClientMessenger;
    private Messenger mClientAcceptMessenger = new Messenger(new ClientHandler());
    private MessagerConection mMessagerConection;
    private Intent mServiceIntent;

    private static class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "服务端告诉我：" + msg.obj);
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);

        setTitle("MessagerActivity");

        LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        linkService();
    }

    private void linkService() {
        mServiceIntent = new Intent(this, MessagerService.class);
        startService(mServiceIntent);
        mMessagerConection = new MessagerConection();
        bindService(mServiceIntent, mMessagerConection, Context.BIND_AUTO_CREATE);
    }

    public void sendToService(View view) {
        Message message = Message.obtain();
        message.what = MessagerService.MESSAGER_SERVICE_GET;
        Bundle bundle = new Bundle();
        Log.e(TAG, "Client：我开始发送信息了");
        bundle.putString(MessagerService.MESSAGER_SERVICE_GET + "", "Client:【你接受到我的信息了吗？】");
        message.replyTo = mClientAcceptMessenger;
        message.setData(bundle);
        try {
            this.mClientMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mMessagerConection);
        stopService(mServiceIntent);

        super.onDestroy();
    }

    private class MessagerConection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mClientMessenger = new Messenger(iBinder);
            Message message = Message.obtain(null, MessagerService.MESSAGER_SERVICE_WHAT);
            try {
                iBinder.linkToDeath(mDeathRecipient, 0);
                mClientMessenger.send(message);
            } catch (RemoteException e) {
                Toast.makeText(getApplicationContext(), "发送异常", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        @Override
        public void binderDied() {
            if (mClientMessenger == null) {
                return;
            }
            Log.e(TAG, "死亡重连接");
            mClientMessenger.getBinder().unlinkToDeath(mDeathRecipient, 0);
            mClientMessenger = null;
        }
    };

}
