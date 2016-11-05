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
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.FilmCatetory;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

/**
 * Created by Javi on 11/4/2016.
 */

public class MovieFragment extends FilmFragment {

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

        category = FilmCatetory.MOVIE;
        mGenres = getArguments().getParcelableArrayList(ActivityExtras.GENRES_KEY);

        String[] sortItems = getArguments().getStringArray(ActivityExtras.SORT_OPTIONS_KEY);
        mDialogContainer = new DialogContainer(mGenres, sortItems);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.movie_fragment_menu_layout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int position = -1;

        switch(item.getItemId()) {

            case R.id.movieFilterGenreItem:
                position = FilterableItemKeys.GENRE;
                break;

            case R.id.movieFilterReleaseDate:
                position = FilterableItemKeys.DATE_RANGE;
                break;

            case R.id.movieFilterRateItem:
                position = FilterableItemKeys.RATE;
                break;

            case R.id.movieFilterVoteCountItem:
                position = FilterableItemKeys.VOTE_COUNT;
                break;

            case R.id.movieFilterSortItem:
                position = FilterableItemKeys.SORT;
                break;
        }

        if(position == -1)
            return super.onOptionsItemSelected(item);

        DialogFragment dialog = getDialog(position);

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
        return new MovieUrlBuilder()
            .withPageNumber(pageNumber + "")
            .withGenres(genreIds)
            .withStartReleaseDate(startDate)
            .withEndReleaseDate(endDate)
            .withRating(rating)
            .withVoteCount(voteCount)
            .sortBy(sort)
            .createMovieUrl();
    }
}
