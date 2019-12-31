package com.elite.world;

/**
 * Stores the energy cost of different actions.
 */
public class EnergyCost {
    private static final double WALKCOST = 0.05;
    private static final double BULLETCOST = 20;
    private static final double GRENADECOST = 50;

    /**
     * Getter method for the walking energy cost
     *
     * @return The walking energy cost
     */
    public static double getWalkCost() {
        return WALKCOST;
    }

    /**
     * Getter method for the shooting a bullet energy cost
     *
     * @return The shooting a bullet energy cost
     */
    public static double getBulletCost() {
        return BULLETCOST;
    }

    /**
     * Getter method for the throwing a grenade energy cost
     *
     * @return The throwing a grenade energy cost
     */
    public static double getGrenadeCost() {
        return GRENADECOST;
    }
    
}
