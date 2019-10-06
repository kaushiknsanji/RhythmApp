/*
 * Copyright 2019 Kaushik N. Sanji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
