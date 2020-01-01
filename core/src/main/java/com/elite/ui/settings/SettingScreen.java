package com.elite.ui.settings;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.elite.audio.Audio;
import com.elite.audio.AudioSettings;
import com.elite.ui.ZombieWars;
import com.elite.ui.menu.HomeScreen;

/**
 * Create object for setting screen
 *
 * @author WEI-TSE CHENG
 */
public class SettingScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private Slider volumeMusicSlider;
    private Slider volumeSoundSlider;
    private SpriteBatch batch;
    private BitmapFont title;
    private BitmapFont volumeMusicFont;
    private BitmapFont musicCheckboxFont;
    private BitmapFont volumeSoundFont;
    private BitmapFont soundCheckboxFont;
    private CheckBox musicCheckbox;
    private CheckBox soundCheckbox;

    private AudioSettings audioSettings;
    private final ZombieWars game;

    public SettingScreen(final ZombieWars game, AudioSettings audioSettings) {
        this.game = game;
        this.audioSettings = audioSettings;
    }

    /**
     * The method which creates the Setting Screen
     */
    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("setting_skin/glassy-ui.json"));

        Texture bgTexture = new Texture(Gdx.files.internal("maps/BG.png"));
        Image bg = new Image(bgTexture);

        volumeMusicSlider = new Slider(0.0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setPosition(750, 350);
        volumeMusicSlider.setValue(1f);
        volumeSoundSlider = new Slider(0.0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setPosition(750, 250);
        volumeSoundSlider.setValue(1f);
        musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setPosition(800, 305);
        soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setPosition(800, 205);


        batch = new SpriteBatch();

        volumeMusicFont = new BitmapFont(Gdx.files.internal("setting_skin/font-big-export.fnt"));
        volumeMusicFont.getData().setScale(0.5f, 0.5f);
        musicCheckboxFont = new BitmapFont(Gdx.files.internal("setting_skin/font-big-export.fnt"));
        musicCheckboxFont.getData().setScale(0.5f, 0.5f);
        volumeSoundFont = new BitmapFont(Gdx.files.internal("setting_skin/font-big-export.fnt"));
        volumeSoundFont.getData().setScale(0.5f, 0.5f);
        soundCheckboxFont = new BitmapFont(Gdx.files.internal("setting_skin/font-big-export.fnt"));
        soundCheckboxFont.getData().setScale(0.5f, 0.5f);
        title = new BitmapFont(Gdx.files.internal("setting_skin/carterone.fnt"));
        title.getData().setScale(2f, 2f);

        TextButton backButton = new TextButton("Back", skin, "default");
        backButton.setPosition(600, 70);
        backButton.setWidth(200);
        backButton.setHeight(100);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HomeScreen(game, audioSettings));
            }
        });

        musicCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audioSettings.toggleMusic();
            }
        });

        soundCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audioSettings.toggleSoundEffects();
            }
        });

        volumeMusicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float sliderVolume = volumeMusicSlider.getValue();

                audioSettings.setMusicVolume(volumeMusicSlider.getValue());
                if (sliderVolume == 0.0f) {
                    audioSettings.stopMusic();
                } else {
                    audioSettings.startMusic();
                }
            }
        });

        volumeSoundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float sliderVolume = volumeSoundSlider.getValue();

                audioSettings.setSoundEffectsVolume(volumeSoundSlider.getValue());
                if (sliderVolume == 0.0f) {
                    audioSettings.stopSoundEffects();
                } else {
                    audioSettings.startSoundEffects();
                }
            }
        });

        stage.addActor(bg);
        stage.addActor(volumeMusicSlider);
        stage.addActor(volumeSoundSlider);
        stage.addActor(musicCheckbox);
        stage.addActor(soundCheckbox);
        stage.addActor(backButton);


        Gdx.input.setInputProcessor(stage);

    }

    /**
     * The rendering method for the setting screen
     *
     * @param delta The rendering time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        batch.begin();
        volumeMusicFont.draw(batch, "Music Volume", 500, 380);
        musicCheckboxFont.draw(batch, "Music", 500, 330);
        volumeSoundFont.draw(batch, "Sound Volume", 500, 280);
        soundCheckboxFont.draw(batch, "Sound", 500, 230);
        title.draw(batch, "Settings", 580, 600);

        batch.end();

        soundCheckbox.setChecked(audioSettings.playingSoundEffects());
        musicCheckbox.setChecked(audioSettings.playingMusic());

        if (audioSettings.playingMusic()) {
            Audio.backgroundMusic.play();
            Audio.backgroundMusic.setVolume(audioSettings.getMusicVolume());
        } else {
            Audio.backgroundMusic.stop();
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    /**
     * Method which clears the screen
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        title.dispose();
        volumeMusicFont.dispose();
        musicCheckboxFont.dispose();
        volumeSoundFont.dispose();
        soundCheckboxFont.dispose();
        Audio.backgroundMusic.dispose();
    }

}
