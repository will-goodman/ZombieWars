package com.elite.test;

import com.elite.game.NetworkGame;
import com.elite.network.Lobby;
import com.elite.network.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestLobby {

    //private static Player player = new Player("x", "1");
    // private static Lobby lobby = new Lobby("x", player, "x", "y");

    @Test
    public void publicConstructorTest() {
        try {
            Player player = new Player("x", "1");
            Lobby testLobby = new Lobby("x", player);
            ArrayList<Player> expectedPlayers = new ArrayList<>();
            expectedPlayers.add(player);

            assertEquals(testLobby.getName(), "x");
            assertEquals(testLobby.getPlayers(), expectedPlayers);
            assertFalse(testLobby.getPrivacy());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void privateConstructorTest() {
        try {
            Player player = new Player("x", "1");
            Lobby testLobby = new Lobby("x", player, "x", "y");
            ArrayList<Player> expectedPlayers = new ArrayList<>();
            expectedPlayers.add(player);

            assertEquals(testLobby.getName(), "x");
            assertEquals(testLobby.getPlayers(), expectedPlayers);
            assertTrue(testLobby.getPrivacy());
            assertEquals(testLobby.getSalt(), "y");
            assertTrue(testLobby.verifyPassword("x"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNameTest() {
        Player player = new Player("x", "1");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");

        assertEquals("x", testPublicLobby.getName());
        assertEquals("x", testPrivateLobby.getName());
    }

    @Test
    public void getGameTest() {
        Player player = new Player("x", "1");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");

        assertNull(testPublicLobby.getGame());
        assertNull(testPrivateLobby.getGame());
    }

    @Test
    public void setGameTest() {
        Player player = new Player("x", "1");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");
        NetworkGame testNetworkGame = new NetworkGame();

        testPublicLobby.setGame(testNetworkGame);
        testPrivateLobby.setGame(testNetworkGame);

        assertEquals(testPublicLobby.getGame(), testNetworkGame);
        assertEquals(testPrivateLobby.getGame(), testNetworkGame);
    }

    @Test
    public void getPlayersTest() {
        Player player = new Player("x", "1");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");
        ArrayList<Player> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(player);

        assertEquals(testPublicLobby.getPlayers(), expectedPlayers);
        assertEquals(testPrivateLobby.getPlayers(), expectedPlayers);
    }

    @Test
    public void setPlayersTest() {
        Player player = new Player("x", "1");
        Player player2 = new Player("y", "2");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");
        ArrayList<Player> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(player);
        expectedPlayers.add(player2);

        testPublicLobby.setPlayers(expectedPlayers);
        testPrivateLobby.setPlayers(expectedPlayers);

        assertEquals(testPublicLobby.getPlayers(), expectedPlayers);
        assertEquals(testPrivateLobby.getPlayers(), expectedPlayers);
    }

    @Test
    public void getPrivacyTest() {
        Player player = new Player("x", "1");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");

        assertFalse(testPublicLobby.getPrivacy());
        assertTrue(testPrivateLobby.getPrivacy());
    }

    @Test
    public void getSaltTest() {
        Player player = new Player("x", "1");
        Lobby testLobby = new Lobby("x", player, "x", "y");
        assertEquals("y", testLobby.getSalt());
    }

    @Test
    public void addPlayerTest() {
        Player player = new Player("x", "1");
        Player player2 = new Player("y", "2");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");
        ArrayList<Player> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(player);
        expectedPlayers.add(player2);

        testPublicLobby.addPlayer(player2);
        testPrivateLobby.addPlayer(player2);

        assertEquals(testPublicLobby.getPlayers(), expectedPlayers);
        assertEquals(testPrivateLobby.getPlayers(), expectedPlayers);
    }

    @Test
    public void removePlayerTest() {
        Player player = new Player("x", "1");
        Player player2 = new Player("y", "2");
        Lobby testPublicLobby = new Lobby("x", player);
        Lobby testPrivateLobby = new Lobby("x", player, "x", "y");
        ArrayList<Player> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(player);

        testPublicLobby.addPlayer(player2);
        testPrivateLobby.addPlayer(player2);

        testPublicLobby.removePlayer(player2);
        testPrivateLobby.removePlayer(player2);

        assertEquals(testPublicLobby.getPlayers(), expectedPlayers);
        assertEquals(testPrivateLobby.getPlayers(), expectedPlayers);
    }

    @Test
    public void verifyPasswordTest() {
        Player player = new Player("x", "1");
        Lobby testLobby = new Lobby("x", player, "x", "y");

        assertTrue(testLobby.verifyPassword("x"));
    }
}
