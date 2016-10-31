package movienight.javi.com.movienight.ui.SearchActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import java.util.List;

import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javi on 10/22/2016.
 */

public interface MoviesActivityView {

    void setMoviesRecyclerViewAdapter(Movie[] movies);
    void updateRecyclerAdapter(List<Movie> movies);
    void setProgressBarVisibility(int someVisibility);
    void setRecyclerViewManager(RecyclerView view, int numberOfColumns, int orientation);
    void setRecyclerSize(RecyclerView view, boolean fixedSize);
    void setFilterItemRecyclerViewAdapter(FilterableItem[] items);
    void setMovieRecyclerScrollListener(RecyclerView.OnScrollListener listener);
    void setFilterOptionsSpinnerViewAdapter(String[] items);
    void setFilterSpinnerItemClickListener(AdapterView.OnItemSelectedListener listener);
}
