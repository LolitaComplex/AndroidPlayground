package com.doing.architectureencapsulation.net.test;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-15.
 */

public class OkHttpConfig {

    private static OkHttpClient sOkHttpClient = null;

    static {
        initOkHttpClient();
    }

    private static void initOkHttpClient() {
        sOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getInstance() {
        return sOkHttpClient;
    }

}
