package com.example.kaushiknsanji.rhythm.ui.jukebox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.data.local.models.SongTrack;
import com.example.kaushiknsanji.rhythm.ui.common.activities.PlayerActivity;

/**
 * A {@link PlayerActivity} that inflates the layout 'R.layout.activity_jukebox_detail'
 * to display a list of Songs available in the Jukebox Channel launched. This Activity ensures that
 * payment is first collected before the user can listen to any of the Songs in the Jukebox channel.
 *
 * @author Kaushik N Sanji
 */
public class JukeboxDetailActivity extends PlayerActivity
        implements View.OnClickListener, JukeboxDetailPaymentDialogFragment.PaymentRequestListener {

    //Intent Extra Constant for the Jukebox Title
    private static final String EXTRA_JUKEBOX_TITLE = JukeboxDetailActivity.class.getPackage() + "extra.JUKEBOX_TITLE";
    //Intent Extra Constant for the Jukebox Image
    private static final String EXTRA_JUKEBOX_IMAGE = JukeboxDetailActivity.class.getPackage() + "extra.JUKEBOX_IMAGE";

    //Bundle Constant to save/restore the state of the Payment Collection
    private static final String BUNDLE_PAYMENT_CAPTURE_BOOL_KEY = "bool.Jukebox.PaymentCollected";
    //Bundle Constant to save/restore the Track to be played
    private static final String BUNDLE_TRACK_TO_PLAY_KEY = "SongTrack.Jukebox.TrackToPlay";

    //For caching the required view references
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mImageViewJukeboxType;
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

    //Stores the Jukebox Title
    private String mJukeboxTitle;
    //Stores the Jukebox Type Image
    private int mJukeboxTypeResId;

    //Saves the state of the Payment Collection
    private boolean mPaymentCollected;

    //Stores the Track to be played
    private SongTrack mSongTrackToPlay;

    /**
     * Method that starts the {@link JukeboxDetailActivity} through an Intent.
     *
     * @param context      Context of the Calling Activity/Fragment
     * @param jukeboxTitle Title of the Jukebox
     * @param jukeboxImage Drawable Resource Id of the Image for the Jukebox
     */
    public static void launchJukeboxDetail(Context context,
                                           String jukeboxTitle,
                                           @DrawableRes int jukeboxImage) {
        //Creating the Intent
        Intent intent = new Intent(context, JukeboxDetailActivity.class);
        //Passing in the Jukebox Title as Extra
        intent.putExtra(EXTRA_JUKEBOX_TITLE, jukeboxTitle);
        //Passing in the Jukebox Image Resource Id as Extra
        intent.putExtra(EXTRA_JUKEBOX_IMAGE, jukeboxImage);
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
        setContentView(R.layout.activity_jukebox_detail);

        //Find the required Views
        mToolbar = findViewById(R.id.include_toolbar_jukebox_detail);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_jukebox_detail);
        mImageViewJukeboxType = findViewById(R.id.image_jukebox_detail_type);
        mTextViewTrack1Title = findViewById(R.id.text_jukebox_detail_track_1_title);
        mTextViewTrack2Title = findViewById(R.id.text_jukebox_detail_track_2_title);
        mTextViewTrack3Title = findViewById(R.id.text_jukebox_detail_track_3_title);
        mTextViewTrack4Title = findViewById(R.id.text_jukebox_detail_track_4_title);
        mTextViewTrack5Title = findViewById(R.id.text_jukebox_detail_track_5_title);
        mTextViewTrack6Title = findViewById(R.id.text_jukebox_detail_track_6_title);
        mTextViewTrack7Title = findViewById(R.id.text_jukebox_detail_track_7_title);
        mTextViewTrack8Title = findViewById(R.id.text_jukebox_detail_track_8_title);
        mTextViewTrack9Title = findViewById(R.id.text_jukebox_detail_track_9_title);
        mTextViewTrack10Title = findViewById(R.id.text_jukebox_detail_track_10_title);
        mTextViewTrack1Artist = findViewById(R.id.text_jukebox_detail_track_1_artists);
        mTextViewTrack2Artist = findViewById(R.id.text_jukebox_detail_track_2_artists);
        mTextViewTrack3Artist = findViewById(R.id.text_jukebox_detail_track_3_artists);
        mTextViewTrack4Artist = findViewById(R.id.text_jukebox_detail_track_4_artists);
        mTextViewTrack5Artist = findViewById(R.id.text_jukebox_detail_track_5_artists);
        mTextViewTrack6Artist = findViewById(R.id.text_jukebox_detail_track_6_artists);
        mTextViewTrack7Artist = findViewById(R.id.text_jukebox_detail_track_7_artists);
        mTextViewTrack8Artist = findViewById(R.id.text_jukebox_detail_track_8_artists);
        mTextViewTrack9Artist = findViewById(R.id.text_jukebox_detail_track_9_artists);
        mTextViewTrack10Artist = findViewById(R.id.text_jukebox_detail_track_10_artists);

        //Set Click listeners on required views
        findViewById(R.id.fab_jukebox_detail_play).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_1_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_2_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_3_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_4_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_5_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_6_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_7_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_8_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_9_clickable).setOnClickListener(this);
        findViewById(R.id.view_jukebox_detail_track_10_clickable).setOnClickListener(this);

        //Get the Jukebox data received from the Intent
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            mJukeboxTitle = receivedIntent.getStringExtra(EXTRA_JUKEBOX_TITLE);
            mJukeboxTypeResId = receivedIntent.getIntExtra(EXTRA_JUKEBOX_IMAGE, R.drawable.ic_all_jukebox);
        }

        //Initialize Toolbar
        setupToolbar();

        //Initialize Image on the ImageView of Toolbar based on Jukebox Type
        setupJukeboxTypeImage();

        //Set the Explanation Text for this screen
        setupExplanationText(
                findViewById(R.id.text_all_explanation),
                R.string.jukebox_detail_explanation
        );
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

        //Restoring the Payment Capture state
        mPaymentCollected = savedInstanceState.getBoolean(BUNDLE_PAYMENT_CAPTURE_BOOL_KEY);
        //Restoring the Track to be Played
        mSongTrackToPlay = savedInstanceState.getParcelable(BUNDLE_TRACK_TO_PLAY_KEY);
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

        //Saving the Payment Capture state
        outState.putBoolean(BUNDLE_PAYMENT_CAPTURE_BOOL_KEY, mPaymentCollected);
        //Saving the Track to be Played
        outState.putParcelable(BUNDLE_TRACK_TO_PLAY_KEY, mSongTrackToPlay);
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

        //Set the Jukebox Title as Toolbar Title
        mCollapsingToolbarLayout.setTitle(mJukeboxTitle);

        //Set the Typeface for Title
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(getTitleTextTypeface());
        mCollapsingToolbarLayout.setExpandedTitleTypeface(getTitleTextTypeface());
    }

    /**
     * Method that sets the Drawable on Toolbar ImageView 'R.id.image_jukebox_detail_type'
     * based on the received Jukebox Type {@link #mImageViewJukeboxType}
     */
    private void setupJukeboxTypeImage() {
        mImageViewJukeboxType.setImageResource(mJukeboxTypeResId);
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
    @Override
    public void onClick(View view) {
        //Taking action based on the Id of the View clicked
        switch (view.getId()) {
            case R.id.fab_jukebox_detail_play:
                //For the FAB 'Play' Button

                //Save the First Track to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack1Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_1_track_album))
                        .setArtists(mTextViewTrack1Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;

            case R.id.view_jukebox_detail_track_1_clickable:
                //For Track-1

                //Save Track-1 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack1Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_1_track_album))
                        .setArtists(mTextViewTrack1Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_2_clickable:
                //For Track-2

                //Save Track-2 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack2Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_2_track_album))
                        .setArtists(mTextViewTrack2Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_3_clickable:
                //For Track-3

                //Save Track-3 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack3Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_3_track_album))
                        .setArtists(mTextViewTrack3Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_4_clickable:
                //For Track-4

                //Save Track-4 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack4Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_4_track_album))
                        .setArtists(mTextViewTrack4Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_5_clickable:
                //For Track-5

                //Save Track-5 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack5Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_5_track_album))
                        .setArtists(mTextViewTrack5Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_6_clickable:
                //For Track-6

                //Save Track-6 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack6Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_6_track_album))
                        .setArtists(mTextViewTrack6Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_7_clickable:
                //For Track-7

                //Save Track-7 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack7Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_7_track_album))
                        .setArtists(mTextViewTrack7Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_8_clickable:
                //For Track-8

                //Save Track-8 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack8Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_8_track_album))
                        .setArtists(mTextViewTrack8Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_9_clickable:
                //For Track-9

                //Save Track-9 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack9Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_9_track_album))
                        .setArtists(mTextViewTrack9Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;
            case R.id.view_jukebox_detail_track_10_clickable:
                //For Track-10

                //Save Track-10 to be played
                mSongTrackToPlay = new SongTrack.Builder()
                        .setSongTitle(mTextViewTrack10Title.getText().toString())
                        .setAlbumTitle(getString(R.string.jukebox_song_10_track_album))
                        .setArtists(mTextViewTrack10Artist.getText().toString())
                        .createSongTrack();

                //Check Payment and then play the selected track
                playAfterPaymentCheck();
                break;

            default:
                //For others, delegate to super
                super.onClick(view);
        }
    }

    /**
     * Method that shows the Bottom Sheet Dialog {@link JukeboxDetailPaymentDialogFragment}
     * to capture the Payment required for listening to a Jukebox channel
     */
    private void showPaymentRequestDialog() {
        JukeboxDetailPaymentDialogFragment.showDialog(
                getSupportFragmentManager(),
                mJukeboxTitle,
                mJukeboxTypeResId
        );
    }

    /**
     * Method that starts playing the selected Song Track
     */
    private void playSelectedTrack() {
        playTrack(
                mSongTrackToPlay.getSongTitle(),
                mSongTrackToPlay.getAlbumTitle(),
                mSongTrackToPlay.getArtists()
        );
    }

    /**
     * Method that validates the Payment for the Jukebox first
     * and then proceeds to play the selected track if the Payment was already collected;
     * otherwise initiates a Payment Transaction through a Payment Bottom Sheet Dialog
     */
    private void playAfterPaymentCheck() {
        //Check Payment
        if (mPaymentCollected) {
            //If Payment is already Collected, then play the track
            playSelectedTrack();
        } else {
            //If Payment is not yet collected, then
            //show the Payment request Bottom Sheet Dialog
            showPaymentRequestDialog();
        }
    }

    /**
     * Callback Method of {@link JukeboxDetailPaymentDialogFragment.PaymentRequestListener}
     * invoked when the user clicks on the "Proceed To Pay" Button in the dialog
     * for making the Payment.
     * <p>
     * This method should initiate the Payment transaction via any Payment API
     * and record the capture.
     * </p>
     */
    @SuppressLint("ShowToast")
    @Override
    public void onProceedToPayClicked() {
        //Since this is just a Stub, we are not initiating any Payment Transaction.
        //We will just record it as completed with a Success message
        showToast(
                Toast.makeText(this,
                        R.string.jukebox_detail_payment_message_success,
                        Toast.LENGTH_LONG
                )
        );
        //Mark the Payment as successful
        mPaymentCollected = true;
        //Start playing the requested track
        playSelectedTrack();
    }

}