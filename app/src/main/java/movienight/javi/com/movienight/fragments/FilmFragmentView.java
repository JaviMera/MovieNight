package movienight.javi.com.movienight.fragments;

import android.support.v7.widget.RecyclerView;

import java.util.Collection;
import java.util.List;

import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;

/**
 * Created by Javi on 10/22/2016.
 */

public interface FilmFragmentView {

    void setFilmRecyclerViewAdapter(FilmBase[] films);
    void updateFilmRecyclerAdapter(List<FilmBase> films);
    void updateFilterItemsRecyclerViewAdapter(Collection<List<FilterableItem>> items);
    void setProgressBarVisibility(int someVisibility);
    void setRecyclerViewManager(RecyclerView view, int numberOfColumns, int orientation);
    void setRecyclerSize(RecyclerView view, boolean fixedSize);
    void setFilterItemRecyclerViewAdapter(FilterableItem[] items);
    void setFilmRecyclerScrollListener(RecyclerView.OnScrollListener listener);
    void updateFilmPoster(FilmBase film);
}
