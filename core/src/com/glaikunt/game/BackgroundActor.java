package com.glaikunt.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.cache.TextureCache;

public class BackgroundActor extends Actor {

    private ApplicationResources applicationResources;
    private Texture background;

    public BackgroundActor(ApplicationResources applicationResources) {

        this.applicationResources = applicationResources;
        this.background = applicationResources.getCacheRetriever().geTextureCache(TextureCache.BACKGROUND);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(background, 0, 0);
    }
}
