package com.glaikunt.dialog;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.ecs.components.DelayedTextComponent;
import com.glaikunt.ecs.components.LevelComponent;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.ecs.components.SizeComponent;
import com.glaikunt.ecs.components.TextQueueComponent;
import com.glaikunt.game.GameScreen;

public class DemonActor extends Actor {

    private ApplicationResources applicationResources;
    private Entity demonEntity;

    private PositionComponent position;
    private SizeComponent size;
    private TextQueueComponent textQueueComponent;

    private TickTimer transitionTimer = new TickTimer(5);

    public DemonActor(ApplicationResources applicationResources) {

        this.applicationResources = applicationResources;
        this.demonEntity = new Entity();
        this.size = new SizeComponent(32, 48);
        this.position = new PositionComponent((Gdx.graphics.getWidth()/2f) + (size.x/2), (Gdx.graphics.getHeight()/2f) - size.y);

        this.textQueueComponent = new TextQueueComponent();

        if (applicationResources.getGlobalEntity().getComponent(LevelComponent.class).getCurrentLevel() == 1) {
            DelayedTextComponent text_1 = new DelayedTextComponent();
            text_1.setLayout(new GlyphLayout());
            text_1.setDelay(new TickTimer(.05f));
            text_1.setColour(new Color(1f, .0f, .0f, 1));
            text_1.setTargetWidth(220);
            text_1.setWrap(true);
            text_1.setText("Welcome. This is asndkjansdkjnaskjd");
            text_1.setFont(applicationResources.getCacheRetriever().getFontCache(FontCache.SPEECH_BLOCK_FONT));
            text_1.getLayout().setText(text_1.getFont(), text_1.getDeltaText(), text_1.getColour(), text_1.getTargetWidth(), text_1.getAlign(), text_1.isWrap());

            textQueueComponent.getQueue().add(text_1);
        }
        demonEntity.add(textQueueComponent);
        applicationResources.getEngine().addEntity(demonEntity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        DelayedTextComponent currentText = demonEntity.getComponent(DelayedTextComponent.class);
        if (currentText != null) {
            currentText.getFont().draw(batch, currentText.getLayout(), (Gdx.graphics.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) + (currentText.getLayout().height*2));
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.RED);
        shapes.rect(position.x, position.y, size.x, size.y);
    }

    @Override
    public void act(float delta) {

        DelayedTextComponent currentText = demonEntity.getComponent(DelayedTextComponent.class);
        if (currentText != null && currentText.isFinished()) {

            transitionTimer.tick(delta);
            if (transitionTimer.isTimerEventReady()) {
                applicationResources.getDisplay().setScreen(new GameScreen(applicationResources));
            }
        }
    }
}
