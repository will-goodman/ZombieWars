package com.elite.audio;

/**
 * Stores the current state of the music and sound effects.
 *
 * @author Will Goodman
 */
public class AudioSettings {

    private boolean playMusic = true;
    private boolean playSoundEffects = true;
    private float musicVolume = 1.0f;
    private float soundEffectsVolume = 1.0f;

    /**
     * States whether the player has enabled/disabled music.
     *
     * @return Whether or not music is set to play.
     */
    public boolean playingMusic() {
        return this.playMusic;
    }

    /**
     * Returns the music volume set by the player.
     *
     * @return The music volume.
     */
    public float getMusicVolume() {
        return this.musicVolume;
    }

    /**
     * Updates the music volume.
     *
     * @param newVolume The new music volume.
     */
    public void setMusicVolume(float newVolume) {
        this.musicVolume = newVolume;
    }

    /**
     * Starts the game music.
     */
    public void startMusic() {
        this.playMusic = true;
    }

    /**
     * Stops the game music.
     */
    public void stopMusic() {
        this.playMusic = false;
    }

    /**
     * Toggles the game music on/off.
     */
    public void toggleMusic() {
        this.playMusic = !this.playMusic;
    }

    /**
     * States whether the player has enabled/disabled sound effects.
     *
     * @return Whether or not the sound  effects are set to play.
     */
    public boolean playingSoundEffects() {
        return this.playSoundEffects;
    }

    /**
     * Returns the sound effect volume set by the player.
     *
     * @return The sound effect volume.
     */
    public float getSoundEffectsVolume() {
        return this.soundEffectsVolume;
    }

    /**
     * Updates the sound effect volume.
     *
     * @param newVolume The new sound effect volume.
     */
    public void setSoundEffectsVolume(float newVolume) {
        this.soundEffectsVolume = newVolume;
    }

    /**
     * Starts the sound effects.
     */
    public void startSoundEffects() {
        this.playSoundEffects = true;
    }

    /**
     * Stops the game sound effects.
     */
    public void stopSoundEffects() {
        this.playSoundEffects = false;
    }

    /**
     * Toggles the sound effects on/off.
     */
    public void toggleSoundEffects() {
        this.playSoundEffects = !this.playSoundEffects;
    }

}
