package com.glaikunt.application.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Map;

public class TiledCache implements Cache {

    public static final String LEVEL_ = "tiled/level_";
    public static final String SUFFIX_TMX = ".tmx";

    public static final String LEVEL_1 = "tiled/level_1.tmx";
    public static final String LEVEL_2 = "tiled/level_2.tmx";

    private Map<String, TiledMap> tiledMap = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        TmxMapLoader loader = new TmxMapLoader(new InternalFileHandleResolver());
        assetManager.setLoader(TiledMap.class, loader);

        assetManager.load(LEVEL_1, TiledMap.class, params);
        getTiledMap().put(LEVEL_1, null);

        assetManager.load(LEVEL_2, TiledMap.class, params);
        getTiledMap().put(LEVEL_2, null);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (tiledMap.isEmpty()) return false;

        for (String key : tiledMap.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : tiledMap.keySet()) {
                getTiledMap().put(key, (TiledMap) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public TiledMap getTiledMapCache(String key) {
        return getTiledMap().get(key);
    }

    public Map<String, TiledMap> getTiledMap() {
        return tiledMap;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
