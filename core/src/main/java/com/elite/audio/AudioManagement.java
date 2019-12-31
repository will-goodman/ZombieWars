package com.elite.audio;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;


/**
 * Makes changes to the audio (music/sound effects) in the game
 */
public class AudioManagement {


    private String[] allSoundNames = {"testSound", "grenade", "win", "loss"};
    private String[] allSoundFiles = {"testSound.mp3", "grenadeSound.mp3", "winSound.mp3", "loss.mp3"};
    private String[] allMusicNames = {"testMusic"};
    private String[] allMusicFiles = {"testMusic.mp3"};
    private ArrayList<GameMusic> listOfMusic = new ArrayList<>();
    private ArrayList<GameSound> listOfSounds = new ArrayList<>();
    private AssetManager soundManager = new AssetManager();
    private boolean soundsToPlay = true;
    private boolean musicToPlay = true;

    private float currentVolume = 50f;
    //TODO Look at removing variable
    private float currentSoundVolume = 50f;


    /**
     * The constructor which initialises the Audio module
     */
    public AudioManagement() {

        initialiseAudio();
    }

    /**
     * The method which allows music to switch. If there is a music file already playing, it will not allow a new music
     * file to play.
     */
    public boolean allowMusicSwitch() {
        if (musicToPlay) {
            musicToPlay = false;
            stopAllMusic();
        } else {
            musicToPlay = true;
            startMusic();
        }

        return musicToPlay;
    }

    /**
     * The method which allows sounds to switch. If there is a sound file already playing, it will not allow a new sound
     * file to play.
     */
    public boolean allowSoundsSwitch() {
        soundsToPlay = !soundsToPlay;

        return soundsToPlay;
    }

    /**
     * The method which checks if a music file is in the list and starts playing it
     *
     * @param musicName The music file that will be played
     */
    public void startMusic(String musicName) {
        for (GameMusic gameMusic : listOfMusic) {
            if ((gameMusic.getFileName().equals(musicName)) && musicToPlay) {
                gameMusic.getMusic().play();
                gameMusic.getMusic().setVolume(currentVolume);
            }
        }
    }

    /**
     * A testing method to check whether the music plays or not
     */
    public void startMusic() {
        for (GameMusic gameMusic : listOfMusic) {
            if ((gameMusic.getFileName().equals("testMusic")) && musicToPlay) {
                gameMusic.getMusic().play();
                gameMusic.getMusic().setVolume(currentVolume);

            }
        }
    }

    /**
     * The method which checks if a sound file is in the list and plays it
     *
     * @param soundName The sound file that will be played
     */
    public void playSound(String soundName) {
        for (GameSound listOfSound : listOfSounds) {
            if ((listOfSound.getFileName().equals(soundName)) && soundsToPlay) {
                listOfSound.getSound().play(currentVolume);
            }
        }
    }

    /**
     * The method which changes the volume by a certain amount of a music file that has been added to the list
     *
     * @param volumeChange The change in volume
     * @param musicName    The music file the change in volume is applied to
     */
    public void changeMusicVolume(float volumeChange, String musicName) {
        if (isMusicPlaying(musicName)) {
            for (GameMusic gameMusic : listOfMusic) {
                if (gameMusic.getFileName().equals(musicName)) {
                    gameMusic.getMusic().setVolume(gameMusic.getMusic().getVolume() + volumeChange);
                }
            }
        }

    }

    /**
     * The method which changes the volume to a certain amount of a music file that has been added to the list
     *
     * @param newVolume The new volume value
     */
    public void setMusicVolume(float newVolume) {
        currentVolume = newVolume;
        for (GameMusic gameMusic : listOfMusic) {
            if (gameMusic.getMusic().isPlaying()) {
                gameMusic.getMusic().setVolume(currentVolume);
            }
        }
    }

    /**
     * The method which gets the volume of a music file added to the list
     *
     * @param musicName The music file name
     * @return Returns the music's volume, and 0.0f if the music is not playing
     */
    public float getMusicVolume(String musicName) {
        if (isMusicPlaying(musicName)) {
            for (GameMusic gameMusic : listOfMusic) {
                if (gameMusic.getFileName().equals(musicName)) {
                    return gameMusic.getMusic().getVolume();
                }
            }
        }
        return 0.0f;
    }

    //TODO Look at removing method

    /**
     * The method which changes the volume of the sounds to a certain amount
     *
     * @param newVolume The new volume value
     */
    public void setSoundVolume(float newVolume) {
        currentSoundVolume = newVolume;
    }

    /**
     * The method which changes the playing music file to another given one that is in the list.
     *
     * @param musicName The new music file that will be played
     */
    public void switchMusic(String musicName) {
        if (isMusicPlaying(musicName)) {
            for (GameMusic gameMusic : listOfMusic) {
                if (gameMusic.getFileName().equals(musicName)) {
                    gameMusic.getMusic().stop();
                }
            }
        } else {
            for (GameMusic gameMusic : listOfMusic) {
                if ((gameMusic.getFileName().equals(musicName)) && musicToPlay) {
                    gameMusic.getMusic().setVolume(currentVolume);
                    gameMusic.getMusic().play();
                }
            }
        }
    }

    /**
     * The method which pauses the playing music file
     *
     * @param musicName The music file that will be paused
     */
    public void pauseMusic(String musicName) {
        if (isMusicPlaying(musicName)) {
            for (GameMusic gameMusic : listOfMusic) {
                if (gameMusic.getFileName().equals(musicName)) {
                    gameMusic.getMusic().pause();
                }
            }
        }
    }

    /**
     * The method which goes through the list of music and pauses each one
     */
    public void stopAllMusic() {
        for (GameMusic gameMusic : listOfMusic) {
            pauseMusic(gameMusic.getFileName());
        }
    }

    /**
     * The method which check whether a music file is playing
     *
     * @param musicName The music file which is checked
     * @return Returns true is the music file is playing, false if not
     */
    public boolean isMusicPlaying(String musicName) {
        for (GameMusic gameMusic : listOfMusic) {
            if (gameMusic.getFileName().equals(musicName)) {
                if (gameMusic.getMusic().isPlaying()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The method which adds all music files and sounds files to their respective lists
     */
    private void initialiseAudio() {
        for (int i = 0; i < allSoundFiles.length; i++) {
            GameSound currentSound = new GameSound(allSoundNames[i], allSoundFiles[i]);
            listOfSounds.add(currentSound);
        }
        for (int i = 0; i < allMusicFiles.length; i++) {
            GameMusic currentMusic = new GameMusic(allMusicNames[i], allMusicFiles[i]);
            listOfMusic.add(currentMusic);
        }
    }

    private class GameMusic {
        private Music musicObject;
        private String fileName;

        /**
         * The Constructor for the game music object
         *
         * @param newFileName The music file name
         * @param filePath    The music file path
         */
        public GameMusic(String newFileName, String filePath) {
            soundManager.load(filePath, Music.class);
            soundManager.finishLoading();
            musicObject = soundManager.get(filePath);
            fileName = newFileName;
        }

        /**
         * The getter method for the game music object
         *
         * @return The music object
         */
        public Music getMusic() {
            return musicObject;
        }

        /**
         * The getter method for the game music file name
         *
         * @return The music file name
         */
        public String getFileName() {
            return fileName;
        }
    }

    private class GameSound {
        private Sound soundObject;
        private String fileName;

        /**
         * The Constructor for the game sound object
         *
         * @param newFileName The sound file name
         * @param filePath    The sound file path
         */
        public GameSound(String newFileName, String filePath) {
            soundManager.load(filePath, Sound.class);
            soundManager.finishLoading();
            soundObject = soundManager.get(filePath);
            fileName = newFileName;
        }

        /**
         * The getter method for the game sound object
         *
         * @return The game sound object
         */
        public Sound getSound() {
            return soundObject;
        }

        /**
         * The getter method for the game sound file name
         *
         * @return The sound file name
         */
        public String getFileName() {
            return fileName;
        }
    }

}
