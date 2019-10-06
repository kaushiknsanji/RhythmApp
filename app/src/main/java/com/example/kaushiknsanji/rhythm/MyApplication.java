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

package com.example.kaushiknsanji.rhythm;

import android.app.Application;

import com.example.kaushiknsanji.rhythm.data.local.PlayerComposition;

/**
 * Registered {@link Application} class of the App that provides
 * services tied to the Application Lifecycle
 */
public class MyApplication extends Application {

    //Instance of PlayerComposition to persist Player information
    private PlayerComposition mPlayerComposition;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //Initializing PlayerComposition
        mPlayerComposition = new PlayerComposition(getResources());
    }

    /**
     * Method that provides the instance of {@link PlayerComposition}
     * to persist Player information
     *
     * @return Instance of {@link PlayerComposition}
     */
    public PlayerComposition getPlayerComposition() {
        return mPlayerComposition;
    }
}