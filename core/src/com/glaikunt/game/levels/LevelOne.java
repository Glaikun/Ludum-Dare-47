package com.glaikunt.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.application.cache.TextureCache;
import com.glaikunt.game.phase.Phase;
import com.glaikunt.game.phase.PhaseOne;
import com.glaikunt.game.phase.PhaseThree;
import com.glaikunt.game.phase.PhaseTwo;
import com.glaikunt.game.player.PlayerActor;
import com.glaikunt.game.water.WaterActor;

public class LevelOne extends Actor {

    private ApplicationResources applicationResources;
    private int currentPhase = 0;
    private Phase activePhase, lastPhase;
    private WaterActor water;
    private PlayerActor player;
    private Stage ux;

    private TickTimer winner = new TickTimer(1);

    private TickTimer newPhase = new TickTimer(3);
    private BitmapFont font;
    private GlyphLayout layout, word;

    public LevelOne(ApplicationResources applicationResources, PlayerActor player, Stage ux) {

        this.applicationResources = applicationResources;
        this.player = player;
        this.ux = ux;
        this.font = applicationResources.getCacheRetriever().getFontCache(FontCache.SENTENCE_FONT);
        this.layout = new GlyphLayout();
        this.layout.setText(font, "0", new Color(1f, 1f, 1f, 1f), 0, Align.center, false);
        this.word = new GlyphLayout();
        this.word.setText(font, "Wasn't", new Color(.3f, .8f, 0f, 1f), 0, Align.center, false);
        this.water = new WaterActor(applicationResources);
        this.setCurrentPhase();
        this.winner.setTick(1);
    }

    private void setCurrentPhase() {
        if (currentPhase == 0) {
            this.activePhase = new PhaseOne(applicationResources, player, water, ux, 1);
            this.ux.addActor(activePhase);
            this.ux.addActor(water);
            if (lastPhase != null) {
                this.lastPhase.remove();
            }
            player.resetPosition();
        } else if (currentPhase == 1) {
            this.activePhase = new PhaseTwo(applicationResources, player, water, ux, 1);
            this.ux.addActor(activePhase);
            this.ux.addActor(water);
            this.lastPhase.remove();
            player.resetPosition();
        } else if (currentPhase == 2) {
            this.activePhase = new PhaseThree(applicationResources, player, water, ux, 1);
            this.ux.addActor(activePhase);
            this.ux.addActor(water);
            this.lastPhase.remove();
            player.resetPosition();
        }
    }

    @Override
    public void act(float delta) {

        if (currentPhase >= 3) {

            this.layout.setText(font, "Remember This Word:", new Color(1f, 1f, 1f, 1f), 0, Align.center, false);
        } else if (activePhase == null) {

            newPhase.tick(delta);

            if (newPhase.isTimerEventReady()) {
                setCurrentPhase();
            }
        } else if (activePhase.isPhasePassed()) {

            currentPhase++;
            if (currentPhase <= 2) {

                lastPhase = activePhase;
                activePhase = null;
                water.setStartRemovingWater(true);
            } else {
                water.setPauseWater(true);
                activePhase.setHide(true);
            }
            Gdx.app.log("DEBUG", "WINNER! WINNER! [" + currentPhase + "]");
        } else if (activePhase.isPhaseFailed()) {
            lastPhase = activePhase;
            activePhase.remove();
            water.resetPosition();
            activePhase = null;
            Gdx.app.log("DEBUG", "LOOOSER! LOOSER!");
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (activePhase == null) {
            this.layout.setText(font, Math.round(Math.abs(newPhase.getTargetTime()-newPhase.getTick())) + "", new Color(1f, 1f, 1f, 1f), 0, Align.center, false);
            font.draw(batch, layout, (Gdx.graphics.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) + (layout.height / 2));
        } else if (currentPhase >= 3) {
            font.draw(batch, layout, (Gdx.graphics.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) + (layout.height / 2));
            font.draw(batch, word, (Gdx.graphics.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) - (word.height ));
        }
    }
}
