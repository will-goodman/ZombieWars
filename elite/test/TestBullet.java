package com.elite.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.elite.entities.Bullet;
import com.elite.entities.Zombie;
import com.elite.game.GameType;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBullet {

    private static World world = new World(new Vector2(0, -200f), true);
    private static GameType spriteRenderer;
    private static Zombie zombieTest = new Zombie(world, 0, 0, spriteRenderer, 0, true);

    private static Bullet bullet = new Bullet(world, zombieTest, 0,0,true, 0.0f, true, 0.0f);

    @Test
    public void getRemoveTest(){
        assertFalse(bullet.getRemove());
    }

    @Test
    public void setRemoveTest() {
        bullet.setRemove(true);
        assertTrue(bullet.getRemove());
    }

    @Test
    public void constructorTest(){
        try {
            Bullet bullet1 = new Bullet(world, zombieTest, 0,0,true, 0.0f, true, 0.0f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getXTest() {
        assertEquals(0, bullet.getX());
    }

    @Test
    public void getYTest() {
        assertEquals(0, bullet.getY());
    }
}
