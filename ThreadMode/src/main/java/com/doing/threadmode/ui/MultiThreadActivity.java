package com.doing.threadmode.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.threadmode.R;
import com.doing.threadmode.data.MultiRestaurant;
import com.doing.threadmode.data.SingleRestaurant;

import butterknife.ButterKnife;

public class MultiThreadActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, MultiThreadActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_thread);


        ButterKnife.findById(this, R.id.open)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MultiRestaurant.getRestaurant().open();
                    }
                });

        ButterKnife.findById(this, R.id.close)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MultiRestaurant.getRestaurant().colse();
                    }
                });

        ButterKnife.findById(this, R.id.business)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MultiRestaurant.getRestaurant().openForBussiness();
                    }
                });
    }
}
