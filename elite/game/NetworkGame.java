package com.elite.game;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Stores the game state on the server side to share with all players
 * @author Will Goodman
 */
public class NetworkGame implements Serializable {

    public String playerName;
    private HashMap<String, GameObject> objects = new HashMap<String, GameObject>();

    /**
     * @return A HashMap of all objects in the game, with their UserData as the key
     */
    public HashMap<String, GameObject> getObjects() {
        return this.objects;
    }

    /**
     * Adds a new object to the game
     * @param newObject The new object
     * @param owner The object's UserData
     */
    public void addObject(GameObject newObject, String owner) {
        objects.put(owner, newObject);
    }

    /**
     * Removes an object from the game
     * @param userData The UserData of the object to remove
     */
    public void removePlayer(int userData) { objects.remove(userData); }

    /**
     * Updates the details about an object in the game
     * @param key The UserData of the object to update
     * @param command The new command
     * @param x The new x coordinate
     * @param y The new y coordinate
     */
    public void updateGame(String key, String command, float x, float y) {
        GameObject object = objects.get(key);
        object.setCommand(command);
        object.setX(x);
        object.setY(y);
    }

    /**
     * Send a message to all players to end the game
     */
    public void endGame() {
        GameObject object = new GameObject("QUIT", 0, 0, "QUIT");
        objects.put("QUIT", object);
    }
}
