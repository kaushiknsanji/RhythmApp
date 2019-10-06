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

package com.example.kaushiknsanji.rhythm.ui.common.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.MyApplication;
import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.data.local.PlayerComposition;

/**
 * Base {@link AppCompatActivity} for all the Activities in the App
 * that provides the required dependencies and functionality.
 *
 * @author Kaushik N Sanji
 */
public abstract class BaseActivity extends AppCompatActivity {

    //Bundle Key constant to save/restore the Height of Status Bar
    private static final String BUNDLE_STATUS_BAR_HEIGHT_INT_KEY = "int.BaseActivity.StatusBarHeight";

    //For the shared instance of Toast
    private static Toast mToast;

    //Typeface for the Title Text
    private Typeface mTypefaceTitleText;

    //Typeface for the Explanation Text
    private Typeface mTypefaceExplanationText;

    //Stores the height of the status bar determined
    private int mStatusBarHeight;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the Typeface for Title Text
        mTypefaceTitleText = ResourcesCompat.getFont(this, R.font.audiowide);

        //Get the Typeface for Explanation Text
        mTypefaceExplanationText = ResourcesCompat.getFont(this, R.font.noticia_text);

        //Find the Status Bar Height using the Window DecorView
        findStatusBarHeight(getWindow().getDecorView());
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Restoring the Height of Status Bar
        mStatusBarHeight = savedInstanceState.getInt(BUNDLE_STATUS_BAR_HEIGHT_INT_KEY);
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed
     * so that the state can be restored in {@link #onCreate} or
     * {@link #onRestoreInstanceState} (the {@link Bundle} populated by this method
     * will be passed to both).
     * <p>
     * <p>This method is called before an activity may be killed so that when it
     * comes back some time in the future it can restore its state.
     * <p>
     * <p>If called, this method will occur before {@link #onStop}.  There are
     * no guarantees about whether it will occur before or after {@link #onPause}.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Saving the Height of Status Bar
        outState.putInt(BUNDLE_STATUS_BAR_HEIGHT_INT_KEY, mStatusBarHeight);
    }

    /**
     * Method that provides the instance of {@link PlayerComposition}
     * to persist Player information for the entire Application Lifecycle
     *
     * @return Instance of {@link PlayerComposition}
     */
    protected PlayerComposition getPlayerComposition() {
        return ((MyApplication) getApplication()).getPlayerComposition();
    }

    /**
     * Method to show the {@code toast} message passed.
     *
     * @param toast Instance of {@link Toast} to show
     */
    protected void showToast(Toast toast) {
        //Cancel any current toasts before showing the new Toast
        if (mToast != null) {
            mToast.cancel();
        }
        //Save the new Toast instance
        mToast = toast;
        //Show the Toast
        mToast.show();
    }

    /**
     * Method that returns the Typeface to be used for Title Text
     *
     * @return Instance of {@link Typeface} for Title Text
     */
    protected Typeface getTitleTextTypeface() {
        return mTypefaceTitleText;
    }

    /**
     * Method that returns the Typeface to be used for Explanation Text
     *
     * @return Instance of {@link Typeface} for Explanation Text
     */
    protected Typeface getExplanationTextTypeface() {
        return mTypefaceExplanationText;
    }

    /**
     * Method that returns the Height of the Status bar determined.
     *
     * @return Integer value of the Height of Status bar
     */
    protected int getStatusBarHeight() {
        return mStatusBarHeight;
    }

    /**
     * Method that initializes the Explanation Text {@code explanationTextResId}
     * on the {@code textViewExplanation} for the screen.
     *
     * @param textViewExplanation  TextView instance that needs
     *                             to show the Explanation Text {@code explanationTextResId}
     * @param explanationTextResId Resource Id of the String for the Explanation
     */
    protected void setupExplanationText(TextView textViewExplanation,
                                        @StringRes int explanationTextResId) {
        //Set the Text passed
        textViewExplanation.setText(explanationTextResId);
        //Set the Typeface
        textViewExplanation.setTypeface(getExplanationTextTypeface());
    }

    /**
     * Method that starts an Activity through an Intent for the {@code activityClass}
     * passed.
     *
     * @param activityClass The {@link Class} of the Activity to be launched
     */
    protected void startActivity(Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    /**
     * Method that replaces an existing Fragment shown at the given {@code containerViewId}
     * with the given {@code fragment} instance and its {@code tag}
     *
     * @param containerViewId Identifier of the container whose fragment(s) are
     *                        to be replaced.
     * @param fragment        The new fragment to place in the container.
     * @param tag             Optional tag name for the fragment, to later retrieve the
     *                        fragment with {@link FragmentManager#findFragmentByTag(String)
     *                        FragmentManager.findFragmentByTag(String)}.
     */
    protected void replaceFragment(@IdRes int containerViewId,
                                   Fragment fragment,
                                   @Nullable String tag) {
        //Get the Instance of the FragmentManager
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            //When the Fragment given is not being displayed at the containerViewId

            //Get the FragmentTransaction
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            //Set the animation for swapping out the Fragment
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            //Replace the Fragment at the containerViewId with the given Fragment and its Tag
            fragmentTransaction.replace(containerViewId, fragment, tag).commit();
        }
    }

    /**
     * Method that determines the Height of Status bar using the Window DecorView given.
     *
     * @param decorView Instance of Window DecorView
     */
    private void findStatusBarHeight(final View decorView) {

        //Attach a Global Layout Listener to the View to determine the Status Bar Height
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * Callback method to be invoked when the global layout state or the visibility of views
             * within the view tree changes
             */
            @Override
            public void onGlobalLayout() {
                //Initialize a Rect to capture the Visible Window Frame Values
                Rect rect = new Rect();
                //Capture the Visible Window Frame Rect
                decorView.getWindowVisibleDisplayFrame(rect);
                //Save the Top Value when present and is different from the existing value
                if (rect.top > 0 && rect.top != mStatusBarHeight) {
                    mStatusBarHeight = rect.top;
                }
                //Detach this Listener from the DecorView after capture
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    decorView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

    }

}