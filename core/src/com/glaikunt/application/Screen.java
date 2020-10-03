package com.glaikunt.application;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.glaikunt.Display;
import com.glaikunt.application.cache.CacheRetriever;

import java.util.Arrays;
import java.util.List;


public abstract class Screen implements com.badlogic.gdx.Screen {

    private ApplicationResources applicationResources;
    private Stage front, background, ux;

    public Screen(ApplicationResources applicationResources) {
        this.front = new Stage(new ScalingViewport(Scaling.stretch, Display.WORLD_WIDTH, Display.WORLD_HEIGHT));
        ((OrthographicCamera) this.front.getCamera()).setToOrtho(false);
        this.front.setDebugAll(true);
        this.background = new Stage(new ScalingViewport(Scaling.stretch, Display.WORLD_WIDTH, Display.WORLD_HEIGHT));
        ((OrthographicCamera) this.background.getCamera()).setToOrtho(false);
        this.ux = new Stage(new ScalingViewport(Scaling.stretch, Display.WORLD_WIDTH, Display.WORLD_HEIGHT));
        ((OrthographicCamera) this.ux.getCamera()).setToOrtho(false);
        this.ux.setDebugAll(true);
        this.applicationResources = applicationResources;
    }

    @Override
    public void resize(int width, int height) {
        getFront().getViewport().update(width, height, true);
        getBackground().getViewport().update(width, height, true);
        getUX().getViewport().update(width, height, true);
        getFront().getCamera().update();
        getBackground().getCamera().update();
        getUX().getCamera().update();

        Gdx.app.log(logDEBUG(), "Width: " + Gdx.graphics.getWidth() );
        Gdx.app.log(logDEBUG(), "Height: " + Gdx.graphics.getHeight() );
        Gdx.app.log(logDEBUG(), "worldWidth: " + Display.WORLD_WIDTH );
        Gdx.app.log(logDEBUG(), "worldHeight: " + Display.WORLD_HEIGHT );
    }

    @Override
    public void render(float delta) {

    }

    protected String logDEBUG() {
        return "DEBUG";
    }

    @Override
    public void hide() {

        getEngine().removeAllEntities();
        for (Actor actor : getFront().getActors()) {
            actor.clear();
        }
        getFront().clear();
        for (Actor actor : getBackground().getActors()) {
            actor.clear();
        }
        getBackground().clear();
        for (Actor actor : getUX().getActors()) {
            actor.clear();
        }
        getUX().clear();
    }

    @Override
    public void dispose() {

        getFront().dispose();
        getBackground().dispose();
        getUX().dispose();
        getEngine().removeAllEntities();
    }

    protected ApplicationResources getApplicationResources() {
        return applicationResources;
    }

    protected Engine getEngine() {
        return getApplicationResources().getEngine();
    }

    protected CacheRetriever getCacheRetriever() {
        return getApplicationResources().getCacheRetriever();
    }

    protected Display getDisplay() {
        return applicationResources.getDisplay();
    }

    public Stage getFront() {
        return front;
    }

    public Stage getBackground() {
        return background;
    }

    public Stage getUX() {
        return ux;
    }

    public List<OrthographicCamera> getCameras() {
        return Arrays.asList((OrthographicCamera) front.getCamera(), (OrthographicCamera) background.getCamera());
    }
}
