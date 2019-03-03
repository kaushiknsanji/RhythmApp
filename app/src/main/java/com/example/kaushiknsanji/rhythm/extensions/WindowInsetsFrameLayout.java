package com.example.kaushiknsanji.rhythm.extensions;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Custom {@link FrameLayout} that dispatches Window Insets received, AS-IS to all its children
 * when its FitSystemWindows property is set.
 *
 * @author Kaushik N Sanji
 */
public class WindowInsetsFrameLayout extends FrameLayout {

    public WindowInsetsFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public WindowInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindowInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (ViewCompat.getFitsSystemWindows(this)) {
            //If we are set to fitSystemWindows, then register the Window Insets Listener
            registerWindowInsetsListener();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WindowInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        if (ViewCompat.getFitsSystemWindows(this)) {
            //If we are set to fitSystemWindows, then register the Window Insets Listener
            registerWindowInsetsListener();
        }
    }

    /**
     * Method that registers the {@link android.support.v4.view.OnApplyWindowInsetsListener}
     * on this view
     */
    private void registerWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(this,
                (v, insets) -> onWindowInsetChanged(insets));
    }

    /**
     * Method invoked when the registered {@link android.support.v4.view.OnApplyWindowInsetsListener}
     * triggers the event.
     *
     * @param insets The insets to apply
     * @return The insets supplied, minus any insets that were consumed
     */
    private WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat insets) {
        //Get the Child Count of FrameLayout
        int childCount = getChildCount();
        //Dispatch the insets AS-IS to all children of FrameLayout
        for (int index = 0; index < childCount; index++) {
            ViewCompat.dispatchApplyWindowInsets(getChildAt(index), insets);
        }
        //Returning the same insets received
        return insets;
    }

}
