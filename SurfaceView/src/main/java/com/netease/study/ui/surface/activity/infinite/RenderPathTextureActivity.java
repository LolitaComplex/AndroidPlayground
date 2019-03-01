package com.netease.study.ui.surface.activity.infinite;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceView;
import android.view.TextureView;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.activity.infinite.renderer.PathRenderer;
import com.netease.study.ui.surface.base.FrameRenderer;
import com.netease.study.ui.surface.base.PlayExecutor;
import com.netease.study.ui.surface.exec.HandlerExecutor;
import com.netease.study.ui.surface.infinite.InfiniteFramePlayer;
import com.netease.study.ui.surface.view.SurfaceVideoView;
import com.netease.study.ui.surface.view.TextureVideoView;

public class RenderPathTextureActivity extends InfiniteFrameActivity<TextureView> {
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected InfiniteFramePlayer onCreatePlayer(TextureView textureView) {
        FrameRenderer renderer = new PathRenderer();

        HandlerThread thread = new HandlerThread("render");
        thread.start();
        PlayExecutor executor = new HandlerExecutor(new Handler(thread.getLooper()));

        InfiniteFramePlayer player = new InfiniteFramePlayer(
                new TextureVideoView(textureView), renderer, executor);

        textureView.setOnTouchListener(new TouchPathFrameSource(player.getFrameSink()));

        return player;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_infinite_frame_texture;
    }
}
