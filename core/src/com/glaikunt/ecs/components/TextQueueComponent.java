package com.glaikunt.ecs.components;

import com.badlogic.ashley.core.Component;
import com.glaikunt.application.TickTimer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TextQueueComponent implements Component {

    private final BlockingQueue<TextComponent> queue = new LinkedBlockingQueue<>();
    private TickTimer swapDelay;

    public BlockingQueue<TextComponent> getQueue() {
        return queue;
    }

    public TickTimer getSwapDelay() {
        return swapDelay;
    }

    public void setSwapDelay(TickTimer swapDelay) {
        this.swapDelay = swapDelay;
    }
}
