package com.dong.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.dong.tool.ShowToast;
import com.dong.viewcollection.MainActivity;
import com.dong.viewcollection.R;

/**
 * Created by Dy on 2016/3/3.
 *  http://blog.csdn.net/loongggdroid/article/details/17616509
 */
public class NotificationFragment extends BaseFragment implements View.OnClickListener {

    private static int NOTIFACATION_FLAG = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification,container,false);
        Button button1 = (Button) view.findViewById(R.id.btn_normal);
        Button button2 = (Button) view.findViewById(R.id.btn_api11);
        Button button3 = (Button) view.findViewById(R.id.btn_api16);
        Button button4 = (Button) view.findViewById(R.id.btn_user_defined);
        Button button5 = (Button) view.findViewById(R.id.btn_clear);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        int viewId = view.getId();
        switch (viewId) {
            //默认通知
            case R.id.btn_normal:
                //延迟的Intent，不同在于不是马上调用跳转，需要在下拉状态条触发的Activity。即点击Notification跳转启动哪个Activity
                PendingIntent intent1 = PendingIntent.getActivity(mContext, 0, new Intent(mContext, MainActivity.class), 0);
                //兼容任何版本
                Notification notification1 = new Notification();
                notification1.icon = R.mipmap.notification_title;
                notification1.tickerText = "过时的通知，不管了，快上车";
                // notification.setLatestEventInfo();//这个方法被谷歌个KO了？反正无法调用了....文档中标注了被Builder替换了
                ShowToast.showText(mContext, "已经过时的方式，无法调用了...", Toast.LENGTH_SHORT);
                break;

            //API11后使的方式
            case R.id.btn_api11:
                PendingIntent intent2 = PendingIntent.getActivity(mContext, 0, new Intent(mContext, MainActivity.class), 0);
                //API11后支持的构建Notification的方法
                /**
                 * setSmallIcon——设置状态栏小图片，尺寸建议是 24x24 ，需要更大图片可以使用setLarge(Bitmap)
                 * setTicker()——设置在statusbar上显示的文字
                 * setContentTitle()——设置在下拉菜单的文字标题
                 * setContentText()——设置在下拉菜单中的文字内容
                 * setPendingIntent——设置点击关联的PendingIntent
                 * setNumbar()——设置下来菜单最右边显示的数字。同样起到一个序列号的作用，如果触发多个通知，可以指定显示哪一个
                 * getNotification()——过时的方法，被build()替代，不过这是API16的方法
                 * setDefaults()为通知设置了声音提示  DEFAULT_SOUND:设置使用默认声音；DEFAULT_VIBRATE：设置使用默认驱动；DEFAULT_LIGHTS:设置使用默认闪光灯；ALL:设置使用默认声音、震动、闪光灯
                 */
                Notification notification2 = new Notification.Builder(mContext).setSmallIcon(R.mipmap.ic_launcher).setTicker("Tiker Text:显示不出来吗？")
                        .setContentTitle("Content title").setContentText("这是兼容性API 11的提示信息").setContentIntent(intent2).setNumber(1)
                        .setDefaults(Notification.DEFAULT_ALL).getNotification();
                notification2.flags |= Notification.FLAG_AUTO_CANCEL;//表示消息被用户点击时将自动消失
                manager.notify(NOTIFACATION_FLAG,notification2);
                break;
            case R.id.btn_api16:
                PendingIntent intent3 = PendingIntent.getActivity(mContext, 0, new Intent(mContext, MainActivity.class), 0);
                //API16后支持的构建Notification的方法
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Notification notification3 = new Notification.Builder(mContext).setSmallIcon(R.mipmap.ic_launcher).setTicker("Tiker Text:显示不出来吗？")
                            .setContentTitle("Content title").setContentText("这是兼容性API 16的提示信息").setContentIntent(intent3).setNumber(2).build();
                    notification3.flags |= Notification.FLAG_AUTO_CANCEL;//表示消息被用户点击时将自动消失
                    manager.notify(2,notification3);//如果id不同每点击一次在下拉菜单中增加一个提示
                }

                break;
            case R.id.btn_user_defined:
                PendingIntent intent4 = PendingIntent.getActivity(mContext, 1, new Intent(mContext, MainActivity.class), 1);

                Notification notification4 = new Notification();
                notification4.icon = R.mipmap.ic_launcher;
                notification4.tickerText = "user defined";
                notification4.when = SystemClock.currentThreadTimeMillis();
                notification4.flags = Notification.FLAG_NO_CLEAR;//不能自动清除
                RemoteViews remoteView = new RemoteViews(mContext.getPackageName(),R.layout.activity_main);
                notification4.contentView = remoteView;
                notification4.contentIntent = intent4;
                manager.notify(NOTIFACATION_FLAG,notification4);
                break;
            case R.id.btn_clear:
                //清除指定ID的通知
                manager.cancel(NOTIFACATION_FLAG);
                //清除所有通知
                //manager.cancelAll();

                break;
        }
    }
}
