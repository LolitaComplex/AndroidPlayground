package com.doing.canvas.surface.frame;

import com.doing.canvas.surface.base.Frame;

import java.util.ArrayDeque;
import java.util.Queue;


/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public class FrameManager {

    private final Queue<Frame> factory = new ArrayDeque<>();

    public final boolean produce(Frame frame) {
        factory.offer(frame);

        return true;
    }

    public final Frame consume() {
        return factory.poll();
    }

    public void drain() {
        factory.clear();
    }

    public boolean isEmpty() {
        return factory.isEmpty();
    }
}
