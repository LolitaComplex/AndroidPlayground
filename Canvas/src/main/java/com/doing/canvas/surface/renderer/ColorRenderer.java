package main.java.com.doing.canvas.surface.renderer;

import android.graphics.Canvas;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public class ColorRenderer extends FiniteFrameRenderer {

    private final int[] colors;
    private final int repeat;

    public ColorRenderer(int[] colors, int repeat) {
        this.colors = colors;
        this.repeat = repeat;
    }

    @Override
    public void render(Canvas canvas, int number) {
        int color = getFrameColor(number);

        canvas.drawColor(color);

        renderFrameNumber(canvas, number, null);
    }

    @Override
    public int getCount() {
        return colors.length * repeat;
    }

    private final int getFrameColor(int num) {
        return colors[num % colors.length];
    }
}
