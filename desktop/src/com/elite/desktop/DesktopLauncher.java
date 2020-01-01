package com.elite.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.elite.ui.ZombieWars;

/**
 * Launches the game.
 */
public class DesktopLauncher {
    /**
     * Main function which launches the game.
     *
     * @param arg A required parameter in Java, although in this case no data is passed.
     */
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new ZombieWars(), config);
    }
}
