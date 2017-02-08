package main.java.com.doing.canvas.surface.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;


import main.java.com.doing.canvas.surface.base.Executor;
import main.java.com.doing.canvas.surface.executor.HandlerExecutor;
import main.java.com.doing.canvas.surface.player.FiniteFramePlayer;
import main.java.com.doing.canvas.surface.renderer.ColorRenderer;
import main.java.com.doing.canvas.surface.renderer.FiniteFrameRenderer;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public class ColorsFrameActivity extends FiniteFrameActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, ColorsFrameActivity.class));
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
        FiniteFrameRenderer renderer = new ColorRenderer(colors, 3);
        Executor executor = new HandlerExecutor();
        return new FiniteFramePlayer(executor, renderer, mSurfaceView.getHolder(), renderer.getCount());
    }
}
