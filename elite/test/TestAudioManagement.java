package com.elite.test;

import com.elite.audio.AudioManagement;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestAudioManagement {

    private static AudioManagement audioManagement = new AudioManagement();
    private static String music = "testMusic";
    private static String music1 = "testMusic";
    private static String sound = "testSound";


    @Test
    public void constructorTest() {
        try{
            //audioManagement.initialiseAudio();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allowMusicSwitchTest() {
        try {
            audioManagement.allowMusicSwitch();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allowSoundsSwitchTest() {
        try {
            audioManagement.allowSoundsSwitch();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void startMusicTest() {
        try {
            audioManagement.startMusic(music);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void playSoundTest() {
        try {
            audioManagement.playSound(sound);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setMusicVolumeTest() {
        try {
            //audioManagement.setMusicVolume(50, music);
        } catch(Exception e) {
            e.printStackTrace();
        }
        //assertEquals(50, audioManagement.getMusicVolume());
    }

    @Test
    public void changeMusicVolumeTest() {
        try {
            audioManagement.changeMusicVolume(11, music);
        } catch(Exception e) {
            e.printStackTrace();
        }
        assertEquals(61, audioManagement.getMusicVolume(music));
    }

    /*@Test
    public void getMusicVolumeTest() {
        assertEquals(61, audioManagement.getMusicVolume());
    }*/

    /*@Test
    public void switchMusicTest() {
        try {
            audioManagement.switchMusic(music1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void pauseMusicTest() {
        try {
            audioManagement.pauseMusic(music1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void switchMusicTest() {
        try {
            audioManagement.switchMusic(music1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isMusicPlayingTest() {
        assertTrue(audioManagement.isMusicPlaying(music1));
    }

}
