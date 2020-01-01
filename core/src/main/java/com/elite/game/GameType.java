package com.elite.game;

import com.elite.entities.characters.Zombie;

import java.util.ArrayList;

/**
 * An interface to specify methods needed for both the singleplayer and multiplayer versions of the game
 *
 * @author Will Goodman
 */
public interface GameType {

    /**
     * @return And ArrayList of all Zombies in the game
     */
    ArrayList<Zombie> getAllPlayers();

    /**
     * @return The UserData of the currentPlayer
     */
    int getPlayerNow();

    /**
     * Reduce the energy the player has
     *
     * @param value The value by which to reduce the energy
     */
    void reduceEnergy(double value);

    /**
     * Check whether a grenade has hit any Zombies and if so, reduce their health
     *
     * @param grenadeThrower The Zombie which threw the grenade
     * @param x              The x coordinate of the grenade
     * @param y              The y coordinate of the grenade
     */
    void checkGrenade(Zombie grenadeThrower, float x, float y);
}
