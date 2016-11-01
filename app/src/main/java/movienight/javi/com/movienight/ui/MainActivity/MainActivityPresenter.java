package movienight.javi.com.movienight.ui.MainActivity;

import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javier on 10/31/2016.
 */

public class MainActivityPresenter {

    private MainActivityView mView;

    public MainActivityPresenter(MainActivityView view) {

        mView = view;
    }

    public void setTopMoviesRecyclerViewAdapter(Movie[] items) {

        mView.setTopMoviesRecyclerViewAdapter(items);
    }

    public void setTopMoviesRecyclerViewLayoutManager(int numberOfColumns, int orientation) {

        mView.setTopMoviesRecyclerViewLayoutManager(numberOfColumns, orientation);
    }

    public void setTopMoviesRecyclerViewSize(boolean fixedSize) {

        mView.setTopMoviesRecyclerViewSize(fixedSize);
    }

    public void updateMoviesRecyclerViewAdapter(Movie[] movies) {

        mView.updateMoviesRecyclerViewAdapter(movies);
    }
}
