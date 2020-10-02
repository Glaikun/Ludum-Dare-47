package com.glaikunt.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.glaikunt.game.application.ApplicationResources;
import com.glaikunt.game.application.Screen;

public class GameScreen extends Screen {

    private SpriteBatch batch;
    private Texture img;

    public GameScreen(ApplicationResources applicationResources) {
        super(applicationResources);

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }
}
