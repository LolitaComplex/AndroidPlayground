package com.doing.architectureencapsulation.net;

import android.text.TextUtils;

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
 * @since 2017-03-16.
 */

public abstract class BaseRequest<T> {

    private final ExecutorDelivery mExecutorDelivery;

    private OkHttpClient mOkHttpClient;
    protected HashMap<String, String> mBodyMap = new HashMap<>();
    protected HashMap<String, String> mQueryMap = new HashMap<>();

    public BaseRequest(HttpClientWrapper clientWrapper) {
        this.mExecutorDelivery = clientWrapper.getExecutorDelivery();
        this.mOkHttpClient = clientWrapper.getOkHttpClient();
    }

    public BaseRequest() {
        this(AppProfile.getHttpClientWrapper());
    }

    public String getHost() {
        return "http://api.example.com";
    }

    public String getUrl() {
        switch (getHttpMethod()) {
            case HttpConstants.POST:
                return getHost() + getApi();
            case HttpConstants.GET:
                String queryString = getQueryString();
                if (!TextUtils.isEmpty(queryString)) {
                    return getHost() + getApi() + "?" + queryString;
                } else {
                    return getHost() + getApi();
                }
        }
        return null;
    }

    public String getQueryString() {
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

    private Request buildReuqest() {
        RequestBody body;
        Request.Builder builder = new Request.Builder()
                .url(getUrl());

        if (HttpConstants.POST == getHttpMethod()) {
            body = getResquestBody();
            builder.post(body);
        }
        return builder.build();
    }

    protected RequestBody getResquestBody() {
        return RequestBody.create(getMediaType(), getRequestString());
    }


    public HttpResponse<T> execute() throws IOException {
        Response response = mOkHttpClient.newCall(buildReuqest()).execute();
        int code = response.code();
        if (code == 200) {
            ResponseBody body = response.body();
            return getResponse(body);
        } else {
            HttpResponse<T> repoonse = new HttpResponse<>();
            repoonse.code = 404;
            repoonse.message = "服务器错误";
            return repoonse;
        }
    }

    private HttpResponse<T> getResponse(ResponseBody body) throws IOException {
        return new GsonBuilder().create().fromJson(body.string(),
                new TypeToken<HttpResponse<T>>(){}.getType());
    }

    public boolean enqueue(final HttpCallback<T> callback) {
        mOkHttpClient.newCall(buildReuqest())
            .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mExecutorDelivery.postFailedResponse(callback, BaseRequest.this, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int code = response.code();
                    HttpResponse<T> httpResponse;
                    if (code == 200) {
                        ResponseBody body = response.body();
                        httpResponse = getResponse(body);
                    } else {
                        httpResponse = new HttpResponse<>();
                        httpResponse.code = 404;
                        httpResponse.message = "服务器错误";
                    }
                    mExecutorDelivery.postSuccessResponse(callback, BaseRequest.this, httpResponse);
                }
            });

        return true;
    }


    public abstract String getApi();

    @HttpConstants.HttpMethodMode
    public abstract int getHttpMethod();

    public abstract String getRequestString();

    public abstract MediaType getMediaType();
}

