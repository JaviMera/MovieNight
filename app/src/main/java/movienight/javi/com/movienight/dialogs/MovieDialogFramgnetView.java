package movienight.javi.com.movienight.dialogs;

import android.graphics.Bitmap;
import android.widget.TextView;

import java.util.List;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 11/1/2016.
 */

public interface MovieDialogFramgnetView {

    void setTextViewText(TextView view, String text, Object... params);
    void setPosterImageView(Bitmap poster);
    void setGenresDescriptionsTextViewText(List<Genre> genres);
    void setReleaseDateTextViewText(String year, String month, String day);
}
