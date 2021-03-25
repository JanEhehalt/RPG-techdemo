package com.trs.main.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.trs.main.Main;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = true;
        
        config.width=1600;
        config.height=900;
        config.fullscreen = false;
        
        //config.width=1920;
        //config.height=1080;
        //config.fullscreen = true;
        new LwjglApplication(new Main(), config);
    }
}