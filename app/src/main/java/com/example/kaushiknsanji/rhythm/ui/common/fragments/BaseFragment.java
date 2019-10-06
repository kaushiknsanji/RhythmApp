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


import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.MyApplication;
import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.data.local.PlayerComposition;

/**
 * Base {@link Fragment} for all the Fragments in the App
 * that provides the required dependencies and functionality.
 *
 * @author Kaushik N Sanji
 */
public abstract class BaseFragment extends Fragment {
    //Bundle Key constant to save/restore the Height of Status Bar
    private static final String BUNDLE_STATUS_BAR_HEIGHT_INT_KEY = "int.BaseFragment.StatusBarHeight";

    //For the shared instance of Toast
    private static Toast mToast;

    //Typeface for the Title Text
    private Typeface mTypefaceTitleText;

    //Typeface for the Explanation Text
    private Typeface mTypefaceExplanationText;

    //Stores the height of the status bar determined
    private int mStatusBarHeight;

    /**
     * Called to do initial creation of a fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the Typeface for Title Text
        mTypefaceTitleText = ResourcesCompat.getFont(requireContext(), R.font.audiowide);

        //Get the Typeface for Explanation Text
        mTypefaceExplanationText = ResourcesCompat.getFont(requireContext(), R.font.noticia_text);

        //Find the Status Bar Height using the Window DecorView
        findStatusBarHeight(requireActivity().getWindow().getDecorView());
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //On Subsequent launch, restore the state

            //Restoring the Height of Status Bar
            mStatusBarHeight = savedInstanceState.getInt(BUNDLE_STATUS_BAR_HEIGHT_INT_KEY);
        }
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>This corresponds to {@link AppCompatActivity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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
        return ((MyApplication) requireActivity().getApplication()).getPlayerComposition();
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
     * Called when the Fragment's Activity has detected the user's press of the back
     * key.
     *
     * @return Boolean value that indicates if the back operation was handled or not.
     * <br/><b>TRUE</b> if handled by the Fragment; <b>FALSE</b> otherwise.
     */
    public abstract boolean onBackPressed();

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