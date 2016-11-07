package movienight.javi.com.movienight.fragments;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;

/**
 * Created by Javi on 10/22/2016.
 */

public class FilmFragmentPresenter {

    private FilmFragmentView mView;

    public FilmFragmentPresenter(FilmFragmentView view) {

        mView = view;
    }

    public void updateFilmRecyclerViewAdapter(List<FilmBase> films) {

        mView.updateFilmRecyclerAdapter(films);
    }

    public void setFilmRecyclerViewAdapter(FilmBase[] films) {

        mView.setFilmRecyclerViewAdapter(films);
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

    public void setFilmRecyclerScrollListener(RecyclerView.OnScrollListener listener) {

        mView.setFilmRecyclerScrollListener(listener);
    }
}
