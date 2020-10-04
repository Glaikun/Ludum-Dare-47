package com.glaikunt.dialog;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.application.cache.TextureCache;
import com.glaikunt.ecs.components.AnimationComponent;
import com.glaikunt.ecs.components.DelayedTextComponent;
import com.glaikunt.ecs.components.LevelComponent;
import com.glaikunt.ecs.components.PlayerComponent;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.ecs.components.SizeComponent;
import com.glaikunt.ecs.components.TextQueueComponent;
import com.glaikunt.game.GameScreen;

public class PlayerActor extends Actor {

    private ApplicationResources applicationResources;
    private Entity playerEntity;
    private AnimationComponent playerAnimation;

    private TextQueueComponent textQueueComponent;
    private PlayerComponent player;
    private PositionComponent position;
    private SizeComponent size;
    private TickTimer transitionTimer = new TickTimer(5);

    public PlayerActor(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
        this.playerEntity = new Entity();

        if (applicationResources.getGlobalEntity().getComponent(LevelComponent.class).getCurrentLevel() == 4) {
            this.playerAnimation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.HUMAN_PLAYER), 1, 1);
            this.playerAnimation.setPlaying(false);
            this.playerAnimation.setxFlip(false);
        } else {
            this.playerAnimation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER), 3, 1);
            this.playerAnimation.setPlaying(false);
            this.playerAnimation.setxFlip(false);
        }
        this.size = new SizeComponent(32, 48);
        this.position = new PositionComponent((Gdx.graphics.getWidth()/2f) - playerAnimation.getCurrentFrame().getRegionWidth(), (Gdx.graphics.getHeight()/2f) - playerAnimation.getCurrentFrame().getRegionHeight());
        this.player = new PlayerComponent();

        this.textQueueComponent = new TextQueueComponent();

        if (applicationResources.getGlobalEntity().getComponent(LevelComponent.class).getCurrentLevel() == 2) {
            DelayedTextComponent text_1 = new DelayedTextComponent();
            text_1.setLayout(new GlyphLayout());
            text_1.setDelay(new TickTimer(.05f));
            text_1.setColour(new Color(0f, 1f, .0f, 1));
            text_1.setTargetWidth(220);
            text_1.setWrap(true);
            text_1.setText("F*ck You!");
            text_1.setFont(applicationResources.getCacheRetriever().getFontCache(FontCache.SPEECH_BLOCK_FONT));
            text_1.getLayout().setText(text_1.getFont(), text_1.getDeltaText(), text_1.getColour(), text_1.getTargetWidth(), text_1.getAlign(), text_1.isWrap());

            textQueueComponent.getQueue().add(text_1);
        }

        if (applicationResources.getGlobalEntity().getComponent(LevelComponent.class).getCurrentLevel() == 3) {
            DelayedTextComponent text_1 = new DelayedTextComponent();
            text_1.setLayout(new GlyphLayout());
            text_1.setDelay(new TickTimer(.05f));
            text_1.setColour(new Color(0f, 1f, .0f, 1));
            text_1.setTargetWidth(220);
            text_1.setWrap(true);
            text_1.setText("I'm String A F*ck!");
            text_1.setFont(applicationResources.getCacheRetriever().getFontCache(FontCache.SPEECH_BLOCK_FONT));
            text_1.getLayout().setText(text_1.getFont(), text_1.getDeltaText(), text_1.getColour(), text_1.getTargetWidth(), text_1.getAlign(), text_1.isWrap());

            textQueueComponent.getQueue().add(text_1);
        }



        playerEntity.add(textQueueComponent);

        this.playerEntity.add(position);
        this.playerEntity.add(size);
        this.playerEntity.add(player);
        this.playerEntity.add(playerAnimation);
        this.applicationResources.getEngine().addEntity(playerEntity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(playerAnimation.getCurrentFrame().getTexture(), position.x, position.y,
                playerAnimation.getCurrentFrame().getRegionWidth(), playerAnimation.getCurrentFrame().getRegionHeight(),
                playerAnimation.getCurrentFrame().getRegionX(), playerAnimation.getCurrentFrame().getRegionY(),
                playerAnimation.getCurrentFrame().getRegionWidth(), playerAnimation.getCurrentFrame().getRegionHeight(),
                playerEntity.getComponent(AnimationComponent.class).isxFlip(), playerEntity.getComponent(AnimationComponent.class).isyFlip());

        DelayedTextComponent currentText = playerEntity.getComponent(DelayedTextComponent.class);
        if (currentText != null) {
            currentText.getFont().draw(batch, currentText.getLayout(), position.x-(currentText.getLayout().width/2), (Gdx.graphics.getHeight() / 2f) + (currentText.getLayout().height*2));
        }
    }

    @Override
    public void act(float delta) {

        DelayedTextComponent currentText = playerEntity.getComponent(DelayedTextComponent.class);
        if (currentText != null && currentText.isFinished()) {

            transitionTimer.tick(delta);
            if (transitionTimer.isTimerEventReady()) {
                applicationResources.getDisplay().setScreen(new GameScreen(applicationResources));
            }
        }
    }
}
