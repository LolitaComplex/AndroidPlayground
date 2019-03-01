package com.netease.study.ui.surface.activity.infinite;

import com.netease.study.ui.surface.base.Frame;

public class PathFrame implements Frame {
    private final float x;

    private final float y;

    public PathFrame(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final float getX() {
        return x;
    }

    public final float getY() {
        return y;
    }
}
