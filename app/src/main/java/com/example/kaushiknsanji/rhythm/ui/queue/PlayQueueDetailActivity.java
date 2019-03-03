package com.example.kaushiknsanji.rhythm.ui.queue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.ui.common.activities.PlayerActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A {@link PlayerActivity} that inflates the layout 'R.layout.activity_play_queue_detail'
 * to display the list of Songs enqueued in the Playlist launched.
 *
 * @author Kaushik N Sanji
 */
public class PlayQueueDetailActivity extends PlayerActivity
        implements View.OnClickListener {

    //Constants defining the types of Playlists
    public static final String TYPE_USER = "Playlist.User";
    public static final String TYPE_SMART = "Playlist.Smart";
    //Intent Extra Constant for the Playlist Title
    private static final String EXTRA_PLAYLIST_TITLE = PlayQueueDetailActivity.class.getPackage() + "extra.PLAYLIST_TITLE";
    //Intent Extra Constant for the Playlist Type
    private static final String EXTRA_PLAYLIST_TYPE = PlayQueueDetailActivity.class.getPackage() + "extra.PLAYLIST_TYPE";
    //For caching the required view references
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mImageViewPlaylistType;
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
    //Stores the Playlist title
    private String mPlaylistTitle;
    //Stores the Playlist Type
    private String mPlaylistType;

    /**
     * Method that starts the {@link PlayQueueDetailActivity} through an Intent.
     *
     * @param context       Context of the Calling Activity/Fragment
     * @param playlistTitle Title of the Playlist
     * @param playlistType  Type of the Playlist as defined by {@link PlaylistType}
     */
    public static void launchPlaylistDetail(Context context,
                                            String playlistTitle,
                                            @PlaylistType String playlistType) {
        //Creating the Intent
        Intent intent = new Intent(context, PlayQueueDetailActivity.class);
        //Passing in the Playlist Title as Extra
        intent.putExtra(EXTRA_PLAYLIST_TITLE, playlistTitle);
        //Passing in the Playlist Type as Extra
        intent.putExtra(EXTRA_PLAYLIST_TYPE, playlistType);
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
        setContentView(R.layout.activity_play_queue_detail);

        //Find the required Views
        mToolbar = findViewById(R.id.include_toolbar_play_queue_detail);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_play_queue_detail);
        mImageViewPlaylistType = findViewById(R.id.image_play_queue_detail_type);
        mTextViewTrack1Title = findViewById(R.id.text_play_queue_detail_track_1_title);
        mTextViewTrack2Title = findViewById(R.id.text_play_queue_detail_track_2_title);
        mTextViewTrack3Title = findViewById(R.id.text_play_queue_detail_track_3_title);
        mTextViewTrack4Title = findViewById(R.id.text_play_queue_detail_track_4_title);
        mTextViewTrack5Title = findViewById(R.id.text_play_queue_detail_track_5_title);
        mTextViewTrack6Title = findViewById(R.id.text_play_queue_detail_track_6_title);
        mTextViewTrack7Title = findViewById(R.id.text_play_queue_detail_track_7_title);
        mTextViewTrack8Title = findViewById(R.id.text_play_queue_detail_track_8_title);
        mTextViewTrack9Title = findViewById(R.id.text_play_queue_detail_track_9_title);
        mTextViewTrack10Title = findViewById(R.id.text_play_queue_detail_track_10_title);
        mTextViewTrack1Artist = findViewById(R.id.text_play_queue_detail_track_1_artists);
        mTextViewTrack2Artist = findViewById(R.id.text_play_queue_detail_track_2_artists);
        mTextViewTrack3Artist = findViewById(R.id.text_play_queue_detail_track_3_artists);
        mTextViewTrack4Artist = findViewById(R.id.text_play_queue_detail_track_4_artists);
        mTextViewTrack5Artist = findViewById(R.id.text_play_queue_detail_track_5_artists);
        mTextViewTrack6Artist = findViewById(R.id.text_play_queue_detail_track_6_artists);
        mTextViewTrack7Artist = findViewById(R.id.text_play_queue_detail_track_7_artists);
        mTextViewTrack8Artist = findViewById(R.id.text_play_queue_detail_track_8_artists);
        mTextViewTrack9Artist = findViewById(R.id.text_play_queue_detail_track_9_artists);
        mTextViewTrack10Artist = findViewById(R.id.text_play_queue_detail_track_10_artists);

        //Set Click listeners on required views
        findViewById(R.id.fab_play_queue_detail_play).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_1_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_2_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_3_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_4_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_5_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_6_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_7_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_8_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_9_clickable).setOnClickListener(this);
        findViewById(R.id.view_play_queue_detail_track_10_clickable).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_1_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_2_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_3_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_4_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_5_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_6_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_7_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_8_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_9_drag_handle).setOnClickListener(this);
        findViewById(R.id.imgbtn_play_queue_detail_track_10_drag_handle).setOnClickListener(this);

        //Get the Playlist data received from the Intent
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            mPlaylistTitle = receivedIntent.getStringExtra(EXTRA_PLAYLIST_TITLE);
            mPlaylistType = receivedIntent.getStringExtra(EXTRA_PLAYLIST_TYPE);
        }

        //Initialize Toolbar
        setupToolbar();

        //Initialize Image on the ImageView of Toolbar based on Playlist Type
        setupPlaylistTypeImage();

        //Set the Explanation Text for this screen
        setupExplanationText(
                findViewById(R.id.text_all_explanation),
                R.string.play_queue_detail_explanation
        );
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

        //Set the Playlist Title as Toolbar Title
        mCollapsingToolbarLayout.setTitle(mPlaylistTitle);

        //Set the Typeface for Title
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(getTitleTextTypeface());
        mCollapsingToolbarLayout.setExpandedTitleTypeface(getTitleTextTypeface());
    }

    /**
     * Method that sets the Drawable on Toolbar ImageView 'R.id.image_play_queue_detail_type'
     * based on the received Playlist Type {@link #mImageViewPlaylistType}
     */
    private void setupPlaylistTypeImage() {
        if (mPlaylistType.equals(TYPE_USER)) {
            //For Normal Playlist managed by User
            mImageViewPlaylistType.setImageResource(R.drawable.ic_all_playlist);
        } else if (mPlaylistType.equals(TYPE_SMART)) {
            //For Auto Playlist created with rules
            mImageViewPlaylistType.setImageResource(R.drawable.ic_all_playlist_smart);
        }
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
            case R.id.fab_play_queue_detail_play:
                //For the FAB 'Play' Button
                //Show a Message
                showToast(
                        Toast.makeText(this,
                                R.string.play_queue_detail_message_fab_play_button,
                                Toast.LENGTH_LONG)
                );
                //Load and Play the First Track
                playTrack(
                        mTextViewTrack1Title.getText().toString(),
                        getString(R.string.song_1_track_album),
                        mTextViewTrack1Artist.getText().toString()
                );
                break;

            case R.id.view_play_queue_detail_track_1_clickable:
                //Load and Play the Track-1
                playTrack(
                        mTextViewTrack1Title.getText().toString(),
                        getString(R.string.song_1_track_album),
                        mTextViewTrack1Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_2_clickable:
                //Load and Play the Track-2
                playTrack(
                        mTextViewTrack2Title.getText().toString(),
                        getString(R.string.song_2_track_album),
                        mTextViewTrack2Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_3_clickable:
                //Load and Play the Track-3
                playTrack(
                        mTextViewTrack3Title.getText().toString(),
                        getString(R.string.song_3_track_album),
                        mTextViewTrack3Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_4_clickable:
                //Load and Play the Track-4
                playTrack(
                        mTextViewTrack4Title.getText().toString(),
                        getString(R.string.song_4_track_album),
                        mTextViewTrack4Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_5_clickable:
                //Load and Play the Track-5
                playTrack(
                        mTextViewTrack5Title.getText().toString(),
                        getString(R.string.song_5_track_album),
                        mTextViewTrack5Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_6_clickable:
                //Load and Play the Track-6
                playTrack(
                        mTextViewTrack6Title.getText().toString(),
                        getString(R.string.song_6_track_album),
                        mTextViewTrack6Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_7_clickable:
                //Load and Play the Track-7
                playTrack(
                        mTextViewTrack7Title.getText().toString(),
                        getString(R.string.song_7_track_album),
                        mTextViewTrack7Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_8_clickable:
                //Load and Play the Track-8
                playTrack(
                        mTextViewTrack8Title.getText().toString(),
                        getString(R.string.song_8_track_album),
                        mTextViewTrack8Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_9_clickable:
                //Load and Play the Track-9
                playTrack(
                        mTextViewTrack9Title.getText().toString(),
                        getString(R.string.song_9_track_album),
                        mTextViewTrack9Artist.getText().toString()
                );
                break;
            case R.id.view_play_queue_detail_track_10_clickable:
                //Load and Play the Track-10
                playTrack(
                        mTextViewTrack10Title.getText().toString(),
                        getString(R.string.song_10_track_album),
                        mTextViewTrack10Artist.getText().toString()
                );
                break;

            case R.id.imgbtn_play_queue_detail_track_1_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_2_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_3_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_4_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_5_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_6_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_7_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_8_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_9_drag_handle:
            case R.id.imgbtn_play_queue_detail_track_10_drag_handle:
                //For the "Drag Handle" ImageButtons
                showToast(
                        Toast.makeText(this,
                                R.string.play_queue_detail_track_item_message_drag_handle_button,
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
        //Inflating the menu 'R.menu.menu_activity_play_queue_detail' for the activity
        getMenuInflater().inflate(R.menu.menu_activity_play_queue_detail, menu);
        //Returning True to display the Menu
        return true;
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
            case R.id.action_playlist_save:
                //For "Save Playlist" menu

                //Show a message
                String message;
                if (mPlaylistTitle.equals(getString(R.string.playlist_1_unsaved))) {
                    //For Unsaved Playlist
                    message = getString(R.string.play_queue_detail_message_unsaved_playlist_save_menu);
                } else {
                    //For an Existing Playlist
                    message = getString(R.string.play_queue_detail_message_existing_playlist_save_menu);
                }
                showToast(
                        Toast.makeText(
                                this,
                                message,
                                Toast.LENGTH_LONG
                        )
                );

                //Returning True since the action has been handled
                return true;
            case R.id.action_playlist_add_more:
                //For "Add More" menu

                //Show a message
                showToast(
                        Toast.makeText(
                                this,
                                R.string.play_queue_detail_message_playlist_add_more_menu,
                                Toast.LENGTH_LONG
                        )
                );

                //Returning True since the action has been handled
                return true;
            case R.id.action_playlist_delete:
                //For "Delete Playlist" menu

                //Show a message
                showToast(
                        Toast.makeText(
                                this,
                                R.string.play_queue_detail_message_playlist_delete_menu,
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

    //Defining the Annotation interface for valid Playlist types
    //Retaining Annotation till Compile Time
    @Retention(RetentionPolicy.SOURCE)
    //Enumerating Annotation with valid Playlist types
    @StringDef({TYPE_USER, TYPE_SMART})
    @interface PlaylistType {
    }
}