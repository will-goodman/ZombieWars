package com.elite.test;

import com.elite.audio.AudioAccessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAudioAccessor {

    private static AudioAccessor audioAccessor = new AudioAccessor();
    private static String music = "testMusic";
    private static String music1 = "testMusic";
    private static String sound = "testSound";

    @Test
    public void startMusicTest() {
        try{
            audioAccessor.startMusic(music);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void playSoundTest() {
        try{
            audioAccessor.playSound(music);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setMusicVolumeTest() {
        try {
            //audioAccessor.setMusicVolume(50, music);
        } catch(Exception e) {
            e.printStackTrace();
        }
        //assertEquals(50, audioAccessor.getMusicVolume());
    }

    @Test
    public void changeMusicVolumeTest() {
        try {
            audioAccessor.changeMusicVolume(11, music);
        } catch(Exception e) {
            e.printStackTrace();
        }
        assertEquals(61, audioAccessor.getMusicVolume(music));
    }

    @Test
    public void allowSoundSwitchTest() {
        try{
            audioAccessor.allowSoundsSwitch();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*@Test
    public void getMusicVolumeTest() {
        assertEquals(61, audioAccessor.getMusicVolume());
    }*/

    @Test
    public void switchMusicTest() {
        try{
            audioAccessor.switchMusic(music1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pauseMusicTest() {
        try{
            audioAccessor.pauseMusic(music1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void isMusicPlayingTest() {
        try{
            audioAccessor.isMusicPlaying(music1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
