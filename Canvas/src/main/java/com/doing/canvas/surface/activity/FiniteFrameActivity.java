package com.doing.canvas.surface.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.doing.canvas.R;
import com.doing.canvas.surface.player.FiniteFramePlayer;
import com.doing.canvas.surface.player.FramePlayer;


/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public abstract class FiniteFrameActivity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, FramePlayer.Callback {

    private static final String TAG = "FiniteFrameActivity";

    protected EditText mEtFrame;
    protected SurfaceView mSurfaceView;
    protected FiniteFramePlayer mPlayer;
    private View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finityframe);

        findViewById(R.id.FinityFrameActivity_btn_frame).setOnClickListener(this);
        findViewById(R.id.FinityFrameActivity_btn_next).setOnClickListener(this);
        findViewById(R.id.FinityFrameActivity_btn_previous).setOnClickListener(this);
        findViewById(R.id.FinityFrameActivity_btn_stop).setOnClickListener(this);

        ((ToggleButton) findViewById(R.id.FinityFrameActivity_toggle_play)).setOnCheckedChangeListener(this);
        ((ToggleButton) findViewById(R.id.FinityFrameActivity_toggle_loop)).setOnCheckedChangeListener(this);
        ((ToggleButton) findViewById(R.id.FinityFrameActivity_toggle_visible)).setOnCheckedChangeListener(this);

        mView = findViewById(R.id.FinityFrameActivity_visible);

        mEtFrame = ((EditText) findViewById(R.id.FinityFrameActivity_ed_frame));
        mSurfaceView = ((SurfaceView) findViewById(R.id.FinityFrameActivity_surface));

        mPlayer = createPlayer();
        mPlayer.setCallback(this);
    }

    protected abstract FiniteFramePlayer createPlayer();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FinityFrameActivity_btn_stop:
                mPlayer.stop();
                break;
            case R.id.FinityFrameActivity_btn_next:
                mPlayer.step(false);
                break;
            case R.id.FinityFrameActivity_btn_previous:
                mPlayer.step(true);
                break;
            case R.id.FinityFrameActivity_btn_frame:
                mPlayer.setFPS(Integer.parseInt(mEtFrame.getText().toString()));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.FinityFrameActivity_toggle_play:
                if (isChecked) {
                    mPlayer.play(false);
                } else {
                    mPlayer.pause();
                }
                break;
            case R.id.FinityFrameActivity_toggle_loop:
                mPlayer.setLoopback(isChecked);
                break;
            case R.id.FinityFrameActivity_toggle_visible:
                mView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    public void onState(FramePlayer.State state) {
        Log.d(TAG, "onState: FiniteFrameActivity");
    }
}
