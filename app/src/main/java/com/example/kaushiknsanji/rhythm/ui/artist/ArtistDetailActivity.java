package com.example.kaushiknsanji.rhythm.ui.artist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
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

/**
 * A {@link PlayerActivity} that inflates the layout 'R.layout.activity_artist_detail'
 * to display the details of the Artist launched along with the list of Artist's Albums
 * and Artist's Songs available in each Album.
 *
 * @author Kaushik N Sanji
 */
public class ArtistDetailActivity extends PlayerActivity
        implements View.OnClickListener {

    //Intent Extra Constant for the Artist Name
    private static final String EXTRA_ARTIST_NAME = ArtistDetailActivity.class.getPackage() + "extra.ARTIST_NAME";
    //Intent Extra Constant for the Artist Photo
    private static final String EXTRA_ARTIST_PHOTO = ArtistDetailActivity.class.getPackage() + "extra.ARTIST_PHOTO";

    //Bundle Key constants to save/restore the state of Favorite
    private static final String BUNDLE_FAVORITE_BOOL_KEY = "bool.Artist.Favorite";

    //For caching the required view references
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mImageViewArtistPhoto;
    private TextView mTextViewAlbum1Track1Title;
    private TextView mTextViewAlbum1Track2Title;
    private TextView mTextViewAlbum1Track3Title;
    private TextView mTextViewAlbum2Track1Title;
    private TextView mTextViewAlbum2Track2Title;
    private TextView mTextViewAlbum2Track3Title;
    private TextView mTextViewAlbum1Title;
    private TextView mTextViewAlbum2Title;

    //For the 'Favorite' Menu item
    private MenuItem mMenuItemFavorite;

    //Stores the Artist Name
    private String mArtistName;
    //Stores the Artist Photo
    private int mArtistPhotoResId;

    //For keeping track of Favorite state
    private boolean mFavoriteArtist;

    //For the Animated Vector Drawables used for Favorite
    private AnimatedVectorDrawableCompat mAvdHeartEmpty;
    private AnimatedVectorDrawableCompat mAvdHeartFill;

    /**
     * Method that starts the {@link ArtistDetailActivity} through an Intent.
     *
     * @param context     Context of the Calling Activity/Fragment
     * @param artistName  Name of the Artist
     * @param artistPhoto Drawable Resource Id of the Artist Photo
     */
    public static void launchArtistDetail(Context context,
                                          String artistName,
                                          @DrawableRes int artistPhoto) {
        //Creating the Intent
        Intent intent = new Intent(context, ArtistDetailActivity.class);
        //Passing in the Artist Name as Extra
        intent.putExtra(EXTRA_ARTIST_NAME, artistName);
        //Passing in the Artist Photo Resource Id as Extra
        intent.putExtra(EXTRA_ARTIST_PHOTO, artistPhoto);
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
        setContentView(R.layout.activity_artist_detail);

        //Find the required Views
        mToolbar = findViewById(R.id.include_toolbar_artist_detail);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_artist_detail);
        mImageViewArtistPhoto = findViewById(R.id.image_artist_photo);
        mTextViewAlbum1Track1Title = findViewById(R.id.text_artist_detail_album_1_track_1_title);
        mTextViewAlbum1Track2Title = findViewById(R.id.text_artist_detail_album_1_track_2_title);
        mTextViewAlbum1Track3Title = findViewById(R.id.text_artist_detail_album_1_track_3_title);
        mTextViewAlbum2Track1Title = findViewById(R.id.text_artist_detail_album_2_track_1_title);
        mTextViewAlbum2Track2Title = findViewById(R.id.text_artist_detail_album_2_track_2_title);
        mTextViewAlbum2Track3Title = findViewById(R.id.text_artist_detail_album_2_track_3_title);
        mTextViewAlbum1Title = findViewById(R.id.text_artist_detail_album_1_title);
        mTextViewAlbum2Title = findViewById(R.id.text_artist_detail_album_2_title);

        //Set Click listeners on required views
        findViewById(R.id.fab_artist_detail_play).setOnClickListener(this);
        findViewById(R.id.view_artist_detail_album_1_track_1_clickable).setOnClickListener(this);
        findViewById(R.id.view_artist_detail_album_1_track_2_clickable).setOnClickListener(this);
        findViewById(R.id.view_artist_detail_album_1_track_3_clickable).setOnClickListener(this);
        findViewById(R.id.view_artist_detail_album_2_track_1_clickable).setOnClickListener(this);
        findViewById(R.id.view_artist_detail_album_2_track_2_clickable).setOnClickListener(this);
        findViewById(R.id.view_artist_detail_album_2_track_3_clickable).setOnClickListener(this);
        findViewById(R.id.imgbtn_artist_detail_album_1_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_artist_detail_album_2_playlist_queue).setOnClickListener(this);
        findViewById(R.id.imgbtn_artist_detail_album_1_more).setOnClickListener(this);
        findViewById(R.id.imgbtn_artist_detail_album_2_more).setOnClickListener(this);

        //Get the Artist data received from the Intent
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            mArtistName = receivedIntent.getStringExtra(EXTRA_ARTIST_NAME);
            mArtistPhotoResId = receivedIntent.getIntExtra(EXTRA_ARTIST_PHOTO, R.drawable.ic_all_artist);
        }

        //Initialize Toolbar
        setupToolbar();

        //Initialize Image on the ImageView of Toolbar based on the Artist Photo received
        setupArtistPhoto();

        //Set the Explanation Text for this screen
        setupExplanationText(
                findViewById(R.id.text_all_explanation),
                R.string.artist_detail_explanation
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
        outState.putBoolean(BUNDLE_FAVORITE_BOOL_KEY, mFavoriteArtist);
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
        mFavoriteArtist = savedInstanceState.getBoolean(BUNDLE_FAVORITE_BOOL_KEY);
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

        //Set the Artist Name as Toolbar Title
        mCollapsingToolbarLayout.setTitle(mArtistName);

        //Set the Typeface for Title
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(getTitleTextTypeface());
        mCollapsingToolbarLayout.setExpandedTitleTypeface(getTitleTextTypeface());
    }

    /**
     * Method that sets the Drawable on Toolbar ImageView 'R.id.image_artist_photo'
     * based on the received Artist Photo {@link #mImageViewArtistPhoto}
     */
    private void setupArtistPhoto() {
        mImageViewArtistPhoto.setImageResource(mArtistPhotoResId);
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
            case R.id.fab_artist_detail_play:
                //For the FAB 'Play' Button
                //Show a Message
                showToast(
                        Toast.makeText(this,
                                R.string.artist_detail_message_fab_play_button,
                                Toast.LENGTH_LONG)
                );
                //Load and Play the First Track of First Album Shown
                playTrack(
                        mTextViewAlbum1Track1Title.getText().toString(),
                        mTextViewAlbum1Title.getText().toString(),
                        mArtistName
                );
                break;

            case R.id.view_artist_detail_album_1_track_1_clickable:
                //Load and Play Track-1 of Album-1
                playTrack(
                        mTextViewAlbum1Track1Title.getText().toString(),
                        mTextViewAlbum1Title.getText().toString(),
                        mArtistName
                );
                break;
            case R.id.view_artist_detail_album_1_track_2_clickable:
                //Load and Play Track-2 of Album-1
                playTrack(
                        mTextViewAlbum1Track2Title.getText().toString(),
                        mTextViewAlbum1Title.getText().toString(),
                        mArtistName
                );
                break;
            case R.id.view_artist_detail_album_1_track_3_clickable:
                //Load and Play Track-3 of Album-1
                playTrack(
                        mTextViewAlbum1Track3Title.getText().toString(),
                        mTextViewAlbum1Title.getText().toString(),
                        mArtistName
                );
                break;
            case R.id.view_artist_detail_album_2_track_1_clickable:
                //Load and Play Track-1 of Album-2
                playTrack(
                        mTextViewAlbum2Track1Title.getText().toString(),
                        mTextViewAlbum2Title.getText().toString(),
                        mArtistName
                );
                break;
            case R.id.view_artist_detail_album_2_track_2_clickable:
                //Load and Play Track-2 of Album-2
                playTrack(
                        mTextViewAlbum2Track2Title.getText().toString(),
                        mTextViewAlbum2Title.getText().toString(),
                        mArtistName
                );
                break;
            case R.id.view_artist_detail_album_2_track_3_clickable:
                //Load and Play Track-3 of Album-2
                playTrack(
                        mTextViewAlbum2Track3Title.getText().toString(),
                        mTextViewAlbum2Title.getText().toString(),
                        mArtistName
                );
                break;

            case R.id.imgbtn_artist_detail_album_1_playlist_queue:
            case R.id.imgbtn_artist_detail_album_2_playlist_queue:
                //For the "Add to Queue" ImageButtons
                showToast(
                        Toast.makeText(this,
                                R.string.artist_detail_album_item_message_playlist_queue_button,
                                Toast.LENGTH_LONG)
                );
                break;

            case R.id.imgbtn_artist_detail_album_1_more:
            case R.id.imgbtn_artist_detail_album_2_more:
                //For the "More" Action ImageButtons
                showToast(
                        Toast.makeText(this,
                                R.string.artist_detail_album_item_message_more_action_button,
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
        //Inflating the menu 'R.menu.menu_activity_artist_detail' for the activity
        getMenuInflater().inflate(R.menu.menu_activity_artist_detail, menu);
        //Find the 'Favorite' Menu item
        mMenuItemFavorite = menu.findItem(R.id.action_favorite);
        //Animate 'Favorite' Menu item to the state accordingly
        animateHeartFill(mFavoriteArtist);
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
     * Method that adds/removes the Artist from "My Favorites" playlist
     */
    @SuppressLint("ShowToast")
    private void modifyFavorite() {
        //Toggle the Favorite state
        mFavoriteArtist = !mFavoriteArtist;
        //Animate to the state accordingly
        animateHeartFill(mFavoriteArtist);
        //Show a message for the action
        if (mFavoriteArtist) {
            showToast(Toast.makeText(this, R.string.artist_detail_message_favorite_menu_artist_add, Toast.LENGTH_LONG));
        } else {
            showToast(Toast.makeText(this, R.string.artist_detail_message_favorite_menu_artist_remove, Toast.LENGTH_LONG));
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
                                R.string.artist_detail_message_add_to_playlist_menu,
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
                                R.string.artist_detail_message_delete_from_storage_menu,
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