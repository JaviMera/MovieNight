package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.VoteCountFilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

/**
 * Created by Javi on 10/24/2016.
 */

public class VoteCountDialogFragment extends DialogFragmentBase {

    private Integer mVoteCount;

    public static VoteCountDialogFragment newInstance(List<FilterableItem> items) {

        VoteCountDialogFragment dialog = new VoteCountDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.SELECTED_VOTE_COUNT_KEY, (ArrayList)items);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVoteCount = 0;

        List<VoteCountFilterableItem> voteCountItems = getArguments().getParcelableArrayList(ActivityExtras.SELECTED_VOTE_COUNT_KEY);

        if(!voteCountItems.isEmpty()) {

            mVoteCount = voteCountItems.get(0).getVoteCount();
        }
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
                mListener.onFilterItemCreated(4, new VoteCountFilterableItem(voteCount));
                dismiss();
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());

        return dialogBuilder.create();
    }
}
