package com.glaikunt;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.cache.SoundCache;
import com.glaikunt.ecs.components.LevelComponent;
import com.glaikunt.splash.SplashScreen;

public class Display extends Game {

	public static final float WORLD_WIDTH = 640; //640 //160 //320
	public static final float WORLD_HEIGHT = 480; //480 //120 //240

	private ApplicationResources applicationResources;
	private LevelComponent level;
	private boolean pause;
	private Music music;

	@Override
	public void create () {

		Gdx.app.setLogLevel(Application.LOG_NONE);
		applicationResources = new ApplicationResources(this);
		level = new LevelComponent();
		applicationResources.getGlobalEntity().add(level);
		setScreen(new SplashScreen(applicationResources));

		music = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		music.setLooping(true);
		music.setVolume(.7f);
		music.play();
	}

	@Override
	public void render() {


		if (!isPaused()) {
			applicationResources.getEngine().update(Gdx.graphics.getDeltaTime());
		}
		super.render();
	}

	@Override
	public void pause() {

		pause = false;
	}

	@Override
	public void resume() {

		pause = true;
	}

	public boolean isPaused() {
		return pause;
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}
}
