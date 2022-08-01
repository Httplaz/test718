package com.httplaz.diefromfire;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.httplaz.diefromfire.Main;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg)
	{
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(800, 480);
		config.setTitle("SwapTestEngine718");
		config.setIdleFPS(2);
		config.setAudioConfig(200, 1024, 1);
		config.setWindowIcon(Files.FileType.Internal, "icon.png");
		config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL30,3,3);
		new Lwjgl3Application(new Main(), config);
	}
}
