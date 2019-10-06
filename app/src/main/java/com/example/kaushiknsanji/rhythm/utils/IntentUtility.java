package com.example.kaushiknsanji.rhythm.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ShareCompat;

/**
 * Utility class that deals with the Common Intents used in the App.
 *
 * @author Kaushik N Sanji
 */
public final class IntentUtility {

    //Text Data Type Constants
    private static final String TEXT_TYPE_PLAIN = "text/plain";

    /**
     * Private constructor to avoid instantiating {@link IntentUtility}
     */
    private IntentUtility() {
        //Suppressing with an error to enforce noninstantiability
        throw new AssertionError("No " + this.getClass().getCanonicalName() + " instances for you!");
    }

    /**
     * Method that opens a webpage for the URL passed
     *
     * @param context A {@link Context} of the Calling Activity/Fragment
     * @param webUrl  String containing the URL of the Web Page to be launched
     */
    public static void openLink(Context context, String webUrl) {
        //Parsing the URL
        Uri webPageUri = Uri.parse(webUrl);
        //Creating an ACTION_VIEW Intent with the URI
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPageUri);
        //Checking if there is an Activity that accepts the Intent
        if (webIntent.resolveActivity(context.getPackageManager()) != null) {
            //Launching the corresponding Activity and passing it the Intent
            context.startActivity(webIntent);
        }
    }

    /**
     * Method that opens up an app chooser for sharing the text content passed.
     *
     * @param launchingActivity The {@link FragmentActivity} instance initiating this.
     * @param textToShare       The text String to be shared
     * @param chooserTitle      The Title text to be shown for the chooser dialog
     */
    public static void shareText(FragmentActivity launchingActivity, String textToShare, String chooserTitle) {
        //Building and launching the share intent, to share the text content
        ShareCompat.IntentBuilder
                .from(launchingActivity)
                .setType(TEXT_TYPE_PLAIN)
                .setText(textToShare)
                .setChooserTitle(chooserTitle)
                .startChooser();
    }
}
