package com.example.imageloader.data.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    private static OkHttpClient mClient;

    static {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    public static InputStream getImageData(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response execute = mClient.newCall(request).execute();
        InputStream inputStream = execute.body().byteStream();
        return inputStream;
    }
}
