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

package com.example.kaushiknsanji.rhythm.ui.album;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.ui.common.activities.PlayerActivity;

/**
 * A {@link PlayerActivity} that inflates the layout 'R.layout.activity_album_detail'
 * to display the details of the Album launched along with the list of Songs
 * available in the Album.
 *
 * @author Kaushik N Sanji
 */
public class AlbumDetailActivity extends PlayerActivity
        implements View.OnClickListener {

    //Intent Extra Constant for the Album Title
    private static final String EXTRA_ALBUM_TITLE = AlbumDetailActivity.class.getPackage() + "extra.ALBUM_TITLE";
    //Intent Extra Constant for the Artist Name
    private static final String EXTRA_ARTIST_NAME = AlbumDetailActivity.class.getPackage() + "extra.ARTIST_NAME";
    //Intent Extra Constant for the Year of Album Release
    private static final String EXTRA_RELEASE_YEAR = AlbumDetailActivity.class.getPackage() + "extra.RELEASE_YEAR";
    //Intent Extra Constant for the Album Genre
    private static final String EXTRA_ALBUM_GENRE = AlbumDetailActivity.class.getPackage() + "extra.ALBUM_GENRE";
    //Intent Extra Constant for the Total Tracks in the Album
    private static final String EXTRA_TRACK_TOTAL = AlbumDetailActivity.class.getPackage() + "extra.TRACK_TOTAL";

    //Bundle Key constants to save/restore the state of Favorite
    private static final String BUNDLE_FAVORITE_BOOL_KEY = "bool.Album.Favorite";

    //For caching the required view references
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mTextViewTrack1Title;
    private TextView mTextViewTrack2Title;
    private TextView mTextViewTrack3Title;
    private TextView mTextViewTrack4Title;
    private TextView mTextViewTrack5Title;
    private TextView mTextViewTrack6Title;
    private TextView mTextViewTrack7Title;
    private TextView mTextViewTrack8Title;
    private TextView mTextViewTrack9Title;
    private TextView mTextViewTrack10Title;
    private TextView mTextViewTrack11Title;
    private TextView mTextViewTrack12Title;
    private TextView mTextViewTrack13Title;
    private TextView mTextViewTrack14Title;
    private TextView mTextViewTrack15Title;
    private TextView mTextViewTrack16Title;
    private TextView mTextViewTrack1Artist;
    private TextView mTextViewTrack2Artist;
    private TextView mTextViewTrack3Artist;
    private TextView mTextViewTrack4Artist;
    private TextView mTextViewTrack5Artist;
    private TextView mTextViewTrack6Artist;
    private TextView mTextViewTrack7Artist;
    private TextView mTextViewTrack8Artist;
    private TextView mTextViewTrack9Artist;
    private TextView mTextViewTrack10Artist;
    private TextView mTextViewTrack11Artist;
    private TextView mTextViewTrack12Artist;
    private TextView mTextViewTrack13Artist;
    private TextView mTextViewTrack14Artist;
    private TextView mTextViewTrack15Artist;
    private TextView mTextViewTrack16Artist;

    //For the 'Favorite' Menu item
    private MenuItem mMenuItemFavorite;

    //Stores the Album Title
    private String mAlbumTitle;

    //For keeping track of Favorite state
    private boolean mFavoriteAlbum;

    //For the Animated Vector Drawables used for Favorite
    private AnimatedVectorDrawableCompat mAvdHeartEmpty;
    private AnimatedVectorDrawableCompat mAvdHeartFill;

    /**
     * Method that starts the {@link AlbumDetailActivity} through an Intent.
     *
     * @param context       Context of the Calling Activity/Fragment
     * @param albumTitle    The Title of the Album
     * @param artistName    Name of the Artist
     * @param yearOfRelease Year of the Album Release
     * @param albumGenre    Genre of the Album
     * @param trackTotal    Total tracks in the Album
     */
    public static void launchAlbumDetail(Context context,
                                         String albumTitle,
                                         String artistName,
                                         String yearOfRelease,
                                         String albumGenre,
                                         String trackTotal) {
        //Creating the Intent
        Intent intent = new Intent(context, AlbumDetailActivity.class);
        //Passing in the Album Title as Extra
        intent.putExtra(EXTRA_ALBUM_TITLE, albumTitle);
        //Passing in the Artist Name as Extra
        intent.putExtra(EXTRA_ARTIST_NAME, artistName);
        //Passing in the Year of Album Release as Extra
        intent.putExtra(EXTRA_RELEASE_YEAR, yearOfRelease);
        //Passing in the Album Genre as Extra
        intent.putExtra(EXTRA_ALBUM_GENRE, albumGenre);
        //Passing in the Total Tracks as Extra
        intent.putExtra(EXTRA_TRACK_TOTAL, trackTotal);
        //Starting the Activity
        context.startActivity(intent);
    }

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
        //Inflating the activity's layout
        setContentView(R.layout.activity_album_detail);

        //Find the required Views
        mToolbar = findViewById(R.id.include_toolbar_album_detail);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_album_detail);
        mTextViewTrack1Title = findViewById(R.id.text_album_detail_track_1_title);
        mTextViewTrack2Title = findViewById(R.id.text_album_detail_track_2_title);
        mTextViewTrack3Title = findViewById(R.id.text_album_detail_track_3_title);
        mTextViewTrack4Title = findViewById(R.id.text_album_detail_track_4_title);
        mTextViewTrack5Title = findViewById(R.id.text_album_detail_track_5_title);
        mTextViewTrack6Title = findViewById(R.id.text_album_detail_track_6_title);
        mTextViewTrack7Title = findViewById(R.id.text_album_detail_track_7_title);
        mTextViewTrack8Title = findViewById(R.id.text_album_detail_track_8_title);
        mTextViewTrack9Title = findViewById(R.id.text_album_detail_track_9_title);
        mTextViewTrack10Title = findViewById(R.id.text_album_detail_track_10_title);
        mTextViewTrack11Title = findViewById(R.id.text_album_detail_track_11_title);
        mTextViewTrack12Title = findViewById(R.id.text_album_detail_track_12_title);
        mTextViewTrack13Title = findViewById(R.id.text_album_detail_track_13_title);
        mTextViewTrack14Title = findViewById(R.id.text_album_detail_track_14_title);
        mTextViewTrack15Title = findViewById(R.id.text_album_detail_track_15_title);
        mTextViewTrack16Title = findViewById(R.id.text_album_detail_track_16_title);
        mTextViewTrack1Artist = findViewById(R.id.text_album_detail_track_1_artists);
        mTextViewTrack2Artist = findViewById(R.id.text_album_detail_track_2_artists);
        mTextViewTrack3Artist = findViewById(R.id.text_album_detail_track_3_artists);
        mTextViewTrack4Artist = findViewById(R.id.text_album_detail_track_4_artists);
        mTextViewTrack5Artist = findViewById(R.id.text_album_detail_track_5_artists);
        mTextViewTrack6Artist = findViewById(R.id.text_album_detail_track_6_artists);
        mTextViewTrack7Artist = findViewById(R.id.text_album_detail_track_7_artists);
        mTextViewTrack8Artist = findViewById(R.id.text_album_detail_track_8_artists);
        mTextViewTrack9Artist = findViewById(R.id.text_album_detail_track_9_artists);
        mTextViewTrack10Artist = findViewById(R.id.text_album_detail_track_10_artists);
        mTextViewTrack11Artist = findViewById(R.id.text_album_detail_track_11_artists);
        mTextViewTrack12Artist = findViewById(R.id.text_album_detail_track_12_artists);
        mTextViewTrack13Artist = findViewById(R.id.text_album_detail_track_13_artists);
        mTextViewTrack14Artist = findViewById(R.id.text_album_detail_track_14_artists);
        mTextViewTrack15Artist = findViewById(R.id.text_album_detail_track_15_artists);
        mTextViewTrack16Artist = findViewById(R.id.text_album_detail_track_16_artists);
        TextView textViewAlbumArtist = findViewById(R.id.text_album_detail_artist);
        TextView textViewReleaseYear = findViewById(R.id.text_album_detail_year);
        TextView textViewGenre = findViewById(R.id.text_album_detail_genre);
        TextView textViewTrackTotal = findViewById(R.id.text_album_detail_songs_count);

        //Set Click listeners on required views
        findViewById(R.id.fab_album_detail_play).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_1_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_2_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_3_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_4_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_5_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_6_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_7_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_8_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_9_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_10_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_11_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_12_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_13_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_14_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_15_clickable).setOnClickListener(this);
        findViewById(R.id.view_album_detail_track_16_clickable).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_1_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_2_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_3_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_4_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_5_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_6_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_7_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_8_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_9_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_10_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_11_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_12_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_13_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_14_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_15_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_16_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_1_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_2_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_3_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_4_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_5_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_6_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_7_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_8_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_9_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_10_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_11_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_12_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_13_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_14_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_15_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_album_detail_track_16_more).setOnClickListener(this);

        //Set the Album data received from the Intent
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            textViewAlbumArtist.setText(receivedIntent.getStringExtra(EXTRA_ARTIST_NAME));
            textViewReleaseYear.setText(receivedIntent.getStringExtra(EXTRA_RELEASE_YEAR));
            textViewGenre.setText(receivedIntent.getStringExtra(EXTRA_ALBUM_GENRE));
            textViewTrackTotal.setText(receivedIntent.getStringExtra(EXTRA_TRACK_TOTAL));
            mAlbumTitle = receivedIntent.getStringExtra(EXTRA_ALBUM_TITLE);
        }

        //Initialize Toolbar
        setupToolbar();

        //Set the Explanation Text for this screen
        setupExplanationText(
                findViewById(R.id.text_all_explanation),
                R.string.album_detail_explanation
        );

        //Get the Animated Vector Drawables for Favorite
        mAvdHeartEmpty = AnimatedVectorDrawableCompat.create(this, R.drawable.ic_all_heart_empty_animatable);
        mAvdHeartFill = AnimatedVectorDrawableCompat.create(this, R.drawable.ic_all_heart_fill_animatable);
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed
     * so that the state can be restored in {@link #onCreate} or
     * {@link #onRestoreInstanceState} (the {@link Bundle} populated by this method
     * will be passed to both).
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Saving the state of Favorite
        outState.putBoolean(BUNDLE_FAVORITE_BOOL_KEY, mFavoriteAlbum);
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.  Most implementations will simply use {@link #onCreate}
     * to restore their state, but it is sometimes convenient to do it here
     * after all of the initialization has been done or to allow subclasses to
     * decide whether to use your default implementation.  The default
     * implementation of this method performs a restore of any view state that
     * had previously been frozen by {@link #onSaveInstanceState}.
     * <p>
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Restoring the state of Favorite
        mFavoriteAlbum = savedInstanceState.getBoolean(BUNDLE_FAVORITE_BOOL_KEY);
    }

    /**
     * Method that initializes the Toolbar as ActionBar and sets the Collapsing Toolbar Title
     */
    private void setupToolbar() {
        //Set as ActionBar
        setSupportActionBar(mToolbar);

        //Retrieving the Action Bar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            //Enabling Home as Up button
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Set the Album Title as Toolbar Title
        mCollapsingToolbarLayout.setTitle(mAlbumTitle);

        //Set the Typeface for Title
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(getTitleTextTypeface());
        mCollapsingToolbarLayout.setExpandedTitleTypeface(getTitleTextTypeface());
    }

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     *
     * @return true if Up navigation completed successfully and this Activity was finished,
     * false otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        //Executing Back Navigation
        onBackPressed();
        //Returning TRUE since Up navigation is handled
        return true;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @SuppressLint("ShowToast")
    @Override
    public void onClick(View view) {
        //Taking action based on the Id of the View clicked
        switch (view.getId()) {
            case R.id.fab_album_detail_play:
                //For the FAB 'Play' Button
                //Show a Message
                showToast(
                        Toast.makeText(this,
                                R.string.album_detail_message_fab_play_button,
                                Toast.LENGTH_LONG)
                );
                //Load and Play the First Track
                playTrack(
                        mTextViewTrack1Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack1Artist.getText().toString()
                );
                break;

            case R.id.view_album_detail_track_1_clickable:
                //Load and Play the Track-1
                playTrack(
                        mTextViewTrack1Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack1Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_2_clickable:
                //Load and Play the Track-2
                playTrack(
                        mTextViewTrack2Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack2Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_3_clickable:
                //Load and Play the Track-3
                playTrack(
                        mTextViewTrack3Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack3Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_4_clickable:
                //Load and Play the Track-4
                playTrack(
                        mTextViewTrack4Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack4Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_5_clickable:
                //Load and Play the Track-5
                playTrack(
                        mTextViewTrack5Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack5Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_6_clickable:
                //Load and Play the Track-6
                playTrack(
                        mTextViewTrack6Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack6Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_7_clickable:
                //Load and Play the Track-7
                playTrack(
                        mTextViewTrack7Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack7Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_8_clickable:
                //Load and Play the Track-8
                playTrack(
                        mTextViewTrack8Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack8Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_9_clickable:
                //Load and Play the Track-9
                playTrack(
                        mTextViewTrack9Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack9Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_10_clickable:
                //Load and Play the Track-10
                playTrack(
                        mTextViewTrack10Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack10Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_11_clickable:
                //Load and Play the Track-11
                playTrack(
                        mTextViewTrack11Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack11Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_12_clickable:
                //Load and Play the Track-12
                playTrack(
                        mTextViewTrack12Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack12Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_13_clickable:
                //Load and Play the Track-13
                playTrack(
                        mTextViewTrack13Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack13Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_14_clickable:
                //Load and Play the Track-14
                playTrack(
                        mTextViewTrack14Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack14Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_15_clickable:
                //Load and Play the Track-15
                playTrack(
                        mTextViewTrack15Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack15Artist.getText().toString()
                );
                break;
            case R.id.view_album_detail_track_16_clickable:
                //Load and Play the Track-16
                playTrack(
                        mTextViewTrack16Title.getText().toString(),
                        mAlbumTitle,
                        mTextViewTrack16Artist.getText().toString()
                );
                break;

            case R.id.imgbtn_album_detail_track_1_playlist_queue:
            case R.id.imgbtn_album_detail_track_2_playlist_queue:
            case R.id.imgbtn_album_detail_track_3_playlist_queue:
            case R.id.imgbtn_album_detail_track_4_playlist_queue:
            case R.id.imgbtn_album_detail_track_5_playlist_queue:
            case R.id.imgbtn_album_detail_track_6_playlist_queue:
            case R.id.imgbtn_album_detail_track_7_playlist_queue:
            case R.id.imgbtn_album_detail_track_8_playlist_queue:
            case R.id.imgbtn_album_detail_track_9_playlist_queue:
            case R.id.imgbtn_album_detail_track_10_playlist_queue:
            case R.id.imgbtn_album_detail_track_11_playlist_queue:
            case R.id.imgbtn_album_detail_track_12_playlist_queue:
            case R.id.imgbtn_album_detail_track_13_playlist_queue:
            case R.id.imgbtn_album_detail_track_14_playlist_queue:
            case R.id.imgbtn_album_detail_track_15_playlist_queue:
            case R.id.imgbtn_album_detail_track_16_playlist_queue:
                //For the "Add to Queue" ImageButtons
                showToast(
                        Toast.makeText(this,
                                R.string.album_detail_track_item_message_playlist_queue_button,
                                Toast.LENGTH_LONG)
                );
                break;

            case R.id.imgbtn_album_detail_track_1_more:
            case R.id.imgbtn_album_detail_track_2_more:
            case R.id.imgbtn_album_detail_track_3_more:
            case R.id.imgbtn_album_detail_track_4_more:
            case R.id.imgbtn_album_detail_track_5_more:
            case R.id.imgbtn_album_detail_track_6_more:
            case R.id.imgbtn_album_detail_track_7_more:
            case R.id.imgbtn_album_detail_track_8_more:
            case R.id.imgbtn_album_detail_track_9_more:
            case R.id.imgbtn_album_detail_track_10_more:
            case R.id.imgbtn_album_detail_track_11_more:
            case R.id.imgbtn_album_detail_track_12_more:
            case R.id.imgbtn_album_detail_track_13_more:
            case R.id.imgbtn_album_detail_track_14_more:
            case R.id.imgbtn_album_detail_track_15_more:
            case R.id.imgbtn_album_detail_track_16_more:
                //For the "More" Action ImageButtons
                showToast(
                        Toast.makeText(this,
                                R.string.album_detail_track_item_message_more_action_button,
                                Toast.LENGTH_LONG)
                );
                break;

            default:
                //For others, delegate to super
                super.onClick(view);
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflating the menu 'R.menu.menu_activity_album_detail' for the activity
        getMenuInflater().inflate(R.menu.menu_activity_album_detail, menu);
        //Find the 'Favorite' Menu item
        mMenuItemFavorite = menu.findItem(R.id.action_favorite);
        //Animate 'Favorite' Menu item to the state accordingly
        animateHeartFill(mFavoriteAlbum);
        //Returning True to display the Menu
        return true;
    }

    /**
     * Method that animates the Favorite and Not-Favorite button using drawables
     *
     * @param favorite <b>TRUE</b> to animate to filled Heart representing as Favorite;
     *                 <b>FALSE</b> to animate to empty Heart representing as Not a Favorite
     */
    private void animateHeartFill(boolean favorite) {
        //Pick the animated drawable based on the boolean passed
        AnimatedVectorDrawableCompat animatedDrawable = favorite ? mAvdHeartFill : mAvdHeartEmpty;
        //Set the MenuItem with the drawable
        mMenuItemFavorite.setIcon(animatedDrawable);
        //Start animating
        animatedDrawable.start();
    }

    /**
     * Method that adds/removes the Album from "My Favorites" playlist
     */
    @SuppressLint("ShowToast")
    private void modifyFavorite() {
        //Toggle the Favorite state
        mFavoriteAlbum = !mFavoriteAlbum;
        //Animate to the state accordingly
        animateHeartFill(mFavoriteAlbum);
        //Show a message for the action
        if (mFavoriteAlbum) {
            showToast(Toast.makeText(this, R.string.album_detail_message_favorite_menu_album_add, Toast.LENGTH_LONG));
        } else {
            showToast(Toast.makeText(this, R.string.album_detail_message_favorite_menu_album_remove, Toast.LENGTH_LONG));
        }
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @SuppressLint("ShowToast")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Taking action based on the Id of the Menu Item clicked
        switch (item.getItemId()) {
            case R.id.action_favorite:
                //For "Favorite" menu

                //Modify Favorite
                modifyFavorite();

                //Returning True since the action has been handled
                return true;
            case R.id.action_add_to_playlist:
                //For "Add to Playlist" menu

                //Show a message
                showToast(
                        Toast.makeText(
                                this,
                                R.string.album_detail_message_add_to_playlist_menu,
                                Toast.LENGTH_LONG
                        )
                );

                //Returning True since the action has been handled
                return true;
            case R.id.action_delete_from_storage:
                //For "Delete from Storage" menu

                //Show a message
                showToast(
                        Toast.makeText(
                                this,
                                R.string.album_detail_message_delete_from_storage_menu,
                                Toast.LENGTH_LONG
                        )
                );

                //Returning True since the action has been handled
                return true;
            default:
                //On all else, delegate to super
                return super.onOptionsItemSelected(item);
        }
    }
}