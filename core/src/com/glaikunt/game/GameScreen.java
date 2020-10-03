package com.glaikunt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.Screen;
import com.glaikunt.ecs.systems.AnimationSystem;
import com.glaikunt.ecs.systems.DetectCollisionSystem;
import com.glaikunt.game.collision.BlockActor;
import com.glaikunt.game.levels.LevelOne;
import com.glaikunt.game.player.PlayerActor;

import static com.glaikunt.application.cache.TiledCache.LEVEL_1;

public class GameScreen extends Screen {

    private float currentLevel = 0;

    public GameScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

        PlayerActor player = new PlayerActor(getApplicationResources());
        Gdx.input.setInputProcessor(player);
        getFront().addActor(player);

        TiledMap tiled = getApplicationResources().getCacheRetriever().getTiledCache().getTiledMapCache(LEVEL_1);
        TiledMapTileLayer blocksLayer = (TiledMapTileLayer) tiled.getLayers().get("Blocks");
        for (int y = blocksLayer.getHeight(); y >= 0; y--) {
            float yPos = (y * (int) blocksLayer.getTileHeight());
            for (int x = 0; x < blocksLayer.getWidth(); x++) {
                float xPos = (x * (int) blocksLayer.getTileWidth());
                TiledMapTileLayer.Cell cell = blocksLayer.getCell(x, y);
                if (cell == null) continue;

                Vector2 pos = new Vector2(xPos, yPos);
                BlockActor blockActor = new BlockActor(getApplicationResources(), pos.x, pos.y);
                getFront().addActor(blockActor);
            }
        }

        if (currentLevel == 0) {

            getUX().addActor(new LevelOne(getApplicationResources(), player, getFront()));
        }

        getBackground().addActor(new BackgroundActor(getApplicationResources()));

        getApplicationResources().getEngine().addSystem(new DetectCollisionSystem(getEngine()));
        getApplicationResources().getEngine().addSystem(new AnimationSystem(getEngine()));
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
