package com.glaikunt.game.phase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.game.collision.SpeechBlockActor;
import com.glaikunt.game.player.PlayerActor;

import java.util.LinkedList;
import java.util.List;

import static com.glaikunt.application.cache.TiledCache.LEVEL_1;

public class PhaseOne extends Phase {

    private ApplicationResources applicationResources;

    private PositionComponent position;

    private BitmapFont futureFont;
    private GlyphLayout word;

    private SpeechBlockActor winningSpeech;
    private List<SpeechBlockActor> wrongSpeech = new LinkedList<>();

    public PhaseOne(ApplicationResources applicationResources, PlayerActor player, Stage uxStage) {

        this.applicationResources = applicationResources;
        TiledMap tiled = applicationResources.getCacheRetriever().getTiledCache().getTiledMapCache(LEVEL_1);
        TiledMapTileLayer speechLayer = (TiledMapTileLayer) tiled.getLayers().get("PhaseOne");
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

        futureFont.draw(batch, word, position.x, position.y);
    }

    @Override
    public boolean isPhasePassed() {
        return winningSpeech.isPlayerOnSpeechBlock();
    }

    @Override
    public boolean isPhaseFailed() {
        return false;
    }
}
