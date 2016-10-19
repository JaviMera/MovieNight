package movienight.javi.com.movienight.ui.SearchActivity;

import android.content.Context;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/18/2016.
 */

public interface SearchActivityView {

    void setGenreSpinnerAdapter(Context ctx, Genre[] someGenres);

    void setSeekBarProgressTextView(int progress);
}
