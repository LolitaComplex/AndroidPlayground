package com.dong.view;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dong.lib.utils.LogUtils;
import com.dong.lib.utils.StreamUtil;
import com.dong.lib.utils.UIUtils;
import com.dong.viewcollection.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by 杜营 on 2016/6/24.
 *
 */
public class OkHttpFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_content;
    private TextView tv_title;
    private static final String URL = "http://tool.oschina.net/commons";
    private ScrollView sv_content;

    //应该取消的任务的标记集
    private Set<String> cancelTagList = new HashSet<>();

    private OkHttpClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_okhttp, container, false);

        initView(inflate);
        return inflate;
    }

    private void initView(View view) {
        tv_content = (TextView) view.findViewById(R.id.FragmentOkHttp_tv_content);
        tv_title = (TextView) view.findViewById(R.id.FragmentOkHttp_tv_title);
        sv_content = (ScrollView) view.findViewById(R.id.FragmentOkHttp_sv_content);

        view.findViewById(R.id.FragmentOkHttp_btn_get).setOnClickListener(this);
        view.findViewById(R.id.FragmentOkHttp_btn_post).setOnClickListener(this);
        view.findViewById(R.id.FragmentOkHttp_btn_upload).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        for (String cancelTag : cancelTagList) {
            if (client != null) {
                client.cancel(cancelTag);
            }
        }
        cancelTagList.clear();

        switch (view.getId()) {
            case R.id.FragmentOkHttp_btn_get:
                get();
                break;

            case R.id.FragmentOkHttp_btn_post:
                post();
                break;

            case R.id.FragmentOkHttp_btn_upload:
                upload();
                break;
        }
    }


    private void get() {
        if (client == null)
            client = new OkHttpClient();

        final String cancleType = "GET";
        cancelTagList.add(cancleType);
        final Request request = new Request.Builder().url(URL).tag(cancleType).build();

        Call call = client.newCall(request);

        call.enqueue(new RequestCallback("GET请求"));
    }

    private void post() {
        if (client == null)
            client = new OkHttpClient();

        final String cancelType = "POST";
        cancelTagList.add(cancelType);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("Doing", "鸿洋大神");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "json");

        Request request = new Request.Builder().url(URL).post(builder.build()).tag(cancelType).build();

        client.newCall(request).enqueue(new RequestCallback("Post请求"));

//        一下只是看看这个逻辑具体做了什么事
//        for (int i = 2; i < 997; i++) {
//            String format = String.format("* %s = %s\n", i, factor(i));
//            LogUtils.e("Doing",format);
//        }

    }

//    private String factor(int n) {
//        for (int i = 2; i < n; i++) {
//            int x = n / i;
//            if (x * i == n) return factor(x) + " × " + i;
//        }
//        return Integer.toString(n);
//    }

    private void upload() {
        if (client == null)
            client = new OkHttpClient();

        client.setConnectTimeout(30, TimeUnit.HOURS);
        final String cancelType = "UPLOAD";
        cancelTagList.add(cancelType);
        //把资产目录某文件复制到本地
        AssetManager assets = UIUtils.getContext().getAssets();
        File targetFile = new File(Environment.getExternalStorageDirectory(), "upload.jpg");

        BufferedInputStream bufis = null;
        BufferedOutputStream bufos = null;

        try {
            File parentFile = targetFile.getParentFile();
            if (!parentFile.isDirectory()) {
                parentFile.mkdirs();
            }

            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            bufis = new BufferedInputStream(assets.open("bt9.jpg"));
            bufos = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] buf = new byte[1024 * 8];
            int len;

            while ((len = bufis.read(buf)) != -1) {
                bufos.write(buf, 0, len);
            }

            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), targetFile);
            RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                    .addPart(Headers.of("Content-Disposition", "form-data;name=\"username\""), RequestBody.create(null, "杜营"))
                    .addPart(Headers.of("Content-Disposition", "form-data;name=\"image\";filename=\"upload.jpg\""), fileBody)
                    .build();

            Request request = new Request.Builder().url(URL).post(requestBody).tag(cancelType).build();

            Call call = client.newCall(request);
            call.enqueue(new RequestCallback("UpLoad上传文件"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(bufis);
            StreamUtil.closeStream(bufos);
        }
    }

    private class RequestCallback implements Callback {
        private String titleText;

        public RequestCallback(String titleText) {
            this.titleText = titleText;
        }

        @Override
        public void onFailure(Request request, IOException e) {
            UIUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setTextViewContent(titleText + "失败", titleText);
                    cancelTagList.clear();
                }
            });
        }

        @Override
        public void onResponse(Response response) throws IOException {
            final String htmlStr = response.body().string();
            UIUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setTextViewContent(titleText + "成功", htmlStr);
                    cancelTagList.clear();
                }
            });
        }
    }

    private void setTextViewContent(String titleText, String htmlStr) {
        sv_content.scrollTo(0, 0);
        tv_title.setText(titleText);
        tv_content.setText(htmlStr);
    }

    @Override
    public void onStop() {
        for (String cancelTag : cancelTagList) {
            if (client != null) {
                client.cancel(cancelTag);
            }
        }
        super.onDestroy();
    }
}