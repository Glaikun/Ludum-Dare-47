package com.glaikunt.game.phase;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Phase extends Actor {

    public abstract boolean isPhasePassed();
    public abstract boolean isPhaseFailed();
}
