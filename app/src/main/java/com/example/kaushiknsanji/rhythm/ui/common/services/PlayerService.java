package com.example.kaushiknsanji.rhythm.ui.common.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.math.MathUtils;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.kaushiknsanji.rhythm.R;

/**
 * Bound {@link Service} class for simulating the Play/Pause/Restart actions of a Music Player.
 *
 * @author Kaushik N Sanji
 */
public class PlayerService extends Service {

    //Constant used for logs
    private static final String LOG_TAG = PlayerService.class.getSimpleName();
    //Binder given to the clients to bind to this Service
    private final LocalBinder mBinder = new LocalBinder();
    //Duration of the Play
    private int mDuration;
    //Thread to update the current position of Play for every second
    private Worker mWorker;

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //Reading the fixed duration value from resources
        mDuration = getResources().getInteger(R.integer.all_song_duration);
    }

    /**
     * Return the communication channel to the service.
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Called when all clients have disconnected from a particular interface
     * published by the service.  The default implementation does nothing and
     * returns false.
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return true if you would like to have the service's
     * {@link #onRebind} method later called when new clients bind to it.
     */
    @Override
    public boolean onUnbind(Intent intent) {
        //Interrupting the Thread Worker to stop during Unbind
        if (mWorker != null) {
            mWorker.interrupt();
        }
        //Not interested in Rebind after Unbind
        return super.onUnbind(intent);
    }

    /**
     * Method that starts/resumes the Play simulation
     * in the thread {@link Worker}
     */
    public void play() {
        if (!isPlayerInitialized()) {
            //When the thread Worker is not yet started,
            //initialize and start the thread Worker to begin the Play simulation
            mWorker = new Worker(mDuration);
            mWorker.start();
        } else {
            //Resume the play simulation when the thread Worker is registered previously
            mWorker.doResume();
        }
    }

    /**
     * Method that checks whether the Play simulation is paused or playing.
     *
     * @return <b>TRUE</b> if the thread {@link Worker} is registered and Play is simulating; <b>FALSE</b> otherwise.
     */
    public boolean isPlaying() {
        return mWorker != null && mWorker.isPlaying();
    }

    /**
     * Method that checks whether the thread {@link Worker} is initialized or not
     *
     * @return <b>TRUE</b> if the thread {@link Worker} is initialized; <b>FALSE</b> otherwise.
     */
    public boolean isPlayerInitialized() {
        return mWorker != null;
    }

    /**
     * Method that Pauses the Play simulation in the thread {@link Worker}
     */
    public void pause() {
        if (isPlaying()) {
            //Pause the Play when the Play is simulating
            mWorker.doPause();
        }
    }

    /**
     * Method that Restarts the Play simulation from 0 position, in the thread {@link Worker}
     */
    public void restart() {
        if (isPlayerInitialized()) {
            //When the thread Worker is registered, restart the Play simulation
            mWorker.doRestart();
        } else {
            //When the thread Worker is not yet started, start the Play simulation
            play();
        }
    }

    /**
     * Method that returns the current duration of Play
     *
     * @return Current Duration of Play in seconds
     */
    public int getDuration() {
        return mDuration;
    }

    /**
     * Method that returns the current position of Play
     *
     * @return Current Position of Play in seconds
     */
    public int getCurrentPosition() {
        if (isPlayerInitialized()) {
            //Returning the current position when the thread Worker is registered
            return mWorker.getPosition();
        }
        //Returning 0 when the thread Worker is not yet started/registered
        return 0;
    }

    /**
     * Method that updates the current position of Play to the given {@code position}
     *
     * @param position New position of Play in seconds
     */
    public void setCurrentPosition(int position) {
        if (isPlayerInitialized()) {
            //Updating the Play position to the given value when the thread Worker is registered
            mWorker.setPosition(position);
        }
    }

    /**
     * Custom {@link Thread} Class for simulating the Play/Pause/Restart
     * for the given length of the song, i.e., duration.
     */
    private static class Worker extends Thread {

        //The Fixed Duration of Play
        private final int mDuration;
        //Flag to keep track of Pause
        private boolean mPaused = false;
        //Keeps track of the current position of Play
        private int mPosition;

        /**
         * Private Constructor of {@link Worker}
         *
         * @param duration The fixed duration of all the songs for simulation
         */
        private Worker(int duration) {
            mDuration = duration;
        }

        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of <code>Thread</code> should override this method.
         */
        @Override
        public void run() {
            try {

                //noinspection InfiniteLoopStatement
                while (true) {

                    if (mPosition >= mDuration && !mPaused) {
                        //Restart the Play simulation when the end is reached
                        mPosition = 0;
                    }

                    //Sleep for a second..
                    sleep(DateUtils.SECOND_IN_MILLIS);
                    //And then increment the position if NOT Paused
                    if (!mPaused) {
                        mPosition++;
                    }
                }

            } catch (InterruptedException e) {
                //When Interrupted during unbind, just log the info
                Log.i(LOG_TAG, "Worker run(): Player unbounded");
            }
        }

        /**
         * Getter for the state of Play simulation in progress.
         *
         * @return <b>TRUE</b> when NOT Paused; <b>FALSE</b> otherwise, which means it is Playing.
         */
        boolean isPlaying() {
            return !mPaused;
        }

        /**
         * Getter for the current position of Play
         *
         * @return Current Position of Play in seconds
         */
        int getPosition() {
            return mPosition;
        }

        /**
         * Method that updates the current position of Play to the given {@code position}
         *
         * @param position New position of Play in seconds
         */
        void setPosition(int position) {
            //Saving current Paused state
            boolean currentPausedState = mPaused;
            //Ensure it is Paused before updating position
            doPause();
            //Updating the position after clamping it within the range
            mPosition = MathUtils.clamp(position, 0, mDuration);
            //Restoring the Paused state
            mPaused = currentPausedState;
        }

        /**
         * Method that updates the 'Pause' flag to FALSE to Resume the Play simulation
         */
        void doResume() {
            mPaused = false;
        }

        /**
         * Method that updates the 'Pause' flag to TRUE to Pause the Play simulation
         */
        void doPause() {
            mPaused = true;
        }

        /**
         * Method that restarts the Play simulation to begin from 0 position. 'Pause' flag
         * if previously applied will be removed to begin the Play immediately.
         */
        void doRestart() {
            //Ensure it is Paused before updating position
            doPause();
            //Restarting the Play from 0 position
            mPosition = 0;
            //Resume after updating position
            doResume();
        }
    }

    /**
     * Class to bind the clients to this Service. We do not deal with IPC since
     * we know that this service always runs in the same process as its clients.
     */
    public class LocalBinder extends Binder {
        /**
         * Getter Method for the {@link PlayerService} which exposes
         * methods for use by the clients
         *
         * @return Instance of {@link PlayerService}
         */
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

}