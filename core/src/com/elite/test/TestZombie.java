package com.elite.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.elite.entities.Zombie;
import com.elite.game.GameType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestZombie {
    private static Texture zombieTexture;
    private static Sprite sprite = new Sprite(zombieTexture);
    private static World world = new World(new Vector2(0, -200f), true);
    private static GameType spriteRenderer;
    Zombie zombie = new Zombie(world, 0, 0, spriteRenderer, 0, false);

    @Test
    public void ConstrutorTest() {
        try {
            Zombie zombieTest = new Zombie(world, 0, 0, spriteRenderer, 0, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePlayerTest() {
        try {
            zombie.updatePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveLeftTest() {
        try {
            zombie.moveLeft();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveRightTest() {
        try {
            zombie.moveRight();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noMovementTest() {
        try {
            zombie.noMovement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jumpTest() {
        try {
            zombie.jump();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shootTest() {
        try {
            zombie.shoot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void throwGrenadeTest() {
        try {
            zombie.throwGrenade();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPlayerXTest() {
        assertEquals(0, zombie.getPlayerX());
    }

    @Test
    public void checkHealthTest() {
        try {
            //zombie.checkHealth();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPlayerYTest(){
        assertEquals(0, zombie.getPlayerY());
    }

    @Test
    public void getUserDataTest(){
        assertEquals(0, zombie.getUserData());
    }
}
