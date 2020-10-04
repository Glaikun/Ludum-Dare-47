package com.glaikunt.game.phase;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.game.collision.SpeechBlockActor;
import com.glaikunt.game.player.PlayerActor;
import com.glaikunt.game.water.WaterActor;

import java.util.List;

public abstract class Phase extends Actor {

    protected PlayerActor player;
    protected WaterActor water;
    protected Stage uxStage;
    protected int currentLevel;
    private boolean hide;

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

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    protected void drawLoading(ShapeRenderer shapes, SpeechBlockActor winningSpeech, List<SpeechBlockActor> wrongSpeech , TickTimer lockedInTimer, TickTimer winnerLockedInTimer) {
        shapes.set(ShapeRenderer.ShapeType.Filled);

        if (winningSpeech.isPlayerOnSpeechBlock()) {
            shapes.setColor(new Color(0, 1, 0, .5f));
            shapes.rect(winningSpeech.getX(), winningSpeech.getY(), (winningSpeech.getWidth()*winnerLockedInTimer.getTick())/winnerLockedInTimer.getTargetTime(), winningSpeech.getHeight());
        }

        for (SpeechBlockActor speechBlockActor : wrongSpeech) {
            if (speechBlockActor.isPlayerOnSpeechBlock()) {
                shapes.setColor(new Color(1, 0, 0, .5f));
                shapes.rect(speechBlockActor.getX(), speechBlockActor.getY(), (speechBlockActor.getWidth()*lockedInTimer.getTick())/lockedInTimer.getTargetTime(), speechBlockActor.getHeight());
            }
        }
    }

    public abstract boolean isPhasePassed();
    public abstract boolean isPhaseFailed();

}
