package movienight.javi.com.movienight.fragments;

import java.util.List;

import movienight.javi.com.movienight.model.FilmBase;

/**
 * Created by Javier on 10/31/2016.
 */

public interface HomeFragmentView {
    void setTopMoviesRecyclerViewAdapter(List<FilmBase> items);
    void setTopMoviesRecyclerViewLayoutManager(int numberOfColumns, int orientation);
    void setTopMoviesRecyclerViewSize(boolean fixedSize);
    void updateMoviesRecyclerViewAdapter(List<FilmBase> movies);
}
