package com.doing.testmoudle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Messenger;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.util.AtomicFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.testmoudle.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;


public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //验证SystemClock的时间准确值
        LogUtil.d(TAG, "elapsedRealtime : " + SystemClock.elapsedRealtime());//在AlarmManager中使用
        LogUtil.d(TAG, "uptimeMillis : " + SystemClock.uptimeMillis());//在Handler中使用
        LogUtil.d(TAG, "elapsedRealtimeNanos : " + SystemClock.elapsedRealtimeNanos());
        LogUtil.d(TAG, "currentThreadTimeMillis : " + SystemClock.currentThreadTimeMillis());

        //判断隐式意图是否匹配成功的API
        PackageManager packageManager = getPackageManager();
        assert packageManager.resolveActivity(new Intent(),
                PackageManager.MATCH_DEFAULT_ONLY) != null;

        assert new Intent().resolveActivity(getPackageManager())
                != null;

        CopyOnWriteArrayList<String> keyWord = new CopyOnWriteArrayList<>(new String[30]);
        keyWord.add("");
        ArrayList<String> list = new ArrayList<>();


        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        boolean result = atomicBoolean.compareAndSet(true, false);


        View view = getWindow().getDecorView();
        view.getLeft();
        view.getX();
        view.getTranslationX();
        view.setX(4);
    }
}
