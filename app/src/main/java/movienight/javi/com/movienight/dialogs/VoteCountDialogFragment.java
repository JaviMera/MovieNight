package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.VoteCountSelectedListener;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

/**
 * Created by Javi on 10/24/2016.
 */

public class VoteCountDialogFragment extends DialogFragment {

    private Integer mVoteCount;
    private VoteCountSelectedListener mListener;

    public static VoteCountDialogFragment newInstance(Integer voteCount) {

        VoteCountDialogFragment dialog = new VoteCountDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("vote_count", voteCount);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (SearchActivity)getActivity();

        mVoteCount = getArguments().getInt("vote_count");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.vote_number_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        final EditText voteCountEditText = (EditText) view.findViewById(R.id.voteCountEditTextView);
        voteCountEditText.setText(String.valueOf(mVoteCount));
        final Button doneButton = (Button) view.findViewById(R.id.voteCountDoneButtonView);

        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Integer voteCount = Integer.parseInt(voteCountEditText.getText().toString());
                mListener.onVoteCountDone(voteCount);
                dismiss();
            }
        });

        return dialogBuilder.create();
    }
}
