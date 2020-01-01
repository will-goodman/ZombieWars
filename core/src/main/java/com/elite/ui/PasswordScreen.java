package com.elite.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.elite.game.ClientGame;
import com.elite.network.client.Client;

/**
 * Create object for password screen
 *
 * @author WEI-TSE CHENG
 */
public class PasswordScreen implements Screen {

    private final Client CLIENT;
    private final String[] lobbyDetails;

    private SpriteBatch batch = new SpriteBatch();
    private final OrthographicCamera CAMERA = new OrthographicCamera(1920, 1080);
    private final Texture BACKGROUND_IMG = new Texture(Gdx.files.internal("maps/BG.png"));
    private final TextureRegion BACKGROUND_REGION = new TextureRegion(BACKGROUND_IMG, 0, 0, 1920, 1080);
    private Stage stage;
    private TextField textField;

    /**
     * The Constructor of the password screen
     *
     * @param client The Client on whose screen the home screen will be rendered
     */
    public PasswordScreen(Client client, String[] lobbyDetails) {
        this.CLIENT = client;
        this.lobbyDetails = lobbyDetails;
    }

    /**
     * The method which creates the Password Screen
     */
    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin menuSkin = new Skin(Gdx.files.internal("menu_skin/menuButton.json"));
        Skin skin = new Skin(Gdx.files.internal("skins/flat-earth-ui.json"));

        textField = new TextField("", skin);
        textField.setPosition(300, 300);

        TextButton backButton = new TextButton("Back", menuSkin, "carterone");
        backButton.setPosition(625, 350);
        backButton.setWidth(200f);
        backButton.setHeight(80);

        TextButton submitButton = new TextButton("Submit", menuSkin, "carterone");
        submitButton.setPosition(625, 450);
        submitButton.setWidth(200f);
        submitButton.setHeight(80);


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //host lobby
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerListing(CLIENT));
                dispose();
            }
        });

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String password = textField.getText();
                String lobbyName = lobbyDetails[0] + lobbyDetails[1];

                if (CLIENT.verifyPassword(lobbyName, "Player2", password)) {
                    ClientGame game = new ClientGame(CLIENT, "Player2");
                    CLIENT.setGame(game);


                    ((Game) Gdx.app.getApplicationListener()).setScreen(game);
                    dispose();

                    CLIENT.connectLobby(lobbyDetails[0] + lobbyDetails[1], "Player2");

                    CLIENT.start();
                } else {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerListing(CLIENT));
                    dispose();
                }

            }
        });

        stage.addActor(textField);
        stage.addActor(backButton);
        stage.addActor(submitButton);

    }

    /**
     * The rendering method for the password screen
     *
     * @param delta The rendering time
     */
    @Override
    public void render(float delta) {

        batch.begin();

        batch.draw(BACKGROUND_REGION, 0, 0, CAMERA.viewportWidth, CAMERA.viewportHeight);

        batch.end();

        stage.act();

        stage.draw();

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

    }

    /**
     * Method which clears the screen
     */
    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

}
