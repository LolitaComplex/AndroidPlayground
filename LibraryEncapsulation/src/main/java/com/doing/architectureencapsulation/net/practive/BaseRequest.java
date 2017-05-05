package com.doing.architectureencapsulation.net.practive;

import android.text.TextUtils;

import com.doing.architectureencapsulation.net.AppProfile;
import com.doing.architectureencapsulation.net.ExecutorDelivery;
import com.doing.architectureencapsulation.net.HttpCallback;
import com.doing.architectureencapsulation.net.HttpClientWrapper;
import com.doing.architectureencapsulation.net.HttpConstants;
import com.doing.architectureencapsulation.net.HttpResponse;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-24.
 */

public abstract class BaseRequest<T> {


    private ExecutorDelivery mExecutorDelivery;
    private OkHttpClient mOkHttpClient;

    protected HashMap<String, String> mBodyMap = new HashMap<>();
    protected HashMap<String, String> mQueryMap = new HashMap<>();

    public BaseRequest(HttpClientWrapper wrapper) {
        mExecutorDelivery = wrapper.getExecutorDelivery();
        mOkHttpClient = wrapper.getOkHttpClient();
    }

    public BaseRequest() {
        this(AppProfile.getHttpClientWrapper());
    }

    public void enqueue(final HttpCallback<T> callback) {
        mOkHttpClient.newCall(buildRequest())
            .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mExecutorDelivery.postFailedResponse(callback, null, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    HttpResponse<T> httpResponse;
                    if (response.code() == 200) {
                        ResponseBody body = response.body();
                        httpResponse = getHttpResponse(body.string());
                    } else {
                        httpResponse = new HttpResponse<>();
                        httpResponse.code = 404;
                        httpResponse.message = "服务器异常";
                    }
                    mExecutorDelivery.postSuccessResponse(callback, null, httpResponse);
                }
            });
    }

    private HttpResponse<T> getHttpResponse(String body){
        return new GsonBuilder().create().fromJson(body,
                new TypeToken<HttpResponse<T>>(){}.getType());
    }

    private Request buildRequest() {
        RequestBody body;
        Request.Builder builder = new Request.Builder()
                .url(getUrl());

        if (HttpConstants.POST == getHttpMethod()) {
            body = getRequestBody();
            builder.post(body);
        }

        return builder.build();
    }

    private RequestBody getRequestBody() {
        return RequestBody.create(getMediaType(), getRequestSting());
    }

    private String getUrl() {
        switch (getHttpMethod()) {
            case HttpConstants.POST:
                return getHost() + getApi();
            case HttpConstants.GET:
                String queryString = getQueryString();
                if (TextUtils.isEmpty(queryString)) {
                    return getHost() + getApi();
                } else {
                    return getHost() + getApi() + "?" + queryString;
                }
        }

        return null;
    }

    private String getQueryString() {
        if (mQueryMap != null && mQueryMap.size() > 0) {
            return encodeParameters(mQueryMap, "utf-8");
        }
        return "";
    }

    protected String encodeParameters(HashMap<String, String> params, String encode) {
        try {
            if (params != null && params.size() > 0) {
                final StringBuilder encodedParams = new StringBuilder();
                int index = 0;
                for (Map.Entry<String, String> entry : params.entrySet()){
                    if (TextUtils.isEmpty(entry.getKey()) || TextUtils.isEmpty(entry.getValue())) {
                        continue;
                    }
                    if (index > 0) {
                        encodedParams.append("&");
                    }
                    encodedParams.append(URLEncoder.encode(entry.getKey(), encode));
                    encodedParams.append("=");
                    encodedParams.append(URLEncoder.encode(entry.getValue(), encode));
                    index++;
                }
                return encodedParams.toString();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String getHost() {
        return "http:159.1.23.41";
    }

    public abstract String getApi();

    public abstract int getHttpMethod();

    public abstract String getRequestSting();

    public abstract MediaType getMediaType();


}
