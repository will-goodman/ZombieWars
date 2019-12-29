package com.elite.test;

import com.elite.game.GameObject;
import com.elite.game.NetworkGame;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TestNetworkGame {

    @Test
    public void getObjectsTest() {
        NetworkGame testNetworkGame = new NetworkGame();
        HashMap<String, GameObject> expectedObjects = new HashMap<>();

        assertEquals(testNetworkGame.getObjects(), expectedObjects);
    }

    @Test
    public void addObjectTest() {
        NetworkGame testNetworkGame = new NetworkGame();
        HashMap<String, GameObject> expectedObjects = new HashMap<>();
        GameObject testObject = Mockito.mock(GameObject.class);
        expectedObjects.put("test_owner", testObject);

        testNetworkGame.addObject(testObject, "test_owner");

        assertEquals(testNetworkGame.getObjects(), expectedObjects);
    }

    //TODO Fix bug in removePlayer
    @Test
    public void removePlayerTest() {
        NetworkGame testNetworkGame = new NetworkGame();
        HashMap<String, GameObject> expectedObjects = new HashMap<>();
        GameObject testObject = Mockito.mock(GameObject.class);
        GameObject testObject2 = Mockito.mock(GameObject.class);
        expectedObjects.put("1", testObject);

        testNetworkGame.addObject(testObject, "1");
        testNetworkGame.addObject(testObject2, "2");

        testNetworkGame.removePlayer(2);

        assertEquals(testNetworkGame.getObjects(), expectedObjects);
    }

    @Test
    public void updateGameTest() {
        NetworkGame testNetworkGame = new NetworkGame();
        GameObject testObject = new GameObject("test_object", 1, 2, "1");
        GameObject expectedObject = new GameObject("test_object", 3, 4, "2");

        testNetworkGame.addObject(testObject, "test_owner");

        testNetworkGame.updateGame("test_owner", "2", 3, 4);

        GameObject updatedObject = testNetworkGame.getObjects().get("test_owner");

        assertEquals(expectedObject.getName(), updatedObject.getName());
        assertEquals(expectedObject.getCommand(), updatedObject.getCommand());
        assertEquals(expectedObject.getX(), updatedObject.getX(), 0.0);
        assertEquals(expectedObject.getY(), updatedObject.getY(), 0.0);
    }

    @Test
    public void endGameTest() {
        NetworkGame testNetworkGame = new NetworkGame();
        HashMap<String, GameObject> expectedObjects = new HashMap<>();
        GameObject expectedObject = new GameObject("QUIT", 0, 0, "QUIT");
        expectedObjects.put("QUIT", expectedObject);

        testNetworkGame.endGame();

        HashMap<String, GameObject> updatedObjects = testNetworkGame.getObjects();
        GameObject updatedObject = updatedObjects.get("QUIT");

        assertEquals(expectedObjects.size(), updatedObjects.size());
        assertEquals(expectedObject.getName(), updatedObject.getName());
        assertEquals(expectedObject.getCommand(), updatedObject.getCommand());
        assertEquals(expectedObject.getX(), updatedObject.getX(), 0.0);
        assertEquals(expectedObject.getY(), updatedObject.getY(), 0.0);
    }
}
