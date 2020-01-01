package com.elite.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elite.entities.characters.ZombieAttributes;


/**
 * Displays the remaining health of a zombie.
 *
 * @author Minhal Khan
 */
public class HealthBar {

    private static final float WIDTH_SPACING = 25;
    private static final float HEIGHT_SPACING = 17;
    public static final float WIDTH = ZombieAttributes.ZOMBIE_WIDTH - WIDTH_SPACING;
    public static final float HEIGHT = 15; //20px height

    private Texture texture = new Texture(Gdx.files.internal("HealthBar.png"));
    private Sprite sprite = new Sprite(texture);
    //Team name text
    private BitmapFont font = new BitmapFont();

    /**
     * Constructor method for the health bar of the zombies
     *
     * @param x x coordinate of the character
     * @param y y coordinate of the character
     */
    public HealthBar(float x, float y) {
        updatePosition(x + WIDTH_SPACING, y + ZombieAttributes.ZOMBIE_HEIGHT - HEIGHT_SPACING, 100, 1); //y up coordinate system hence we add the height for the bar to be on top of the user
        sprite.setSize(WIDTH, HEIGHT);
    }

    /**
     * The method which takes care of the health bar's positioning
     *
     * @param x        The x coordinate of the zombie
     * @param y        The y coordinate of the zombie
     * @param health   The health left
     * @param userData The character whose health bar it is
     */
    public void updatePosition(float x, float y, float health, int userData) {
        updateHealth(health);
        sprite.setPosition(x + WIDTH_SPACING, y + ZombieAttributes.ZOMBIE_HEIGHT - HEIGHT_SPACING); //y up coordinate system hence we add the height for the bar to be on top of the user
        if (userData > 100) {
            //Team 2
            font.setColor(Color.WHITE);
        } else {
            font.setColor(Color.GREEN);
        }
    }

    /**
     * The method which updates the health bar width
     *
     * @param health health out of 100 of character, used to alter the width of the bar
     */
    public void updateHealth(float health) {
        sprite.setSize(WIDTH - 100 + health, HEIGHT);
    }

    /**
     * Method which renders the health bar into the world
     *
     * @param batch    The Sprite for the bar
     * @param userdata The character whose health bar it is
     * @param isLeft   The direction the character is facing
     */
    public void drawHealthBar(SpriteBatch batch, int userdata, boolean isLeft) {
        String team = "Team ";
        if (userdata > 100) {
            //Team 1
            team += "1";
        } else {
            team += "2";
        }
        if (isLeft) {
            sprite.setPosition(sprite.getX() - 30, sprite.getY());
        }
        font.draw(batch, team, sprite.getX() + 22, sprite.getY() + 30);
        sprite.draw(batch);
    }


}
