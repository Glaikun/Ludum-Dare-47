package com.glaikunt.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.game.phase.Phase;
import com.glaikunt.game.phase.PhaseOne;
import com.glaikunt.game.player.PlayerActor;

public class LevelOne extends Actor {

    private int currentPhase = 0;
    private Phase activePhase;

    private TickTimer winner = new TickTimer(1);

    public LevelOne(ApplicationResources applicationResources, PlayerActor player, Stage ux) {

        if (currentPhase == 0) {
            this.activePhase = new PhaseOne(applicationResources, player, ux);
            ux.addActor(activePhase);
        }

        winner.setTick(1);
    }

    @Override
    public void act(float delta) {

        if (activePhase.isPhasePassed()) {
            winner.tick(delta);

            if (winner.isTimerEventReady()) {
                Gdx.app.log("DEBUG", "WINNER! WINNER!");
            }
        }
    }
}
