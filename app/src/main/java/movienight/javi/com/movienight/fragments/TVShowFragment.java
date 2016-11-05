package movienight.javi.com.movienight.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.FilterItems.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.TVShowUrlBuilder;

/**
 * Created by Javi on 11/4/2016.
 */

public class TVShowFragment extends FilmFragment {

    public static TVShowFragment newInstance(String[] sortItems) {

        TVShowFragment fragment = new TVShowFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArray(ActivityExtras.SORT_OPTIONS_KEY, sortItems);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        category = 1;
        String[] sortItems = getArguments().getStringArray(ActivityExtras.SORT_OPTIONS_KEY);
        mDialogContainer = new DialogContainer(sortItems);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.tv_show_fragment_menu_layout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int position = -1;

        switch(item.getItemId()) {

            case R.id.tvFilterByGenreItem:
                position = FilterableItemKeys.GENRE;
                break;

            case R.id.tvFilterByReleaseDateItem:
                position = FilterableItemKeys.DATE_RANGE;
                break;

            case R.id.tvFilterByRatingItem:
                position = FilterableItemKeys.RATE;
                break;

            case R.id.tvFilterByVoteCountItem:
                position = FilterableItemKeys.VOTE_COUNT;
                break;

            case R.id.tvFiltertByItem:
                position = FilterableItemKeys.SORT;
                break;
        }

        if(position == -1)
            return super.onOptionsItemSelected(item);

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

        return new TVShowUrlBuilder()
            .withPageNumber(pageNumber + "")
            .withGenres(genresIds)
            .withStartAirDate(startDate)
            .withEndAirDate(endDate)
            .withRating(rateSelected == null ? "" : String.valueOf(rateSelected))
            .withVoteCount(votesCount == null ? "" : String.valueOf(votesCount))
            .sortBy(sortOption)
            .createTVShowUrl();
    }
}
