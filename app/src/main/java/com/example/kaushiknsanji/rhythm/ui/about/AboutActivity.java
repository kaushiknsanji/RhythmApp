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

package com.example.kaushiknsanji.rhythm.ui.about;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.utils.IntentUtility;
import com.example.kaushiknsanji.rhythm.utils.TextAppearanceUtility;

/**
 * Activity that inflates the layout 'R.layout.activity_about' to display
 * the info related to the App and the developer on click of "About"
 * Menu item in the Overflow Menu of the {@link com.example.kaushiknsanji.rhythm.ui.home.HomeActivity}
 *
 * @author Kaushik N Sanji
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Initializing the Toolbar
        setupToolbar();

        //Initializing the Content Title Text
        initContentTitleText();

        //Initializing the Content Intro Text
        initContentIntroText();

        //Initializing the Content Description Text
        initContentDescriptionText();

        //Initializing the ImageViews and registering the click listeners
        initImageViews();
    }

    /**
     * Method that initializes the Toolbar as ActionBar, sets the Scroll Flags
     * and adds a top inset padding for Status Bar
     */
    private void setupToolbar() {
        //Finding the Custom Toolbar
        final Toolbar toolbar = findViewById(R.id.include_toolbar_about);

        //Set the Scroll Flags
        AppBarLayout.LayoutParams lp =
                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        lp.setScrollFlags(
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS |
                        AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
        );
        toolbar.setLayoutParams(lp);

        //Register the OnApplyWindowInsetsListener on toolbar
        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            //Use the Top Inset for Top Padding
            toolbar.setPadding(toolbar.getPaddingLeft(),
                    insets.getSystemWindowInsetTop(),
                    toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom()
            );
            //Prepare Rect for new Insets marking the Top Inset as Consumed (=0)
            Rect systemWindowInsetsRemainingRect = new Rect();
            systemWindowInsetsRemainingRect.set(
                    insets.getSystemWindowInsetLeft(),
                    0,
                    insets.getSystemWindowInsetRight(),
                    insets.getSystemWindowInsetBottom()
            );
            //Returning the Insets supplied, minus the top inset that was consumed
            return insets.replaceSystemWindowInsets(systemWindowInsetsRemainingRect);
        });

        //Set the System UI Flags to enable us to lay out in the window insets
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            toolbar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //Setting the toolbar as the ActionBar
        setSupportActionBar(toolbar);

        //Enable the Up button navigation
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            //Enabling the home button for Up Action
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            //Changing the Up button icon to close
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_about_close);
            //Set the Title
            supportActionBar.setTitle(R.string.title_about);
        }
    }

    /**
     * Method that initializes the Content Title TextView ('R.id.text_about_content_title')
     * with the Text and sets the Typeface.
     */
    private void initContentTitleText() {
        //Finding the TextView
        TextView textViewContentTitle = findViewById(R.id.text_about_content_title);
        //Setting the Typeface
        textViewContentTitle.setTypeface(ResourcesCompat.getFont(this, R.font.audiowide));
    }

    /**
     * Method that initializes the Content Introductory TextView ('R.id.text_about_content_intro')
     * with the Html Text and sets the Typeface.
     */
    private void initContentIntroText() {
        //Finding the TextView
        TextView textViewContentIntro = findViewById(R.id.text_about_content_intro);
        //Setting the Html Content
        TextAppearanceUtility.setHtmlText(textViewContentIntro, getString(R.string.about_content_intro));
        //Setting the Typeface
        textViewContentIntro.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_alternates));
    }

    /**
     * Method that initializes the Content Description TextView ('R.id.text_about_content_desc')
     * with the Html Text and sets the Typeface.
     */
    private void initContentDescriptionText() {
        //Finding the TextView
        TextView textViewContentDescription = findViewById(R.id.text_about_content_desc);
        //Setting the Html Content
        TextAppearanceUtility.setHtmlText(textViewContentDescription, getString(R.string.about_content_desc));
        //Making the embedded links clickable
        textViewContentDescription.setMovementMethod(LinkMovementMethod.getInstance());
        //Setting the Typeface
        textViewContentDescription.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_alternates));
    }

    /**
     * Method that initializes the ImageViews and registers the Click Listeners on them
     */
    private void initImageViews() {
        //Finding the ImageViews
        ImageView imageViewUdacity = findViewById(R.id.image_about_udacity);
        ImageView imageViewGithub = findViewById(R.id.image_about_github);
        ImageView imageViewLinkedIn = findViewById(R.id.image_about_linkedin);

        //Registering Click Listener on these Views
        imageViewUdacity.setOnClickListener(this);
        imageViewGithub.setOnClickListener(this);
        imageViewLinkedIn.setOnClickListener(this);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handling the Menu Item selected based on their Id
        switch (item.getItemId()) {
            case android.R.id.home:
                //Handling the action bar's home/up button
                finish(); //Finishing the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        //Executing action based on View's id
        switch (view.getId()) {
            case R.id.image_about_udacity:
                //For the Udacity ImageView

                //Opens the webpage for the course provided by Udacity
                IntentUtility.openLink(this, getString(R.string.about_link_udacity_course));
                break;
            case R.id.image_about_github:
                //For the GitHub ImageView

                //Opens my Github profile
                IntentUtility.openLink(this, getString(R.string.about_link_github_profile));
                break;
            case R.id.image_about_linkedin:
                //For the LinkedIn ImageView

                //Opens my LinkedIn profile
                IntentUtility.openLink(this, getString(R.string.about_link_linkedin_profile));
                break;
        }
    }
}
