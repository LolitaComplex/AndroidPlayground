package com.doing.canvas.surface.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-09.
 */

public class AlphaRenderer extends FiniteFrameRenderer {


    private int[] colors;
    private int repeat;

    public AlphaRenderer(int[] colors, int repeat) {
        this.colors = colors;
        this.repeat = repeat;
    }

    private static final Paint paintClear = new Paint();

    private static final Paint paintAlpha = new Paint();

    static {
        paintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    @Override
    public void render(Canvas canvas, int number) {
        canvas.drawPaint(paintClear);

        canvas.drawPaint(getFramePaint(paintAlpha, number));
    }

    private Paint getFramePaint(Paint paint, int number) {
        paint.setColor(colors[number % colors.length ]);
        paint.setAlpha(128);
        return paint;
    }

    @Override
    public int getCount() {
        return colors.length * repeat;
    }
}
