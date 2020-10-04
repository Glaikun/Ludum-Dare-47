package com.glaikunt.game.phase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.game.collision.SpeechBlockActor;
import com.glaikunt.game.player.PlayerActor;
import com.glaikunt.game.water.WaterActor;

import java.util.LinkedList;
import java.util.List;

import static com.glaikunt.application.cache.TiledCache.LEVEL_;
import static com.glaikunt.application.cache.TiledCache.SUFFIX_TMX;

public class PhaseThree extends Phase {

    private ApplicationResources applicationResources;
    private TiledMapRenderer tiledMapRenderer;

    private PositionComponent position;

    private BitmapFont futureFont;
    private GlyphLayout word;

    private SpeechBlockActor winningSpeech;
    private List<SpeechBlockActor> wrongSpeech = new LinkedList<>();

    private TickTimer lockedInTimer = new TickTimer(5), winnerLockedInTimer = new TickTimer(2);

    public PhaseThree(ApplicationResources applicationResources, PlayerActor player, WaterActor water, Stage uxStage, int currentLevel) {
        super(applicationResources, player, water, uxStage, currentLevel);

        this.applicationResources = applicationResources;
        TiledMap tiled = applicationResources.getCacheRetriever().getTiledCache().getTiledMapCache(LEVEL_ + currentLevel + SUFFIX_TMX);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiled);
        TiledMapTileLayer speechLayer = (TiledMapTileLayer) tiled.getLayers().get("PhaseThree");
        for (int y = speechLayer.getHeight(); y >= 0; y--) {
            float yPos = (y * speechLayer.getTileHeight());
            for (int x = 0; x < speechLayer.getWidth(); x++) {
                float xPos = (x * speechLayer.getTileWidth());
                TiledMapTileLayer.Cell cell = speechLayer.getCell(x, y);
                if (cell == null) continue;

                String[] vals = ((String) speechLayer.getProperties().get(x + ";" + Math.abs((speechLayer.getHeight()-1)-y))).split(";");
                boolean winner = Boolean.valueOf(vals[1]);
                Gdx.app.log("DEBUG", "Word: " + vals[0] + " (" + x + ";" + Math.abs((speechLayer.getHeight()-1)-y) + ")");
                Vector2 pos = new Vector2(xPos, yPos);
                SpeechBlockActor blockActor = new SpeechBlockActor(applicationResources, player, vals[0] != null ? vals[0] : "", pos.x+16f, pos.y + (16f));
                if (winner) {
                    this.winningSpeech = blockActor;
                } else {
                    this.wrongSpeech.add(blockActor);
                }
                uxStage.addActor(blockActor);
            }
        }

        this.futureFont = applicationResources.getCacheRetriever().getFontCache(FontCache.SENTENCE_FONT);
        this.word = new GlyphLayout();
        this.word.setText(futureFont, (String) speechLayer.getProperties().get("Sentence"), new Color(1f, 1f, 1f, 1f), 0, Align.center, false);
        this.position = new PositionComponent((Gdx.graphics.getWidth()/2f) + (futureFont.getXHeight()/2), 25 + (word.height*2));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (!isHide()) {
            futureFont.draw(batch, word, position.x, (water.getY() + water.getHeight()) - (50 + (word.height * 2)));
        }
        tiledMapRenderer.render();

    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        drawLoading(shapes, winningSpeech, wrongSpeech, lockedInTimer, winnerLockedInTimer);
    }

    @Override
    public void act(float delta) {

        tiledMapRenderer.setView((OrthographicCamera) uxStage.getCamera());

        if (isHide()) return;

        boolean isSpeechCollidingPlayer = false;
        if (winningSpeech.isPlayerOnSpeechBlock()) {
            isSpeechCollidingPlayer = true;
        }
        for (SpeechBlockActor speechBlockActor : wrongSpeech) {
            if (speechBlockActor.isPlayerOnSpeechBlock()) {
                isSpeechCollidingPlayer = true;
            }
        }

        if (!isSpeechCollidingPlayer && (lockedInTimer.getTick() > 0 || winnerLockedInTimer.getTick() > 0)){
            lockedInTimer.setTick(0);
            winnerLockedInTimer.setTick(0);
        }
    }

    @Override
    public boolean isPhasePassed() {
        if (isHide()) return false;

        if (winningSpeech.isPlayerOnSpeechBlock()) {
            winnerLockedInTimer.tick(Gdx.graphics.getDeltaTime());
            return winnerLockedInTimer.isTimerEventReady();
        }
        return false;
    }

    @Override
    public boolean isPhaseFailed() {
        if (isHide()) return false;

        for (SpeechBlockActor speechBlockActor : wrongSpeech) {
            if (speechBlockActor.isPlayerOnSpeechBlock()) {
                lockedInTimer.tick(Gdx.graphics.getDeltaTime());
                return lockedInTimer.isTimerEventReady();
            }
        }
        return false;
    }

    @Override
    public boolean remove() {
        winningSpeech.remove();
        for (SpeechBlockActor speechBlockActor : wrongSpeech) {
            speechBlockActor.remove();
        }
        return super.remove();
    }
}
