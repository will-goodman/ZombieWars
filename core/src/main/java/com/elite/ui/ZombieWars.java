package com.elite.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elite.audio.Audio;
import com.elite.audio.AudioSettings;
import com.elite.ui.menu.HomeScreen;

/**
 * Launches the game UI and the HomeScreen
 *
 * @author Will Goodman
 */
public class ZombieWars extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    /**
     * Sets the screen to the HomeScreen
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        setScreen(new HomeScreen(this, new AudioSettings()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        Audio.backgroundMusic.dispose();
    }

}
