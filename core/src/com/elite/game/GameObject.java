package com.elite.game;

import java.io.Serializable;

/**
 * Presents data about an object in the game in a serialized form so it can be sent over the network
 * @author Will Goodman
 */
public class GameObject implements Serializable {

    private String name;
    private float x;
    private float y;
    private String command;

    /**
     * The Constructor for the GameObject. It stores the objects name, it's x and y coordinates and the command given
     * @param name The name (UserData) of the object
     * @param x The object's x coordinate
     * @param y The object's y coordinate
     * @param command The command to be applied to the object
     */
    public GameObject(String name, float x, float y, String command) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.command = command;
    }

    /**
     * Getter method for the UserData
     * @return The name (UserData) of the object
     */
    public String getName() { return this.name; }

    /**
     * Getter method for the objects x coordinate
     * @return The object's x coordinate
     */
    public float getX() {
        return this.x;
    }

    /**
     * Set the object's x coordinate
     * @param x The new x coordinate
     */
    public void setX(float x) { this.x = x; }

    /**
     * Getter method for the objects y coordinate
     * @return The object's y coordinate
     */
    public float getY() {
        return this.y;
    }

    /**
     * Set the object's y coordinate
     * @param y The new y coordinate
     */
    public void setY(float y) { this.y = y; }

    /**
     * Getter method for the objects command
     * @return The object's command
     */
    public String getCommand() { return this.command; }

    /**
     * Set the object's command
     * @param command The new command
     */
    public void setCommand(String command) { this.command = command; }


}
