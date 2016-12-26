package com.doing.testmoudle.base;

import android.app.Application;

import com.doing.testmoudle.utils.LogUtil;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setCurrentLevel(6);
    }


}
