package com.glaikunt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.Screen;
import com.glaikunt.ecs.systems.DetectCollisionSystem;
import com.glaikunt.game.collision.BlockActor;
import com.glaikunt.game.player.PlayerActor;

import static com.glaikunt.application.cache.TiledCache.LEVEL_1;

public class GameScreen extends Screen {

    public GameScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

        PlayerActor player = new PlayerActor(getApplicationResources());
        Gdx.input.setInputProcessor(player);
        getFront().addActor(player);

        TiledMap tiled = getApplicationResources().getCacheRetriever().getTiledCache().getTiledMapCache(LEVEL_1);

        TiledMapTileLayer pathLayer = (TiledMapTileLayer) tiled.getLayers().get("Blocks");
        for (int y = pathLayer.getHeight(); y >= 0; y--) {
            float yPos = (y * (int) pathLayer.getTileHeight());
            for (int x = 0; x < pathLayer.getWidth(); x++) {
                float xPos = (x * (int) pathLayer.getTileWidth());
                TiledMapTileLayer.Cell cell = pathLayer.getCell(x, y);
                if (cell == null) continue;

                Vector2 pos = new Vector2(xPos, yPos);
                BlockActor blockActor = new BlockActor(getApplicationResources(), pos.x, pos.y);
                getFront().addActor(blockActor);
            }
        }

        getApplicationResources().getEngine().addSystem(new DetectCollisionSystem(getApplicationResources().getEngine()));
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
