package com.doing.runtimepermisstion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.runtimepermisstion.ui.DefaultPermissionActivity;

public class RuntimePermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);

        findViewById(R.id.RuntimePermissionActivity_default)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultPermissionActivity.start(
                        RuntimePermissionActivity.this);
            }
        });
    }
}
