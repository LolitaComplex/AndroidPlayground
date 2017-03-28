package com.doing.architectureencapsulation.net;

import android.content.Context;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */
public class AppProfile {

    private static Context sApplicationContext;
    private static HttpClientWrapper sHttpClientWrapper;

    public static HttpClientWrapper getHttpClientWrapper() {
        return sHttpClientWrapper;
    }

    public static Context getContext() {
        return sApplicationContext;
    }

    public static void init(Context context) {
        sApplicationContext = context;
        sHttpClientWrapper = new HttpClientWrapper(context);
    }



}
