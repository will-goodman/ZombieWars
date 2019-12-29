package com.elite.audio;

import com.elite.audio.AudioManagement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.junit.Test;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AudioManagement.class)
public class TestAudioManagement {

    @Test
    public void constructorTest() {
        try{
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            assertEquals(new AudioManagement(), mockAudioManagement);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allowMusicSwitchTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            Mockito.doCallRealMethod().when(mockAudioManagement).allowMusicSwitch();
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            AudioManagement testAudioManagement = new AudioManagement();

            assertFalse(testAudioManagement.allowMusicSwitch());
            assertTrue(testAudioManagement.allowMusicSwitch());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allowSoundsSwitchTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            Mockito.doCallRealMethod().when(mockAudioManagement).allowSoundsSwitch();
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            AudioManagement testAudioManagement = new AudioManagement();

            boolean firstResult = testAudioManagement.allowSoundsSwitch();
            assertFalse(firstResult);
            boolean secondResult = testAudioManagement.allowSoundsSwitch();
            assertTrue(secondResult);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //TODO startMusicTests for both methods
    @Test
    public void startMusicTest() {
        fail("Not Implemented.");
    }

    //TODO playSoundTest
    @Test
    public void playSoundTest() {
        fail("Not Implemented.");
    }

    //TODO Improve to test music list
    @Test
    public void changeMusicVolumeTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            AudioManagement testAudioManagement = new AudioManagement();

            Mockito.when(testAudioManagement.isMusicPlaying("test_music")).thenReturn(true);
            testAudioManagement.changeMusicVolume(0.5f, "test_music");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //TODO setMusicVolumeTest
    @Test
    public void setMusicVolumeTest() {
        fail("Not Implemented.");
    }

    //TODO MusicVolumeTest with a music file
    @Test
    public void getMusicVolumeTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            AudioManagement testAudioManagement = new AudioManagement();
            assertEquals(0.0f, testAudioManagement.getMusicVolume("fake_music"), 0.0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //TODO setSoundVolumeTest
    @Test
    public void setSoundVolumeTest() {
        fail("Not Implemented.");
    }

    //TODO Improve to test music list
    @Test
    public void switchMusicTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            AudioManagement testAudioManagement = new AudioManagement();

            Mockito.when(testAudioManagement.isMusicPlaying("test_music")).thenReturn(true);
            testAudioManagement.switchMusic("test_music");
            Mockito.when(testAudioManagement.isMusicPlaying("test_music")).thenReturn(false);
            testAudioManagement.switchMusic("test_music");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //TODO Improve to test music list
    @Test
    public void pauseMusicTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            AudioManagement testAudioManagement = new AudioManagement();

            Mockito.when(testAudioManagement.isMusicPlaying("test_music")).thenReturn(true);
            testAudioManagement.pauseMusic("test_music");
            Mockito.when(testAudioManagement.isMusicPlaying("test_music")).thenReturn(false);
            testAudioManagement.pauseMusic("test_music");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stopAllMusicTest() {
        fail("Not Implemented.");
    }

    //TODO Improve to test music list
    @Test
    public void isMusicPlayingTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);

            AudioManagement testAudioManagement = new AudioManagement();

            assertFalse(testAudioManagement.isMusicPlaying("test_music"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
