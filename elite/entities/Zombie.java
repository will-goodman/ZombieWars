package com.elite.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.elite.animations.SpriteAnimator;
import com.elite.audio.AudioAccessor;
import com.elite.game.GameType;
import com.elite.ui.Aim;
import com.elite.ui.HealthBar;
import com.elite.world.EnergyCost;
import com.elite.world.MyInputProcessor;

import java.util.ArrayList;

import static com.elite.world.SpriteRenderer.getAllowMove;

/**
 * @author Jacob Wheale
 */
public class Zombie extends Sprite {

    private static World world;
    private Body body;
    private Texture zombieTexture;
    private SpriteBatch batch;
    private Sprite sprite;
    private Rectangle zombie;

    private static final float LEFT_MAP_EDGE = 0f;
    private static final float RIGHT_MAP_EDGE = 1500f;
    private boolean walkingLeft = false;
    private static final float MOVEMENT_IMPULSE = 1000f;
    private static final float JUMP_IMPULSE = 10000f;
    private boolean edgeOfMapLeft = false;
    private boolean edgeOfMapRight = false;
    private float startingX;
    private float startingY;
    private int userData; //team 1 = <100 team 2 = >100
    private boolean isPlayerControlled;

    private int health = 100;

    private SpriteAnimator spriteAnimator;
    private MyInputProcessor inputProcessor;
    private GameType spriteRenderer;

    //Arrow attributes
    private Aim aim = new Aim();
    public float vy = 0f;
    private HealthBar healthBar = new HealthBar(0, 0);

    private ArrayList<Bullet> bullets;
    private ArrayList<Grenade> grenades;

    private int numGrenades;
    private int footContacts;


    /**
     * @param world          The physics world which the objects are simulated in.
     * @param x              The starting X position of the zombie.
     * @param y              The starting Y position of the zombie.
     * @param spriteRenderer The renderer which holds all of the players and deals with input.
     * @param userData       An integer value which is used to uniquely identify a character.
     * @param control        boolean value which says if the zombie is player controlled or not.
     */
    public Zombie(World world, float x, float y, GameType spriteRenderer, int userData, boolean control) {
        super(new Texture(Gdx.files.internal("sprite/male/Idle (2).png")));
        zombieTexture = getTexture();
        this.world = world;
        this.spriteRenderer = spriteRenderer;
        this.userData = userData;
        spriteAnimator = new SpriteAnimator();
        inputProcessor = new MyInputProcessor(spriteRenderer);
        Gdx.input.setInputProcessor(inputProcessor);
        bullets = new ArrayList<>();
        grenades = new ArrayList<>();
        this.startingX = x;
        this.startingY = y;
        isPlayerControlled = control;
        this.numGrenades = 2;
        this.footContacts = 0;
        setPosition(x, y);
        createBody();
    }

    /**
     * Method for setting the mouse position to adjust the aim indicator.
     *
     * @param mousePosition The current mouse position as a vector
     */
    public void setMousePosition(Vector3 mousePosition) {
        aim.addAim(walkingLeft, new Vector2(getX(), getY()), mousePosition);
    }

    /**
     * Method for setting the 'footContacts' variable.
     *
     * @param amount The amount to increment/decrement the value by.
     */
    public void setFootContacts(int amount) {
        footContacts += amount;
    }

    /**
     * Getter method for the variable 'body'.
     *
     * @return The physics body that represents this Zombie in the world.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Getter method for the variable 'numGrenades'.
     *
     * @return The amount of grenades the Zombie currently has.
     */
    public int getNumGrenades() {
        return numGrenades;
    }

    /**
     * Method to remove the aim indicator from the Zombie.
     */
    public void removeAim() {
        this.aim.removeAim();
    }

    /**
     * Getter method for the aim velocity.
     *
     * @return The aim objects current velocity.
     */
    public float getVelocity() {
        return this.aim.getVelocity();
    }

    /**
     * This method decreases the players health based on how much damage they took.
     *
     * @param damage
     */
    public void getHit(int damage) {
        health -= damage;
    }

    /**
     * Getter method for the x position.
     *
     * @return The players current x position.
     */
    public float getPlayerX() {
        return body.getPosition().x;
    }

    /**
     * Getter method for the y position.
     *
     * @return The players current y position.
     */
    public float getPlayerY() {
        return body.getPosition().y;
    }

    /**
     * Getter method for the 'userData' variable.
     *
     * @return This Zombies unique identifier number.
     */
    public int getUserData() {
        return userData;
    }

    /**
     * Getter method for the ArrayList of Bullet
     *
     * @return The list of all the bullets the Zombie currently has active.
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * @return Boolean for if the Zombie is player controlled or not.
     */
    public boolean getIsPlayerControlled() {
        return isPlayerControlled;
    }

    /**
     * @return The Zombies current health value.
     */
    public int getHealth() {
        return health;
    }

    /**
     * This method creates all of the relevant physics objects and bodies and puts them in the world.
     * It also sets the properties for the zombie.
     */
    private void createBody() {
        /*Adding character to screen*/
        batch = new SpriteBatch();
        sprite = new Sprite(zombieTexture);

        sprite.setScale(1 / 4f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        // Create zombie representation
        zombie = new Rectangle();
        zombie.x = startingX;
        zombie.y = startingY;
        zombie.width = 60;
        zombie.height = 130;

        bodyDef.position.set(zombie.getX(), zombie.getY());
        body = world.createBody(bodyDef);
        body.setGravityScale(0.1f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(zombie.getWidth() / 2, zombie.getHeight() / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.15f;
        fixtureDef.filter.groupIndex = -1;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);

        // create foot fixture to go under zombie
        shape.setAsBox(0.3f, 0.3f, new Vector2(0, -2), 0);
        fixtureDef.isSensor = true;
        Fixture footSensorFixture = body.createFixture(fixtureDef);
        footSensorFixture.setUserData(2);

        shape.dispose();

        this.setPosition(body.getPosition().x, body.getPosition().y - zombie.height / 2);

        System.out.println("PLAYER position " + this.getX() + " " + this.getY());
        System.out.println("BODY position " + body.getPosition().x
                + " " + body.getPosition().y);
    }


    /**
     * This method is called every 1/60th of a second to update the zombie.
     * This includes checking map bounds; checking if walking left or right;
     * and updating each zombies bullets and grenades in action.
     */
    public void updatePlayer() {

        if (body.getPosition().x < LEFT_MAP_EDGE) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            edgeOfMapLeft = true;
            spriteAnimator.isIdle(); //stops walking animation
        } else if (body.getPosition().x > RIGHT_MAP_EDGE) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            edgeOfMapRight = true;
            spriteAnimator.isIdle(); //stops walking animation
        } else {
            edgeOfMapLeft = false;
            edgeOfMapRight = false;
        }
        spriteAnimator.elapsedTime();
        TextureRegion textureRegion = spriteAnimator.getAnimation();
        batch.begin();

        if (this.isPlayerControlled) {
            aim.drawDots(batch); //render aim
        }
        aim.drawArrow(batch, walkingLeft);
        healthBar.updatePosition(getX(), getY(), health, this.userData);
        healthBar.drawHealthBar(batch, this.userData, this.walkingLeft); //render health bar

        if (walkingLeft) {
            batch.draw(textureRegion, this.getX() + 90, this.getY(), -ZombieAttributes.ZOMBIE_WIDTH, ZombieAttributes.ZOMBIE_HEIGHT);
        } else {
            batch.draw(textureRegion, this.getX(), this.getY(), ZombieAttributes.ZOMBIE_WIDTH, ZombieAttributes.ZOMBIE_HEIGHT);
        }


        for (Grenade grenade : grenades) {
            grenade.render(batch);
        }

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }

        setPosition(body.getPosition().x, body.getPosition().y);
        batch.end();

        // checking grenade damage and removing them from the world
        ArrayList<Grenade> grenadesToRemove = new ArrayList<>();
        for (Grenade grenade : grenades) {
            grenade.checkRemove();
            if (grenade.getRemove()) {
                grenadesToRemove.add(grenade);
                spriteRenderer.checkGrenade(this, grenade.getX(), grenade.getY());
                world.destroyBody(grenade.getBody());
                AudioAccessor.playSound("grenade");
            }
        }
        grenades.removeAll(grenadesToRemove);


        // checking if bullets are ready to be removed from the world.
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.checkRemove();
            if (bullet.getRemove()) {
                world.destroyBody(bullet.getBody());
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);

        this.setPosition(body.getPosition().x, body.getPosition().y - zombie.height / 2);
        // System.out.println("PLAYER position " + this.getX() + " " + this.getY());
        // System.out.println("BODY position " + body.getPosition().x + " " + body.getPosition().y);

    }

    /**
     * This method checks if the player is at the left edge of the map.
     * If they are not, then it will move the player left and play the animation.
     */
    public void moveLeft() {
        if (!edgeOfMapLeft && body.getLinearVelocity().y >= 0) {
            spriteAnimator.isWalking();
            walkingLeft = true;
            float impulse = body.getMass() * -MOVEMENT_IMPULSE;
            body.applyLinearImpulse(new Vector2(impulse, 0), body.getWorldCenter(), true);
        }
    }

    /**
     * This method checks if the player is at the right edge of the map.
     * If they are not, then it will move the player right and play the animation.
     */
    public void moveRight() {
        if (!edgeOfMapRight && body.getLinearVelocity().y >= 0) {
            spriteAnimator.isWalking();
            walkingLeft = false;
            float impulse = body.getMass() * MOVEMENT_IMPULSE;
            body.applyLinearImpulse(new Vector2(impulse, 0), body.getWorldCenter(), true);
        }
    }

    /**
     * This method is called when no input is pressed which stops the players movement.
     */
    public void noMovement() {
        body.setLinearVelocity(0f, body.getLinearVelocity().y);
        spriteAnimator.isIdle();
    }

    /**
     * This method allows the player to jump, by checking if they are standing on a floor.
     * If they are, it will apply a force to the zombie and play a sound.
     */
    public void jump() {
        AudioAccessor.playSound("testSound");
        if (footContacts > 0) {
            float impulse = body.getMass() * JUMP_IMPULSE;
            body.applyLinearImpulse(new Vector2(0, impulse), body.getWorldCenter(), true);
        }
    }


    /**
     * This method checks which direction the player is facing and adds a bullet to the array of bullets.
     */
    public void shoot() {
        if (getAllowMove()) {
            if (walkingLeft) {
                bullets.add(new Bullet(world, this, body.getPosition().x - 50, body.getPosition().y, true, -vy, this.isPlayerControlled, this.aim.getAngle()));

            } else {
                bullets.add(new Bullet(world, this, body.getPosition().x + 70, body.getPosition().y, false, vy, this.isPlayerControlled, this.aim.getAngle()));
            }
            spriteRenderer.reduceEnergy(EnergyCost.getBulletCost());
        }
    }

    /**
     * This method checks the direction and adds a new Grenade to the players list.
     * It also reduces their amount of ammo.
     */
    public void throwGrenade() {
        if (walkingLeft) {
            grenades.add(new Grenade(world, body.getPosition().x - 40, body.getPosition().y, true));
        } else {
            grenades.add(new Grenade(world, body.getPosition().x + 60, body.getPosition().y, false));
        }
        numGrenades--;
    }

    /**
     * This method is responsible for increasing the players grenade ammo when they pick up an ammo crate.
     */
    public void pickupGrenades() {

        numGrenades += 2;
        System.out.println("NUMGRENADES: " + numGrenades);
    }

    /**
     * This method is responsible for increasing the players health if they pick up a health crate.
     */
    public void pickupHealth() {
        this.health += 25;
        if (this.health > 100) {
            this.health = 100;
        }
        System.out.println("HEALTH: " + health);
    }

}
