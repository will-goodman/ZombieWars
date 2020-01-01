package com.elite.ui.multiplayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.elite.audio.Audio;
import com.elite.audio.AudioSettings;
import com.elite.game.multiplayer.ClientGame;
import com.elite.network.client.Client;
import com.elite.ui.ZombieWars;
import com.elite.ui.menu.HomeScreen;

import java.util.ArrayList;


/**
 * UI which shows all available multiplayer servers to join.
 *
 * @author Will Goodman
 */
public class ServerListing implements Screen {


    public static final float WORLD_WIDTH = 1920;
    public static final float WORLD_HEIGHT = 1080;


    private final Viewport VIEWPORT = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT);
    private final Stage STAGE = new Stage(VIEWPORT);
    private final OrthographicCamera CAMERA = new OrthographicCamera(1920, 1080);
    private final SpriteBatch BATCH = new SpriteBatch();
    private final Texture BACKGROUND_IMG = new Texture(Gdx.files.internal("maps/BG.png"));
    private final TextureRegion BACKGROUND_REGION = new TextureRegion(BACKGROUND_IMG, 0, 0, 1920, 1080);
    private final ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();
    private final Client CLIENT;
    private final Texture UP_TEXTURE = new Texture(Gdx.files.internal("buttonone.png"));
    private final Texture DOWN_TEXTURE = new Texture(Gdx.files.internal("buttontwo.png"));
    private final Texture LOCKED_TEXTURE = new Texture(Gdx.files.internal("Locked.png"));
    private final Texture UNLOCKED_TEXTURE = new Texture(Gdx.files.internal("Unlocked.png"));


    private ArrayList<Button> connectButtons = new ArrayList<>();
    private AudioSettings audioSettings;
    private final ZombieWars game;


    /**
     * The Constructor of the server screen
     *
     * @param client The Client on whose screen the home screen will be rendered
     */
    public ServerListing(final ZombieWars game, Client client, AudioSettings audioSettings) {
        this.game = game;
        this.CLIENT = client;
        this.audioSettings = audioSettings;

        Gdx.input.setInputProcessor(STAGE);

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(UP_TEXTURE));
        style.down = new TextureRegionDrawable(new TextureRegion(DOWN_TEXTURE));

    }

    /**
     * The method which creates the Server Screen
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(STAGE);
        ArrayList<String> servers = CLIENT.getServers();
        for (String server : servers) {
            System.out.println(server);
        }

        Skin menuSkin = new Skin(Gdx.files.internal("menu_skin/menuButton.json"));
        TextButton menuButton = new TextButton("︎Menu", menuSkin, "carterone");
        menuButton.setPosition((float) 0.03 * CAMERA.viewportWidth, (float) 0.028 * CAMERA.viewportHeight);
        menuButton.setWidth(200f);
        menuButton.setHeight(80);

        TextButton refreshButton = new TextButton("︎Refresh", menuSkin, "carterone");
        refreshButton.setPosition((float) 0.2 * CAMERA.viewportWidth, (float) 0.028 * CAMERA.viewportHeight);
        refreshButton.setWidth(200f);
        refreshButton.setHeight(80);

        TextButton hostButton = new TextButton("︎Host", menuSkin, "carterone");
        hostButton.setPosition((float) 0.37 * CAMERA.viewportWidth, (float) 0.028 * CAMERA.viewportHeight);
        hostButton.setWidth(200f);
        hostButton.setHeight(80);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HomeScreen(game, audioSettings));
                dispose();
            }
        });

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerListing(game, CLIENT, audioSettings));
                dispose();
            }
        });

        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //host lobby
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LobbyCreation(game, CLIENT, audioSettings));
                dispose();
            }
        });


        STAGE.addActor(menuButton);
        STAGE.addActor(refreshButton);
        STAGE.addActor(hostButton);

        if (audioSettings.playingMusic()) {
            Audio.backgroundMusic.play();
            Audio.backgroundMusic.setVolume(audioSettings.getMusicVolume());
        }

    }

    /**
     * Resize the UI to fit the window size.
     *
     * @param width  The width of the window in pixels.
     * @param height The height of the window in pixels.
     */
    @Override
    public void resize(int width, int height) {
        VIEWPORT.update(width, height);
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
     * The rendering method for the server screen
     *
     * @param delta The rendering time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        BATCH.setProjectionMatrix(CAMERA.combined);
        BATCH.begin();

        BATCH.draw(BACKGROUND_REGION, 0, 0, CAMERA.viewportWidth, CAMERA.viewportHeight);

        BATCH.end();

        //Draw the translucent rectangle for the server browser
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        SHAPE_RENDERER.setProjectionMatrix(CAMERA.combined);
        SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        SHAPE_RENDERER.setColor(1, 1, 1, 0.3f);
        SHAPE_RENDERER.rect((float) (0.1 * CAMERA.viewportWidth), (float) (0.1 * CAMERA.viewportHeight), (float) (0.8 * CAMERA.viewportWidth), (float) (0.8 * CAMERA.viewportHeight));
        SHAPE_RENDERER.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        //Title
        Label label1 = new Label("Servers", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label1.setSize(Gdx.graphics.getWidth(), 100);
        label1.setPosition(20, 950);
        label1.setAlignment(Align.topLeft);
        label1.setFontScale(2);
        STAGE.addActor(label1);

        //Server listing (table)
        Skin skin = new Skin(Gdx.files.internal("skins/flat-earth-ui.json"));
        Table servers = new Table();
        servers.row().size((float) (0.4 * CAMERA.viewportWidth), (float) (0.05 * CAMERA.viewportHeight));
        Label serverName = new Label("Server Name", skin);
        Label numPlayers = new Label("Number of Players", skin);
        serverName.setFontScale(2);
        numPlayers.setFontScale(2);

        ArrayList<String> listing = CLIENT.getServers();

        //must create a connection button and listener for each lobby
        Button connect;
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(UP_TEXTURE));
        style.down = new TextureRegionDrawable(new TextureRegion(DOWN_TEXTURE));
        connectButtons.clear();
        for (String server : listing) {
            connect = new Button(style);
            connectButtons.add(connect);
        }
        int countLobbies = 0;
        for (Button button : connectButtons) {
            final int lobbyNum = countLobbies;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(listing.get(lobbyNum));
                    String[] lobbyDetails = listing.get(lobbyNum).split(" ");
                    if (lobbyDetails[2].equals("false")) {
                        ClientGame clientGame = new ClientGame(game, CLIENT, "Player2", audioSettings);
                        CLIENT.setGame(clientGame);


                        ((Game) Gdx.app.getApplicationListener()).setScreen(clientGame);
                        dispose();

                        CLIENT.connectLobby(lobbyDetails[0] + lobbyDetails[1], "Player2");

                        CLIENT.start();
                    } else {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new PasswordScreen(game, CLIENT, lobbyDetails, audioSettings));
                        dispose();
                    }

                }
            });
            countLobbies++;
        }

        //Make a row for each server
        int countServers = 0;
        for (String server : listing) {
            servers.row();
            servers.row().size(connectButtons.get(countServers).getWidth(), connectButtons.get(countServers).getHeight());

            String[] serverDetails = server.split(" ");
            if (serverDetails[2].equals("false")) {
                Image unlockedImage = new Image(UNLOCKED_TEXTURE);
                unlockedImage.setSize(165, 263);
                servers.add(unlockedImage);
            } else {
                Image lockedImage = new Image(LOCKED_TEXTURE);
                lockedImage.setSize(164, 235);
                servers.add(lockedImage);
            }
            Label lobbyName = new Label(serverDetails[0], skin);
            lobbyName.setFontScale((float) 1.5);
            servers.add(lobbyName);
            Label lobbyPlayers = new Label(serverDetails[1], skin);
            lobbyName.setFontScale((float) 1.5);
            servers.add(lobbyPlayers);
            servers.add(connectButtons.get(countServers));
            countServers++;
        }

        servers.setPosition((float) (0.5 * CAMERA.viewportWidth), (float) (0.9 * CAMERA.viewportHeight));
        servers.top();
        STAGE.addActor(servers);


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
        Audio.backgroundMusic.dispose();
    }
}
