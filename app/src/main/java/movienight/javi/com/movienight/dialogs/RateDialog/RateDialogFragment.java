package movienight.javi.com.movienight.dialogs.RateDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.FilterableItemKeys;
import movienight.javi.com.movienight.model.RateFIlterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 10/24/2016.
 */

public class RateDialogFragment extends FilterDialogBase implements RateDialogFragmentView {

    private static final int RATING_BAR_BACKGROUND_INDEX = 2;

    private float mRate;
    private RateDialogFragmentPresenter mPresenter;

    @BindView(R.id.ratingTextView) TextView mScoreTextView;
    @BindView(R.id.ratingBarView) RatingBar mRatingBarView;

    public static DialogFragment newInstance(List<FilterableItem> rateItems) {

        RateDialogFragment dialog = new RateDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.SELECTED_RATE_KEY, (ArrayList)rateItems);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRate = 0.0f;
        List<RateFIlterableItem> rateItems = getArguments().getParcelableArrayList(ActivityExtras.SELECTED_RATE_KEY);

        if(!rateItems.isEmpty()) {

            mRate = rateItems.get(0).getValue()[0];
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.rate_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mPresenter = new RateDialogFragmentPresenter(this);

        mPresenter.setRatingBarProgressDrawable(
            RATING_BAR_BACKGROUND_INDEX,
            Color.YELLOW,
            PorterDuff.Mode.SRC_ATOP
        );

        mPresenter.setRatingBar(mRate);
        mPresenter.setRatingTextViewText(mRate);

        mPresenter.setRatingBarOnChangeListener(
            context.getResources(),
            R.string.rate_text_format_dialog
        );

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());

        return dialog;
    }

    @OnClick(R.id.rateDoneButtonView)
    public void onDialogButtonClick(View view) {

        float rate = mRatingBarView.getRating();

        mListener.onFilterItemCreated(
            FilterableItemKeys.RATE,
            new RateFIlterableItem(rate)
        );

        dismiss();
    }

    @Override
    public void setRatingBarProgressDrawable(int index, int color, PorterDuff.Mode mode) {

        LayerDrawable lDrawable = (LayerDrawable)mRatingBarView.getProgressDrawable();
        lDrawable.getDrawable(index).setColorFilter(color, mode);
    }

    @Override
    public void setRatingBar(float value) {

        mRatingBarView.setRating(value);
    }

    @Override
    public void setRatingTextViewText(float value) {

        String rateFormat = String.valueOf(value);
        mScoreTextView.setText(rateFormat);
    }

    @Override
    public void setRatingBarOnChangeListener(final Resources resources, int textId) {

        mRatingBarView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                String rateText = resources.getString(R.string.rate_text_format_dialog);
                String rateFormatted = String.format(Locale.ENGLISH, rateText, rating);
                mScoreTextView.setText(rateFormatted);
            }
        });
    }
}
