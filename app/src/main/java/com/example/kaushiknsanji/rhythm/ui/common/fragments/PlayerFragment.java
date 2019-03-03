package com.example.kaushiknsanji.rhythm.ui.common.fragments;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.data.local.models.SongTrack;
import com.example.kaushiknsanji.rhythm.ui.common.services.PlayerService;
import com.example.kaushiknsanji.rhythm.utils.AppConstants;
import com.example.kaushiknsanji.rhythm.utils.IntentUtility;

/**
 * An Abstract {@link Fragment} class which acts as a client to the bound {@link PlayerService}.
 * <p>
 * This initializes all the views of the Bottom Sheet Player (R.layout.layout_all_player),
 * implements and provides their functionality, to the subclasses of this Fragment.
 * </p><p>
 * The View Hierarchy is to be inflated by the Fragment subclasses. Hence they must include
 * the layout (R.layout.layout_all_player).
 * </p>
 *
 * @author Kaushik N Sanji
 */
public abstract class PlayerFragment extends BaseFragment implements View.OnClickListener {

    //Bundle Key constants to save/restore the state
    private static final String BUNDLE_BOTTOM_SHEET_STATE_INT_KEY = "int.Player.BottomSheetState";
    private static final String BUNDLE_SHUFFLE_BOOL_KEY = "bool.Player.Shuffle";
    private static final String BUNDLE_FAVORITE_BOOL_KEY = "bool.Player.Favorite";
    private static final String BUNDLE_REPEAT_BOOL_KEY = "bool.Player.Repeat";

    //For the Bottom Sheet Player
    private BottomSheetBehavior<View> mPlayerBottomSheetBehavior;
    //For the Toolbar of the Bottom Sheet Player
    private Toolbar mToolbar;

    //Caching the required View references of the Collapsed Bottom Sheet Player
    private View mViewPlayerCollapsed;
    private SeekBar mSeekBarCollapsed;
    private TextView mTextViewSongArtists;
    private ImageButton mImageButtonPlayPauseCollapsed;

    //Caching the required View references of the Expanded Bottom Sheet Player
    private SeekBar mSeekBarFull;
    private TextView mTextViewToolbarSongTitle;
    private TextView mTextViewAlbumTitle;
    private TextView mTextViewArtists;
    private TextView mTextViewSongElapsedTime;
    private TextView mTextViewSongDuration;
    private ImageButton mImageButtonShuffle;
    private ImageButton mImageButtonFavorite;
    private ImageButton mImageButtonRepeat;
    private FloatingActionButton mFabPlayPause;

    //For the Animated Vector Drawables used
    private AnimatedVectorDrawableCompat mAvdPlay;
    private AnimatedVectorDrawableCompat mAvdPause;
    private AnimatedVectorDrawableCompat mAvdHeartEmpty;
    private AnimatedVectorDrawableCompat mAvdHeartFill;

    //For keeping track of Shuffle state
    private boolean mShuffleActivated = false;
    //For keeping track of Repeat state
    private boolean mRepeatActivated = false;
    //For keeping track of Favorite state
    private boolean mFavoriteSong = false;
    //For keeping track of the Play-Pause Action
    private boolean mPlayPauseAction = false;

    //Instance of the Bound Service that simulates the Player functions
    private PlayerService mPlayerService;
    //For keeping track of the Bound Service state
    private boolean mBound = false;

    //Stores the fixed Seek amount for Forward and Rewind operations
    private int mSeekForwardRewindDelta;
    /**
     * A Handler attached to the current Main Looper for updating the current Song Play progress
     * status to the UI and dispatching update requests, every second.
     */
    private Handler mProgressUpdateHandler = new Handler(new Handler.Callback() {
        /**
         * @param msg A {@link android.os.Message Message} object
         * @return True if no further handling is desired
         */
        @Override
        public boolean handleMessage(Message msg) {
            //Get the current position of Play
            final int currentPosition = mPlayerService.getCurrentPosition();
            //Get the duration of Song
            final int duration = mPlayerService.getDuration();
            //Update the Progress status to the UI
            updateProgress(currentPosition, duration);
            //Dispatch an update request after a second
            sendDelayedUpdateRequest();
            //No further handling required
            return true;
        }
    });
    /**
     * Defines callbacks for Binding Service to this client, passed to bindService()
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        /**
         * Called when a connection to the Service has been established, with
         * the {@link android.os.IBinder} of the communication channel to the
         * Service.
         *
         * @param name The concrete component name of the service that has
         * been connected.
         *
         * @param service The IBinder of the Service's communication channel,
         * which you can now make calls on.
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //We have bound to the PlayerService, cast IBinder and get the PlayerService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mPlayerService = binder.getService();
            //Marking as Bound
            mBound = true;
            //Restore the last Player state if any
            restoreLastPlayerState();
            //Trigger an Update request immediately to get the Song Play progress status
            sendUpdateRequest();
        }

        /**
         * Called when a connection to the Service has been lost.  This typically
         * happens when the process hosting the service has crashed or been killed.
         * This does <em>not</em> remove the ServiceConnection itself -- this
         * binding to the service will remain active, and you will receive a call
         * to {@link #onServiceConnected} when the Service is next running.
         *
         * @param name The concrete component name of the service whose
         * connection has been lost.
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //We have lost the connection to the PlayerService

            //Marking as Unbound
            mBound = false;
            //Remove any pending update requests previously dispatched
            removePendingUpdateRequests();
        }
    };

    /**
     * Method that updates the current Song Play progress to the UI
     *
     * @param currentPosition The Current Position of Play in seconds which is the elapsed time
     * @param duration        The Duration of the Song playing, in seconds
     */
    private void updateProgress(int currentPosition, int duration) {
        //Set the Song duration
        if (mTextViewSongDuration != null) {
            mTextViewSongDuration.setText(DateUtils.formatElapsedTime(duration));
        }

        //Set the Song elapsed Time
        if (mTextViewSongElapsedTime != null) {
            mTextViewSongElapsedTime.setText(DateUtils.formatElapsedTime(currentPosition));
        }

        //Set the Visual progress on the Seekbar of Collapsed Player
        if (mSeekBarCollapsed != null) {
            setProgressOnSeekBar(mSeekBarCollapsed, currentPosition);
        }

        //Set the Visual progress on the Seekbar of Expanded Player
        if (mSeekBarFull != null) {
            setProgressOnSeekBar(mSeekBarFull, currentPosition);
        }
    }

    /**
     * Method that sets the Visual progress of the Song being played, on the Seekbar.
     *
     * @param seekBar  The {@link SeekBar} instance
     * @param progress The Current Position of Play in seconds which is the elapsed time
     */
    private void setProgressOnSeekBar(SeekBar seekBar, int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Set with Animation for Android N+
            seekBar.setProgress(progress, true);
        } else {
            //Set without Animation for below Android N
            seekBar.setProgress(progress);
        }
    }

    /**
     * Method that dispatches an update request for updating the Song Play Progress to the UI
     * after a delay of a second.
     */
    private void sendDelayedUpdateRequest() {
        //Send an empty message to the Handler after a second for updating the progress status
        mProgressUpdateHandler.sendEmptyMessageDelayed(0, DateUtils.SECOND_IN_MILLIS);
    }

    /**
     * Method that dispatches an immediate update request for updating the Song Play Progress
     * to the UI
     */
    private void sendUpdateRequest() {
        mProgressUpdateHandler.sendEmptyMessage(0);
    }

    /**
     * Method that removes any pending updating requests previously dispatched
     */
    private void removePendingUpdateRequests() {
        mProgressUpdateHandler.removeMessages(0);
    }

    /**
     * Method invoked when the {@link PlayerService} is Bound to restore the state of
     * the Player after reconstruction
     */
    private void restoreLastPlayerState() {
        //Get Last Played Progress if any
        int lastPlayedProgress = getPlayerComposition().getLastPlayedProgress();
        if (lastPlayedProgress > 0) {
            //If Last Progress was saved
            if (!mPlayerService.isPlayerInitialized()) {
                //When not yet initialized, start the Play, else we cannot update the Progress
                play();
            }

            //Restore the state of Play Simulation
            if (getPlayerComposition().getLastPlayingState()) {
                //Start the Play if it was previously running
                play();
            } else {
                //Pause the Play if it was NOT running
                pause();
            }

            //Restore the Player position in the end
            mPlayerService.setCurrentPosition(lastPlayedProgress);
        }
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(android.content.Context)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>Any restored child fragments will be created before the base
     * <code>Fragment.onCreate</code> method returns.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Binding the client fragments to the service PlayerService
        Intent serviceIntent = new Intent(requireContext(), PlayerService.class);
        requireContext().bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        //Get the Seek Amount for Forward and Rewind operations
        mSeekForwardRewindDelta = requireContext().getResources().getInteger(R.integer.player_forward_rewind_delta);
        //Get the Animated Vector Drawables for Play/Pause/Favorite
        mAvdPlay = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.ic_all_play_white_animatable);
        mAvdPause = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.ic_all_pause_white_animatable);
        mAvdHeartEmpty = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.ic_all_heart_empty_animatable);
        mAvdHeartFill = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.ic_all_heart_fill_animatable);
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        //Unbind the PlayerService if still bound
        if (mBound) {
            requireContext().unbindService(mServiceConnection);
            //Marking as Unbound
            mBound = false;
        }
    }

    /**
     * Called immediately after {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Finding the Bottom Sheet View and Behavior
        View viewPlayerBottomSheet = view.findViewById(R.id.bottom_sheet_player);
        mPlayerBottomSheetBehavior = BottomSheetBehavior.from(viewPlayerBottomSheet);
        //Setting Bottom Sheet Elevation
        ViewCompat.setElevation(viewPlayerBottomSheet,
                getResources().getDimension(R.dimen.bottom_sheet_material_elevation));

        //Finding the Views Needed
        mToolbar = view.findViewById(R.id.toolbar_player);
        mViewPlayerCollapsed = view.findViewById(R.id.cl_player_collapsed);
        mSeekBarCollapsed = view.findViewById(R.id.seek_player_collapsed);
        mTextViewSongArtists = view.findViewById(R.id.text_player_collapsed_song_artist);
        mImageButtonPlayPauseCollapsed = view.findViewById(R.id.imgbtn_player_collapsed_play_pause);
        mSeekBarFull = view.findViewById(R.id.seek_player_full);
        mTextViewToolbarSongTitle = view.findViewById(R.id.text_player_song_title);
        mTextViewAlbumTitle = view.findViewById(R.id.text_player_full_album_title);
        mTextViewArtists = view.findViewById(R.id.text_player_full_song_artists);
        mTextViewSongElapsedTime = view.findViewById(R.id.text_player_full_song_elapsed_time);
        mTextViewSongDuration = view.findViewById(R.id.text_player_full_song_duration);
        mImageButtonShuffle = view.findViewById(R.id.imgbtn_player_full_shuffle);
        mImageButtonFavorite = view.findViewById(R.id.imgbtn_player_full_favorite);
        mImageButtonRepeat = view.findViewById(R.id.imgbtn_player_full_repeat);
        mFabPlayPause = view.findViewById(R.id.fab_player_full_play_pause);

        //Initializing the Views
        setupBottomSheetPlayer();
        setupToolbar();
        setupInteractiveSeekBar();

        //Setting the Explanation Text for this screen
        setupExplanationText(
                view.findViewById(R.id.include_player_full_explanation)
                        .findViewById(R.id.text_all_explanation),
                R.string.player_explanation
        );

        //Setting Click Listeners on the Views
        mViewPlayerCollapsed.setOnClickListener(this);
        mImageButtonPlayPauseCollapsed.setOnClickListener(this);
        view.findViewById(R.id.imgbtn_player_collapsed_skip_next).setOnClickListener(this);
        mImageButtonShuffle.setOnClickListener(this);
        mImageButtonFavorite.setOnClickListener(this);
        view.findViewById(R.id.imgbtn_player_full_share).setOnClickListener(this);
        mImageButtonRepeat.setOnClickListener(this);
        mFabPlayPause.setOnClickListener(this);
        view.findViewById(R.id.imgbtn_player_full_fast_forward).setOnClickListener(this);
        view.findViewById(R.id.imgbtn_player_full_skip_next).setOnClickListener(this);
        view.findViewById(R.id.imgbtn_player_full_fast_rewind).setOnClickListener(this);
        view.findViewById(R.id.imgbtn_player_full_skip_previous).setOnClickListener(this);
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

            //Restore the Bottom Sheet state
            mPlayerBottomSheetBehavior.setState(savedInstanceState.getInt(BUNDLE_BOTTOM_SHEET_STATE_INT_KEY));
            if (mPlayerBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                //When Player is Expanded

                //Make the Toolbar Visible if not yet visible,
                //and hide the Collapsed Player View
                if (!isToolbarVisible()) {
                    showToolbar(true);
                }
            }

            //Restore Shuffle state
            mShuffleActivated = savedInstanceState.getBoolean(BUNDLE_SHUFFLE_BOOL_KEY);
            mImageButtonShuffle.setActivated(mShuffleActivated);

            //Restore Favorite state
            mFavoriteSong = savedInstanceState.getBoolean(BUNDLE_FAVORITE_BOOL_KEY);
            //Animate to the Favorite state accordingly
            animateHeartFill(mFavoriteSong);

            //Restore Repeat state
            mRepeatActivated = savedInstanceState.getBoolean(BUNDLE_REPEAT_BOOL_KEY);
            mImageButtonRepeat.setActivated(mRepeatActivated);
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

        //Save the Bottom Sheet state
        outState.putInt(BUNDLE_BOTTOM_SHEET_STATE_INT_KEY, mPlayerBottomSheetBehavior.getState());
        //Save the Shuffle state
        outState.putBoolean(BUNDLE_SHUFFLE_BOOL_KEY, mShuffleActivated);
        //Save the Favorite state
        outState.putBoolean(BUNDLE_FAVORITE_BOOL_KEY, mFavoriteSong);
        //Save the Repeat state
        outState.putBoolean(BUNDLE_REPEAT_BOOL_KEY, mRepeatActivated);
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link AppCompatActivity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();

        //Load Last Played or Default Track data
        loadLastPlayedTrack(getPlayerComposition().getLastPlayedTrack());

        //Restore the state of Play Pause Button when PlayerService is bound
        if (mBound) {
            if (mPlayerService.isPlaying() && !mPlayPauseAction) {
                //When Player is already playing but shows Play Button
                animatePlayPause(true);
            } else if (!mPlayerService.isPlaying() && mPlayPauseAction) {
                //When Player has stopped playing but shows Pause Button
                animatePlayPause(false);
            }
        }

    }

    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to {@link AppCompatActivity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();

        if (mBound) {
            //When PlayerService is still bound

            //Save the Player Position
            getPlayerComposition().saveLastPlayedProgress(mPlayerService.getCurrentPosition());
            //Save the state of Play Simulation
            getPlayerComposition().saveLastPlayingState(mPlayerService.isPlaying());
        }

    }

    /**
     * Method that registers the BottomSheetBehavior with its
     * {@link android.support.design.widget.BottomSheetBehavior.BottomSheetCallback}
     */
    private void setupBottomSheetPlayer() {
        mPlayerBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            /**
             * Called when the bottom sheet changes its state.
             *
             * @param bottomSheet The bottom sheet view.
             * @param newState    The new state. This will be one of {@link BottomSheetBehavior#STATE_DRAGGING},
             *                    {@link BottomSheetBehavior#STATE_SETTLING}, {@link BottomSheetBehavior#STATE_EXPANDED},
             *                    {@link BottomSheetBehavior#STATE_COLLAPSED}, or {@link BottomSheetBehavior#STATE_HIDDEN}.
             */
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //no-op
            }

            /**
             * Called when the bottom sheet is being dragged.
             *
             * @param bottomSheet The bottom sheet view.
             * @param slideOffset The new offset of this bottom sheet within [-1,1] range. Offset
             *                    increases as this bottom sheet is moving upward. From 0 to 1 the sheet
             *                    is between collapsed and expanded states and from -1 to 0 it is
             *                    between hidden and collapsed states.
             */
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset > AppConstants.PLAYER_TOOLBAR_REVEAL_OFFSET) {
                    //When slideOffset is nearing to expanded state

                    //Show the Toolbar and hide the Collapsed Player View
                    showToolbar(true);
                } else {
                    //When slideOffset is away from expanded state

                    //Hide the Toolbar and Show the Collapsed Player View
                    showToolbar(false);
                }
            }
        });
    }

    /**
     * Method that collapses the Bottom Sheet Player
     */
    protected void collapsePlayer() {
        mPlayerBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    /**
     * Method that expands the Bottom Sheet Player
     */
    protected void expandPlayer() {
        mPlayerBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * Method that checks whether the Bottom Sheet Player is expanded or not
     *
     * @return <b>TRUE</b> if Bottom Sheet Player is expanded; <b>FALSE</b> otherwise.
     */
    protected boolean isPlayerExpanded() {
        return mPlayerBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED;
    }

    /**
     * Method that initializes the Toolbar of the Bottom Sheet Player with its Menu
     * and configures its Navigation Icon to collapse the Player on click
     */
    private void setupToolbar() {
        //Set Navigation Icon
        mToolbar.setNavigationIcon(R.drawable.ic_player_navigation);

        //Set Listener to allow user to collapse the Player on click of the Navigation Icon
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                //Collapse Bottom Sheet Player on click
                collapsePlayer();
            }
        });

        //Inflating the Menu 'R.menu.menu_player' for this Fragment
        mToolbar.inflateMenu(R.menu.menu_player);
        //Registering the Toolbar Menu Item Click Listener
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            /**
             * This method will be invoked when a menu item is clicked if the item itself did
             * not already handle the event.
             *
             * @param item {@link MenuItem} that was clicked
             * @return <code>true</code> if the event was handled, <code>false</code> otherwise.
             */
            @SuppressLint("ShowToast")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Taking action based on the Id of the Menu Item Clicked
                switch (item.getItemId()) {
                    case R.id.action_player_artist:
                        //For "Artist" menu

                        //Show a message
                        showToast(
                                Toast.makeText(
                                        requireContext(),
                                        R.string.player_message_artist_menu,
                                        Toast.LENGTH_LONG
                                )
                        );

                        //Returning True since the action has been handled
                        return true;
                    case R.id.action_player_album:
                        //For "Album" menu

                        //Show a message
                        showToast(
                                Toast.makeText(
                                        requireContext(),
                                        R.string.player_message_album_menu,
                                        Toast.LENGTH_LONG
                                )
                        );

                        //Returning True since the action has been handled
                        return true;
                    case R.id.action_player_song_info:
                        //For "Song Info" menu

                        //Show a message
                        showToast(
                                Toast.makeText(
                                        requireContext(),
                                        R.string.player_message_song_info_menu,
                                        Toast.LENGTH_LONG
                                )
                        );

                        //Returning True since the action has been handled
                        return true;
                    case R.id.action_player_eq:
                        //For "EQ Settings" menu

                        //Show a message
                        showToast(
                                Toast.makeText(
                                        requireContext(),
                                        R.string.player_message_eq_menu,
                                        Toast.LENGTH_LONG
                                )
                        );

                        //Returning True since the action has been handled
                        return true;
                    default:
                        //When the item could not be found

                        //Return False as the action is not handled in this case
                        return false;
                }
            }
        });

        //Set the Typeface for Toolbar Title
        mTextViewToolbarSongTitle.setTypeface(getTitleTextTypeface());
    }

    /**
     * Method that controls the Visibility of Toolbar and Collapsed Player View
     * based on {@code visible}
     *
     * @param visible <b>TRUE</b> to show the Toolbar and hide the Collapsed Player View;
     *                <b>FALSE</b> to hide the Toolbar and show the Collapsed Player View
     */
    private void showToolbar(boolean visible) {
        if (visible) {
            //When TRUE, show the Toolbar and hide the Collapsed Player View
            mToolbar.setVisibility(View.VISIBLE);
            mViewPlayerCollapsed.setVisibility(View.GONE);

            //Get the height of Status Bar
            int statusBarHeight = getStatusBarHeight();
            //Add Status Bar Height as Top Padding to Toolbar
            //when the Toolbar's current Padding is not the same
            if (statusBarHeight > 0 && mToolbar.getPaddingTop() != statusBarHeight) {
                mToolbar.setPadding(
                        mToolbar.getPaddingLeft(),
                        statusBarHeight,
                        mToolbar.getPaddingRight(),
                        mToolbar.getPaddingBottom()
                );
            }

        } else {
            //When FALSE, hide the Toolbar and show the Collapsed Player View
            mToolbar.setVisibility(View.INVISIBLE);
            mViewPlayerCollapsed.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method that checks whether the Toolbar is visible or not.
     *
     * @return <b>TRUE</b> if shown; <b>FALSE</b> otherwise
     */
    private boolean isToolbarVisible() {
        return mToolbar.getVisibility() == View.VISIBLE;
    }

    /**
     * Method that initializes the Seekbar of the Expanded Player with its Listener
     * to update the Progress/change the Seek point based on user interaction
     */
    private void setupInteractiveSeekBar() {
        mSeekBarFull.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Notification that the progress level has changed. Clients can use the fromUser parameter
             * to distinguish user-initiated changes from those that occurred programmatically.
             *
             * @param seekBar The SeekBar whose progress has changed
             * @param progress The current progress level. This will be in the range min..max where min
             *                 and max were set by {@link ProgressBar#setMin(int)} and
             *                 {@link ProgressBar#setMax(int)}, respectively. (The default values for
             *                 min is 0 and max is 100.)
             * @param fromUser True if the progress change was initiated by the user.
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //When user is making the change
                    //Update the song elapsed time to the UI
                    mTextViewSongElapsedTime.setText(DateUtils.formatElapsedTime(progress));
                    //And pass the new position to the PlayerService
                    mPlayerService.setCurrentPosition(progress);
                }
            }

            /**
             * Notification that the user has started a touch gesture. Clients may want to use this
             * to disable advancing the seekbar.
             * @param seekBar The SeekBar in which the touch gesture began
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Remove any update requests while the user is making changes
                removePendingUpdateRequests();
            }

            /**
             * Notification that the user has finished a touch gesture. Clients may want to use this
             * to re-enable advancing the seekbar.
             * @param seekBar The SeekBar in which the touch gesture began
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Send an update request immediately when the user is done making changes
                sendUpdateRequest();
            }
        });
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
            case R.id.cl_player_collapsed:
                //When Collapsed Player View is clicked, Expand the Bottom Sheet Player
                expandPlayer();
                break;

            case R.id.imgbtn_player_collapsed_play_pause:
            case R.id.fab_player_full_play_pause:
                //For Play-Pause Buttons
                if (mPlayerService.isPlaying()) {
                    //If Playing, then Pause the Player
                    pause();
                } else {
                    //If Paused, then start Playing
                    play();
                }
                break;

            case R.id.imgbtn_player_full_shuffle:
                //Toggle Shuffle when clicked
                shuffleSongs();
                break;

            case R.id.imgbtn_player_full_favorite:
                //Toggle Favorite when clicked
                modifyFavorite();
                break;

            case R.id.imgbtn_player_full_share:
                //Share the Song info when clicked
                shareSongInfo();
                break;

            case R.id.imgbtn_player_full_repeat:
                //Toggle Repeat when clicked
                repeatSongs();
                break;

            case R.id.imgbtn_player_collapsed_skip_next:
            case R.id.imgbtn_player_full_skip_next:
                //Skip to the Next Song in queue when clicked
                skipNext();
                break;

            case R.id.imgbtn_player_full_fast_forward:
                //Fast forward the song when clicked
                fastForward();
                break;

            case R.id.imgbtn_player_full_skip_previous:
                //Skip to the Previous Song in queue when clicked
                skipPrevious();
                break;

            case R.id.imgbtn_player_full_fast_rewind:
                //Fast rewind the song when clicked
                fastRewind();
                break;
        }
    }

    /**
     * Method that loads the last played / default track data into the view
     */
    private void loadLastPlayedTrack(SongTrack lastPlayedTrack) {
        loadTrack(
                lastPlayedTrack.getSongTitle(),
                lastPlayedTrack.getAlbumTitle(),
                lastPlayedTrack.getArtists()
        );
    }

    /**
     * Method that loads the given track data into the view
     *
     * @param songTitle  Title of the Song track
     * @param albumTitle Album Title of the Song
     * @param artists    Artists of the Song
     */
    private void loadTrack(String songTitle, String albumTitle, String artists) {
        //For Collapsed Player View: Start
        //Set the Song Title and Artists
        mTextViewSongArtists.setText(String.format("%s - %s", songTitle, artists));
        //Mark the text as selected to start the Marquee
        mTextViewSongArtists.setSelected(true);
        //For Collapsed Player View: End

        //For Expanded Player View: Start
        //Set the Song Title on the Toolbar
        mTextViewToolbarSongTitle.setText(songTitle);
        //Set the Album Title
        mTextViewAlbumTitle.setText(albumTitle);
        //Set the Artists
        mTextViewArtists.setText(artists);
        //For Expanded Player View: End
    }

    /**
     * Method that loads the given track data into the view and starts the Play simulation
     *
     * @param songTitle  Title of the Song track
     * @param albumTitle Album Title of the Song
     * @param artists    Artists of the Song
     */
    protected void playTrack(String songTitle, String albumTitle, String artists) {
        //Load the Track data
        loadTrack(songTitle, albumTitle, artists);
        //Restart Play simulation
        restartPlay();

        //Save the data of the track being played
        //(for displaying the same track across all Player Fragments/Activities)
        getPlayerComposition().saveLastPlayedTrack(
                new SongTrack.Builder()
                        .setSongTitle(songTitle)
                        .setAlbumTitle(albumTitle)
                        .setArtists(artists)
                        .createSongTrack()
        );
    }

    /**
     * Method to start the Play simulation
     */
    private void play() {
        //Delegate to PlayerService to start the Play
        mPlayerService.play();
        //Animate to Pause button
        animatePlayPause(true);
    }

    /**
     * Method to restart the Play simulation
     */
    private void restartPlay() {
        //Delegate to PlayerService to restart the Play
        mPlayerService.restart();
        //Animate to Pause button if previously paused
        if (!mPlayPauseAction) {
            animatePlayPause(true);
        }
    }

    /**
     * Method to Pause the Play simulation
     */
    private void pause() {
        //Delegate to PlayerService to Pause the Play
        mPlayerService.pause();
        //Animate to Play button
        animatePlayPause(false);
    }

    /**
     * Method that animates the Play-Pause button between Play-Pause drawables
     *
     * @param playPauseAction <b>TRUE</b> to animate to Pause; <b>FALSE</b> to animate to Play
     */
    private void animatePlayPause(boolean playPauseAction) {
        //Save the boolean
        mPlayPauseAction = playPauseAction;
        //Pick the animated drawable based on the boolean passed
        AnimatedVectorDrawableCompat animatedDrawable = mPlayPauseAction ? mAvdPlay : mAvdPause;
        //Set the ImageButton and FAB with the drawable
        mImageButtonPlayPauseCollapsed.setImageDrawable(animatedDrawable);
        mFabPlayPause.setImageDrawable(animatedDrawable);
        //Start animating
        animatedDrawable.start();
    }

    /**
     * Method that skips to the Next Song in queue
     */
    @SuppressLint("ShowToast")
    private void skipNext() {
        //Show the function info
        showToast(Toast.makeText(requireContext(), R.string.player_skip_next, Toast.LENGTH_LONG));
        //Restart the Play simulation
        restartPlay();
    }

    /**
     * Method that skips to the Previous Song in queue
     */
    @SuppressLint("ShowToast")
    private void skipPrevious() {
        //Show the function info
        showToast(Toast.makeText(requireContext(), R.string.player_skip_previous, Toast.LENGTH_LONG));
        //Restart the Play simulation
        restartPlay();
    }

    /**
     * Method that fast forwards the Song by the Forward amount stored in {@link #mSeekForwardRewindDelta}
     */
    @SuppressLint("ShowToast")
    private void fastForward() {
        //Show the function info
        showToast(Toast.makeText(requireContext(), getString(R.string.player_fast_forward, mSeekForwardRewindDelta), Toast.LENGTH_LONG));
        //Fast forward the Song Playing position by the amount stored
        mPlayerService.setCurrentPosition(mPlayerService.getCurrentPosition() + mSeekForwardRewindDelta);
        //Set the Song Elapsed time for the updated position
        mTextViewSongElapsedTime.setText(DateUtils.formatElapsedTime(mPlayerService.getCurrentPosition()));
    }

    /**
     * Method that fast rewinds the Song by the Rewind amount stored in {@link #mSeekForwardRewindDelta}
     */
    @SuppressLint("ShowToast")
    private void fastRewind() {
        //Show the function info
        showToast(Toast.makeText(requireContext(), getString(R.string.player_fast_rewind, mSeekForwardRewindDelta), Toast.LENGTH_LONG));
        //Fast rewind the Song Playing position by the amount stored
        mPlayerService.setCurrentPosition(mPlayerService.getCurrentPosition() - mSeekForwardRewindDelta);
        //Set the Song Elapsed time for the updated position
        mTextViewSongElapsedTime.setText(DateUtils.formatElapsedTime(mPlayerService.getCurrentPosition()));
    }

    /**
     * Method that toggles the Shuffle state of the songs in Play queue
     */
    @SuppressLint("ShowToast")
    private void shuffleSongs() {
        //Toggle the Shuffle state
        mShuffleActivated = !mShuffleActivated;
        //Show the updated Shuffle state
        if (mShuffleActivated) {
            showToast(Toast.makeText(requireContext(), R.string.player_shuffle_on, Toast.LENGTH_LONG));
        } else {
            showToast(Toast.makeText(requireContext(), R.string.player_shuffle_off, Toast.LENGTH_LONG));
        }
        //Update the Activated state accordingly
        mImageButtonShuffle.setActivated(mShuffleActivated);
    }

    /**
     * Method that toggles the Repeat state of the songs in Play queue
     */
    @SuppressLint("ShowToast")
    private void repeatSongs() {
        //Toggle the Repeat state
        mRepeatActivated = !mRepeatActivated;
        //Show the updated Repeat state
        if (mRepeatActivated) {
            showToast(Toast.makeText(requireContext(), R.string.player_repeat_on, Toast.LENGTH_LONG));
        } else {
            showToast(Toast.makeText(requireContext(), R.string.player_repeat_off, Toast.LENGTH_LONG));
        }
        //Update the Activated state accordingly
        mImageButtonRepeat.setActivated(mRepeatActivated);
    }

    /**
     * Method that launches a Text Sharing Intent to share the Song information
     */
    private void shareSongInfo() {
        //Start the Share Intent to share the Song Title and Artists information
        IntentUtility.shareText(requireActivity(), mTextViewSongArtists.getText().toString(), getString(R.string.player_share_song_chooser_title));
    }

    /**
     * Method that adds/removes the song from "My Favorites" playlist
     */
    @SuppressLint("ShowToast")
    private void modifyFavorite() {
        //Toggle the Favorite state
        mFavoriteSong = !mFavoriteSong;
        //Animate to the state accordingly
        animateHeartFill(mFavoriteSong);
        //Show a message for the action
        if (mFavoriteSong) {
            showToast(Toast.makeText(requireContext(), R.string.player_favorite_song_add, Toast.LENGTH_LONG));
        } else {
            showToast(Toast.makeText(requireContext(), R.string.player_favorite_song_remove, Toast.LENGTH_LONG));
        }
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
        //Set the ImageButton with the drawable
        mImageButtonFavorite.setImageDrawable(animatedDrawable);
        //Start animating
        animatedDrawable.start();
    }

    /**
     * Called when the Fragment's Activity has detected the user's press of the back
     * key.
     *
     * @return Boolean value that indicates if the back operation was handled or not.
     * <br/><b>TRUE</b> if handled by the Fragment; <b>FALSE</b> otherwise.
     */
    @Override
    public boolean onBackPressed() {
        if (isPlayerExpanded()) {
            //If the Bottom Sheet Player is expanded, collapse the player
            collapsePlayer();
            //Returning True since the back operation was handled
            return true;
        }
        //On all else, returning False
        return false;
    }

}