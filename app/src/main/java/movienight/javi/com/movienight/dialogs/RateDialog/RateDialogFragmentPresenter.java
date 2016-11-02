package movienight.javi.com.movienight.dialogs.RateDialog;

import android.content.res.Resources;
import android.graphics.PorterDuff;

/**
 * Created by Javi on 11/2/2016.
 */

public class RateDialogFragmentPresenter {

    private RateDialogFragmentView mView;

    public RateDialogFragmentPresenter(RateDialogFragmentView view) {

        mView = view;
    }

    public void setRatingBarProgressDrawable(int index, int color, PorterDuff.Mode mode) {

        mView.setRatingBarProgressDrawable(index, color, mode);
    }

    public void setRatingBar(float value) {

        mView.setRatingBar(value);
    }

    public void setRatingTextViewText(float value) {

        mView.setRatingTextViewText(value);
    }

    public void setRatingBarOnChangeListener(Resources resources, int textId) {

        mView.setRatingBarOnChangeListener(resources, textId);
    }
}
