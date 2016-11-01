package movienight.javi.com.movienight.ui.MainActivity;

import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javier on 10/31/2016.
 */

public interface MainActivityView {
    void setTopMoviesRecyclerViewAdapter(Movie[] items);
    void setTopMoviesRecyclerViewLayoutManager(int numberOfColumns, int orientation);
    void setTopMoviesRecyclerViewSize(boolean fixedSize);
    void updateMoviesRecyclerViewAdapter(Movie[] movies);
}
