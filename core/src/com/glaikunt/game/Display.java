package com.glaikunt.game;

import com.badlogic.gdx.Game;
import com.glaikunt.game.application.ApplicationResources;
import com.glaikunt.game.game.GameScreen;

public class Display extends Game {

	public static final float WORLD_WIDTH = 320; //640 //160 //320
	public static final float WORLD_HEIGHT = 240; //480 //120 //240

	private ApplicationResources applicationResources;

	@Override
	public void create () {

		applicationResources = new ApplicationResources(this);
		setScreen(new GameScreen(applicationResources));
	}
}
