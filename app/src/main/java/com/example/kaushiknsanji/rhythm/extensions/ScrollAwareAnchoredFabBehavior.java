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

package com.example.kaushiknsanji.rhythm.extensions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Custom {@link FloatingActionButton.Behavior} for controlling the
 * visibility and appearance of {@link FloatingActionButton} when anchored to views
 * other than AppBarLayout or views with BottomSheetBehavior, as these are taken care by default.
 *
 * @author Kaushik N Sanji
 */
public class ScrollAwareAnchoredFabBehavior extends FloatingActionButton.Behavior {

    //Constant that hides the FAB when its current scale reaches below the set value
    private static final float FAB_VISIBILITY_SCALE_THRESHOLD = 0.25f;
    //Min Scale of FAB
    private static final float FAB_SCALE_MIN = 0.0f;
    //Max Scale of FAB
    private static final float FAB_SCALE_MAX = 1.0f;

    //Minimum Height of the Anchored/Dependent View
    private int mAnchoredViewMinHeight;
    //Normalized Height of the Anchored/Dependent View based on its Minimum Height if any
    private int mAnchoredViewNormalizedHeight;

    /**
     * Default constructor for instantiating Behaviors.
     */
    public ScrollAwareAnchoredFabBehavior() {
        //Delegating to super
        super();
        //Initializing properties of Fab
        initFab();
    }

    /**
     * Default constructor for inflating Behaviors from layout. The Behavior will have
     * the opportunity to parse specially defined layout parameters. These parameters will
     * appear on the child view tag.
     */
    public ScrollAwareAnchoredFabBehavior(Context context, AttributeSet attrs) {
        //Delegating to super
        super(context, attrs);
        //Initializing properties of Fab
        initFab();
    }

    /**
     * Method that sets the initial properties for the {@link FloatingActionButton}
     */
    private void initFab() {
        //Disable Auto Hide as we are managing this ourselves
        setAutoHideEnabled(false);
    }

    /**
     * Respond to a change in a child's dependent view
     * <p>
     * <p>This method is called whenever a dependent view changes in size or position outside
     * of the standard layout flow. A Behavior may use this method to appropriately update
     * the child view in response.</p>
     * <p>
     * <p>A view's dependency is determined by
     * {@link #layoutDependsOn(CoordinatorLayout, View, View)} or
     * if {@code child} has set another view as it's anchor.</p>
     * <p>
     * <p>Note that if a Behavior changes the layout of a child via this method, it should
     * also be able to reconstruct the correct position in
     * {@link #onLayoutChild(CoordinatorLayout, View, int) onLayoutChild}.
     * <code>onDependentViewChanged</code> will not be called during normal layout since
     * the layout of each child view will always happen in dependency order.</p>
     * <p>
     * <p>If the Behavior changes the child view's size or position, it should return true.
     * The default implementation returns false.</p>
     *
     * @param parent     the parent view of the given child
     * @param child      the child view to manipulate
     * @param dependency the dependent view that changed
     * @return true if the Behavior changed the child view's size or position, false otherwise
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        //Get the Layout params of the FloatingActionButton to read its Anchor
        final CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) child.getLayoutParams();

        if (lp.getAnchorId() == dependency.getId()) {
            //When the dependency is the Anchor of the FloatingActionButton

            if (dependency instanceof AppBarLayout || isBottomSheet(dependency)) {
                //When the Anchor/dependency is AppBarLayout or a view with BottomSheetBehavior
                //then let default behavior of FloatingActionButton handle this

                //Restoring the Auto Hide
                setAutoHideEnabled(true);
                //Returning to super to handle the case
                return super.onDependentViewChanged(parent, child, dependency);

            } else {
                //When the Anchor/dependency is not ones that are handled by default

                //Initialize the required dependent view properties
                //if normalized height is not yet determined
                if (mAnchoredViewNormalizedHeight == 0) {
                    initDependentViewProperties(dependency);
                }

                //When we have the normalized height of the dependency recorded, then
                //work the magic of Fab disappearance
                updateFabVisibility(child, dependency);

                //(Not returning True since we are just scaling the FAB and making
                //it disappear/appear accordingly, and we are not changing its existing Size/position)
            }
        }

        //On all else, return to super
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     * Method that determines whether the given {@code view} is a Bottom Sheet or not.
     *
     * @param view The View that may or may not contain the Bottom Sheet Behavior
     * @return <b>True</b> if it is a Bottom Sheet; <b>False</b> otherwise
     */
    private boolean isBottomSheet(@NonNull View view) {
        //Get the Layout params of the view
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        //Check and return if the behavior associated is BottomSheetBehavior
        return lp instanceof CoordinatorLayout.LayoutParams
                && ((CoordinatorLayout.LayoutParams) lp).getBehavior() instanceof BottomSheetBehavior;
    }

    /**
     * Method that initializes the properties of Anchored/Dependent View {@code dependency}
     *
     * @param dependency The Anchored/Dependent View of {@link FloatingActionButton}
     */
    private void initDependentViewProperties(View dependency) {
        //Empty Rect to capture the values
        Rect visibleRect = new Rect();

        if (dependency.getGlobalVisibleRect(visibleRect)) {
            //When dependent view is somewhat visible and visibility Rect is captured

            //Get the Minimum height of the Dependent View
            mAnchoredViewMinHeight = ViewCompat.getMinimumHeight(dependency);
            //Normalize the height of the Dependent View with its Minimum Height
            mAnchoredViewNormalizedHeight = visibleRect.height() - mAnchoredViewMinHeight;
        }
    }

    /**
     * Method that calculates the change in {@code dependency} to begin animating the
     * scale and visibility of {@link FloatingActionButton} accordingly.
     *
     * @param child      Instance of {@link FloatingActionButton} to manipulate its appearance
     * @param dependency The Dependent view that changed
     */
    private void updateFabVisibility(FloatingActionButton child, View dependency) {
        //Empty Rect to capture the values
        Rect visibleRect = new Rect();

        if (dependency.getGlobalVisibleRect(visibleRect)) {
            //When dependent view is somewhat visible and visibility Rect is captured

            //Calculate the current Normalized Height of the Dependent View with its initial Minimum Height
            int currentDependencyNormalizedHeight = visibleRect.height() - mAnchoredViewMinHeight;
            //Calculate the change in current Normalized Height of the Dependent View
            float dependencyHeightChangeRatio = (float) currentDependencyNormalizedHeight / (float) mAnchoredViewNormalizedHeight;
            //Begin the Fab animation based on the above change
            animateFabVisibility(child, dependencyHeightChangeRatio);
        }
    }

    /**
     * Method that animates the scale and visibility of {@link FloatingActionButton}
     * based on the {@code newScale} passed.
     *
     * @param child    Instance of {@link FloatingActionButton} to manipulate its appearance
     * @param newScale The new Scale to be applied to the {@code child}
     */
    private void animateFabVisibility(FloatingActionButton child, float newScale) {
        //Ensuring the Scale is always in the range of 0 to 1
        newScale = MathUtils.clamp(newScale, FAB_SCALE_MIN, FAB_SCALE_MAX);

        if (ViewCompat.isLaidOut(child) && child.getScaleX() != newScale
                && (child.getAnimation() == null || child.getAnimation().hasEnded())) {
            //When the FloatingActionButton is laid out and the new scale is different
            //from the previous scale applied, and the last animation if any has ended

            //Begin animating with the new scale as the applied scale
            child.animate()
                    .scaleX(newScale)
                    .scaleY(newScale)
                    .alpha(newScale)
                    .setDuration(0) //No Duration as it needs to animate quickly
                    .setListener(new AnimatorListenerAdapter() {
                        /**
                         * <p>Notifies the end of the animation. This callback is not invoked
                         * for animations with repeat count set to INFINITE.</p>
                         *
                         * @param animation The animation which reached its end.
                         */
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            if (child.getScaleX() <= FAB_VISIBILITY_SCALE_THRESHOLD) {
                                //If the current scale is less than the threshold set, then
                                //make the FAB invisible
                                child.setVisibility(View.INVISIBLE);
                            }

                        }

                        /**
                         * <p>Notifies the start of the animation.</p>
                         *
                         * @param animation The started animation.
                         */
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            if (child.getScaleX() > FAB_VISIBILITY_SCALE_THRESHOLD
                                    && child.getVisibility() != View.VISIBLE) {
                                //If the current scale is more than the threshold set,
                                //then make the FAB visible if still invisible
                                child.setVisibility(View.VISIBLE);
                            }

                        }
                    })
                    .start(); //Start the animation set
        }

    }
}