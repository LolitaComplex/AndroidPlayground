package com.doing.architectureencapsulation.net.test;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.doing.architectureencapsulation.R;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OkHttpTestActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.activity_btn_get)
    Button mActivityBtnGet;
    @BindView(R.id.activity_btn_post)
    Button mActivityBtnPost;
    @BindView(R.id.activity_tv_show)
    TextView mActivityTvShow;

    public static void start(Context context) {
        context.startActivity(new Intent(context, OkHttpTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);
        ButterKnife.bind(this);

        mActivityBtnGet.setOnClickListener(this);
        mActivityBtnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        doTask(this, id);
    }

    private void doTask(final OkHttpTestActivity own, final int id) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                switch (id) {
                    case R.id.activity_btn_get:
                        return own.doGet();
                    default:
                        return own.doPost();
                }
            }

            @Override
            protected void onPostExecute(String response) {
                mActivityTvShow.setText(response);
            }
        }.execute();
    }


    public String doGet() {
        Request request = new Request.Builder()
                .url("http://httpbin.org/get")
                .addHeader("User-Agent", "OkHttpDemo/1.0")
                .addHeader("key1","aaa")
                .header("key2", "bbb")
                .build();
        try {
            Response response = OkHttpConfig.getInstance().newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "网络错误";
        }
        return "服务器异常";
    }

    private String doPost() {
        //自定义RequestBody
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/plain");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                InputStream in = getResources().openRawResource(R.raw.test);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        sink.write(buf, 0, len);
                    }
                    sink.flush();
                } finally {
                    in.close();
                }
            }
        };

        //MultipartBody的构建
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addPart(RequestBody.create(MediaType.parse("text/html"), "<a>咖喱Gigi</a>"))
                .addPart(requestBody)
                .build();

        //FormDataBody的构建
        new FormBody.Builder().add("name", "key").build();


        Request request = new Request.Builder()
                .url("http://httpbin.org/post")
                .post(multipartBody)
                .addHeader("User-Agent", "OkHttpDemo/1.0")
                .build();

        try {
            Response response = OkHttpConfig.getInstance().newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "网络错误";
        }
        return "服务器异常";
    }
}
