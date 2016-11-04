package movienight.javi.com.movienight.fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import java.util.List;

import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javi on 10/22/2016.
 */

public interface FilmFragmentView {

    void setMoviesRecyclerViewAdapter(Film[] films);
    void updateRecyclerAdapter(List<Film> films);
    void setProgressBarVisibility(int someVisibility);
    void setRecyclerViewManager(RecyclerView view, int numberOfColumns, int orientation);
    void setRecyclerSize(RecyclerView view, boolean fixedSize);
    void setFilterItemRecyclerViewAdapter(FilterableItem[] items);
    void setMovieRecyclerScrollListener(RecyclerView.OnScrollListener listener);
}
