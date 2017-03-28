package com.doing.architectureencapsulation.net;

import android.app.ActivityManager;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public class TokenInterceptor implements Interceptor {

    private String mToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .addHeader("token", mToken)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
