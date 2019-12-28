package com.elite.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elite.world.WorldAttributes;

public class EnergyBar {

    private Texture energyIconTexture = new Texture(Gdx.files.internal("energyIcon.png"));
    private Sprite energyIconSprite = new Sprite(energyIconTexture);

    private Texture energyTexture = new Texture(Gdx.files.internal("energy.png"));
    private Sprite energySprite = new Sprite(energyTexture);

    /**
     * The Constructor method for the Energy Bar
     */
    public EnergyBar(){
        energyIconSprite.setSize(20,20);
    }

    /**
     * The render method for the Energy Bar
     * @param energy The enery left
     * @param batch The Sprite for the bar
     */
    public void renderEnergy(double energy, SpriteBatch batch){
        if(energy <= 100){
            energySprite.setSize(200f - 100 + (float) energy, 20); //- (float) energy
            this.energySprite.setPosition(WorldAttributes.WORLD_WIDTH - 1000 - 200f, WorldAttributes.WORLD_HEIGHT - 520);
            energyIconSprite.setPosition(this.energySprite.getX()-20,this.energySprite.getY());
            energySprite.draw(batch);
        }
        energyIconSprite.draw(batch);
    }



}
