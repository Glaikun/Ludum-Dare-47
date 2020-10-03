package com.glaikunt.game.phase;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.game.player.PlayerActor;
import com.glaikunt.game.water.WaterActor;

public abstract class Phase extends Actor {

    protected PlayerActor player;
    protected WaterActor water;
    protected Stage uxStage;
    protected int currentLevel;

    public Phase(ApplicationResources applicationResources, PlayerActor player, WaterActor water, Stage uxStage, int currentLevel) {

        this.player = player;
        this.water = water;
        this.uxStage = uxStage;
        this.currentLevel = currentLevel;
    }

    public PlayerActor getPlayer() {
        return player;
    }

    public WaterActor getWater() {
        return water;
    }

    public Stage getUxStage() {
        return uxStage;
    }

    public abstract boolean isPhasePassed();
    public abstract boolean isPhaseFailed();
}
