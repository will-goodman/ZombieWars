package com.elite.entities.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.elite.entities.characters.Zombie;

/**
 * Bullet entity fired by Zombies.
 *
 * @author Jacob Wheale
 */
public class Bullet {

    private static Texture texture;
    private static World world;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 15;

    private float x, y;
    private boolean remove = false;
    private boolean left;
    private Body body;
    private Zombie owner;

    float rotation; //rotation matrix of the bullet

    /**
     * Getter method for variable 'body'.
     *
     * @return The physics Body that represents this bullet.
     */
    public Body getBody() {
        return this.body;
    }

    //TODO Look at removing method

    /**
     * Getter method for variable 'owner'
     *
     * @return The Zombie which shot this bullet.
     */
    public Zombie getOwner() {
        return this.owner;
    }

    /**
     * This method returns the value of the boolean variable 'remove'
     *
     * @return The boolean value
     */
    public boolean getRemove() {
        return remove;
    }

    /**
     * This method sets the value of the boolean variable 'remove'
     *
     * @param flag boolean parameter which sets the value
     */
    public void setRemove(boolean flag) {
        remove = flag;
    }


    /**
     * @param world            The physics world which holds all of the objects in the game.
     * @param owner            The Zombie which shot this particulr bullet.
     * @param x                The X co-ordinate of where the bullet will start.
     * @param y                The Y co-ordinate of where the bullet will start.
     * @param left             boolean for if the player is facing left or not.
     * @param vy               The Y velocity component of the bullet to determine the angle of shot.
     * @param playerControlled Whether the Zombie that shot this bullet is AI or not.
     * @param thita            The rotation of the bullet.
     */
    public Bullet(World world, Zombie owner, float x, float y, boolean left, float vy, boolean playerControlled, float thita) {
        Bullet.world = world;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.left = left;

        if (texture == null) {
            texture = new Texture("bullet.png");
        }

        this.rotation = thita;

        if (!playerControlled)
            rotation = 0;

        createBody(vy);
    }

    /**
     * Method which creates the physical representation of the bullet in the world.
     *
     * @param vy The Y velocity of the bullet which is used to give a shooting angle.
     */
    private void createBody(float vy) {
        // create a physics body for the grenade
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        PolygonShape grenade_shape = new PolygonShape();
        grenade_shape.setAsBox(WIDTH / 2f, HEIGHT / 2f);


        // define the properties of the grenade e.g how much it bounces.
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.01f;
        fixtureDef.shape = grenade_shape;

        // put the grenade in the world and reduce gravity impact.
        body = world.createBody(bodyDef);
        body.setBullet(true);
        body.setGravityScale(0f);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(50);
        grenade_shape.dispose();


        if (left) {
            body.setLinearVelocity(-10000f, vy);
        } else {
            body.setLinearVelocity(10000f, vy);
        }
    }

    /**
     * Method to update the visual representation of the bullet from the physical one.
     */
    private void update() {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    /**
     * Method to check if the bullet has gone off the map, if so we remove it.
     */
    public void checkRemove() {
        if (body.getPosition().x < 0 || body.getPosition().x > Gdx.graphics.getWidth()) {
            remove = true;
        }
    }


    /**
     * This method renders the bullet on the screen, rotating the image if facing left.
     *
     * @param batch The SpriteBatch object responsible for rendering.
     */
    public void render(SpriteBatch batch) {
        if (left) {
            batch.draw(texture, x, y, WIDTH / 2f, HEIGHT / 2f, WIDTH, HEIGHT, 1, 1, -rotation, 0, 0, WIDTH, HEIGHT, false, false);
        } else {
            batch.draw(texture, x, y, WIDTH / 2f, HEIGHT / 2f, WIDTH, HEIGHT, 1, 1, -rotation, 0, 0, WIDTH, HEIGHT, false, false);
        }
        update();
    }

    /**
     * Getter method for the X co-ordinate
     *
     * @return The X value of this bullet
     */
    public float getX() {
        return x;
    }

    /**
     * Getter method for the Y co-ordinate
     *
     * @return The Y value of this bullet
     */
    public float getY() {
        return y;
    }
}
