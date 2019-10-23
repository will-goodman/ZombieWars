package com.elite.test;

import com.elite.network.Lobby;
import com.elite.network.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLobby {

    private static Player player = new Player("x", "1");
    private static Lobby lobby = new Lobby("x", player, "x", "y");

    @Test
    public void ConstructorTest() {
        try {
            Lobby lobbyTest = new Lobby("x", player);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ConstructorTest2() {
        try {
            Lobby lobbyTest = new Lobby("x", player, "x", "y");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNameTest() {
        assertEquals("x", lobby.getName());
    }

    @Test
    public void getSaltTest() {
        assertEquals("y", lobby.getSalt());
    }

    @Test
    public void verifyPasswordTest() {
        assertTrue(lobby.verifyPassword("x"));
    }
}
