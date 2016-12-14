package com.doing.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.smallicon:
                makeSmallIconNotification();
                break;

            case R.id.style:
                makeStyleNotification();
                break;

            case R.id.progress:
                makeProgressNotification();
                break;

            case R.id.accept_or_refuse:
                makeAcceptOrRefuseNotification();
                break;

            case R.id.remote:
                makeRemoteNotification();
                break;
        }
    }


    private void makeSmallIconNotification() {
        Intent intent = new Intent();
        intent.setAction("com.doing.notification");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("doing://com.doing.notification"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("这是我的通知头")
                .setContentText("这是我的通知内容")
                .setContentInfo("提醒")
                .setSmallIcon(R.drawable.lock)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lock_pink))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify("其它分组33", 3, builder.build());
    }

    private void makeStyleNotification() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("颤抖吧，闪烁吧, 出来吧")
                .setContentText("DarkFrameMaster")
                .setSmallIcon(R.drawable.ic_pan_tool_pink_500_24dp)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSound(Uri.parse("android:resource://" + this.getPackageName()
                        + "/" + R.raw.click))
                .setVibrate(new long[]{0, 300, 300, 300})
                .setLights(Color.BLUE, 1000, 1000)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManagerCompat.from(this).notify(2, notification);
    }

    private void makeProgressNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("这是我的通知头")
                .setContentText("这是我的通知内容")
                .setContentInfo("提醒")
                .setSmallIcon(R.drawable.ball)
                .setAutoCancel(true);


        final NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        Observable.interval(1, TimeUnit.SECONDS)
                .take(100)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    private int progress = 1;

                    @Override
                    public void call(Long aLong) {

                        builder.setProgress(100, progress++, false);
                        manager.notify("55", 33, builder.build());
                    }
                });

    }

    private void makeAcceptOrRefuseNotification() {
        Intent intent = new Intent(this, NotificationIntentService.class);
        NotificationCompat.BigTextStyle bigTextStyle = new
                NotificationCompat.BigTextStyle();

        NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle();
        mediaStyle.setShowActionsInCompactView(0, 1);//向上拖动菜单栏后，通知会缩放只剩下播放和暂停按钮
        bigTextStyle.bigText("长文本");
        bigTextStyle.setSummaryText("注释");
        Notification build = new NotificationCompat.Builder(this)
                .setContentTitle("隔壁老王")
                .setSmallIcon(R.drawable.love)
                .addAction(R.drawable.sure, "确认", PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.del, "取消", PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setStyle(mediaStyle)
                .build();

        NotificationManagerCompat.from(this).notify(4, build);
    }

    private void makeRemoteNotification() {
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.layout_remote);
//        remoteView.setTextViewText(R.id.tv_title,titleText);
//        remoteView.setImageViewBitmap(R.id.iv_icon,bitmap);
//        remoteView.setOnClickPendingIntent(R.id.btn_play,PendingIntent);
        Notification build = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.music)
                .setContent(remoteView)
                .build();

        NotificationManagerCompat.from(this).notify(32, build);
    }




}
