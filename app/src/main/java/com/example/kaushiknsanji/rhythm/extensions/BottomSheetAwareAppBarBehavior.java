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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Custom {@link AppBarLayout.Behavior} for controlling Nested Scroll
 * on layouts with Bottom Sheets.
 *
 * @author Kaushik N Sanji
 */
public class BottomSheetAwareAppBarBehavior extends AppBarLayout.Behavior {

    /**
     * Default constructor for instantiating Behaviors.
     */
    public BottomSheetAwareAppBarBehavior() {
        //Delegating to super
        super();
    }

    /**
     * Default constructor for inflating Behaviors from layout. The Behavior will have
     * the opportunity to parse specially defined layout parameters. These parameters will
     * appear on the child view tag.
     */
    public BottomSheetAwareAppBarBehavior(Context context, AttributeSet attrs) {
        //Delegating to super
        super(context, attrs);
    }

    /**
     * Called when a descendant of the CoordinatorLayout attempts to initiate a nested scroll.
     * <p>
     * <p>Any Behavior associated with any direct child of the CoordinatorLayout may respond
     * to this event and return true to indicate that the CoordinatorLayout should act as
     * a nested scrolling parent for this scroll. Only Behaviors that return true from
     * this method will receive subsequent nested scroll events.</p>
     *
     * @param parent            the CoordinatorLayout parent of the view this Behavior is
     *                          associated with
     * @param child             the child view of the CoordinatorLayout this Behavior is associated with
     * @param directTargetChild the child view of the CoordinatorLayout that either is or
     *                          contains the target of the nested scroll operation
     * @param target            the descendant view of the CoordinatorLayout initiating the nested scroll
     * @param nestedScrollAxes  the axes that this nested scroll applies to. See
     *                          {@link ViewCompat#SCROLL_AXIS_HORIZONTAL},
     *                          {@link ViewCompat#SCROLL_AXIS_VERTICAL}
     * @param type              the type of input which cause this scroll event
     * @return true if the Behavior wishes to accept this nested scroll
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child,
                                       View directTargetChild, View target,
                                       int nestedScrollAxes, int type) {
        //Determine if the views initiating the scroll is a Bottom Sheet: START
        boolean bottomSheetFound = isBottomSheet(directTargetChild);
        if (!bottomSheetFound) {
            //Check in the target view as well when directTargetChild is not a Bottom Sheet View
            bottomSheetFound = isBottomSheet(target);
        }
        //Determine if the views initiating the scroll is a Bottom Sheet: END

        //Accept the Nested Scroll when it is NOT being initiated by a view with BottomSheetBehavior
        return !bottomSheetFound && super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    /**
     * Method that determines whether the given {@code view} is a Bottom Sheet or not.
     *
     * @param target The View that may or may not contain the Bottom Sheet Behavior
     *               which initiated the Nested Scroll
     * @return <b>True</b> if it is a Bottom Sheet; <b>False</b> otherwise
     */
    private boolean isBottomSheet(@NonNull View target) {
        //Get the Layout params of the view
        final ViewGroup.LayoutParams lp = target.getLayoutParams();
        //Check and return if the behavior associated is BottomSheetBehavior
        return lp instanceof CoordinatorLayout.LayoutParams
                && ((CoordinatorLayout.LayoutParams) lp).getBehavior() instanceof BottomSheetBehavior;
    }

}