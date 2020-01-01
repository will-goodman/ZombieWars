package com.elite.ui.menu;

import com.badlogic.gdx.*;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.elite.audio.AudioSettings;
import com.elite.network.client.Client;
import com.elite.game.singleplayer.SpriteRenderer;
import com.elite.ui.multiplayer.ServerListing;
import com.elite.ui.settings.SettingScreen;
import com.elite.audio.Audio;

/**
 * Create object for home screen
 *
 * @author WEI-TSE CHENG
 */
public class HomeScreen implements Screen {

    private Stage stage;
    private Skin menuSkin;
    private Texture bgTexture;
    private SpriteBatch batch;
    private BitmapFont menuTitle;

    private AudioSettings audioSettings;

    public HomeScreen(AudioSettings audioSettings) {
        this.audioSettings = audioSettings;
    }

    /**
     * The method which creates the Home Screen
     */
    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setTitle("Zombie Wars");

        menuSkin = new Skin(Gdx.files.internal("menu_skin/menuButton.json"));


        bgTexture = new Texture(Gdx.files.internal("maps/BG.png"));
        Image bg = new Image(bgTexture);

        batch = new SpriteBatch();
        menuTitle = new BitmapFont(Gdx.files.internal("menu_skin/carterone.fnt"));
        menuTitle.getData().setScale(2f, 2f);

        TextButton startButton = new TextButton("Multiplayer", menuSkin, "carterone");
        startButton.setPosition(450, 350);
        startButton.setWidth(200f);
        startButton.setHeight(80);
        TextButton settingsButton = new TextButton("Settings", menuSkin, "carterone");
        settingsButton.setPosition(625, 250);
        settingsButton.setWidth(200f);
        settingsButton.setHeight(80);
        TextButton exitButton = new TextButton("Exit", menuSkin, "carterone");
        exitButton.setPosition(625, 50);
        exitButton.setWidth(200f);
        exitButton.setHeight(80);
        TextButton singlePlayerButton = new TextButton("Singleplayer", menuSkin, "carterone");
        singlePlayerButton.setPosition(800, 350);
        singlePlayerButton.setWidth(200f);
        singlePlayerButton.setHeight(80);


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Client client = new Client();
                if (client.getConnectionStatus()) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerListing(client, audioSettings));
                    dispose();
                }
            }
        });

        singlePlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SpriteRenderer());
                dispose();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingScreen(audioSettings));
                dispose();

            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        stage.addActor(bg);
        stage.addActor(startButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);
        stage.addActor(singlePlayerButton);

        if (audioSettings.playingMusic()) {
            Audio.backgroundMusic.play();
            Audio.backgroundMusic.setVolume(audioSettings.getMusicVolume());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * The rendering method for the home screen
     *
     * @param delta The rendering time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.draw();

        batch.begin();

        menuTitle.draw(batch, "Zombie Wars", 525, 600);

        batch.end();
    }

    /**
     * Method which clears the screen
     */
    @Override
    public void dispose() {

        stage.dispose();
        menuSkin.dispose();
        batch.dispose();
        bgTexture.dispose();
        menuTitle.dispose();

        Audio.backgroundMusic.dispose();

    }


}