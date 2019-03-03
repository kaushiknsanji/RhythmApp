package com.example.kaushiknsanji.rhythm.ui.artist;


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
 * for the Drawer Menu 'Artists'. This inflates the layout 'R.layout.fragment_artist_list' to
 * display a list of Artists available.
 *
 * @author Kaushik N Sanji
 */
public class ArtistListFragment extends DrawerFragment implements View.OnClickListener {

    //Constant used for logs
    private static final String LOG_TAG = ArtistListFragment.class.getSimpleName();
    //Constant for use as Fragment's Tag
    public static final String NAV_FRAGMENT_TAG = LOG_TAG;

    //For caching the required view references
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mTextViewArtist1Name;
    private TextView mTextViewArtist2Name;
    private TextView mTextViewArtist3Name;
    private TextView mTextViewArtist4Name;
    private TextView mTextViewArtist5Name;
    private TextView mTextViewArtist6Name;

    /**
     * Mandatory Empty Constructor of {@link ArtistListFragment}.
     * This is required by the {@link android.support.v4.app.FragmentManager} to instantiate
     * the fragment (e.g. upon screen orientation changes).
     */
    public ArtistListFragment() {
    }

    /**
     * Static Factory constructor that creates an instance of {@link ArtistListFragment}
     *
     * @return Instance of {@link ArtistListFragment}
     */
    @NonNull
    public static ArtistListFragment newInstance() {
        return new ArtistListFragment();
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
     * @return Returns the View for the fragment's UI ('R.layout.fragment_artist_list')
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout 'R.layout.fragment_artist_list'
        //Passing false as we are attaching the layout ourselves
        View rootView = inflater.inflate(R.layout.fragment_artist_list, container, false);

        //Find the required Views
        mToolbar = rootView.findViewById(R.id.include_toolbar_home);
        mCollapsingToolbarLayout = rootView.findViewById(R.id.collapsing_toolbar_artist_list);
        mTextViewArtist1Name = rootView.findViewById(R.id.text_artist_1_name);
        mTextViewArtist2Name = rootView.findViewById(R.id.text_artist_2_name);
        mTextViewArtist3Name = rootView.findViewById(R.id.text_artist_3_name);
        mTextViewArtist4Name = rootView.findViewById(R.id.text_artist_4_name);
        mTextViewArtist5Name = rootView.findViewById(R.id.text_artist_5_name);
        mTextViewArtist6Name = rootView.findViewById(R.id.text_artist_6_name);

        //Initialize Click Listeners on Card Artist Items
        rootView.findViewById(R.id.card_artist_list_item_1).setOnClickListener(this);
        rootView.findViewById(R.id.card_artist_list_item_2).setOnClickListener(this);
        rootView.findViewById(R.id.card_artist_list_item_3).setOnClickListener(this);
        rootView.findViewById(R.id.card_artist_list_item_4).setOnClickListener(this);
        rootView.findViewById(R.id.card_artist_list_item_5).setOnClickListener(this);
        rootView.findViewById(R.id.card_artist_list_item_6).setOnClickListener(this);

        //Initialize Toolbar
        setupToolbar();

        //Set the Explanation Text for this screen
        setupExplanationText(
                rootView.findViewById(R.id.text_all_explanation),
                R.string.artist_list_explanation
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
        mCollapsingToolbarLayout.setTitle(getString(R.string.title_artists));

        //Set the Typeface for Title
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(getTitleTextTypeface());
        mCollapsingToolbarLayout.setExpandedTitleTypeface(getTitleTextTypeface());
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
            case R.id.card_artist_list_item_1:
                //Launch Artist-1
                ArtistDetailActivity.launchArtistDetail(
                        requireContext(),
                        mTextViewArtist1Name.getText().toString(),
                        R.drawable.ic_artist_1_beyonce
                );
                break;

            case R.id.card_artist_list_item_2:
                //Launch Artist-2
                ArtistDetailActivity.launchArtistDetail(
                        requireContext(),
                        mTextViewArtist2Name.getText().toString(),
                        R.drawable.ic_artist_2_elvis_presley
                );
                break;

            case R.id.card_artist_list_item_3:
                //Launch Artist-3
                ArtistDetailActivity.launchArtistDetail(
                        requireContext(),
                        mTextViewArtist3Name.getText().toString(),
                        R.drawable.ic_artist_3_jessie_j
                );
                break;

            case R.id.card_artist_list_item_4:
                //Launch Artist-4
                ArtistDetailActivity.launchArtistDetail(
                        requireContext(),
                        mTextViewArtist4Name.getText().toString(),
                        R.drawable.ic_artist_4_john_lennon
                );
                break;

            case R.id.card_artist_list_item_5:
                //Launch Artist-5
                ArtistDetailActivity.launchArtistDetail(
                        requireContext(),
                        mTextViewArtist5Name.getText().toString(),
                        R.drawable.ic_artist_5_rihanna
                );
                break;

            case R.id.card_artist_list_item_6:
                //Launch Artist-6
                ArtistDetailActivity.launchArtistDetail(
                        requireContext(),
                        mTextViewArtist6Name.getText().toString(),
                        R.drawable.ic_artist_6_selena_gomez
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
        //Inflating the menu 'R.menu.menu_fragment_artist_list' for the fragment
        inflater.inflate(R.menu.menu_fragment_artist_list, menu);
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
                                R.string.artist_list_message_search_menu,
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
