package com.doing.canvas.surface.frame;


import com.doing.canvas.surface.base.Frame;
import com.doing.canvas.surface.frame.FiniteFrame;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public class FiniteFrameGenerator {

    private final int count;

    private int number = -1;

    public FiniteFrameGenerator(int count) {
        this.count = count;
    }

    public final Frame next(boolean backword, boolean loopback) {
        int num = next(number, count, backword, loopback);
        if (num == -1) {
            return null;
        }
        number = num;

        return new FiniteFrame(num);
    }

    private int next(int index, int count, boolean backword, boolean loopback) {
        if (index == -1) {
            return backword ? (loopback ? count - 1 : -1) : 0;
        } else {
            index += backword ? -1 : 1;
            if (index < 0 || index >= count) {
                index = loopback ? (index + count) % count : -1;
            }
            return index;
        }
    }

    public void stop() {
        number = -1;
    }
}
