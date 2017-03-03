package com.doing.loader.ui;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.doing.loader.R;

import butterknife.ButterKnife;

public class AsyncActivity extends AppCompatActivity {

    private AsyncTaskLoader<Integer> mLoader;

    public static void start(Context context) {
        context.startActivity(new Intent(context, AsyncActivity.class));
    }

    private static final String TAG = "AsyncActivity";
    public static final int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        mLoader = new AsyncTaskLoader<Integer>(this) {


            private boolean isCanceled = false;

            @Override
            public void cancelLoadInBackground() {
                Log.e(TAG, "这个默认会执行吗？");
                isCanceled = true;
            }

            @Override
            public Integer loadInBackground() {
                int value = 0;
                for(int x = 0; x< 10000 && !isCanceled; x++){
                    for(int y = 0; y < 10000 && !isCanceled; y++){
                        value++;
                    }
                }
                isCanceled = false;
                return value;
            }

            @Override
            public void onCanceled(Integer data) {
                Log.d(TAG, "取消了数据是" + data);
            }
        };
        mLoader.registerListener(LOADER_ID, new Loader.OnLoadCompleteListener<Integer>() {
            @Override
            public void onLoadComplete(Loader<Integer> loader, Integer data) {
                Log.d(TAG, "当前线程"+ Thread.currentThread().getName() + "\t结果数据：" + data);
            }
        });


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loader.cancelLoad();
//            }
//        },1000);

        ButterKnife.findById(this, R.id.AsyncActivity_btn_start)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoader.forceLoad();
                    }
                });

        ButterKnife.findById(this, R.id.AsyncActivity_btn_cancel)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "是否取消成功：" + mLoader.cancelLoad());
                    }
                });

        ButterKnife.findById(this, R.id.AsyncActivity_btn_cancelInBackground)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoader.cancelLoadInBackground();
                    }
                });
    }

}
