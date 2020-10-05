package com.glaikunt.splash;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.Screen;
import com.glaikunt.application.cache.SoundCache;
import com.glaikunt.dialog.DialogScreen;
import com.glaikunt.game.GameScreen;

public class SplashScreen extends Screen {

    public SplashScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void render(float delta) {

        if (getApplicationResources().getCacheRetriever().isCacheLoaded()) {
            getDisplay().setScreen(new DialogScreen(getApplicationResources()));
        } else {

            getApplicationResources().getCacheRetriever().update();
        }
    }

    @Override
    public void show() {

        getApplicationResources().getCacheRetriever().loadCache();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
