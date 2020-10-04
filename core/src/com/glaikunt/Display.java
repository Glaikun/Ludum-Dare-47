package com.glaikunt;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.ecs.components.LevelComponent;
import com.glaikunt.splash.SplashScreen;

public class Display extends Game {

	public static final float WORLD_WIDTH = 640; //640 //160 //320
	public static final float WORLD_HEIGHT = 480; //480 //120 //240

	private ApplicationResources applicationResources;
	private LevelComponent level;
	private boolean pause;

	@Override
	public void create () {

		applicationResources = new ApplicationResources(this);
		level = new LevelComponent();
		applicationResources.getGlobalEntity().add(level);
		setScreen(new SplashScreen(applicationResources));
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
}
