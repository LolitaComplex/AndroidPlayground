package com.doing.architectureencapsulation.service;

import android.os.Handler;
import android.os.Looper;

import static android.os.Looper.myLooper;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-14.
 */

public class ThreadUtils {

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void runOnMainThread(Runnable runnable) {
        sHandler.post(runnable);
    }
}
