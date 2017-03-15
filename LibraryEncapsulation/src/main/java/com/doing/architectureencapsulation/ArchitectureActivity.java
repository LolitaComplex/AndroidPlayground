package com.doing.architectureencapsulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.doing.architectureencapsulation.image.ImageActivity;
import com.doing.architectureencapsulation.net.test.OkHttpTestActivity;

public class ArchitectureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architecture);

        findViewById(R.id.activity_btn_image).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageActivity.start(ArchitectureActivity.this);
                }
            }
        );

        findViewById(R.id.activity_btn_okhttp).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpTestActivity.start(ArchitectureActivity.this);
                }
            }
        );

    }
}
