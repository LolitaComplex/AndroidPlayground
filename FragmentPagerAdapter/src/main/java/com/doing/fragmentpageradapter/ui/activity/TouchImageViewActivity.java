package com.doing.fragmentpageradapter.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doing.fragmentpageradapter.R;

public class TouchImageViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TouchImageViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_image_view);
    }
}
