package com.elite.network;

/**
 * Stores details about a player in the game
 *
 * @author Will Goodman
 */
public class Player {

    private String name;
    //TODO Look at removing IP variable
    private String ip;

    /**
     * Makes a new Player
     *
     * @param playerName The player's name
     * @param ip         The player's IP
     */
    public Player(String playerName, String ip) {
        this.name = playerName;
        this.ip = ip;
    }

    /**
     * @return The player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the player's name
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
