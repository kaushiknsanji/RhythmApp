package com.example.kaushiknsanji.rhythm.ui.home;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.ui.about.AboutActivity;
import com.example.kaushiknsanji.rhythm.ui.album.AlbumListFragment;
import com.example.kaushiknsanji.rhythm.ui.artist.ArtistListFragment;
import com.example.kaushiknsanji.rhythm.ui.common.activities.BaseActivity;
import com.example.kaushiknsanji.rhythm.ui.common.fragments.DrawerFragment;
import com.example.kaushiknsanji.rhythm.ui.jukebox.JukeboxListActivity;
import com.example.kaushiknsanji.rhythm.ui.queue.PlayQueueListFragment;
import com.example.kaushiknsanji.rhythm.ui.song.SongListFragment;

/**
 * The Main Activity of the App that inflates the layout 'R.layout.activity_home'
 * containing the DrawerLayout that displays a Navigation Drawer with the Main content.
 * <p>
 * Responsible for initializing the Navigation Drawer and handling the click actions
 * on the Drawer items.
 * </p>
 *
 * @author Kaushik N Sanji
 */
public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerFragment.DrawerSelectionListener {

    //Bundle constant to save/restore the Drawer item shown
    private static final String BUNDLE_SELECTED_DRAWER_ITEM_INT_KEY = "int.Drawer.SelectedItem";

    //For caching the required view references
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    //Instance of Drawer Item Fragment
    private DrawerFragment mSelectedDrawerFragment;

    //Stores the Resource Id of the Drawer Item shown
    private int mSelectedDrawerItemId;

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
        setContentView(R.layout.activity_home);

        //Find the Drawer
        mDrawerLayout = findViewById(R.id.drawer_home);

        //Initialize the NavigationView
        setupNavigationView();

        if (savedInstanceState == null) {
            //On Initial launch

            //Open "Home" Item by default
            openDrawerItem(R.id.nav_item_home);
        }
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Restoring the Drawer Item previously shown
        openDrawerItem(savedInstanceState.getInt(BUNDLE_SELECTED_DRAWER_ITEM_INT_KEY));
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

        //Saving the id of the Drawer item last shown
        outState.putInt(BUNDLE_SELECTED_DRAWER_ITEM_INT_KEY, mSelectedDrawerItemId);
    }

    /**
     * This is the fragment-orientated version of {@link #onResume()} that you
     * can override to perform operations in the Activity at the same point
     * where its fragments are resumed.  Be sure to always call through to
     * the super-class.
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        //Finding the Toolbar
        Toolbar toolbar = findViewById(R.id.include_toolbar_home);
        if (toolbar != null) {
            //Setting up the Drawer Toggle Listener when Toolbar is present
            setupDrawerToggle(toolbar);
        }
    }

    /**
     * Callback Method of {@link DrawerFragment} invoked after the Fragment's layout inflation
     * to register the {@code toolbar} of the Fragment with
     * the Drawer Activity's {@link android.support.v7.app.ActionBarDrawerToggle}
     *
     * @param toolbar The {@link Toolbar} of the {@link DrawerFragment}
     */
    @Override
    public void setupDrawerToggle(Toolbar toolbar) {
        //Removing any old Drawer Listener if present
        if (mDrawerToggle != null) {
            mDrawerLayout.removeDrawerListener(mDrawerToggle);
            mDrawerToggle = null;
        }

        //Initializing the ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Attaching DrawerLayout events to ActionBarDrawerToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //Sync the toggle state of the Drawer
        mDrawerToggle.syncState();
    }

    /**
     * Method that initializes the Drawer's NavigationView and its Listener
     */
    private void setupNavigationView() {
        //Set the Listener for Drawer Menu items
        mNavigationView = findViewById(R.id.nav_view_home);
        mNavigationView.setNavigationItemSelectedListener(this);

        //Change the Font of the Header Title
        TextView textViewNavHeaderTitle = mNavigationView.getHeaderView(0).findViewById(R.id.text_home_nav_header_title);
        textViewNavHeaderTitle.setTypeface(getTitleTextTypeface());
    }

    /**
     * Called by the system when the device configuration changes while your
     * activity is running.
     *
     * @param newConfig The new device configuration.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Passing any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            //If the Drawer is Open, close the Drawer
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else if (mSelectedDrawerFragment != null && !mSelectedDrawerFragment.onBackPressed()) {
            //If the Selected DrawerFragment is present and has not handled its Back Action

            //If the Selected DrawerFragment is not HomeFragment,
            //then Launch Home before exiting out finally
            if (!(mSelectedDrawerFragment instanceof HomeFragment)) {
                openDrawerItem(R.id.nav_item_home);
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Taking action based on the Id of the Menu Item clicked
        switch (item.getItemId()) {
            case android.R.id.home:
                //For Android "Home" menu

                //Delegate to the ActionBarDrawerToggle to handle
                return mDrawerToggle.onOptionsItemSelected(item);
            default:
                //On all else, delegate to super
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handling navigation view item clicks
        openDrawerItem(item.getItemId());
        //Closing the drawer after handling
        mDrawerLayout.closeDrawer(GravityCompat.START);
        //Returning true to display the selected item
        return true;
    }

    /**
     * Callback Method of {@link DrawerFragment} that injects
     * the instance of the selected DrawerFragment when the Fragment is started.
     *
     * @param drawerFragment Instance of the {@link DrawerFragment} started
     */
    @Override
    public void setSelectedDrawerFragment(DrawerFragment drawerFragment) {
        //Saving the selected DrawerFragment
        mSelectedDrawerFragment = drawerFragment;
    }

    /**
     * Callback Method of {@link DrawerFragment} invoked
     * to launch another DrawerFragment or Activity from the current selected DrawerFragment.
     *
     * @param drawerItemId Integer value of the Drawer Menu item resource.
     */
    @SuppressLint("ShowToast")
    @Override
    public void openDrawerItem(@IdRes int drawerItemId) {
        //Save the selected drawer item resource id
        mSelectedDrawerItemId = drawerItemId;

        if (mSelectedDrawerItemId == R.id.nav_item_home) {
            //For "Home" item

            //Launch the HomeFragment at the container view
            replaceFragment(HomeFragment.newInstance(), HomeFragment.NAV_FRAGMENT_TAG);

        } else if (mSelectedDrawerItemId == R.id.nav_item_album) {
            //For "Albums" item

            //Launch the AlbumListFragment at the container view
            replaceFragment(AlbumListFragment.newInstance(), AlbumListFragment.NAV_FRAGMENT_TAG);

        } else if (mSelectedDrawerItemId == R.id.nav_item_artist) {
            //For "Artists" item

            //Launch the ArtistListFragment at the container view
            replaceFragment(ArtistListFragment.newInstance(), ArtistListFragment.NAV_FRAGMENT_TAG);

        } else if (mSelectedDrawerItemId == R.id.nav_item_song) {
            //For "Songs" item

            //Launch the SongListFragment at the container view
            replaceFragment(SongListFragment.newInstance(), SongListFragment.NAV_FRAGMENT_TAG);

        } else if (mSelectedDrawerItemId == R.id.nav_item_playlist) {
            //For "Playlists" item

            //Launch the PlayQueueListFragment at the container view
            replaceFragment(PlayQueueListFragment.newInstance(), PlayQueueListFragment.NAV_FRAGMENT_TAG);

        } else if (mSelectedDrawerItemId == R.id.nav_item_jukebox) {
            //For "Jukebox" item

            //Start the JukeboxListActivity
            startActivity(JukeboxListActivity.class);

        } else if (mSelectedDrawerItemId == R.id.nav_item_app_settings) {
            //For "App Settings" item

            //Show a message
            showToast(
                    Toast.makeText(
                            this,
                            R.string.home_message_app_settings_menu,
                            Toast.LENGTH_LONG
                    )
            );

        } else if (mSelectedDrawerItemId == R.id.nav_item_eq_settings) {
            //For "EQ Settings" item

            //Show a message
            showToast(
                    Toast.makeText(
                            this,
                            R.string.home_message_eq_settings_menu,
                            Toast.LENGTH_LONG
                    )
            );

        } else if (mSelectedDrawerItemId == R.id.nav_item_about) {
            //For "About" item

            //Start the AboutActivity
            startActivity(AboutActivity.class);

        }

        //Mark the Drawer item as selected
        mNavigationView.setCheckedItem(drawerItemId);
    }

    /**
     * Method that replaces the Fragment shown at the container view id 'R.id.frame_home_drawer_item'
     * with the given {@code fragment} instance and its {@code tag}
     *
     * @param fragment The new fragment to place in the container.
     * @param tag      Optional tag name for the fragment, to later retrieve the
     *                 fragment with {@link FragmentManager#findFragmentByTag(String)
     *                 FragmentManager.findFragmentByTag(String)}.
     */
    private void replaceFragment(Fragment fragment, String tag) {
        //Delegating to super to replace the Fragment
        //at the container view id 'R.id.frame_home_drawer_item'
        replaceFragment(R.id.frame_home_drawer_item, fragment, tag);
    }

}