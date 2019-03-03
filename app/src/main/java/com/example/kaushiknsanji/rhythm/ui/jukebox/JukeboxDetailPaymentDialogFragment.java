package com.example.kaushiknsanji.rhythm.ui.jukebox;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaushiknsanji.rhythm.R;

/**
 * A {@link BottomSheetDialogFragment} that inflates the layout 'R.layout.dialog_jukebox_detail_payment'
 * and displayed as a {@link BottomSheetDialog} whenever a user starts to listen to any Song in the
 * Jukebox channel that the user has not yet Subscribed to.
 * <p>
 * Responsible for initiating the Payment transaction
 * and delivering the Payment Capture response to the {@link JukeboxDetailActivity}.
 * </p>
 *
 * @author Kaushik N Sanji
 */
public class JukeboxDetailPaymentDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    //Constant used for logs
    private static final String LOG_TAG = JukeboxDetailPaymentDialogFragment.class.getSimpleName();
    //Constant used as a Fragment Tag identifier
    private static final String DIALOG_FRAGMENT_TAG = LOG_TAG;

    //Bundle Argument Constant for the Jukebox Title
    private static final String ARGUMENT_JUKEBOX_TITLE_STR_KEY = "argument.String.JUKEBOX_TITLE";
    //Bundle Argument Constant for the Jukebox Image
    private static final String ARGUMENT_JUKEBOX_IMAGE_INT_KEY = "argument.int.JUKEBOX_IMAGE";

    //Listener Interface to deliver payment related action events to the attached Activity
    private PaymentRequestListener mPaymentRequestListener;

    /**
     * Static Factory Constructor of {@link JukeboxDetailPaymentDialogFragment}
     *
     * @param jukeboxTitle Title of the Jukebox
     * @param jukeboxImage Drawable Resource Id of the Image for the Jukebox
     * @return Instance of {@link JukeboxDetailPaymentDialogFragment}
     */
    public static JukeboxDetailPaymentDialogFragment newInstance(String jukeboxTitle,
                                                                 @DrawableRes int jukeboxImage) {
        //Initializing this DialogFragment
        JukeboxDetailPaymentDialogFragment dialogFragment = new JukeboxDetailPaymentDialogFragment();

        //Storing the Arguments in a Bundle: START
        final Bundle bundleArgs = new Bundle(2);
        bundleArgs.putString(ARGUMENT_JUKEBOX_TITLE_STR_KEY, jukeboxTitle);
        bundleArgs.putInt(ARGUMENT_JUKEBOX_IMAGE_INT_KEY, jukeboxImage);
        //Storing the Arguments in a Bundle: END

        //Passing Arguments to the Fragment instance
        dialogFragment.setArguments(bundleArgs);

        //Returning the DialogFragment Instance
        return dialogFragment;
    }

    /**
     * Method that displays this {@link JukeboxDetailPaymentDialogFragment}
     *
     * @param fragmentManager An instance of the {@link FragmentManager} to use
     *                        for managing the Fragments shown
     * @param jukeboxTitle    Title of the Jukebox
     * @param jukeboxImage    Drawable Resource Id of the Image for the Jukebox
     */
    public static void showDialog(FragmentManager fragmentManager,
                                  String jukeboxTitle,
                                  @DrawableRes int jukeboxImage) {
        //Finding the DialogFragment to see if it is already instantiated and shown
        JukeboxDetailPaymentDialogFragment dialogFragment
                = (JukeboxDetailPaymentDialogFragment) fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG);

        if (dialogFragment == null) {
            //Instantiating and displaying the DialogFragment when it is not currently shown
            dialogFragment = JukeboxDetailPaymentDialogFragment.newInstance(jukeboxTitle, jukeboxImage);
            dialogFragment.show(fragmentManager, DIALOG_FRAGMENT_TAG);
        }
    }

    /**
     * Called when a fragment is first attached to its context.
     *
     * @param context Context of the Fragment
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //Casting context to PaymentRequestListener
            mPaymentRequestListener = (PaymentRequestListener) getActivity();
        } catch (ClassCastException e) {
            //Throw the exception when the Context does not implement PaymentRequestListener
            throw new ClassCastException(getParentFragment() + " must implement PaymentRequestListener");
        }
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
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI ('R.layout.dialog_jukebox_detail_payment')
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout 'R.layout.dialog_jukebox_detail_payment'
        //Passing false as we are attaching the layout ourselves
        View rootView = inflater.inflate(R.layout.dialog_jukebox_detail_payment, container, false);

        //Find the required Views
        TextView textViewExplanation = rootView.findViewById(R.id.text_all_explanation);
        TextView textViewJukeboxTypeTitle = rootView.findViewById(R.id.text_jukebox_detail_payment_type_title);
        ImageView imageViewJukeboxType = rootView.findViewById(R.id.image_jukebox_detail_payment_type);

        //Set the Jukebox Data received from the Arguments
        Bundle arguments = getArguments();
        if (arguments != null) {
            textViewJukeboxTypeTitle.setText(arguments.getString(ARGUMENT_JUKEBOX_TITLE_STR_KEY));
            imageViewJukeboxType.setImageResource(arguments.getInt(ARGUMENT_JUKEBOX_IMAGE_INT_KEY, R.drawable.ic_all_jukebox));
        }

        //Set the Explanation Text for this dialog
        textViewExplanation.setText(R.string.jukebox_detail_payment_explanation);
        //Set the Typeface for Explanation Text
        textViewExplanation.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.noticia_text));

        //Set Click listeners on required views
        rootView.findViewById(R.id.button_jukebox_detail_payment_pay).setOnClickListener(this);

        //Returning the prepared layout
        return rootView;
    }

    /**
     * Method invoked by the system to create the Dialog to be shown.
     * The layout 'R.layout.dialog_jukebox_detail_payment'
     * will be inflated and returned as {@link BottomSheetDialog}.
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new {@link BottomSheetDialog} instance to be displayed by the Fragment.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link android.support.v7.app.AppCompatActivity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            //Changing Bottom Sheet State to Expanded instead of Collapsed when Dialog is shown: START
            //Find the Bottom Sheet from the Dialog
            View viewBottomSheet = getDialog().findViewById(android.support.design.R.id.design_bottom_sheet);
            //Get the BottomSheetBehavior from the Bottom Sheet View
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(viewBottomSheet);
            //Set to Expanded State when set to Collapsed
            if (behavior != null && behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            //Changing Bottom Sheet State to Expanded instead of Collapsed when Dialog is shown: END
        }

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
            case R.id.button_jukebox_detail_payment_pay:
                //When "Proceed to Pay" button is clicked

                //Dispatch the event to the listener
                mPaymentRequestListener.onProceedToPayClicked();
                //Dismiss the dialog
                dismiss();
                break;
        }
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        //Clearing the reference to the listener to avoid leaks
        mPaymentRequestListener = null;
    }

    /**
     * Activity that creates an instance of {@link JukeboxDetailPaymentDialogFragment}
     * needs to implement the listener interface to receive payment related event callbacks.
     */
    interface PaymentRequestListener {
        /**
         * Callback Method of {@link PaymentRequestListener}
         * invoked when the user clicks on the "Proceed To Pay" Button in the dialog
         * for making the Payment.
         * <p>
         * This method should initiate the Payment transaction via any Payment API
         * and record the capture.
         * </p>
         */
        void onProceedToPayClicked();
    }

}