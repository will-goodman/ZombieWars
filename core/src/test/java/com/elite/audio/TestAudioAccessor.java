package com.elite.audio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(com.elite.audio.AudioAccessor.class)
public class TestAudioAccessor {

    @Test
    public void startMusicTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();

            testAudioAccessor.startMusic("test_music");
            Mockito.verify(mockAudioManagement, Mockito.times(1)).startMusic("test_music");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void playSoundTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();
            testAudioAccessor.playSound("test_music");
            Mockito.verify(mockAudioManagement, Mockito.times(1)).playSound("test_music");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void changeMusicVolumeTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();
            testAudioAccessor.changeMusicVolume(11, "test_music");
            Mockito.verify(mockAudioManagement, Mockito.times(1)).changeMusicVolume(11, "test_music");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setMusicVolumeTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();
            testAudioAccessor.setMusicVolume(11);
            Mockito.verify(mockAudioManagement, Mockito.times(1)).setMusicVolume(11);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setSoundVolumeTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();
            testAudioAccessor.setSoundVolume(11);
            Mockito.verify(mockAudioManagement, Mockito.times(1)).setSoundVolume(11);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allowSoundSwitchTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();
            testAudioAccessor.allowSoundsSwitch();
            Mockito.verify(mockAudioManagement, Mockito.times(1)).allowSoundsSwitch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMusicVolumeTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);
            Mockito.when(mockAudioManagement.getMusicVolume("test_music")).thenReturn(11f);

            AudioAccessor testAudioAccessor = new AudioAccessor();
            assertEquals(testAudioAccessor.getMusicVolume("test_music"), 11f, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void switchMusicTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();

            testAudioAccessor.switchMusic("test_music");
            Mockito.verify(mockAudioManagement, Mockito.times(1)).switchMusic("test_music");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pauseMusicTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();

            testAudioAccessor.pauseMusic("test_music");
            Mockito.verify(mockAudioManagement, Mockito.times(1)).pauseMusic("test_music");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isMusicPlayingTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);
            Mockito.when(mockAudioManagement.isMusicPlaying("test_music")).thenReturn(true);

            AudioAccessor testAudioAccessor = new AudioAccessor();

            assertTrue(testAudioAccessor.isMusicPlaying("test_music"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allowMusicSwitchTest() {
        try {
            AudioManagement mockAudioManagement = Mockito.mock(AudioManagement.class);
            PowerMockito.whenNew(AudioManagement.class).withNoArguments().thenReturn(mockAudioManagement);
            Whitebox.setInternalState(AudioAccessor.class, mockAudioManagement);

            AudioAccessor testAudioAccessor = new AudioAccessor();

            testAudioAccessor.allowMusicSwitch();
            Mockito.verify(mockAudioManagement, Mockito.times(1)).allowMusicSwitch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
