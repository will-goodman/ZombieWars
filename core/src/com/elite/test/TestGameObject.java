package com.elite.test;

import com.elite.game.GameObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGameObject {

    @Test
    public void ConstructorTest() {
        try {
            GameObject gameObject= new GameObject("x", 0, 0, "1");
            assertEquals("x", gameObject.getName());
            assertEquals(0, gameObject.getX(), 0.0);
            assertEquals(0, gameObject.getY(), 0.0);
            assertEquals("1", gameObject.getCommand());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNameTest() {
        GameObject gameObject = new GameObject("x", 0, 0, "1");
        assertEquals("x", gameObject.getName());
    }

    @Test
    public void getXTest() {
        GameObject gameObject = new GameObject("x", 0, 0, "1");
        assertEquals(0, gameObject.getX(), 0.0);
    }

    @Test
    public void setXTest() {
        GameObject gameObject = new GameObject("x", 0, 0, "1");
        try {
            gameObject.setX(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, gameObject.getX(), 0.0);
    }

    @Test
    public void getYTest() {
        GameObject gameObject = new GameObject("x", 0, 0, "1");
        assertEquals(0, gameObject.getY(), 0.0);
    }

    @Test
    public void setYTest() {
        GameObject gameObject = new GameObject("x", 0, 0, "1");
        try {
            gameObject.setY(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, gameObject.getY(), 0.0);
    }

    @Test
    public void getCommandTest() {
        GameObject gameObject = new GameObject("x", 0, 0, "1");
        assertEquals("1", gameObject.getCommand());
    }

    @Test
    public void setCommandTest() {
        GameObject gameObject = new GameObject("x", 0, 0, "1");
        gameObject.setCommand("2");
        assertEquals("2", gameObject.getCommand());
    }
}
