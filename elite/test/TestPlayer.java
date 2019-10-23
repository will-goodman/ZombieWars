package com.elite.test;

import com.elite.network.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayer {

    private static Player player = new Player("x", "1");

    @Test
    public void ConstructorTest() {
        try {
            Player playerTest = new Player("x", "1");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNameTest() {
        assertEquals("x", player.getName());
    }

    @Test
    public void setNameTest() {
        try {
            player.setName("y");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("y", player.getName());
    }
}
