package com.doing.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by Administrator on 2016/11/30.
 */
public class ActivityD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);

    }

    public void Start(View view) {
        startActivity(new Intent(this, ActivityB.class));
    }
}
