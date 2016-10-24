package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.RateSelectedListener;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

/**
 * Created by Javi on 10/24/2016.
 */

public class RateDialogFragment extends DialogFragment {

    private RateSelectedListener mListener;

    public static DialogFragment newInstance() {

        RateDialogFragment dialog = new RateDialogFragment();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (SearchActivity)getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        View view = LayoutInflater.from(context).inflate(R.layout.rate_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        final RatingBar rateBar = (RatingBar) view.findViewById(R.id.rateBarView);
        LayerDrawable lDrawable = (LayerDrawable)rateBar.getProgressDrawable();
        lDrawable.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        final Button button = (Button) view.findViewById(R.id.rateDoneButtonView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onRateDone(0.0);
                dismiss();
            }
        });

        return dialogBuilder.create();
    }
}
