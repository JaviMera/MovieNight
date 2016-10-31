package movienight.javi.com.movienight.ui.SearchActivity;

import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.adapters.FilterItemRecyclerAdapter;
import movienight.javi.com.movienight.adapters.FilterSpinnerAdapter;
import movienight.javi.com.movienight.asyntasks.MoviesFilterAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.DaterangeFilterDialog;
import movienight.javi.com.movienight.dialogs.GenresFragmentFilterDialog;
import movienight.javi.com.movienight.dialogs.RateFilterDialog;
import movienight.javi.com.movienight.dialogs.VoteCountFilterDialog;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.FilterableItemKeys;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class SearchActivity extends AppCompatActivity
        implements FilterItemAddedListener,
        FilterItemRemovedListener,
        MoviesActivityView,
        MoviesAsyncTaskListener,
        MovieSelectedListener,
        MoviePostersListener
    {

    private Integer mTotalPages;
    private int mCurrentPageNumber;
    private List<Movie> mMovies;
    private List<Genre> mGenres;
    private MoviesActivityPresenter mPresenter;
    private MovieUrl mUrl;
    private Map<Integer, List<FilterableItem>> mFilters;

    @BindView(R.id.moviesSearchRecyclerView) RecyclerView mMovieRecyclerView;
    @BindView(R.id.updateRecyclerViewProgressBar) ProgressBar mMoviesProgressBar;
    @BindView(R.id.filterMoviesSpinnerView) Spinner mFilterMovieSpinner;
    @BindView(R.id.filtersRecyclerView) RecyclerView mFiltersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        mGenres = getIntent().getParcelableArrayListExtra(ActivityExtras.GENRES_KEY);
        mFilters = initializeFiltersMap();

        mCurrentPageNumber = 1;
        mMovies = new LinkedList<>();
        mPresenter = new MoviesActivityPresenter(this);

        String[] filterItems = getResources().getStringArray(R.array.filter_options_array);
        mPresenter.setFilterOptionsSpinnerViewAdapter(filterItems);
        mPresenter.setFilterSpinnerItemClickListener(spinnerItemClickListener());

        mPresenter.setFilterItemRecyclerViewAdapter(new FilterableItem[]{});
        mPresenter.setRecyclerViewManager(mFiltersRecyclerView, 1, LinearLayoutManager.HORIZONTAL);
        mPresenter.setRecyclerSize(mFiltersRecyclerView, true);

        mPresenter.setMoviesRecyclerViewAdapter(mMovies.toArray(new Movie[]{}));
        mPresenter.setRecyclerViewManager(mMovieRecyclerView, 3, LinearLayoutManager.VERTICAL);
        mPresenter.setRecyclerSize(mMovieRecyclerView, true);
        mPresenter.setMovieRecyclerScrollListener(scrollListener());
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        mTotalPages = totalPages;
        mMovies = new LinkedList<>(Arrays.asList(movies));
        List<String> posterPaths = new LinkedList<>();

        for(Movie m : mMovies) {

            posterPaths.add(m.getPosterPath());
        }

        new PostersAsyncTask(this, getSupportFragmentManager(), ActivityExtras.POSTER_RESOLUTION_342)
            .execute(posterPaths.toArray(new String[posterPaths.size()]));
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

        Toast.makeText(this, movie.getOverview(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setMoviesRecyclerViewAdapter(Movie[] movies) {

        MovieRecyclerViewAdapter movieAdapter = new MovieRecyclerViewAdapter(
                this,
                new LinkedList<>(Arrays.asList(movies)),
                this);

        mMovieRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void updateRecyclerAdapter(List<Movie> movies) {

        MovieRecyclerViewAdapter movieAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieAdapter.updateData(movies);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {

        mMoviesProgressBar.setVisibility(visibility);
    }

    @Override
    public void setRecyclerViewManager(RecyclerView view, int numberOfColumns, int orientation) {

        RecyclerView.LayoutManager manager = new GridLayoutManager(
            this,
            numberOfColumns,
            orientation,
            false
        );

        view.setLayoutManager(manager);
    }

    @Override
    public void setRecyclerSize(RecyclerView view, boolean fixedSize) {

        view.setHasFixedSize(fixedSize);
    }

    @Override
    public void setFilterItemRecyclerViewAdapter(FilterableItem[] items) {

        FilterItemRecyclerAdapter adapter = new FilterItemRecyclerAdapter(
            this,
            new ArrayList(Arrays.asList(items)), this)
        ;

        mFiltersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setMovieRecyclerScrollListener(RecyclerView.OnScrollListener listener) {

        mMovieRecyclerView.addOnScrollListener(listener);
    }

    @Override
    public void setFilterOptionsSpinnerViewAdapter(String[] items) {

        FilterSpinnerAdapter spinnerAdapter = new FilterSpinnerAdapter(
            this
            , new LinkedList<>(Arrays.asList(items)));

        mFilterMovieSpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void setFilterSpinnerItemClickListener(AdapterView.OnItemSelectedListener listener) {

        mFilterMovieSpinner.setOnItemSelectedListener(listener);
    }

    @Override
    public void onPostersCompleted(Bitmap[] posters) {

        for(int i = 0 ; i < mMovies.size() ; i++) {

            mMovies.get(i).setPoster(posters[i]);
        }

        mPresenter.updateRecyclerViewAdapter(mMovies);
        mPresenter.setProgressBarVisibility(View.INVISIBLE);
    }

    @Override
    public void onFilterItemCreated(Integer key, FilterableItem... newItems) {

        mFilterMovieSpinner.setSelection(0);
        mFilters.put(key, new ArrayList<>(Arrays.asList(newItems)));

        // If the user pressed on the back button on any filter item dialog, then return from method
        // and don't request for movies
        if(key == -1) {
            return;
        }

        FilterItemRecyclerAdapter adapter = (FilterItemRecyclerAdapter) mFiltersRecyclerView.getAdapter();
        adapter.updateData(mFilters.values());

        MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieSearchAdapter.removeData();

        mMovies.clear();
        mCurrentPageNumber = 1;
        requestMovies(mCurrentPageNumber);
    }

    @Override
    public void onFilterItemDeleted(FilterableItem itemRemoved) {

        for(List<FilterableItem> items : mFilters.values()) {

            if(items.contains(itemRemoved)) {

                items.remove(itemRemoved);
                break;
            }
        }

        MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieSearchAdapter.removeData();

        mCurrentPageNumber = 1;
        mMovies.clear();
        mUrl = createUrl(mCurrentPageNumber);

        new MoviesFilterAsyncTask(this).execute(mUrl);
        mMoviesProgressBar.setVisibility(View.VISIBLE);
    }

    private MovieUrl createUrl(int pageNumber) {

        String genresIds = "";
        List<FilterableItem> genreItems =mFilters.get(FilterableItemKeys.GENRE);
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
        List<FilterableItem> datesSelected = mFilters.get(FilterableItemKeys.DATE_RANGE);

        if(!datesSelected.isEmpty()) {

            Date[] dates = ((DateRangeFilterableItem)datesSelected.get(0)).getValue();
            startDate = formatter.format(dates[0]);
            endDate = formatter.format(dates[1]);
        }

        Float rateSelected = null;

        for(FilterableItem item : mFilters.get(FilterableItemKeys.RATE)) {

            rateSelected = (Float)item.getValue()[0];
        }

        Integer votesCount = null;

        for(FilterableItem item : mFilters.get(FilterableItemKeys.VOTE_COUNT)) {

            votesCount = (Integer)item.getValue()[0];
        }

        return new MovieUrlBuilder()
                .withPageNumber(pageNumber + "")
                .withGenres(genresIds)
                .withStartReleaseDate(startDate)
                .withEndReleaseDate(endDate)
                .withRating(rateSelected == null ? "" : String.valueOf(rateSelected))
                .withVoteCount(votesCount == null ? "" : String.valueOf(votesCount))
                .createMovieUrl();
    }

    private AdapterView.OnItemSelectedListener spinnerItemClickListener() {

        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DialogFragment dialog;
                List<FilterableItem> selectedItems;

                switch(position) {

                    case FilterableItemKeys.GENRE:
                        selectedItems = mFilters.get(FilterableItemKeys.GENRE);
                        dialog = GenresFragmentFilterDialog.newInstance(mGenres, selectedItems);
                        dialog.show(getSupportFragmentManager(), "genre_dialog");
                        break;

                    case 2:
                        selectedItems = mFilters.get(FilterableItemKeys.DATE_RANGE);
                        dialog = DaterangeFilterDialog.newInstance(selectedItems);
                        dialog.show(getSupportFragmentManager(), "daterange_dialog");
                        break;

                    case 3:
                        selectedItems = mFilters.get(FilterableItemKeys.RATE);
                        dialog = RateFilterDialog.newInstance(selectedItems);
                        dialog.show(getSupportFragmentManager(), "rate_dialog");
                        break;

                    case 4:
                        selectedItems = mFilters.get(FilterableItemKeys.VOTE_COUNT);
                        dialog = VoteCountFilterDialog.newInstance(selectedItems);
                        dialog.show(getSupportFragmentManager(), "votecount_dialog");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void requestMovies(int pageNumber) {

        mPresenter.setProgressBarVisibility(View.VISIBLE);
        mUrl = createUrl(pageNumber);

        new MoviesFilterAsyncTask(this).execute(mUrl);
    }

    private RecyclerView.OnScrollListener scrollListener() {

        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager linearManager = (LinearLayoutManager)recyclerView.getLayoutManager();

                // Check if the scroll happened when the adapter's data was cleared
                // In such case, we don't want to call the endless scroll code.
                if(linearManager.getItemCount() == 0)
                    return;

                if(linearManager.getItemCount() == linearManager.findLastCompletelyVisibleItemPosition() + 1) {

                    if(mCurrentPageNumber < mTotalPages) {

                        mCurrentPageNumber++;
                        requestMovies(mCurrentPageNumber);
                    }
                }
            }
        };
    }

    private Map<Integer, List<FilterableItem>> initializeFiltersMap() {

            return new LinkedHashMap<Integer, List<FilterableItem>>(){
                {
                    put(-1, new ArrayList<FilterableItem>());
                    put(1, new ArrayList<FilterableItem>());
                    put(2, new ArrayList<FilterableItem>());
                    put(3, new ArrayList<FilterableItem>());
                    put(4, new ArrayList<FilterableItem>());
                }
            };
        }
}