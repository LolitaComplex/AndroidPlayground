package com.netease.study.ui.surface.activity.infinite;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.base.FramePlayer;
import com.netease.study.ui.surface.infinite.InfiniteFramePlayer;

public abstract class InfiniteFrameActivity<T extends View> extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, FramePlayer.Callback {
    private ToggleButton buttonPlay;

    private InfiniteFramePlayer player;

    protected abstract InfiniteFramePlayer onCreatePlayer(T surfaceView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        buttonPlay = (ToggleButton) findViewById(R.id.play);
        buttonPlay.setOnCheckedChangeListener(this);

        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.fps).setOnClickListener(this);

        T surfaceView = (T) findViewById(R.id.video_view);

        player = onCreatePlayer(surfaceView);
        player.setCallback(this);
    }

    protected abstract int getLayoutId();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                    player.play();
                } else {
                    player.pause();
                }
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
