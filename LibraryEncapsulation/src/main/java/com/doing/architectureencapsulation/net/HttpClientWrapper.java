package com.doing.architectureencapsulation.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public class HttpClientWrapper {

    private OkHttpClient mOkHttpClient;
    private ExecutorDelivery mExecutorDelivery;

    public HttpClientWrapper(Context context) {
        mOkHttpClient = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        mExecutorDelivery = new ExecutorDelivery(new Handler(Looper.getMainLooper()));
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public ExecutorDelivery getExecutorDelivery() {
        return mExecutorDelivery;
    }
}
