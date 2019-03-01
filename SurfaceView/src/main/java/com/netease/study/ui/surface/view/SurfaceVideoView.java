package com.netease.study.ui.surface.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.netease.study.ui.surface.base.InterVideoView;

public class SurfaceVideoView implements InterVideoView {

    private final SurfaceHolder mHolder;
    private static final String TAG = "SurfaceVideoView";

    public SurfaceVideoView(SurfaceHolder holder) {
        mHolder = holder;
    }

    @Override
    public void init(final Runnable task) {
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated" + holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (task != null) {
                    task.run();
                }
                Log.d(TAG, String.format("surfaceChanged \t format: %d \t width: %d \t height: %d",
                        format, width, height));
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (task != null) {
                    task.run();
                }
                Log.d(TAG, "surfaceDestroyed" + holder);
            }
        });
    }

    @Override
    public Canvas lockCanvas(Rect dirty) {
        return mHolder.lockCanvas(dirty);
    }

    @Override
    public void unlockCanvasAndPost(Canvas canvas) {
        mHolder.unlockCanvasAndPost(canvas);
    }
}
