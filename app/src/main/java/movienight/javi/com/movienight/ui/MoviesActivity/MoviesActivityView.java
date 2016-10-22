package movienight.javi.com.movienight.ui.MoviesActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javi on 10/22/2016.
 */

public interface MoviesActivityView {

    void setRecyclerViewManager(RecyclerView.LayoutManager manager);
    void setRecyclerViewAdapter(Context context, Movie[] movies, MovieSelectedListener listener);
    void updateRecyclerAdapter(Movie[] movies);
}
