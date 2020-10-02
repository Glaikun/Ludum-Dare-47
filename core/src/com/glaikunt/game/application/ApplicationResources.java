package com.glaikunt.game.application;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.glaikunt.game.Display;
import com.glaikunt.game.application.cache.CacheRetriever;

public class ApplicationResources {

    private final Engine engine = new Engine();
    private final Entity immutableGlobalEntity = new Entity();
    private final Entity globalEntity = new Entity();
    private final CacheRetriever cacheRetriever = new CacheRetriever();
    private final Vector3 frontStageMousePosition = new Vector3();
    private final Vector3 uxStageMousePosition = new Vector3();
    private Display display;

    public ApplicationResources(Display display) {
        this.display = display;
    }

    public Engine getEngine() {
        return engine;
    }

    public Entity getGlobalEntity() {
        return globalEntity;
    }

    public Entity getImmutableGlobalEntity() {
        return immutableGlobalEntity;
    }

    public CacheRetriever getCacheRetriever() {
        return cacheRetriever;
    }

    public Vector3 getFrontStageMousePosition() {
        return frontStageMousePosition;
    }

    public Vector3 getUxStageMousePosition() {
        return uxStageMousePosition;
    }

    public Display getDisplay() {
        return display;
    }
}
