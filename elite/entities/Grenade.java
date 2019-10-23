package com.elite.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * @author Jacob Wheale
 */
public class Grenade {

    private static World world;
    private static Texture texture;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 47;


    private float x, y;
    private boolean remove = false;
    private boolean left;

    private Body body;

    /**
     * Getter method for the value 'x'.
     *
     * @return The x position of the grenade.
     */
    float getX() {
        return x;
    }

    /**
     * Getter method for the value 'y'.
     *
     * @return The y position of the grenade.
     */
    float getY() {
        return y;
    }

    /**
     * Returns the boolean value of the variable 'remove'.
     *
     * @return The value of the boolean variable 'remove'
     */
    boolean getRemove() {
        return remove;
    }

    /**
     * Getter method for the variable 'body'.
     *
     * @return The physics body that represents this grenade.
     */
    Body getBody() {
        return this.body;
    }


    /**
     * Constructor for the class which initialises variables
     * and sets up the texture and then calls createBody()
     *
     * @param world The physics world the objects act in
     * @param x     The X position of where the grenade will start
     * @param y     The Y position of where the grenade will start
     * @param left  boolean for whether the player is facing left or not
     */
    Grenade(World world, float x, float y, boolean left) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.left = left;

        if (texture == null) {
            texture = new Texture("grenade.png");
        }
        createBody();

    }

    /**
     * Method to create all of the relevant physics objects to represent the grenade.
     * Defines the properties of the grenade and adds it to the world.
     */
    private void createBody() {

        // create a physics body for the grenade
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        PolygonShape grenade_shape = new PolygonShape();
        grenade_shape.setAsBox(WIDTH / 2f, 1);


        // define the properties of the grenade e.g how much it bounces.
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.4f;
        fixtureDef.friction = 0.8f;
        fixtureDef.filter.groupIndex = -1;
        fixtureDef.shape = grenade_shape;

        // put the grenade in the world and reduce gravity impact.
        body = world.createBody(bodyDef);
        body.setGravityScale(0.15f);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(3);
        grenade_shape.dispose();


        // initial force applied to the grenade when its created to create the arc path.
        // if facing left then apply force in the opposite direction.
        if (left) {
            body.applyLinearImpulse(-120000000f, 120000000f, body.getPosition().x, body.getPosition().y, true);
        } else {
            body.applyLinearImpulse(120000000f, 120000000f, body.getPosition().x, body.getPosition().y, true);
        }

    }

    /**
     * This method checks if the grenade has come to a stop, so it can then be deleted.
     * Or if the grenade has gone outside of the map bounds.
     */
    public void checkRemove() {
        if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y < 1) {
            remove = true;
        } else if (body.getPosition().x > Gdx.graphics.getWidth() || body.getPosition().x < 0) {
            remove = true;
        }
    }

    /**
     * This method updates the positions of the grenade according to the physics objects position.
     */
    private void update() {
        x = body.getPosition().x;
        y = body.getPosition().y;

    }

    /**
     * This method draws the grenade on the screen and then calls to update the positions.
     *
     * @param batch The spriteBatch object passed which is responsible for rendering on the screen.
     */
    void render(SpriteBatch batch) {

        batch.draw(texture, x, y);
        update();
    }

}
