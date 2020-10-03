package com.glaikunt.game;

import com.badlogic.gdx.Game;
import com.glaikunt.game.application.ApplicationResources;
import com.glaikunt.game.game.GameScreen;

public class Display extends Game {

	public static final float WORLD_WIDTH = 640; //640 //160 //320
	public static final float WORLD_HEIGHT = 480; //480 //120 //240

	private ApplicationResources applicationResources;
	private boolean pause;

	@Override
	public void create () {

		applicationResources = new ApplicationResources(this);
		setScreen(new GameScreen(applicationResources));
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
