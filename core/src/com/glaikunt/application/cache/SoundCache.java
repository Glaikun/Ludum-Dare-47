package com.glaikunt.application.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class SoundCache implements Cache {

    public static final String music = "music.wav";

    private Map<String, Music> sounds = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (sounds.isEmpty()) return false;

        for (String key : sounds.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : sounds.keySet()) {
                getSounds().put(key, (Music) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Map<String, Music> getSounds() {
        return sounds;
    }
}
