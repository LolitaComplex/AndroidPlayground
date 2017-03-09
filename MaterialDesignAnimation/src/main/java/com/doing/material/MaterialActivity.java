package com.doing.material;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.material.ui.AnimateViewActivity;
import com.doing.material.ui.CurvedMotionActivity;
import com.doing.material.ui.RevealEffectActivity;
import com.doing.material.ui.TouchFeedbackActivity;
import com.doing.material.ui.TransitionActivity;
import com.doing.material.ui.ViewStateChangeActivity;

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

        findViewById(R.id.MaterialActivity_btn_curvedmotion)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CurvedMotionActivity.start(MaterialActivity.this);
                    }
                });

        findViewById(R.id.MaterialActivity_btn_viewstatechange)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewStateChangeActivity.start(MaterialActivity.this);
                    }
                });

        findViewById(R.id.MaterialActivity_btn_animateview)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AnimateViewActivity.start(MaterialActivity.this);
                    }
                });

        findViewById(R.id.MaterialActivity_btn_transition)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TransitionActivity.start(MaterialActivity.this);
                    }
                });


    }
}
