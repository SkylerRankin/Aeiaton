package com.aeiaton.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.aeiaton.Aeiaton;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int scale = 3;
		config.width = 480*scale;
		config.height = 270*scale;
		new LwjglApplication(new Aeiaton(), config);
	}
}
