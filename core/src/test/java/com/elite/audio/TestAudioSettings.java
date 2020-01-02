package com.elite.audio;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestAudioSettings {

    @Test
    public void playingMusic() {
        AudioSettings testAudioSettings = new AudioSettings();

        assertTrue(testAudioSettings.playingMusic());
    }

    @Test
    public void getMusicVolume() {
        AudioSettings testAudioSettings = new AudioSettings();

        assertEquals(1.0f, testAudioSettings.getMusicVolume(), 0.0f);
    }

    @Test
    public void setMusicVolume() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.setMusicVolume(0.5f);

        assertEquals(0.5f, testAudioSettings.getMusicVolume(), 0.0f);
    }

    @Test
    public void startMusic() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.stopMusic();
        testAudioSettings.startMusic();

        assertTrue(testAudioSettings.playingMusic());
    }

    @Test
    public void stopMusic() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.stopMusic();

        assertFalse(testAudioSettings.playingMusic());
    }

    @Test
    public void toggleMusic() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.toggleMusic();
        assertFalse(testAudioSettings.playingMusic());

        testAudioSettings.toggleMusic();
        assertTrue(testAudioSettings.playingMusic());
    }

    @Test
    public void playingSoundEffects() {
        AudioSettings testAudioSettings = new AudioSettings();

        assertTrue(testAudioSettings.playingSoundEffects());
    }

    @Test
    public void getSoundEffectsVolume() {
        AudioSettings testAudioSettings = new AudioSettings();

        assertEquals(1.0f, testAudioSettings.getSoundEffectsVolume(), 0.0f);
    }

    @Test
    public void setSoundEffectsVolume() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.setSoundEffectsVolume(0.5f);

        assertEquals(0.5f, testAudioSettings.getSoundEffectsVolume(), 0.0f);
    }

    @Test
    public void startSoundEffects() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.stopSoundEffects();
        testAudioSettings.startSoundEffects();

        assertTrue(testAudioSettings.playingSoundEffects());
    }

    @Test
    public void stopSoundEffects() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.stopSoundEffects();

        assertFalse(testAudioSettings.playingSoundEffects());
    }

    @Test
    public void toggleSoundEffects() {
        AudioSettings testAudioSettings = new AudioSettings();

        testAudioSettings.toggleSoundEffects();
        assertFalse(testAudioSettings.playingSoundEffects());

        testAudioSettings.toggleSoundEffects();
        assertTrue(testAudioSettings.playingSoundEffects());
    }
}