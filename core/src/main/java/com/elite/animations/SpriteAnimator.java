package com.elite.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * @author Minhal Khan
 */
public class SpriteAnimator {

    //Default Idle state
    private int state = AnimationState.IDLE;
    private Animation crntAnimation = AnimationState.IDLE_ATLAS;
    private float elapsedTime = 0;
    private boolean repeat = true;

    /**
     * The method which sets the animation state to idle and plays the idle animations
     */
    public void isIdle() {
        if (state != AnimationState.IDLE && !dead()) {
            repeat = true;
            elapsedTime = 0;
            state = AnimationState.IDLE;
            crntAnimation = AnimationState.IDLE_ATLAS;
        }
    }

    /**
     * The method which sets the animation state to walking and plays the walking animations
     */
    public void isWalking() {
        if (state != AnimationState.WALKING && !dead()) {
            repeat = true;
            elapsedTime = 0;
            state = AnimationState.WALKING;
            crntAnimation = AnimationState.WALK_ATLAS;
        }
    }

    /**
     * The method which sets the animation to dead and plays the dead animation
     */
    public void isDead() {
        if (state != AnimationState.DEAD) {
            repeat = false; //one time loop
            elapsedTime = 0;
            state = AnimationState.DEAD;
            crntAnimation = AnimationState.DEAD_ATLAS;
        }
    }

    /**
     * The method which returns whether the zombie is dead or not
     *
     * @return Returns true if the zombie is dead, and false if not
     */
    private boolean dead() {
        return state == AnimationState.DEAD;
    }

    /**
     * The method which returns the current animation frame
     *
     * @return Returns the current animation frame
     */
    public TextureRegion getAnimation() {
        return (TextureRegion) crntAnimation.getKeyFrame(elapsedTime, repeat);
    }

    /**
     * The method which increments the animation time frames
     */
    public void elapsedTime() {
        elapsedTime += Gdx.graphics.getDeltaTime();
    }


}
