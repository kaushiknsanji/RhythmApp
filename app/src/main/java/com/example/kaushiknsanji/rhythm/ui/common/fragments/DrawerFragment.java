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

package com.example.kaushiknsanji.rhythm.ui.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kaushiknsanji.rhythm.R;

/**
 * Abstract {@link Fragment} class extended by the Fragments involved in the
 * {@link com.example.kaushiknsanji.rhythm.ui.home.HomeActivity}
 * which is the Drawer Activity of the App.
 * <p>
 * All {@link DrawerFragment}s are {@link PlayerFragment} as well which initializes
 * and shows the Bottom Sheet Player.
 * </p>
 *
 * @author Kaushik N Sanji
 */
public abstract class DrawerFragment extends PlayerFragment {

    //Instance of the Listener to deliver action events to the attached Activity
    protected DrawerSelectionListener mDrawerSelectionListener;

    /**
     * Called when a fragment is first attached to its context.
     *
     * @param context Context of the Fragment
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //Casting context to DrawerSelectionListener
            mDrawerSelectionListener = (DrawerSelectionListener) context;
        } catch (ClassCastException e) {
            //Throw the exception when the Context does not implement DrawerSelectionListener
            throw new ClassCastException(context.toString() + " must implement DrawerSelectionListener");
        }
    }

    /**
     * Called immediately after {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Finding the Toolbar of the DrawerFragment
        Toolbar toolbarHome = view.findViewById(R.id.include_toolbar_home);
        if (toolbarHome != null) {
            //When found, delegate to the registered listener to setup the Toolbar with the ActionBarDrawerToggle
            mDrawerSelectionListener.setupDrawerToggle(toolbarHome);

            if (toolbarHome.getParent() instanceof CollapsingToolbarLayout) {
                //When Toolbar's Parent is CollapsingToolbarLayout,
                //trigger layout pass to reposition the incorrectly positioned Collapsed Title
                //caused by the Window insets
                final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) toolbarHome.getParent();
                collapsingToolbarLayout.post(collapsingToolbarLayout::requestLayout);
            }
        }

    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link android.support.v4.app.FragmentActivity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();

        //Delegate to the listener to inject the Fragment launched, into the Activity
        mDrawerSelectionListener.setSelectedDrawerFragment(this);
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        //Clearing the reference to the listener to avoid leaks
        mDrawerSelectionListener = null;
    }

    /**
     * Method to launch another DrawerFragment or Activity from the current selected DrawerFragment.
     *
     * @param drawerItemId Integer value of the Drawer Menu item resource.
     */
    protected void openDrawerItem(@IdRes int drawerItemId) {
        //Delegate to the listener to launch the desired DrawerFragment or Activity
        mDrawerSelectionListener.openDrawerItem(drawerItemId);
    }

    /**
     * Activity that creates an instance of {@link DrawerFragment}
     * needs to implement this listener interface to receive event callbacks
     */
    public interface DrawerSelectionListener {
        /**
         * Callback Method of {@link DrawerFragment} that injects
         * the instance of the selected DrawerFragment when the Fragment is started.
         *
         * @param drawerFragment Instance of the {@link DrawerFragment} started
         */
        void setSelectedDrawerFragment(DrawerFragment drawerFragment);

        /**
         * Callback Method of {@link DrawerFragment} invoked
         * to launch another DrawerFragment or Activity from the current selected DrawerFragment.
         *
         * @param drawerItemId Integer value of the Drawer Menu item resource.
         */
        void openDrawerItem(@IdRes int drawerItemId);

        /**
         * Callback Method of {@link DrawerFragment} invoked after the Fragment's layout inflation
         * to register the {@code toolbar} of the Fragment with
         * the Drawer Activity's {@link android.support.v7.app.ActionBarDrawerToggle}
         *
         * @param toolbar The {@link Toolbar} of the {@link DrawerFragment}
         */
        void setupDrawerToggle(Toolbar toolbar);
    }

}
