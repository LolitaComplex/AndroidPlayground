package com.doing.material;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.material.ui.RevealEffectActivity;
import com.doing.material.ui.TouchFeedbackActivity;

public class MaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        findViewById(R.id.MaterialActivity_btn_touchfeedback)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TouchFeedbackActivity.start(MaterialActivity.this);
                    }
                });

        findViewById(R.id.MaterialActivity_btn_revealeffect)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RevealEffectActivity.start(MaterialActivity.this);
                    }
                });
    }
}
