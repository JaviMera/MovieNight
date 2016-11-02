package movienight.javi.com.movienight.dialogs.RateDialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;

/**
 * Created by Javi on 11/2/2016.
 */

public interface RateDialogFragmentView {

    void setRatingBarProgressDrawable(int index, int color, PorterDuff.Mode mode);
    void setRatingBar(float value);
    void setRatingTextViewText(float value);
    void setRatingBarOnChangeListener(Resources resources, int textId);
}
