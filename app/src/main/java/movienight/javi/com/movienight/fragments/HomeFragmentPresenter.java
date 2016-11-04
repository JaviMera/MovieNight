package movienight.javi.com.movienight.fragments;

import java.util.List;

import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javier on 10/31/2016.
 */

public class HomeFragmentPresenter {

    private HomeFragmentView mView;

    public HomeFragmentPresenter(HomeFragmentView view) {

        mView = view;
    }

    public void setTopMoviesRecyclerViewAdapter(List<Movie> items) {

        mView.setTopMoviesRecyclerViewAdapter(items);
    }

    public void setTopMoviesRecyclerViewLayoutManager(int numberOfColumns, int orientation) {

        mView.setTopMoviesRecyclerViewLayoutManager(numberOfColumns, orientation);
    }

    public void setTopMoviesRecyclerViewSize(boolean fixedSize) {

        mView.setTopMoviesRecyclerViewSize(fixedSize);
    }

    public void updateMoviesRecyclerViewAdapter(List<Movie> movies) {

        mView.updateMoviesRecyclerViewAdapter(movies);
    }
}