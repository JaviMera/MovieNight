package movienight.javi.com.movienight.ui.SearchActivity;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import movienight.javi.com.movienight.listeners.FilterItemListener;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.FilterableItemKeys;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.GenreFilterableItem;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.RateFilterableItem;
import movienight.javi.com.movienight.model.VoteCountFilterableItem;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class SearchActivity extends AppCompatActivity
    implements SearchActivityView, MoviesAsyncTaskListener, FilterItemListener{

    private Map<Integer, FilterableItem> mFilters;
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
        mFilters.put(1, new GenreFilterableItem(new Genre[]{}));
        mFilters.put(2, new DateRangeFilterableItem(new Date(), new Date()));
        mFilters.put(3, new RateFilterableItem(0.0f));
        mFilters.put(4, new VoteCountFilterableItem(0));

        mPresenter = new SearchActivityPresenter(this);

        String[] filterItems = getResources().getStringArray(R.array.filter_options_array);
        mPresenter.setFilterSpinnerAdapter(filterItems);

        mFilterMovieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DialogFragment dialog;

                switch(position) {

                    case FilterableItemKeys.GENRE:
                        GenreFilterableItem genreItem = (GenreFilterableItem) mFilters.get(FilterableItemKeys.GENRE);
                        List<Genre> selectedGenres = new ArrayList(Arrays.asList(genreItem.getSelectedGenres()));

                        dialog = GenresFragmentDialog.newInstance(selectedGenres);
                        dialog.show(getSupportFragmentManager(), "genre_dialog");
                        break;

                    case 2:
                        DateRangeFilterableItem dateRangeItem = (DateRangeFilterableItem)mFilters.get(2);
                        dialog = DaterangeDialogFragment.newInstance(dateRangeItem.getStartDate(), dateRangeItem.getEndDate());
                        dialog.show(getSupportFragmentManager(), "daterange_dialog");
                        break;

                    case 3:
                        RateFilterableItem rateItem = (RateFilterableItem) mFilters.get(3);
                        dialog = RateDialogFragment.newInstance(rateItem.getRate());
                        dialog.show(getSupportFragmentManager(), "rate_dialog");
                        break;

                    case 4:
                        VoteCountFilterableItem voteItem = (VoteCountFilterableItem) mFilters.get(4);
                        dialog = VoteCountDialogFragment.newInstance(voteItem.getVoteCount());
                        dialog.show(getSupportFragmentManager(), "votecount_dialog");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final FilterItemRecyclerAdapter recyclerAdapter = new FilterItemRecyclerAdapter(this, new ArrayList<FilterableItem>());
        mFiltersRecyclerView.setAdapter(recyclerAdapter);

        RecyclerView.LayoutManager gridManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
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
    public void onFilterItemCreated(Integer key, FilterableItem item) {

        mFilterMovieSpinner.setSelection(0);

        FilterItemRecyclerAdapter adapter = (FilterItemRecyclerAdapter) mFiltersRecyclerView.getAdapter();
        adapter.updateData(item);

        mFilters.put(key, item);
    }
}