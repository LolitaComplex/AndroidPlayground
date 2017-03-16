package com.doing.aliveservice;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AliveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alive);

        findViewById(R.id.AliveActivity_btn_alarm).setOnClickListener(
            new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    AlarmDoingReceiver.start(AliveActivity.this);
                }
            }
        );

        findViewById(R.id.AliveActivity_btn_alarm_disable).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmDoingReceiver.destory(AliveActivity.this);
                }
            }
        );

        findViewById(R.id.AliveActivity_btn_alarm_alive).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlarmDoingReceiver.alive(AliveActivity.this);
                    }
                }
        );

        findViewById(R.id.AliveActivity_btn_alarm_job).setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        JobManager.schedule(AliveActivity.this);
                    }
                }
        );
    }
}
