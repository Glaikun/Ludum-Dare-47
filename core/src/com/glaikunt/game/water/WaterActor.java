package com.glaikunt.game.water;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.application.cache.TextureCache;
import com.glaikunt.ecs.components.AnimationComponent;
import com.glaikunt.ecs.components.PositionComponent;

public class WaterActor extends Actor {

    private TickTimer removeWater = new TickTimer(3);
    private boolean startRemovingWater;

    private AnimationComponent waterAnimation;

    private PositionComponent position;

    public WaterActor(ApplicationResources applicationResources) {

        Entity waterEntity = new Entity();

        this.waterAnimation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.WATER), 9, 5);
        this.waterAnimation.setPlayMode(Animation.PlayMode.LOOP);
        this.position = new PositionComponent(-15, (-waterAnimation.getCurrentFrame().getRegionHeight())+30);

        waterEntity.add(position);
        waterEntity.add(waterAnimation);
        applicationResources.getEngine().addEntity(waterEntity);
    }

    @Override
    public void act(float delta) {

        if (isStartRemovingWater()) {

            position.y -= 15 * delta;
            removeWater.tick(delta);
            if (removeWater.isTimerEventReady()) {
                setStartRemovingWater(false);
            }
        } else if (position.y < 0) {

            position.y += 30 * delta;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 1, 1, .4f);
        batch.draw(waterAnimation.getCurrentFrame(), position.x, position.y);
        batch.setColor(1, 1, 1, 1f);
    }

    public boolean isStartRemovingWater() {
        return startRemovingWater;
    }

    public void setStartRemovingWater(boolean startRemovingWater) {
        this.startRemovingWater = startRemovingWater;
    }
}
