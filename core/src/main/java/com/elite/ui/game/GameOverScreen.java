package com.elite.ui.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.elite.ui.menu.HomeScreen;

/**
 * Create object for game over screen
 *
 * @author WEI-TSE CHENG
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;


    /**
     * The method which creates the game over Screen
     */
    @Override
    public void show() {
        viewport = new FitViewport(1580, 800, new OrthographicCamera());
        stage = new Stage(viewport);
        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label MenuScreenLabel = new Label("Click to Menu Screen", font);

        table.add(gameOverLabel);
        table.row();
        table.add(MenuScreenLabel);


        stage.addActor(table);


    }

    /**
     * Method for rendering the game over screen
     *
     * @param delta The render time
     */
    @Override
    public void render(float delta) {


        final HomeScreen homeScreen = new HomeScreen();
        if (Gdx.input.justTouched()) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(homeScreen);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}