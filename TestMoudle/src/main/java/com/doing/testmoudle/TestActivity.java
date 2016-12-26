package com.doing.testmoudle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doing.testmoudle.utils.LogUtil;


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

    }
}
