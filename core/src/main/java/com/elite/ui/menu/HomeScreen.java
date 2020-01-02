package com.elite.ui.menu;

import com.badlogic.gdx.*;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.elite.audio.AudioSettings;
import com.elite.network.client.Client;
import com.elite.game.singleplayer.SpriteRenderer;
import com.elite.ui.ZombieWars;
import com.elite.ui.multiplayer.ServerListing;
import com.elite.ui.settings.SettingScreen;
import com.elite.audio.Audio;

/**
 * Create object for home screen
 *
 * @author WEI-TSE CHENG
 */
public class HomeScreen implements Screen {

    private Skin menuSkin;
    private Texture bgTexture;
    private BitmapFont menuTitle;

    private AudioSettings audioSettings;

    private OrthographicCamera camera;
    private Stage stage;
    private final ZombieWars game;

    public HomeScreen(final ZombieWars game, AudioSettings audioSettings) {
        this.game = game;
        this.audioSettings = audioSettings;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.stage = new Stage(new ScreenViewport());
    }

    /**
     * The method which creates the Home Screen
     */
    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setTitle("Zombie Wars");

        menuSkin = new Skin(Gdx.files.internal("menu_skin/menuButton.json"));

        bgTexture = new Texture(Gdx.files.internal("maps/BG.png"));
        Image bg = new Image(bgTexture);

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
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerListing(game, client, audioSettings));
                    dispose();
                }
            }
        });

        singlePlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SpriteRenderer(game, audioSettings));
                dispose();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingScreen(game, audioSettings));
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

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        stage.draw();

        game.batch.begin();

        menuTitle.draw(game.batch, "Zombie Wars", 525, 600);

        game.batch.end();

        if (audioSettings.playingMusic()) {
            Audio.backgroundMusic.play();
            Audio.backgroundMusic.setVolume(audioSettings.getMusicVolume());
        }
    }

    /**
     * Method which clears the screen
     */
    @Override
    public void dispose() {

        stage.dispose();
        menuSkin.dispose();
        bgTexture.dispose();
        menuTitle.dispose();
    }


}