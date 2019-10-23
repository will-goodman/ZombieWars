package com.elite.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.elite.entities.HealthCrate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHealthCrate {
    private static World world = new World(new Vector2(0, -200f), true);
    private static HealthCrate healthCrate = new HealthCrate(world,0,0,0);

    @Test
    public void ConstrutorTest() {
        try {
            HealthCrate healthCrateTest = new HealthCrate(world, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTypeTest() {
        assertEquals("HEALTH", healthCrate.getType());
    }

    @Test
    public void getUserDataTest() {
        assertEquals(0, healthCrate.getUserData());
    }

    @Test
    public void updateCrateTest() {
        try {
            healthCrate.updateCrate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
