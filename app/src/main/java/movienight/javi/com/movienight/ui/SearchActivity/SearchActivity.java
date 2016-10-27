package movienight.javi.com.movienight.ui.SearchActivity;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.adapters.DefaultMoviesRecyclerAdapter;
import movienight.javi.com.movienight.adapters.FilterItemRecyclerAdapter;
import movienight.javi.com.movienight.adapters.FilterSpinnerAdapter;
import movienight.javi.com.movienight.asyntasks.PopularMoviesAsyncTask;
import movienight.javi.com.movienight.dialogs.DaterangeDialogFragment;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.GenresFragmentDialog;
import movienight.javi.com.movienight.dialogs.RateDialogFragment;
import movienight.javi.com.movienight.dialogs.VoteCountDialogFragment;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.FilterableItemKeys;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class SearchActivity extends AppCompatActivity
    implements SearchActivityView, MoviesAsyncTaskListener, FilterItemAddedListener, FilterItemRemovedListener{

    private Map<Integer, List<FilterableItem>> mFilters;
    private SearchActivityPresenter mPresenter;

    @BindView(R.id.filterMoviesSpinnerView)
    Spinner mFilterMovieSpinner;

    @BindView(R.id.movieResultRecyclerView)
    RecyclerView mMovieResultRecyclerView;

    @BindView(R.id.filtersRecyclerView)
    RecyclerView mFiltersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mFilters = new LinkedHashMap<>();
        mFilters.put(-1, new ArrayList<FilterableItem>());
        mFilters.put(1, new ArrayList<FilterableItem>());
        mFilters.put(2, new ArrayList<FilterableItem>());
        mFilters.put(3, new ArrayList<FilterableItem>());
        mFilters.put(4, new ArrayList<FilterableItem>());

        mPresenter = new SearchActivityPresenter(this);

        String[] filterItems = getResources().getStringArray(R.array.filter_options_array);
        mPresenter.setFilterSpinnerAdapter(filterItems);

        mFilterMovieSpinner.setOnItemSelectedListener(spinnerListener());

        final FilterItemRecyclerAdapter recyclerAdapter = new FilterItemRecyclerAdapter(
            this,
            new ArrayList<FilterableItem>(),
            this);

        mFiltersRecyclerView.setAdapter(recyclerAdapter);

        RecyclerView.LayoutManager gridManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mFiltersRecyclerView.setLayoutManager(gridManager);

        mFiltersRecyclerView.setHasFixedSize(true);

        PopularMoviesUrl url = new PopularMoviesUrl();
            new PopularMoviesAsyncTask(this)
                .execute(url);
    }

    @Override
    public void setFilterSpinnerAdapter(String[] items) {

        FilterSpinnerAdapter adapter = new FilterSpinnerAdapter(this, new LinkedList<>(Arrays.asList(items)));
        mFilterMovieSpinner.setAdapter(adapter);
    }

    @OnClick(R.id.findMoviesButtonView)
    public void onFindMoviesButtonClick(View view) {

//        MovieRequest movieRequest = new MovieRequest();
//        movieRequest.setGenre(mSelectedGenres.toArray(new Genre[mSelectedGenres.size()]));
//
//        SimpleDateFormat formatter = new SimpleDateFormat(ReleaseDate.FORMAT);
//
//        movieRequest.setStartDateRelease(formatter.format(mStartDate));
//        movieRequest.setEndDateReleaseSelected(formatter.format(mEndDate));
//        movieRequest.setVoteCount(mVoteCount);
//        movieRequest.setRating(mRate);
//
//        Intent intent = new Intent(SearchActivity.this, MoviesActivity.class);
//        intent.putExtra(ActivityExtras.MOVIE_REQUEST_KEY, movieRequest);
//        startActivity(intent);
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        final DefaultMoviesRecyclerAdapter recyclerAdapter = new DefaultMoviesRecyclerAdapter(this, movies);
        mMovieResultRecyclerView.setAdapter(recyclerAdapter);

        RecyclerView.LayoutManager gridManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mMovieResultRecyclerView.setLayoutManager(gridManager);

        mMovieResultRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onFilterItemCreated(Integer key, FilterableItem[] item) {

        mFilterMovieSpinner.setSelection(0);
        mFilters.put(key, new ArrayList<>(Arrays.asList(item)));

        FilterItemRecyclerAdapter adapter = (FilterItemRecyclerAdapter) mFiltersRecyclerView.getAdapter();
        adapter.updateData(mFilters.values());
    }

    @Override
    public void onFilterItemDeleted(FilterableItem itemRemoved) {

        for(List<FilterableItem> items : mFilters.values()) {

            if(items.contains(itemRemoved)) {

                items.remove(itemRemoved);
                break;
            }
        }
    }

    private AdapterView.OnItemSelectedListener spinnerListener() {

        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DialogFragment dialog;
                List<FilterableItem> items;

                switch(position) {

                    case FilterableItemKeys.GENRE:
                        items = mFilters.get(FilterableItemKeys.GENRE);
                        dialog = GenresFragmentDialog.newInstance(items);
                        dialog.show(getSupportFragmentManager(), "genre_dialog");
                        break;

                    case 2:
                        items = mFilters.get(FilterableItemKeys.DATE_RANGE);
                        dialog = DaterangeDialogFragment.newInstance(items);
                        dialog.show(getSupportFragmentManager(), "daterange_dialog");
                        break;

                    case 3:
                        items = mFilters.get(FilterableItemKeys.RATE);
                        dialog = RateDialogFragment.newInstance(items);
                        dialog.show(getSupportFragmentManager(), "rate_dialog");
                        break;

                    case 4:
                        items = mFilters.get(FilterableItemKeys.VOTE_COUNT);
                        dialog = VoteCountDialogFragment.newInstance(items);
                        dialog.show(getSupportFragmentManager(), "votecount_dialog");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

}
