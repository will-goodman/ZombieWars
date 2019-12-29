package com.elite.test;

import com.elite.network.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayer {

    @Test
    public void ConstructorTest() {
        Player testPlayer = new Player("x", "1");

        assertEquals(testPlayer.getName(), "x");
    }

    @Test
    public void getNameTest() {
        Player testPlayer = new Player("x", "1");

        assertEquals(testPlayer.getName(), "x");
    }

    @Test
    public void setNameTest() {
        Player testPlayer = new Player("x", "1");

        testPlayer.setName("y");

        assertEquals(testPlayer.getName(), "y");
    }
}
