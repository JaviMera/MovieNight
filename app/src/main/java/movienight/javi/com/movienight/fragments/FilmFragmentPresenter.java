package movienight.javi.com.movienight.fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import java.util.List;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javi on 10/22/2016.
 */

public class FilmFragmentPresenter {

    private FilmFragmentView mView;

    public FilmFragmentPresenter(FilmFragmentView view) {

        mView = view;
    }

    public void updateRecyclerViewAdapter(List<Movie> movies) {

        mView.updateRecyclerAdapter(movies);
    }

    public void setMoviesRecyclerViewAdapter(Movie[] movies) {

        mView.setMoviesRecyclerViewAdapter(movies);
    }

    public void setProgressBarVisibility(int progressBarVisibility) {

        mView.setProgressBarVisibility(progressBarVisibility);
    }

    public void setRecyclerViewManager(RecyclerView view, int numberOfColumns, int orientation) {

        mView.setRecyclerViewManager(view, numberOfColumns, orientation);
    }

    public void setRecyclerSize(RecyclerView view, boolean fixedSize) {

        mView.setRecyclerSize(view, fixedSize);
    }


    public void setFilterItemRecyclerViewAdapter(FilterableItem[] items) {

        mView.setFilterItemRecyclerViewAdapter(items);
    }

    public void setMovieRecyclerScrollListener(RecyclerView.OnScrollListener listener) {

        mView.setMovieRecyclerScrollListener(listener);
    }

    public void setFilterOptionsSpinnerViewAdapter(String[] items) {

        mView.setFilterOptionsSpinnerViewAdapter(items);
    }

    public void setFilterSpinnerItemClickListener(AdapterView.OnItemSelectedListener listener) {

        mView.setFilterSpinnerItemClickListener(listener);
    }
}
