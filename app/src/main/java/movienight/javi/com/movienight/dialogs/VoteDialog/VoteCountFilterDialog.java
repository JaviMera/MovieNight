package movienight.javi.com.movienight.dialogs.VoteDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.FilterableItemKeys;
import movienight.javi.com.movienight.model.VoteCountFilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 10/24/2016.
 */

public class VoteCountFilterDialog extends FilterDialogBase implements VoteDialogFragmentView{

    private Integer mVoteCount;
    private VoteDialogFragmentPresenter mPresenter;

    public static VoteCountFilterDialog newInstance(List<FilterableItem> items) {

        VoteCountFilterDialog dialog = new VoteCountFilterDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.SELECTED_VOTE_COUNT_KEY, (ArrayList)items);
        dialog.setArguments(bundle);

        return dialog;
    }

    @BindView(R.id.voteCountEditTextView) EditText mVoteEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVoteCount = 0;

        List<VoteCountFilterableItem> voteCountItems = getArguments().getParcelableArrayList(ActivityExtras.SELECTED_VOTE_COUNT_KEY);

        if(!voteCountItems.isEmpty()) {

            mVoteCount = voteCountItems.get(0).getValue()[0];
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.vote_number_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mPresenter = new VoteDialogFragmentPresenter(this);

        String countText = String.valueOf(mVoteCount);
        mPresenter.setVoteEditText(countText);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());

        return dialog;
    }

    @OnClick(R.id.voteCountEditTextView)
    public void onEditTextClick(View view) {

        mPresenter.setVoteEditText("");
    }

    @OnClick(R.id.voteCountDoneButtonView)
    public void onDialogButtonClick(View view) {

        String text = mVoteEditText.getText().toString();
        Integer voteCount = Integer.parseInt(text);

        mListener.onFilterItemCreated(
            FilterableItemKeys.VOTE_COUNT,
            new VoteCountFilterableItem(voteCount)
        );

        dismiss();
    }

    @Override
    public void setVoteEditText(String text) {

        mVoteEditText.setText(text);
    }
}
