package movienight.javi.com.movienight.ui.SearchActivity;

import android.content.Context;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/18/2016.
 */

public class SearchActivityPresenter {

    private SearchActivityView mView;

    public SearchActivityPresenter(SearchActivityView view) {

        mView = view;
    }

    public void setGenreSpinnerAdapter(Context ctx, Genre[] genres) {

        mView.setGenreSpinnerAdapter(ctx, genres);
    }
}
