package com.doing.architectureencapsulation.image;


import android.telecom.Call;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-15.
 */

public class AsyncUtils {

    private static ExecutorService sSingleExector = null;
    public static ExecutorService getSingleExector(){
        if (sSingleExector == null) {
            synchronized (AsyncUtils.class) {
                if (sSingleExector == null) {
                    sSingleExector = Executors.newSingleThreadExecutor();
                }
            }
        }

        return sSingleExector;
    }

    private static ExecutorService sMultiExector = null;
    public static ExecutorService getMultiExector() {
        if (sMultiExector == null) {
            synchronized (AsyncUtils.class) {
                if (sMultiExector == null) {
                    int available = Runtime.getRuntime().availableProcessors();
                    sMultiExector = new ThreadPoolExecutor(available + 1,
                            2 * available,
                            30L, TimeUnit.SECONDS,
                            new LinkedBlockingDeque<Runnable>(20),
                            new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }
        return sMultiExector;
    }

    public static void runOnSingleExecutor(Runnable runnable) {
        if (runnable != null) {
            getSingleExector().execute(runnable);
        }
    }

    public static void runOnMultiExecutor(Runnable runnable) {
        if (runnable != null) {
            getMultiExector().execute(runnable);
        }
    }

    public static <T> Future<T> submitOnSingleExector(Callable<T> callable) {
        if (callable != null) {
            return getSingleExector().submit(callable);
        }
        return null;
    }

    public static <T> Future<T> submitOnMultiExector(Callable<T> callable) {
        if (callable != null) {
            return getMultiExector().submit(callable);
        }
        return null;
    }

    public static void destroy() {
        if (sSingleExector != null && !sSingleExector.isShutdown()) {
            sSingleExector.shutdown();
            sSingleExector = null;
        }

        if (sMultiExector.isShutdown() && !sMultiExector.isShutdown()) {
            sMultiExector.shutdown();
            sMultiExector = null;
        }
    }
}
