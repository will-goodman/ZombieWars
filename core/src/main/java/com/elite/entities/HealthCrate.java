package com.elite.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

/**
 * A health crate which increases the zombie's health when they walk into it
 * @author Will Goodman
 */
public class HealthCrate extends Sprite implements Crate {

    private SpriteBatch batch;
    private Sprite sprite;
    private Rectangle crate;
    private Body body;
    private Texture crateTexture;
    private World world;
    private float startingX;
    private float startingY;
    private int userData;

    /**
     * Makes a new HealthCrate object
     * @param world Holds all the physics entities
     * @param x The start x coordinate
     * @param y The start y coordinate
     * @param userData The crate's unique int identifier
     */
    public HealthCrate(World world, float x, float y, int userData) {
        super(new Texture(Gdx.files.internal("healthCrate.png")));
        crateTexture = getTexture();
        this.world = world;
        this.startingX = x;
        this.startingY = y;
        this.userData = userData;
        createBody();
    }

    public String getType() {
        return "HEALTH";
    }

    /**
     * @return The crate's unique int identifier
     */
    public int getUserData() { return this.userData; }

    /**
     * @return The crate's body
     */
    @Override
    public Body getBody() {
        return body;
    }

    /**
     * Update's the crate's render
     */
    public void updateCrate() {
        batch.begin();
        batch.draw(crateTexture,this.getX(),this.getY(),49,50);
        batch.end();

        this.setPosition(body.getPosition().x, body.getPosition().y - 25);
    }

    /**
     * Creates the HealthCrate object in the game
     */
    private void createBody() {
        /*Adding character to screen*/
        batch = new SpriteBatch();
        sprite = new Sprite(crateTexture);

        sprite.setScale(1/4f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        // Create zombie representation
        crate = new Rectangle();
        crate.x = startingX;
        crate.y = startingY;
        crate.width = 49;
        crate.height = 50;

        bodyDef.position.set(crate.getX(), crate.getY());
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(crate.getWidth()/2, crate.getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);


        shape.dispose();

        this.setPosition(body.getPosition().x,body.getPosition().y - 25);
    }

}
