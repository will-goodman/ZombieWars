package com.elite.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Used to interact with the music and sound effects.
 *
 * @author Will Goodman
 */
public class Audio {

    public static Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("testMusic.mp3"));

    public static Sound grenade = Gdx.audio.newSound(Gdx.files.internal("grenadeSound.mp3"));
    public static Sound jump = Gdx.audio.newSound(Gdx.files.internal("testSound.mp3"));
    public static Sound win = Gdx.audio.newSound(Gdx.files.internal("winSound.mp3"));
    public static Sound loss = Gdx.audio.newSound(Gdx.files.internal("loss.mp3"));

}
