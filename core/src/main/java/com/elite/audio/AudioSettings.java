package com.elite.audio;

public class AudioSettings {

    private boolean playMusic = true;
    private boolean playSoundEffects = true;
    private float musicVolume = 1.0f;
    private float soundEffectsVolume = 1.0f;

    public boolean playingMusic() {
        return this.playMusic;
    }

    public void toggleMusic() {
        this.playMusic = !this.playMusic;
    }

    public float getMusicVolume() {
        return this.musicVolume;
    }

    public void setMusicVolume(float newVolume) {
        this.musicVolume = newVolume;
    }

    public boolean playingSoundEffects() {
        return this.playSoundEffects;
    }

    public void toggleSoundEffects() {
        this.playSoundEffects = !this.playSoundEffects;
    }

    public float getSoundEffectsVolume() {
        return this.soundEffectsVolume;
    }

    public void setSoundEffectsVolume(float newVolume) {
        this.soundEffectsVolume = newVolume;
    }

}
