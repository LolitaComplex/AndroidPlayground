package com.doing.threadmode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.doing.threadmode.ui.MultiThreadActivity;
import com.doing.threadmode.ui.SingleThreadActivity;

import butterknife.ButterKnife;

public class ThreadModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_mode);

        ButterKnife.findById(this,R.id.ThreadModeActivity_btn_single).
                setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleThreadActivity.start(ThreadModeActivity.this);
            }
        });

        ButterKnife.findById(this,R.id.ThreadModeActivity_btn_multi)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MultiThreadActivity.start(ThreadModeActivity.this);
                    }
                });
    }

}
