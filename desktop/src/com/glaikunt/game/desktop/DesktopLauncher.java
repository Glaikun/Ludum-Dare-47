package com.glaikunt.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.glaikunt.game.Display;

public class DesktopLauncher {

	public static final int WORLD_WIDTH = 640; //640 //160 //320 //800
	public static final int WORLD_HEIGHT = 480; //480 //120 //240 //600

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WORLD_WIDTH;
		config.height = WORLD_HEIGHT;
		config.resizable = false;
		new LwjglApplication(new Display(), config);
	}
}
