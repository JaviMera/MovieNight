package movienight.javi.com.movienight.dialogs.FilmDialog;

import android.graphics.Bitmap;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Javi on 11/1/2016.
 */

public class FilmDialogFragmentPresenter {

    private FilmDialogFramgnetView mView;

    public FilmDialogFragmentPresenter(FilmDialogFramgnetView view) {

        mView = view;
    }

    public void setTextViewText(TextView view, String text, Object... params) {

        mView.setTextViewText(view, text, params);
    }

    public void setPosterImageView(Bitmap poster) {

        mView.setPosterImageView(poster);
    }

    public void setGenresDescriptionsTextViewText(List<String> genres) {

        mView.setGenresDescriptionsTextViewText(genres);
    }

    public void setReleaseDateTextViewText(String year, String month, String day) {

        mView.setReleaseDateTextViewText(year, month, day);
    }
}
