package com.doing.canvas.surface.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.doing.canvas.surface.base.Executor;
import com.doing.canvas.surface.executor.HandlerExecutor;
import com.doing.canvas.surface.player.FiniteFramePlayer;
import com.doing.canvas.surface.renderer.AlphaRenderer;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-09.
 */

public class AlphaFrameActivity extends FiniteFrameActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, AlphaFrameActivity.class));
    }

    private final int[] colors = {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.GRAY,
            Color.CYAN,
            Color.YELLOW,
            Color.MAGENTA
    };

    @Override
    protected FiniteFramePlayer createPlayer() {
        AlphaRenderer renderer = new AlphaRenderer(colors, 3);
        Executor executor = new HandlerExecutor();

//        mSurfaceView.setBackgroundColor(Color.BLUE);
        mSurfaceView.setZOrderOnTop(true);

        return new FiniteFramePlayer(executor, renderer, mSurfaceView.getHolder(), renderer.getCount());
    }
}
