package com.elite.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.elite.game.ClientGame;
import com.elite.network.Client;

/**
 * UI for creating a multiplayer lobby.
 *
 * @author Will Goodman
 */
public class LobbyCreation implements Screen {

    public static final float WORLD_WIDTH = 1920;
    public static final float WORLD_HEIGHT = 1080;

    private final Client CLIENT;
    private final Viewport VIEWPORT = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT);
    private final Stage STAGE = new Stage(VIEWPORT);
    private final OrthographicCamera CAMERA = new OrthographicCamera(1920, 1080);
    private final SpriteBatch BATCH = new SpriteBatch();
    private final Texture BACKGROUND_IMG = new Texture(Gdx.files.internal("maps/BG.png"));
    private final TextureRegion BACKGROUND_REGION = new TextureRegion(BACKGROUND_IMG, 0, 0, 1920, 1080);
    private final Texture UP_TEXTURE = new Texture(Gdx.files.internal("buttonone.png"));
    private final Texture DOWN_TEXTURE = new Texture(Gdx.files.internal("buttontwo.png"));

    private TextField serverNameTxt;
    private TextField hostNameTxt;
    private CheckBox privateChkBox;
    private TextField passwordTxt;
    private Label passwordLabel;


    /**
     * The Constructor of the lobby screen
     *
     * @param client The Client on whose screen the home screen will be rendered
     */
    public LobbyCreation(Client client) {
        this.CLIENT = client;
    }

    /**
     * The method which creates the Lobby Screen
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(STAGE);

        Skin skin = new Skin(Gdx.files.internal("skins/flat-earth-ui.json"));
        serverNameTxt = new TextField("", skin);
        serverNameTxt.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.8 * CAMERA.viewportHeight);
        serverNameTxt.setSize((float) 0.6 * CAMERA.viewportWidth, (float) 0.05 * CAMERA.viewportHeight);
        STAGE.addActor(serverNameTxt);

        hostNameTxt = new TextField("", skin);
        hostNameTxt.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.7 * CAMERA.viewportHeight);
        hostNameTxt.setSize((float) 0.6 * CAMERA.viewportWidth, (float) 0.05 * CAMERA.viewportHeight);
        STAGE.addActor(hostNameTxt);

        privateChkBox = new CheckBox("Private Lobby?", skin);
        privateChkBox.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.6 * CAMERA.viewportHeight);
        STAGE.addActor(privateChkBox);

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(UP_TEXTURE));
        style.down = new TextureRegionDrawable(new TextureRegion(DOWN_TEXTURE));

        privateChkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (privateChkBox.isChecked()) {
                    passwordTxt = new TextField("", skin);
                    passwordTxt.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.5 * CAMERA.viewportHeight);
                    passwordTxt.setSize((float) 0.6 * CAMERA.viewportWidth, (float) 0.05 * CAMERA.viewportHeight);
                    STAGE.addActor(passwordTxt);

                    passwordLabel = new Label("Password:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
                    passwordLabel.setSize(Gdx.graphics.getWidth(), 100);
                    passwordLabel.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.525 * CAMERA.viewportHeight);
                    passwordLabel.setFontScale(2);
                    STAGE.addActor(passwordLabel);
                } else {
                    passwordTxt.setDisabled(true);
                }
            }
        });

        Skin menuSkin = new Skin(Gdx.files.internal("menu_skin/menuButton.json"));

        TextButton backButton = new TextButton("︎Back", menuSkin, "carterone");
        backButton.setPosition((float) 0.1 * CAMERA.viewportWidth, (float) 0.1 * CAMERA.viewportHeight);
        backButton.setWidth(200f);
        backButton.setHeight(80);

        TextButton createButton = new TextButton("︎Create", menuSkin, "carterone");
        createButton.setPosition((float) 0.25 * CAMERA.viewportWidth, (float) 0.1 * CAMERA.viewportHeight);
        createButton.setWidth(200f);
        createButton.setHeight(80);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerListing(CLIENT));
                dispose();
            }
        });

        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String lobbyName = serverNameTxt.getText();
                String hostName = hostNameTxt.getText();

                ClientGame game = new ClientGame(CLIENT, hostName);
                CLIENT.setGame(game);
                ((Game) Gdx.app.getApplicationListener()).setScreen(game);
                dispose();

                if (privateChkBox.isChecked()) {
                    CLIENT.hostLobby(lobbyName, hostName, passwordTxt.getText());
                } else {
                    CLIENT.hostLobby(lobbyName, hostName);
                }

                CLIENT.start();
            }
        });

        STAGE.addActor(backButton);
        STAGE.addActor(createButton);

    }

    /**
     * Resize the UI to match the window size.
     *
     * @param width  The width of the window in pixels.
     * @param height The height of the window in pixels.
     */
    @Override
    public void resize(int width, int height) {
        VIEWPORT.update(width, height);
        System.out.println(width + " " + height);
        CAMERA.position.set(CAMERA.viewportWidth / 2, CAMERA.viewportHeight / 2, 0);
        CAMERA.update();
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
     * Renders the lobby creation screen.
     *
     * @param delta The rendering time.
     */
    @Override
    public void render(float delta) {
        BATCH.setProjectionMatrix(CAMERA.combined);
        BATCH.begin();

        BATCH.draw(BACKGROUND_REGION, 0, 0, CAMERA.viewportWidth, CAMERA.viewportHeight);

        BATCH.end();

        Label label1 = new Label("Server Name:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label1.setSize(Gdx.graphics.getWidth(), 100);
        label1.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.825 * CAMERA.viewportHeight);
        label1.setFontScale(2);
        STAGE.addActor(label1);

        Label label2 = new Label("Player Name:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label2.setSize(Gdx.graphics.getWidth(), 100);
        label2.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.725 * CAMERA.viewportHeight);
        label2.setFontScale(2);
        STAGE.addActor(label2);


        STAGE.act();
        STAGE.draw();
    }

    /**
     * Method which clears the screen
     */
    @Override
    public void dispose() {
        STAGE.dispose();
        BATCH.dispose();
        BACKGROUND_IMG.dispose();
    }
}
