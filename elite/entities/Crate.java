package com.elite.entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Interface for crate objects
 * @author Will Goodman
 */
public interface Crate {

    /**
     * @return The crate's user data
     */
    int getUserData();

    /**
     * @return The type of the crate
     */
    String getType();

    /**
     * @return The crate's x coordinate
     */
    float getX();

    /**
     * @return The crate's y coordinate
     */
    float getY();

    /**
     * @return The body of the crate
     */
    Body getBody();

    /**
     * Updates the render of the crate
     */
    void updateCrate();

}
