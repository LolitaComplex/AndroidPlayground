package com.netease.study.ui.surface.finite;

import com.netease.study.ui.surface.base.Frame;

public class FiniteFrameGenerator {
    // total frame count
    private final int count;

    // current frame number
    private int number = -1;

    public FiniteFrameGenerator(int count) {
        this.count = count;
    }

    public final Frame next(boolean backward, boolean loopback) {
        int num = next(number, count, backward, loopback);
        if (num == -1) {
            return null;
        }

        number = num;

        return new FiniteFrame(num);
    }

    public final void stop() {
        number = -1;
    }

    private static int next(int index, int count, boolean backward, boolean loopback) {
        if (index == -1) {
            return backward ? (loopback ? count - 1 : -1) : 0;
        } else {
            index += backward ? -1 : 1;
            if (index < 0 || index >= count) {
                index = loopback ? (index + count) % count : -1;
            }
            return index;
        }
    }
}
