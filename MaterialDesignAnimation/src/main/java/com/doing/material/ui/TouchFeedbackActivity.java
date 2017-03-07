package com.doing.material.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doing.material.R;

public class TouchFeedbackActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TouchFeedbackActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_feedback);


    }
}
