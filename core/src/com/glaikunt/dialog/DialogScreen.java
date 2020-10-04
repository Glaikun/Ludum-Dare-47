package com.glaikunt.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.Screen;
import com.glaikunt.ecs.systems.AnimationSystem;
import com.glaikunt.ecs.systems.DelayedTextQueueSystem;
import com.glaikunt.ecs.systems.DelayedTextSystem;

public class DialogScreen extends Screen {

    private ShapeRenderer shapeRenderer;

    public DialogScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

        this.shapeRenderer = new ShapeRenderer();

        getFront().addActor(new PlayerActor(getApplicationResources()));
        getFront().addActor(new DemonActor(getApplicationResources()));

        getEngine().addSystem(new AnimationSystem(getEngine()));
        getEngine().addSystem(new DelayedTextQueueSystem(getEngine()));
        getEngine().addSystem(new DelayedTextSystem(getEngine()));
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

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float x = (Gdx.graphics.getWidth()/2f);
        float y = (Gdx.graphics.getHeight()/2f)+100f;
        shapeRenderer.triangle(x-50, y-150, x-90, y+75, x+100, y-175, new Color(1, 1, 1, .1f), Color.WHITE, new Color(1, 1, 1, .1f));

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
