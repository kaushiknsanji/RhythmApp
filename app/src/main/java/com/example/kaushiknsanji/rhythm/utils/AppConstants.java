package com.example.kaushiknsanji.rhythm.utils;

/**
 * Utility class that maintains the Constants used across the App
 *
 * @author Kaushik N Sanji
 */
public final class AppConstants {
    //Constant that controls the visibility of Player Toolbar while dragging the Bottom Sheet Player
    public static final float PLAYER_TOOLBAR_REVEAL_OFFSET = 0.9f;

    /**
     * Private constructor to avoid instantiating {@link AppConstants}
     */
    private AppConstants() {
        //Suppressing with an error to enforce noninstantiability
        throw new AssertionError("No " + this.getClass().getCanonicalName() + " instances for you!");
    }

}
