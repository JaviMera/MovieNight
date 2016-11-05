package movienight.javi.com.movienight.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.FilmCatetory;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;
import movienight.javi.com.movienight.urls.TVShowUrlBuilder;

/**
 * Created by Javi on 11/4/2016.
 */

public class TVShowFragment extends FilmFragment {

    public static TVShowFragment newInstance(List<Genre> genres, String[] sortItems) {

        TVShowFragment fragment = new TVShowFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.GENRES_KEY, (ArrayList)genres);
        bundle.putStringArray(ActivityExtras.SORT_OPTIONS_KEY, sortItems);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        category = FilmCatetory.TV_SHOW;
        mGenres = getArguments().getParcelableArrayList(ActivityExtras.GENRES_KEY);

        String[] sortItems = getArguments().getStringArray(ActivityExtras.SORT_OPTIONS_KEY);
        mDialogContainer = new DialogContainer(mGenres, sortItems);

        super.onCreate(savedInstanceState);
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

            case R.id.tvFilterGenreItem:
                position = FilterableItemKeys.GENRE;
                break;

            case R.id.tvFilterAirDateItem:
                position = FilterableItemKeys.DATE_RANGE;
                break;

            case R.id.tvFilterRatingItem:
                position = FilterableItemKeys.RATE;
                break;

            case R.id.tvFilterVoteCountItem:
                position = FilterableItemKeys.VOTE_COUNT;
                break;

            case R.id.tvFilterSortItem:
                position = FilterableItemKeys.SORT;
                break;
        }

        if(position == -1)
            return super.onOptionsItemSelected(item);

        FilterDialogBase dialog = getDialog(position);
        dialog.setTargetFragment(getTargetFragment(), 1);
        dialog.show(
                mParentActivity.getSupportFragmentManager(),
                "dialog"
        );

        return true;
    }

    @Override
    protected AbstractUrl createUrl(
        int pageNumber,
        String genreIds,
        String startDate,
        String endDate,
        String rating,
        String voteCount,
        String sort
    )
    {
        return new TVShowUrlBuilder()
            .withPageNumber(pageNumber + "")
            .withGenres(genreIds)
            .withStartAirDate(startDate)
            .withEndAirDate(endDate)
            .withRating(rating)
            .withVoteCount(voteCount)
            .sortBy(sort)
            .createTVShowUrl();
    }
}
