package com.glaikunt.game.water;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.TickTimer;
import com.glaikunt.application.cache.TextureCache;
import com.glaikunt.ecs.components.AnimationComponent;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.ecs.components.VelocityComponent;

public class WaterActor extends Actor {

    private TickTimer removeWater = new TickTimer(3);
    private boolean startRemovingWater, pauseWater;

    private AnimationComponent waterAnimation;

    private PositionComponent position;
    private VelocityComponent vel;

    private float drawX, drawY, orbitRadius, rad;

    public WaterActor(ApplicationResources applicationResources) {

        Entity waterEntity = new Entity();

        this.waterAnimation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.WATER), 9, 5);
        this.waterAnimation.setPlayMode(Animation.PlayMode.LOOP);
        this.position = new PositionComponent(-18, (-waterAnimation.getCurrentFrame().getRegionHeight())+120);
        this.vel = new VelocityComponent();
        this.vel.set(8, 8);
        this.orbitRadius = vel.x/2;
        this.rad = 50;

        waterEntity.add(position);
        waterEntity.add(waterAnimation);
        applicationResources.getEngine().addEntity(waterEntity);
    }

    @Override
    public void act(float delta) {

        if (isStartRemovingWater()) {

            position.y -= (vel.x/2) * delta;
            removeWater.tick(delta);
            if (removeWater.isTimerEventReady()) {
                setStartRemovingWater(false);
            }
        } else if (!isPauseWater() && position.y < -15) {

            position.y += vel.x * delta;
        }

        rad += .8f * delta;
        drawX = (float) (orbitRadius * Math.cos(rad));
        drawY = (float) (orbitRadius * Math.sin(rad));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 1, 1, .4f);
        batch.draw(waterAnimation.getCurrentFrame(), position.x + drawX, position.y + drawY);
        batch.setColor(1, 1, 1, 1f);
    }

    public boolean isStartRemovingWater() {
        return startRemovingWater;
    }

    public void setStartRemovingWater(boolean startRemovingWater) {
        this.startRemovingWater = startRemovingWater;
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public float getWidth() {
        return waterAnimation.getCurrentFrame().getRegionWidth();
    }

    @Override
    public float getHeight() {
        return waterAnimation.getCurrentFrame().getRegionHeight();
    }

    public void resetPosition() {
        this.position = new PositionComponent(-18, (-waterAnimation.getCurrentFrame().getRegionHeight())+30);
    }

    public void setPauseWater(boolean pauseWater) {
        this.pauseWater = pauseWater;
    }

    public boolean isPauseWater() {
        return pauseWater;
    }
}
