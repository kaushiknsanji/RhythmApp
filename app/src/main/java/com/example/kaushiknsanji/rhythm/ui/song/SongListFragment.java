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

package com.example.kaushiknsanji.rhythm.ui.song;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaushiknsanji.rhythm.R;
import com.example.kaushiknsanji.rhythm.ui.common.fragments.DrawerFragment;

/**
 * {@link com.example.kaushiknsanji.rhythm.ui.home.HomeActivity}'s Drawer Fragment shown
 * for the Drawer Menu 'Songs'. This inflates the layout 'R.layout.fragment_song_list' to
 * display a list of Songs available.
 *
 * @author Kaushik N Sanji
 */
public class SongListFragment extends DrawerFragment implements View.OnClickListener {

    //Constant used for logs
    private static final String LOG_TAG = SongListFragment.class.getSimpleName();
    //Constant for use as Fragment's Tag
    public static final String NAV_FRAGMENT_TAG = LOG_TAG;

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

    /**
     * Mandatory Empty Constructor of {@link SongListFragment}.
     * This is required by the {@link android.support.v4.app.FragmentManager} to instantiate
     * the fragment (e.g. upon screen orientation changes).
     */
    public SongListFragment() {
    }

    /**
     * Static Factory constructor that creates an instance of {@link SongListFragment}
     *
     * @return Instance of {@link SongListFragment}
     */
    @NonNull
    public static SongListFragment newInstance() {
        return new SongListFragment();
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Context)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This Fragment has its own Menu to show
        setHasOptionsMenu(true);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Returns the View for the fragment's UI ('R.layout.fragment_song_list')
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout 'R.layout.fragment_song_list'
        //Passing false as we are attaching the layout ourselves
        View rootView = inflater.inflate(R.layout.fragment_song_list, container, false);

        //Find the required Views
        mToolbar = rootView.findViewById(R.id.include_toolbar_home);
        mCollapsingToolbarLayout = rootView.findViewById(R.id.collapsing_toolbar_song_list);
        mTextViewTrack1Title = rootView.findViewById(R.id.text_song_list_track_1_title);
        mTextViewTrack2Title = rootView.findViewById(R.id.text_song_list_track_2_title);
        mTextViewTrack3Title = rootView.findViewById(R.id.text_song_list_track_3_title);
        mTextViewTrack4Title = rootView.findViewById(R.id.text_song_list_track_4_title);
        mTextViewTrack5Title = rootView.findViewById(R.id.text_song_list_track_5_title);
        mTextViewTrack6Title = rootView.findViewById(R.id.text_song_list_track_6_title);
        mTextViewTrack7Title = rootView.findViewById(R.id.text_song_list_track_7_title);
        mTextViewTrack8Title = rootView.findViewById(R.id.text_song_list_track_8_title);
        mTextViewTrack9Title = rootView.findViewById(R.id.text_song_list_track_9_title);
        mTextViewTrack10Title = rootView.findViewById(R.id.text_song_list_track_10_title);
        mTextViewTrack1Artist = rootView.findViewById(R.id.text_song_list_track_1_artists);
        mTextViewTrack2Artist = rootView.findViewById(R.id.text_song_list_track_2_artists);
        mTextViewTrack3Artist = rootView.findViewById(R.id.text_song_list_track_3_artists);
        mTextViewTrack4Artist = rootView.findViewById(R.id.text_song_list_track_4_artists);
        mTextViewTrack5Artist = rootView.findViewById(R.id.text_song_list_track_5_artists);
        mTextViewTrack6Artist = rootView.findViewById(R.id.text_song_list_track_6_artists);
        mTextViewTrack7Artist = rootView.findViewById(R.id.text_song_list_track_7_artists);
        mTextViewTrack8Artist = rootView.findViewById(R.id.text_song_list_track_8_artists);
        mTextViewTrack9Artist = rootView.findViewById(R.id.text_song_list_track_9_artists);
        mTextViewTrack10Artist = rootView.findViewById(R.id.text_song_list_track_10_artists);

        //Set Click listeners on required views
        rootView.findViewById(R.id.fab_song_list_play).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_1_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_2_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_3_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_4_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_5_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_6_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_7_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_8_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_9_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.view_song_list_track_10_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_1_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_2_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_3_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_4_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_5_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_6_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_7_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_8_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_9_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_10_playlist_queue).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_1_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_2_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_3_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_4_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_5_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_6_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_7_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_8_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_9_more).setOnClickListener(this);
        rootView.findViewById(R.id.imgbtn_song_list_track_10_more).setOnClickListener(this);

        //Initialize Toolbar
        setupToolbar();

        //Set the Explanation Text for this screen
        setupExplanationText(
                rootView.findViewById(R.id.text_all_explanation),
                R.string.song_list_explanation
        );

        //Returning the prepared layout
        return rootView;
    }

    /**
     * Method that initializes the Toolbar as ActionBar and sets the Collapsing Toolbar Title
     */
    private void setupToolbar() {
        //Set as ActionBar
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbar);

        //Set the Title
        mCollapsingToolbarLayout.setTitle(getString(R.string.title_songs));

        //Set the Typeface for Title
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(getTitleTextTypeface());
        mCollapsingToolbarLayout.setExpandedTitleTypeface(getTitleTextTypeface());
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
            case R.id.fab_song_list_play:
                //For the FAB 'Play' Button
                //Show a Message
                showToast(
                        Toast.makeText(requireContext(),
                                R.string.song_list_message_fab_play_button,
                                Toast.LENGTH_LONG)
                );
                //Load and Play the First Track
                playTrack(
                        mTextViewTrack1Title.getText().toString(),
                        getString(R.string.song_1_track_album),
                        mTextViewTrack1Artist.getText().toString()
                );
                break;

            case R.id.view_song_list_track_1_clickable:
                //Load and Play the Track-1
                playTrack(
                        mTextViewTrack1Title.getText().toString(),
                        getString(R.string.song_1_track_album),
                        mTextViewTrack1Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_2_clickable:
                //Load and Play the Track-2
                playTrack(
                        mTextViewTrack2Title.getText().toString(),
                        getString(R.string.song_2_track_album),
                        mTextViewTrack2Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_3_clickable:
                //Load and Play the Track-3
                playTrack(
                        mTextViewTrack3Title.getText().toString(),
                        getString(R.string.song_3_track_album),
                        mTextViewTrack3Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_4_clickable:
                //Load and Play the Track-4
                playTrack(
                        mTextViewTrack4Title.getText().toString(),
                        getString(R.string.song_4_track_album),
                        mTextViewTrack4Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_5_clickable:
                //Load and Play the Track-5
                playTrack(
                        mTextViewTrack5Title.getText().toString(),
                        getString(R.string.song_5_track_album),
                        mTextViewTrack5Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_6_clickable:
                //Load and Play the Track-6
                playTrack(
                        mTextViewTrack6Title.getText().toString(),
                        getString(R.string.song_6_track_album),
                        mTextViewTrack6Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_7_clickable:
                //Load and Play the Track-7
                playTrack(
                        mTextViewTrack7Title.getText().toString(),
                        getString(R.string.song_7_track_album),
                        mTextViewTrack7Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_8_clickable:
                //Load and Play the Track-8
                playTrack(
                        mTextViewTrack8Title.getText().toString(),
                        getString(R.string.song_8_track_album),
                        mTextViewTrack8Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_9_clickable:
                //Load and Play the Track-9
                playTrack(
                        mTextViewTrack9Title.getText().toString(),
                        getString(R.string.song_9_track_album),
                        mTextViewTrack9Artist.getText().toString()
                );
                break;
            case R.id.view_song_list_track_10_clickable:
                //Load and Play the Track-10
                playTrack(
                        mTextViewTrack10Title.getText().toString(),
                        getString(R.string.song_10_track_album),
                        mTextViewTrack10Artist.getText().toString()
                );
                break;

            case R.id.imgbtn_song_list_track_1_playlist_queue:
            case R.id.imgbtn_song_list_track_2_playlist_queue:
            case R.id.imgbtn_song_list_track_3_playlist_queue:
            case R.id.imgbtn_song_list_track_4_playlist_queue:
            case R.id.imgbtn_song_list_track_5_playlist_queue:
            case R.id.imgbtn_song_list_track_6_playlist_queue:
            case R.id.imgbtn_song_list_track_7_playlist_queue:
            case R.id.imgbtn_song_list_track_8_playlist_queue:
            case R.id.imgbtn_song_list_track_9_playlist_queue:
            case R.id.imgbtn_song_list_track_10_playlist_queue:
                //For the "Add to Queue" ImageButtons
                showToast(
                        Toast.makeText(requireContext(),
                                R.string.song_list_track_item_message_playlist_queue_button,
                                Toast.LENGTH_LONG)
                );
                break;

            case R.id.imgbtn_song_list_track_1_more:
            case R.id.imgbtn_song_list_track_2_more:
            case R.id.imgbtn_song_list_track_3_more:
            case R.id.imgbtn_song_list_track_4_more:
            case R.id.imgbtn_song_list_track_5_more:
            case R.id.imgbtn_song_list_track_6_more:
            case R.id.imgbtn_song_list_track_7_more:
            case R.id.imgbtn_song_list_track_8_more:
            case R.id.imgbtn_song_list_track_9_more:
            case R.id.imgbtn_song_list_track_10_more:
                //For the "More" Action ImageButtons
                showToast(
                        Toast.makeText(requireContext(),
                                R.string.song_list_track_item_message_more_action_button,
                                Toast.LENGTH_LONG)
                );
                break;

            default:
                //For others, delegate to super
                super.onClick(view);
        }
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater {@link MenuInflater} instance to inflate the Menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Inflating the menu 'R.menu.menu_fragment_song_list' for the fragment
        inflater.inflate(R.menu.menu_fragment_song_list, menu);
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
     * perform the default menu handling.
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
            case R.id.action_search:
                //For "Search" menu

                //Show a message
                showToast(
                        Toast.makeText(
                                requireContext(),
                                R.string.song_list_message_search_menu,
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