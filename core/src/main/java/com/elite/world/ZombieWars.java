package com.elite.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.elite.ui.HomeScreen;

/*
@author Minhal Khan
*/
@Deprecated
public class ZombieWars extends Game {

    private SpriteRenderer spriteRenderer;
    private HomeScreen homeScreen;

    @Override
    public void create(){
        spriteRenderer = new SpriteRenderer();
        setScreen(spriteRenderer);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
        spriteRenderer.render(Gdx.graphics.getDeltaTime());


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }



}
