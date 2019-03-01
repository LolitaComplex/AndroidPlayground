package com.netease.study.ui.surface.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

import com.netease.study.ui.surface.base.InterVideoView;


public class TextureVideoView implements InterVideoView {

    private final TextureView mTextureView;
    private static final String TAG = "TextureVideoView";

    public TextureVideoView(TextureView textureView) {
        mTextureView = textureView;
    }

    @Override
    public void init(Runnable task) {
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Log.d(TAG, String.format("onSurfaceTextureAvailable \t width: %d \t height: %d",
                        width, height));
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                Log.d(TAG,  String.format("onSurfaceTextureSizeChanged \t width: %d \t height: %d",
                        width, height));
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Log.d(TAG, "onSurfaceTextureDestroyed: " + surface);
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                Log.d(TAG, "onSurfaceTextureUpdated: " + surface);
            }
        });
    }

    @Override
    public Canvas lockCanvas(Rect dirty) {
        return mTextureView.lockCanvas(dirty);
    }

    @Override
    public void unlockCanvasAndPost(Canvas canvas) {
        mTextureView.unlockCanvasAndPost(canvas);
    }
}
