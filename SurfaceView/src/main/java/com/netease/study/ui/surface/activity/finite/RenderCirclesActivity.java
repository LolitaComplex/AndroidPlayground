package com.netease.study.ui.surface.activity.finite;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceView;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.activity.finite.renderer.CirclesRenderer;
import com.netease.study.ui.surface.base.PlayExecutor;
import com.netease.study.ui.surface.exec.HandlerExecutor;
import com.netease.study.ui.surface.finite.FiniteFramePlayer;
import com.netease.study.ui.surface.finite.FiniteFrameRenderer;
import com.netease.study.ui.surface.view.SurfaceVideoView;

public class RenderCirclesActivity extends FiniteFrameActivity<SurfaceView> {
    @Override
    protected FiniteFramePlayer onCreatePlayer(SurfaceView surfaceView) {
        FiniteFrameRenderer renderer = new CirclesRenderer(10, 72);

        HandlerThread thread = new HandlerThread("render");
        thread.start();
        PlayExecutor executor = new HandlerExecutor(new Handler(thread.getLooper()));

        return new FiniteFramePlayer(new SurfaceVideoView(surfaceView.getHolder()),
                renderer, executor, renderer.getCount());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finite_frame_surface;
    }
}
