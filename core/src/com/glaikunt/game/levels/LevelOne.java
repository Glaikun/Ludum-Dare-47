package com.glaikunt.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.game.phase.Phase;
import com.glaikunt.game.phase.PhaseOne;
import com.glaikunt.game.player.PlayerActor;
import com.glaikunt.game.water.WaterActor;

public class LevelOne extends Actor {

    private ApplicationResources applicationResources;
    private int currentPhase = 0;
    private Phase activePhase;
    private WaterActor water;
    private PlayerActor player;
    private Stage ux;

    private TickTimer winner = new TickTimer(1);

    private TickTimer newPhase = new TickTimer(3);
    private BitmapFont font;
    private GlyphLayout layout;

    public LevelOne(ApplicationResources applicationResources, PlayerActor player, Stage ux) {

        this.applicationResources = applicationResources;
        this.player = player;
        this.ux = ux;
        this.font = applicationResources.getCacheRetriever().getFontCache(FontCache.SENTENCE_FONT);
        this.layout = new GlyphLayout();
        this.layout.setText(font, "0", new Color(1f, 1f, 1f, 1f), 0, Align.center, false);
        this.water = new WaterActor(applicationResources);
        this.setCurrentPhase();
        this.winner.setTick(1);


    }

    private void setCurrentPhase() {
        if (currentPhase == 0) {
            this.activePhase = new PhaseOne(applicationResources, player, water, ux, 1);
            ux.addActor(activePhase);

            this.water = new WaterActor(applicationResources);
            this.ux.addActor(water);
        }
    }

    @Override
    public void act(float delta) {

        if (activePhase == null) {

            newPhase.tick(delta);

            if (newPhase.isTimerEventReady()) {
                setCurrentPhase();
            }
        } else if (activePhase.isPhasePassed()) {
            activePhase.remove();
            activePhase = null;
            currentPhase++;
            water.setStartRemovingWater(true);

            Gdx.app.log("DEBUG", "WINNER! WINNER!");
        } else if (activePhase.isPhaseFailed()) {
            activePhase.remove();
            water.remove();
            activePhase = null;

            Gdx.app.log("DEBUG", "LOOOSER! LOOSER!");
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (activePhase == null) {
            this.layout.setText(font, Math.round(Math.abs(newPhase.getTargetTime()-newPhase.getTick())) + "", new Color(1f, 1f, 1f, 1f), 0, Align.center, false);
            font.draw(batch, layout, (Gdx.graphics.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) + (layout.height / 2));
        }
    }
}
