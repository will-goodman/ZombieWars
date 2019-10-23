package com.elite.test;

import com.elite.game.GameObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGameObject {

    private static GameObject gameObject = new GameObject("x", 0, 0, "1");

    @Test
    public void ConstructorTest() {
        try {
            GameObject gameObjectTest = new GameObject("x", 0, 0, "1");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNameTest() {
        assertEquals("x", gameObject.getName());
    }

    @Test
    public void getXTest() {
        assertEquals(0, gameObject.getX());
    }

    @Test
    public void setXTest() {
        try {
            gameObject.setX(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, gameObject.getX());
    }

    @Test
    public void getYTest() {
        assertEquals(0, gameObject.getY());
    }

    @Test
    public void setYTest() {
        try {
            gameObject.setY(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, gameObject.getY());
    }

    @Test
    public void getCommandTest() {
        assertEquals("1", gameObject.getCommand());
    }

    @Test
    public void setCommandTest() {
        try {
            //gameObject.setY("2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("2", gameObject.getCommand());
    }
}
