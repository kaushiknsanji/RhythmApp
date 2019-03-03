package com.example.kaushiknsanji.rhythm.data.local;

import android.content.res.Resources;
import android.os.Bundle;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.data.local.models.SongTrack;

/**
 * Class that saves and persists the Player information for the Application Lifecycle.
 *
 * @author Kaushik N Sanji
 */
public class PlayerComposition {

    //Default value of the Last Played Progress of a Song
    private static final int DEFAULT_LAST_PLAYED_PROGRESS = 0;
    //Default Value of the Last Playing State of the Player
    private static final boolean DEFAULT_LAST_PLAYING_STATE = false;
    //Resources to read the Keys and Default Track information
    private final Resources mResources;
    //Bundle to save the State of the Player
    private Bundle mBundle;

    /**
     * Constructor of {@link PlayerComposition}
     *
     * @param resources Instance of {@link Resources} to read the Keys
     *                  and Default Track information
     */
    public PlayerComposition(Resources resources) {
        mResources = resources;
        //Initializing Bundle with required capacity
        mBundle = new Bundle(3);
    }

    /**
     * Method that saves and persists the Last Played Track information.
     *
     * @param songTrack The {@link SongTrack} instance of the Track
     *                  information that needs to be saved
     */
    public void saveLastPlayedTrack(SongTrack songTrack) {
        if (songTrack != null) {
            //Save the Track information if present
            mBundle.putParcelable(
                    mResources.getString(R.string.bundle_last_played_track_str_key),
                    songTrack
            );
        }
    }

    /**
     * Method that retrieves the Last Played Track information or
     * the Default Track information if Last Played Track data is not present.
     *
     * @return The {@link SongTrack} instance of the Last Played Track if present;
     * else the Default Track.
     */
    public SongTrack getLastPlayedTrack() {
        String keyString = mResources.getString(R.string.bundle_last_played_track_str_key);
        if (mBundle.getParcelable(keyString) != null) {
            //Return the saved Last Played Track information if present
            return mBundle.getParcelable(keyString);
        } else {
            //Return the Default Track information when Last Played Track info is unavailable
            return getDefaultTrack();
        }
    }

    /**
     * Method that retrieves the Default Track information.
     *
     * @return The {@link SongTrack} instance of the Default Track information
     */
    private SongTrack getDefaultTrack() {
        return new SongTrack.Builder()
                .setSongTitle(mResources.getString(R.string.default_track_title))
                .setAlbumTitle(mResources.getString(R.string.default_track_album))
                .setArtists(mResources.getString(R.string.default_track_artists))
                .createSongTrack();
    }

    /**
     * Method that saves and persists the Elapsed time of Song last played.
     *
     * @param progress Integer value of the Song Elapsed time in seconds
     */
    public void saveLastPlayedProgress(int progress) {
        mBundle.putInt(
                mResources.getString(R.string.bundle_last_played_progress_int_key),
                progress
        );
    }

    /**
     * Method that retrieves the Elapsed time of Song last played if any.
     *
     * @return The Elapsed time of Song last played if any in seconds;
     * else {@link #DEFAULT_LAST_PLAYED_PROGRESS}
     */
    public int getLastPlayedProgress() {
        return mBundle.getInt(
                mResources.getString(R.string.bundle_last_played_progress_int_key),
                DEFAULT_LAST_PLAYED_PROGRESS
        );
    }

    /**
     * Method that saves and persists the state of Player, as to whether it was Paused or Playing.
     *
     * @param wasPlaying Boolean value indicating the state of the Player.
     *                   <b>TRUE</b> for Playing; <b>FALSE</b> for Paused.
     */
    public void saveLastPlayingState(boolean wasPlaying) {
        mBundle.putBoolean(
                mResources.getString(R.string.bundle_last_playing_state_bool_key),
                wasPlaying
        );
    }

    /**
     * Method that retrieves the last state of Player if any.
     *
     * @return Boolean value indicating the last state of the Player if any; else {@link #DEFAULT_LAST_PLAYING_STATE}.
     * <b>TRUE</b> for Playing; <b>FALSE</b> for Paused.
     */
    public boolean getLastPlayingState() {
        return mBundle.getBoolean(
                mResources.getString(R.string.bundle_last_playing_state_bool_key),
                DEFAULT_LAST_PLAYING_STATE
        );
    }
}