package com.elite.world;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.elite.game.GameType;

/**
 * @author Jacob Wheale
 */
public class MyInputProcessor implements InputProcessor {

    private GameType renderer;

    public MyInputProcessor(GameType renderer) {
        this.renderer = renderer;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT && (renderer.getClass() == SpriteRenderer.class)) {
            if (renderer.getAllPlayers().get(renderer.getPlayerNow()).getIsPlayerControlled()) {
                renderer.getAllPlayers().get(renderer.getPlayerNow()).vy = renderer.getAllPlayers().get(renderer.getPlayerNow()).getVelocity();
                renderer.getAllPlayers().get(renderer.getPlayerNow()).shoot();
            }
            renderer.reduceEnergy(20);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
