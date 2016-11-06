package movienight.javi.com.movienight.dialogs.FilmDialog;

import android.graphics.Bitmap;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Javi on 11/1/2016.
 */

public interface FilmDialogFramgnetView {

    void setTextViewText(TextView view, String text, Object... params);
    void setPosterImageView(Bitmap poster);
    void setGenresDescriptionsTextViewText(List<String> genres);
    void setReleaseDateTextViewText(String year, String month, String day);
}
