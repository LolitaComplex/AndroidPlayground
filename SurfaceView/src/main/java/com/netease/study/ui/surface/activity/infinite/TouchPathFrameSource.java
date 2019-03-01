package com.netease.study.ui.surface.activity.infinite;

import android.view.MotionEvent;
import android.view.View;

import com.netease.study.ui.surface.base.FrameSink;

public class TouchPathFrameSource implements View.OnTouchListener {
    private final FrameSink frameSink;

    public TouchPathFrameSource(FrameSink frameSink) {
        this.frameSink = frameSink;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            if (frameSink != null) {
                frameSink.onFrame(new PathFrame(event.getX(), event.getY()));
            }
        }

        // eat all
        return true;
    }
}
