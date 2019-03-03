package com.example.kaushiknsanji.rhythm.ui.jukebox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
 * A {@link PlayerActivity} that inflates the layout 'R.layout.activity_jukebox_list'
 * to display a list of Jukebox Channels available for listening.
 *
 * @author Kaushik N Sanji
 */
public class JukeboxListActivity extends PlayerActivity
        implements View.OnClickListener {

    //For caching the required view references
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mTextViewJukebox1Title;
    private TextView mTextViewJukebox2Title;
    private TextView mTextViewJukebox3Title;
    private TextView mTextViewJukebox4Title;
    private TextView mTextViewJukebox5Title;
    private TextView mTextViewJukebox6Title;

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
        setContentView(R.layout.activity_jukebox_list);

        //Find the required Views
        mToolbar = findViewById(R.id.include_toolbar_jukebox_list);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_jukebox_list);
        mTextViewJukebox1Title = findViewById(R.id.text_jukebox_1_title);
        mTextViewJukebox2Title = findViewById(R.id.text_jukebox_2_title);
        mTextViewJukebox3Title = findViewById(R.id.text_jukebox_3_title);
        mTextViewJukebox4Title = findViewById(R.id.text_jukebox_4_title);
        mTextViewJukebox5Title = findViewById(R.id.text_jukebox_5_title);
        mTextViewJukebox6Title = findViewById(R.id.text_jukebox_6_title);

        //Set Click listeners on required views
        findViewById(R.id.card_jukebox_list_item_1).setOnClickListener(this);
        findViewById(R.id.card_jukebox_list_item_2).setOnClickListener(this);
        findViewById(R.id.card_jukebox_list_item_3).setOnClickListener(this);
        findViewById(R.id.card_jukebox_list_item_4).setOnClickListener(this);
        findViewById(R.id.card_jukebox_list_item_5).setOnClickListener(this);
        findViewById(R.id.card_jukebox_list_item_6).setOnClickListener(this);

        //Initialize Toolbar
        setupToolbar();

        //Set the Explanation Text for this screen
        setupExplanationText(
                findViewById(R.id.text_all_explanation),
                R.string.jukebox_list_explanation
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

        //Set the Toolbar Title
        mCollapsingToolbarLayout.setTitle(getString(R.string.title_jukebox));

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
    @Override
    public void onClick(View view) {
        //Taking action based on the Id of the View clicked
        switch (view.getId()) {
            case R.id.card_jukebox_list_item_1:
                //Launch Jukebox-1
                JukeboxDetailActivity.launchJukeboxDetail(
                        this,
                        mTextViewJukebox1Title.getText().toString(),
                        R.drawable.ic_jukebox_1_popular_hits
                );
                break;
            case R.id.card_jukebox_list_item_2:
                //Launch Jukebox-2
                JukeboxDetailActivity.launchJukeboxDetail(
                        this,
                        mTextViewJukebox2Title.getText().toString(),
                        R.drawable.ic_jukebox_2_country
                );
                break;
            case R.id.card_jukebox_list_item_3:
                //Launch Jukebox-3
                JukeboxDetailActivity.launchJukeboxDetail(
                        this,
                        mTextViewJukebox3Title.getText().toString(),
                        R.drawable.ic_jukebox_3_metal
                );
                break;
            case R.id.card_jukebox_list_item_4:
                //Launch Jukebox-4
                JukeboxDetailActivity.launchJukeboxDetail(
                        this,
                        mTextViewJukebox4Title.getText().toString(),
                        R.drawable.ic_jukebox_4_rock
                );
                break;
            case R.id.card_jukebox_list_item_5:
                //Launch Jukebox-5
                JukeboxDetailActivity.launchJukeboxDetail(
                        this,
                        mTextViewJukebox5Title.getText().toString(),
                        R.drawable.ic_jukebox_5_classic
                );
                break;
            case R.id.card_jukebox_list_item_6:
                //Launch Jukebox-6
                JukeboxDetailActivity.launchJukeboxDetail(
                        this,
                        mTextViewJukebox6Title.getText().toString(),
                        R.drawable.ic_jukebox_6_folk
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
        //Inflating the menu 'R.menu.menu_activity_jukebox_list' for the activity
        getMenuInflater().inflate(R.menu.menu_activity_jukebox_list, menu);
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
            case R.id.action_search:
                //For "Search" menu

                //Show a message
                showToast(
                        Toast.makeText(
                                this,
                                R.string.jukebox_list_message_search_menu,
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