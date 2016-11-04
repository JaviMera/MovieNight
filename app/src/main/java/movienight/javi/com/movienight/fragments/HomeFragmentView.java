package movienight.javi.com.movienight.fragments;

import java.util.List;

import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javier on 10/31/2016.
 */

public interface HomeFragmentView {
    void setTopMoviesRecyclerViewAdapter(List<Film> items);
    void setTopMoviesRecyclerViewLayoutManager(int numberOfColumns, int orientation);
    void setTopMoviesRecyclerViewSize(boolean fixedSize);
    void updateMoviesRecyclerViewAdapter(List<Film> movies);
}
