package com.glaikunt.game.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.Rectangle;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.ecs.components.SizeComponent;
import com.glaikunt.ecs.components.SpeechBlockComponent;
import com.glaikunt.game.player.PlayerActor;

public class SpeechBlockActor extends Actor {

    private ApplicationResources applicationResources;
    private Entity speechBlockEntity;

    private PositionComponent position;
    private SizeComponent size;

    private BitmapFont futureFont;
    private GlyphLayout word;

    private PlayerActor player;
    private Rectangle playerRect, speechBlockRect;

    public SpeechBlockActor(ApplicationResources applicationResources, PlayerActor player, String text, float xPos, float yPos) {

        this.applicationResources = applicationResources;
        this.speechBlockEntity = new Entity();
        this.futureFont = this.applicationResources.getCacheRetriever().getFontCache(FontCache.SPEECH_BLOCK_FONT);
        this.word = new GlyphLayout();
        this.word.setText(futureFont, text, new Color(1f, 1f, 1f, 1f), 0, Align.center, false);
        this.position = new PositionComponent(xPos + (futureFont.getXHeight()/2), yPos + (word.height*2));
        this.size = new SizeComponent(futureFont.getXHeight(), word.height);

        this.player = player;
        this.playerRect = new Rectangle();
        this.speechBlockRect = new Rectangle();

        this.playerRect.set(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        this.speechBlockRect.set(position.x - (word.width/2), position.y - word.height, word.width, word.height);

        this.speechBlockEntity.add(position);
        this.speechBlockEntity.add(new SpeechBlockComponent());
        applicationResources.getEngine().addEntity(speechBlockEntity);
    }

    @Override
    public void act(float delta) {

        this.playerRect.set(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

//    @Override
//    public void drawDebug(ShapeRenderer shapes) {
//        shapes.setColor(Color.BLUE);
//        shapes.rect(speechBlockRect.x, speechBlockRect.y, speechBlockRect.width, speechBlockRect.height);
//        shapes.setColor(Color.RED);
//        shapes.rect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        futureFont.draw(batch, word, position.x, position.y);
    }

    public boolean isPlayerOnSpeechBlock() {
        return playerRect.intersects(speechBlockRect);
    }
}
