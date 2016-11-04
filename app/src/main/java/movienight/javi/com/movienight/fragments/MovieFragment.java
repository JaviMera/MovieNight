package movienight.javi.com.movienight.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import movienight.javi.com.movienight.adapters.FilterItemRecyclerAdapter;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.MoviesFilterAsyncTask;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.FilterItems.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.SortItemContainer;
import movienight.javi.com.movienight.model.SortItems.SortItemBase;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

/**
 * Created by Javi on 11/4/2016.
 */

public class MovieFragment extends FilmFragment
    implements FilterItemAddedListener {

    public static MovieFragment newInstance(List<Genre> genres, String[] sortItems) {

        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ActivityExtras.GENRES_KEY, (ArrayList)genres);
        bundle.putStringArray(ActivityExtras.SORT_OPTIONS_KEY, sortItems);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        mGenres = getArguments().getParcelableArrayList(ActivityExtras.GENRES_KEY);

        String[] sortItems = getArguments().getStringArray(ActivityExtras.SORT_OPTIONS_KEY);
        mDialogContainer = new DialogContainer(mGenres, sortItems);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.movie_fragment_menu_layout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onFilterItemCreated(Integer key, FilterableItem... newItems) {
        //mFilterMovieSpinner.setSelection(0);
        mFilterItemContainer.put(key, new ArrayList<>(Arrays.asList(newItems)));

        // If the user pressed on the back button on any filter item dialog, then return from method
        // and don't request for movies
        if(key == -1) {
            return;
        }

        FilterItemRecyclerAdapter adapter = (FilterItemRecyclerAdapter) mFiltersRecyclerView.getAdapter();
        adapter.updateData(mFilterItemContainer.getAll());

        MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieSearchAdapter.removeData();

        mFilms.clear();
        mCurrentPageNumber = 1;
        requestFilms(mCurrentPageNumber);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int position = -1;

        switch(item.getItemId()) {

            case R.id.filterByGenreItem:
                position = FilterableItemKeys.GENRE;
                break;

            case R.id.filterByReleaseDateItem:
                position = FilterableItemKeys.DATE_RANGE;
                break;

            case R.id.filterByRatingItem:
                position = FilterableItemKeys.RATE;
                break;

            case R.id.filterByVoteCountItem:
                position = FilterableItemKeys.VOTE_COUNT;
                break;

            case R.id.sortByItem:
                position = FilterableItemKeys.SORT;
                break;
        }

        if(position == -1)
            return onOptionsItemSelected(item);

        List<FilterableItem> selectedItems = mFilterItemContainer.get(position);
        DialogFragment dialog = mDialogContainer.getDialog(position, selectedItems);

        dialog.setTargetFragment(getTargetFragment(), 1);
        dialog.show(
                mParentActivity.getSupportFragmentManager(),
                "dialog"
        );

        return true;
    }

    @Override
    protected AbstractUrl createUrl(int pageNumber) {

        String genresIds = "";
        List<FilterableItem> genreItems = mFilterItemContainer.get(FilterableItemKeys.GENRE);
        for(int i = 0 ; i < genreItems.size() ; i++ ) {

            if(i == genreItems.size() - 1) {
                genresIds += ((Genre)genreItems.get(i).getValue()[0]).getId();
            }
            else {
                genresIds += ((Genre)genreItems.get(i).getValue()[0]).getId() + ",";
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT);
        String startDate = "";
        String endDate = "";
        List<FilterableItem> datesSelected = mFilterItemContainer.get(FilterableItemKeys.DATE_RANGE);

        if(!datesSelected.isEmpty()) {

            Date[] dates = ((DateRangeFilterableItem)datesSelected.get(0)).getValue();
            startDate = formatter.format(dates[0]);
            endDate = formatter.format(dates[1]);
        }

        Float rateSelected = null;

        for(FilterableItem item : mFilterItemContainer.get(FilterableItemKeys.RATE)) {

            rateSelected = (Float)item.getValue()[0];
        }

        Integer votesCount = null;

        for(FilterableItem item : mFilterItemContainer.get(FilterableItemKeys.VOTE_COUNT)) {

            votesCount = (Integer)item.getValue()[0];
        }

        String sortOption = "";

        for(FilterableItem item : mFilterItemContainer.get(FilterableItemKeys.SORT)) {

            sortOption = (String) item.getValue()[0];
        }

        return new MovieUrlBuilder()
                .withPageNumber(pageNumber + "")
                .withGenres(genresIds)
                .withStartReleaseDate(startDate)
                .withEndReleaseDate(endDate)
                .withRating(rateSelected == null ? "" : String.valueOf(rateSelected))
                .withVoteCount(votesCount == null ? "" : String.valueOf(votesCount))
                .sortBy(sortOption)
                .createMovieUrl();
    }
}
