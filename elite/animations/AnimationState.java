package com.elite.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/*
@author Minhal Khan
Identifies the animation state, and loads animation files
 */
public class AnimationState {

    private static String PATH = "sprite/male/";

    public static  Animation IDLE_ATLAS = new Animation(1f/5f, new TextureAtlas(AnimationState.PATH+"breathingAnimation.atlas").getRegions());
    public static  Animation WALK_ATLAS = new Animation(1f/14.3f, new TextureAtlas(AnimationState.PATH+"walkAnimation.atlas").getRegions());
    public static  Animation DEAD_ATLAS = new Animation(1f/5f, new TextureAtlas(AnimationState.PATH+"deadAnimation.atlas").getRegions());

    /*Add more animations by assigning a key to it*/
    public static int  IDLE = 0;
    public static int  WALKING = 1;
    public static int  DEAD = 2;


}
