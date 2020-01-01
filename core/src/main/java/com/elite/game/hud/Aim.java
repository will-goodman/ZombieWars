package com.elite.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.elite.game.entities.characters.ZombieAttributes;

import java.util.ArrayList;


/**
 * @author Minhal Khan
 */
public class Aim {

    //Aim attributes
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int MAX_CIRCLES = 10; //aim length
    //Aim attributes
    private Texture dotTexture = new Texture(Gdx.files.internal("aim.png"));

    //Arrow
    private static final float ARROW_WIDTH = 20; //px
    private static final float ARROW_HEIGHT = 25; //px
    private Texture arrowTexture = new Texture(Gdx.files.internal("arrow.png"));
    private ArrayList<Sprite> arrowSprite = new ArrayList<>();

    private ArrayList<Sprite> aimDots = new ArrayList<>();
    private Line plane = new Line();


    /**
     * Defining a line for the aim.
     */
    public class Line {
        public float thita = 0;
        public float m = 0; //default values
        public float c = 0; //default values

        /**
         * Update the equation of the line.
         *
         * @param characterPosition The 2D vector of the character where the line originates.
         * @param mousePosition     The 2D vector of the mouse where the line ends.
         */
        public void setParameters(Vector2 characterPosition, Vector3 mousePosition) {
            this.m = ((mousePosition.y - characterPosition.y) / (characterPosition.x - mousePosition.x)); //calculate dy/dx = y2-y1/x2-x1
            this.c = characterPosition.y - (m * characterPosition.x); //Set C
        }

        /**
         * Resolves the point on a line at a given x-coordinate.
         *
         * @param x The x-coordinate at which to resolve the point on the line.
         * @return The y-coordinate where (x, y) is a point on the line.
         */
        public float getY(float x) {
            return m * x + c;
        }

    }

    /**
     * Getter method for the bullet velocity
     *
     * @return The velocity value
     */
    public float getVelocity() {
        if (this.aimDots.size() > 0) {
            return this.plane.m * 9690f;
        }
        return 0f;
    }

    /**
     * The getter method for the angle of aim
     *
     * @return The angle of aim
     */
    public float getAngle() {
        return plane.thita;
    }

    /**
     * The method which creates an aim indicator in the game by drawing a series of dots in a straight line.
     * The aim method will be used when grenades are thrown, as they will be thrown in the direction of the aim
     *
     * @param isLeft            if the sprite is facing the left direction
     * @param characterPosition position of the sprite in the game
     * @param mousePosition     position of the mouse within the game
     */
    public void addAim(Boolean isLeft, Vector2 characterPosition, Vector3 mousePosition) {
        arrowSprite.add(new Sprite(arrowTexture));
        arrowSprite.get(0).setBounds(characterPosition.x + (int) ((double) ZombieAttributes.ZOMBIE_WIDTH / (double) 2), characterPosition.y + ZombieAttributes.ZOMBIE_HEIGHT + HealthBar.HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
        float x = characterPosition.x + (int) ((double) ZombieAttributes.ZOMBIE_HEIGHT / (double) 2) + 10; //gets mid value
        if (isLeft) {
            x -= ((double) ZombieAttributes.ZOMBIE_WIDTH / (double) 2) + 35; //flip first circle
        }
        float y = characterPosition.y + (int) ((double) ZombieAttributes.ZOMBIE_HEIGHT / (double) 3) + 15;
        characterPosition.x = x;
        characterPosition.y = y;

        float xDistance = mousePosition.x - characterPosition.x;
        float yDistance = mousePosition.y - characterPosition.y;
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance)) + 10;
        //Calculating plane to mouse
        this.plane.setParameters(characterPosition, mousePosition);
        this.plane.thita = (float) Math.toDegrees(Math.atan2(yDistance, xDistance));

        //width of aim is 10px
        int circles = (int) (distance / 15);
        if (circles > MAX_CIRCLES) {
            circles = MAX_CIRCLES;
        }

        if ((isLeft && mousePosition.x > x) || (!isLeft && mousePosition.x < x)) {
            circles = 0;
        }

        this.aimDots.clear();
        for (int i = 0; i < circles; i++) {
            Sprite s = new Sprite(dotTexture);
            s.setSize(WIDTH, HEIGHT);
            if (isLeft) { //set negative x values
                x -= 15;
            } else
                x += 15;

            float newY = plane.getY(x - 15);
            s.setPosition(x, newY);
            this.aimDots.add(s);
        }
    }

    /**
     * The method which removes the aim indicator
     */
    public void removeAim() {
        this.aimDots.clear();
        this.arrowSprite.clear();
    }

    /**
     * The method which draws an arrow indicating the direction of the aim
     *
     * @param batch    The Sprite set
     * @param walkLeft The direction the zombie is facing
     */
    public void drawArrow(SpriteBatch batch, boolean walkLeft) {
        if (this.arrowSprite.size() > 0) {
            if (walkLeft) {
                this.arrowSprite.get(0).setPosition(this.arrowSprite.get(0).getX() - 25, this.arrowSprite.get(0).getY());
            }
            this.arrowSprite.get(0).draw(batch);
        }
    }

    /**
     * The method which draws the aim dots
     *
     * @param batch The Sprite set
     */
    //Batch must begin before calling this method
    public void drawDots(SpriteBatch batch) {
        //draw arrow
        for (Sprite aimDot : this.aimDots) {
            aimDot.draw(batch); //draw arrow
        }
    }


}

