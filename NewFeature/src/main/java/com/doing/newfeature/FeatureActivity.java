package com.doing.newfeature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.doing.newfeature.jellybean.JellyBeanActivity;

public class FeatureActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FeatureActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        findViewById(R.id.FeatureActivity_btn_4x).setOnClickListener(this);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FeatureActivity_btn_4x:
                JellyBeanActivity.start(this);
                break;
            
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent: ");
        super.onNewIntent(intent);
    }
}
