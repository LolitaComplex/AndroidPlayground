package com.doing.testmoudle.utils;


import android.util.Log;

public class LogUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;

    private static int currentLevel = 0;

    public static void setCurrentLevel(int level){
        currentLevel = level;
    }

    public static void v(String tag, String message) {
        if (currentLevel > VERBOSE) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (currentLevel > DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (currentLevel > INFO) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (currentLevel > WARN) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (currentLevel > ERROR) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message,Throwable e) {
        if (currentLevel > ERROR) {
            Log.e(tag, message, e);
        }
    }
}
