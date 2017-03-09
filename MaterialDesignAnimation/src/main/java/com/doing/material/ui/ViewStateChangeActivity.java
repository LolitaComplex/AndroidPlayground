package com.doing.material.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doing.material.R;

public class ViewStateChangeActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewStateChangeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_state_change);


    }
}
