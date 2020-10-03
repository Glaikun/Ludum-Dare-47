package com.glaikunt.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.glaikunt.game.application.ApplicationResources;
import com.glaikunt.game.application.Screen;
import com.glaikunt.game.game.player.PlayerActor;

public class GameScreen extends Screen {

    public GameScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

        PlayerActor player = new PlayerActor(getApplicationResources());
        Gdx.input.setInputProcessor(player);
        getFront().addActor(player);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!getDisplay().isPaused()) {
            getBackground().act();
            getFront().act();
            getUX().act();
        }

        getBackground().draw();
        getFront().draw();
        getUX().draw();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
