package com.elite.game.entities.pickups;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Interface for crate objects
 *
 * @author Will Goodman
 */
public interface Crate {

    //TODO Look at removing getUserData

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
