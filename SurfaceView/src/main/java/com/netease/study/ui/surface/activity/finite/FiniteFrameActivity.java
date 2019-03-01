package com.netease.study.ui.surface.activity.finite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.base.FramePlayer;
import com.netease.study.ui.surface.finite.FiniteFramePlayer;

public abstract class FiniteFrameActivity<T extends View> extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, FramePlayer.Callback {
    private ToggleButton buttonPlay;

    private T videoView;

    private FiniteFramePlayer player;
    protected ViewGroup mContainer;

    protected abstract FiniteFramePlayer onCreatePlayer(T surfaceView);

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        buttonPlay = (ToggleButton) findViewById(R.id.play);
        buttonPlay.setOnCheckedChangeListener(this);
        mContainer = (ViewGroup) findViewById(R.id.video_container);

        findViewById(R.id.stop).setOnClickListener(this);

        findViewById(R.id.fps).setOnClickListener(this);

        findViewById(R.id.forward).setOnClickListener(this);
        findViewById(R.id.backward).setOnClickListener(this);
        ((ToggleButton) findViewById(R.id.loop)).setOnCheckedChangeListener(this);

        ((ToggleButton) findViewById(R.id.set_place_holder)).setOnCheckedChangeListener(this);

        videoView = (T) findViewById(R.id.video_view);

        player = onCreatePlayer(videoView);
        player.setCallback(this);
    }

    protected abstract int getLayoutId();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backward: {
                player.step(true);
                break;
            }
            case R.id.forward: {
                player.step(false);
                break;
            }
            case R.id.stop: {
                player.stop();
                break;
            }
            case R.id.fps: {
                try {
                    int fps = Integer.parseInt(((EditText) findViewById(R.id.fps_text)).getText().toString());
                    player.setFPS(fps);
                } catch (Throwable tr) {
                    tr.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        switch (v.getId()) {
            case R.id.play: {
                if (isChecked) {
                    player.play(false);
                } else {
                    player.pause();
                }
                break;
            }
            case R.id.loop: {
                player.setLoopback(isChecked);
                break;
            }
            case R.id.set_place_holder: {
                findViewById(R.id.place_holder).setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            }
        }
    }

    @Override
    public void onState(FramePlayer.State state) {
        switch (state) {
            case STOPPED: {
                buttonPlay.setChecked(false);
                break;
            }
            case PLAYING: {
                buttonPlay.setChecked(true);
                break;
            }
            case PAUSED: {
                buttonPlay.setChecked(false);
                break;
            }
        }
    }
}
