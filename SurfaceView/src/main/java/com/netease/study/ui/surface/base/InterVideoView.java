package com.netease.study.ui.surface.base;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface InterVideoView {

    void init(Runnable task);

    Canvas lockCanvas(Rect dirty);

    void unlockCanvasAndPost(Canvas canvas);
}
