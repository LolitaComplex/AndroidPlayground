package com.doing.newfeature.jellybean;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.newfeature.FeatureActivity;
import com.doing.newfeature.R;
import com.doing.newfeature.jellybean.parentactivity.TargetActivity;

public class JellyBeanActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        context.startActivity(new Intent(context, JellyBeanActivity.class));
    }

    public static JellyBeanActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jelly_bean);

        instance = this;

        findViewById(R.id.JellyBeanActivity_btn_parentActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.JellyBeanActivity_btn_parentActivity:
                TargetActivity.start(this);
                break;
        }
    }
}
