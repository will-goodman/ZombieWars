package com.elite.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Renders the world for a game.
 *
 * @author Minhal Khan
 */
public class RenderWorld implements Screen {

    protected TiledMap map;
    protected OrthographicCamera camera;

    private OrthogonalTiledMapRenderer renderer;
    private Vector3 mouse_position = new Vector3(0, 0, 0);
    private FitViewport viewport;

    public SpriteBatch spriteBatch = new SpriteBatch();

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

        map = new TmxMapLoader().load("maps/finalmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        viewport = new FitViewport(WorldAttributes.WORLD_WIDTH, WorldAttributes.WORLD_HEIGHT, camera);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        viewport.getCamera().position.set(w, h, 0);
        camera.update();

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse_position);

    }

    /**
     * Resizes the UI to fit the window size.
     *
     * @param width  The width of the window in pixels.
     * @param height The height of the window in pixels.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {


    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
