package com.doing.launchmode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
    }

    public void Start(View view) {
//        Intent.FLAG_ACTIVITY_MULTIPLE_TASK

//        intent.setFlags()
        startActivity(new Intent(this,ActivityB.class));
    }


}
