package com.elite.audio;


public class AudioAccessor {

    private static AudioManagement audioManagement = new AudioManagement();

    /**
     * The method which plays a certain music file
     * @param musicName The music file name
     */
    public static void startMusic(String musicName){
        audioManagement.startMusic(musicName);
    }

    /**
     * The method which plays a certain sound
     * @param soundName The sound file name
     */
    public static void playSound(String soundName){
        audioManagement.playSound(soundName);
    }

    /**
     * The method which changes a certain music file's volume by an amount
     * @param volumeChange The amount of with which the volume will be changed
     * @param musicName The music file the volume change is applied to
     */
    public static void changeMusicVolume(float volumeChange, String musicName){
        audioManagement.changeMusicVolume(volumeChange, musicName);
    }

    /**
     * The method which sets a certain music file's volume to a certain amount
     * @param newVolume The volume to which it will be set
     */
    public static void setMusicVolume(float newVolume){
        audioManagement.setMusicVolume(newVolume);
    }

    /**
     * The method which sets a certain sound file's volume to a certain amount
     * @param newVolume The volume to which it will be set
     */
    public static void setSoundVolume(float newVolume){
        audioManagement.setSoundVolume(newVolume);
    }

    /**
     * Allows the change of sounds while playing
     */
    public static void allowSoundsSwitch(){
        audioManagement.allowSoundsSwitch();
    }

    /**
     * The method which returns the volume of a certain music file
     * @param musicName The music file
     * @return The volume of the music file
     */
    public static float getMusicVolume(String musicName){
        return audioManagement.getMusicVolume(musicName);
    }

    /**
     * The method which changes the playing music file
     * @param musicName The music file name
     */
    public static void switchMusic(String musicName){
        audioManagement.switchMusic(musicName);
    }

    /**
     * The method which pauses a certain music file
     * @param musicName The music file it is applied to
     */
    public static void pauseMusic(String musicName){
        audioManagement.pauseMusic(musicName);
    }

    /**
     * The method which check if a certain music file is playing
     * @param musicName The music file that is checked
     * @return Returns true if the music file is playing, false otherwise
     */
    public static boolean isMusicPlaying(String musicName){
        return audioManagement.isMusicPlaying(musicName);
    }

    /**
     * Allows the change of music while playing
     */
    public static void allowMusicSwitch() {audioManagement.allowMusicSwitch();
    }
}
