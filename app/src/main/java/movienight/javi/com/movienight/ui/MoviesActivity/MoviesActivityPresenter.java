package movienight.javi.com.movienight.ui.MoviesActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import movienight.javi.com.movienight.Listeners.MovieSelectedListener;
import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javi on 10/22/2016.
 */

public class MoviesActivityPresenter {

    private MoviesActivityView mView;

    public MoviesActivityPresenter(MoviesActivityView view) {

        mView = view;
    }

    public void updateRecyclerViewAdapter(Movie[] movies) {

        mView.updateRecyclerAdapter(movies);
    }

    public void setRecyclerViewLayoutManager(RecyclerView.LayoutManager manager) {

        mView.setRecyclerViewManager(manager);
    }

    public void setRecyclerViewAdapter(Context context, Movie[] movies, MovieSelectedListener listener) {

        mView.setRecyclerViewAdapter(context, movies, listener);
    }

    public void setProgressBarVisibility(int progressBarVisibility) {

        mView.setProgressBarVisibility(progressBarVisibility);
    }
}