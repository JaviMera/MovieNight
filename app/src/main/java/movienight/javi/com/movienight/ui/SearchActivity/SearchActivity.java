package movienight.javi.com.movienight.ui.SearchActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import movienight.javi.com.movienight.adapters.CustomSpinnerAdapter;
import movienight.javi.com.movienight.asyntasks.MoviesFilterAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.DateRangeDialog.DateRangeDialogFragment;
import movienight.javi.com.movienight.dialogs.GenresDialog.GenresDialogFragment;
import movienight.javi.com.movienight.dialogs.MovieDialog.MovieDialogFragment;
import movienight.javi.com.movienight.dialogs.RateDialog.RateDialogFragment;
import movienight.javi.com.movienight.dialogs.VoteDialog.VoteDialogFragment;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.FilterItemContainer;
import movienight.javi.com.movienight.model.FilterItems.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.SortItemContainer;
import movienight.javi.com.movienight.model.SortItems.SortItemBase;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class SearchActivity extends AppCompatActivity
        implements FilterItemAddedListener,
        FilterItemRemovedListener,
        SearchActivityView,
        MoviesAsyncTaskListener,
        MovieSelectedListener,
        MoviePostersListener
    {

    private Integer mTotalPages;
    private int mCurrentPageNumber;
    private Map<String, Movie> mMovies;
    private List<Genre> mGenres;
    private SearchActivityPresenter mPresenter;
    private MovieUrl mUrl;
    private DialogContainer mDialogContainer;
    private FilterItemContainer mFilterItemContainer;
    private SortItemContainer mSortItemContainer;
    private SortItemBase mSortSelected;

    @BindView(R.id.moviesSearchRecyclerView) RecyclerView mMovieRecyclerView;
    @BindView(R.id.updateRecyclerViewProgressBar) ProgressBar mMoviesProgressBar;
    @BindView(R.id.filterMoviesSpinnerView) Spinner mFilterMovieSpinner;
    @BindView(R.id.filtersRecyclerView) RecyclerView mFiltersRecyclerView;
    @BindView(R.id.sortBySpinnerView) Spinner mSortBySpinnerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        mGenres = getIntent().getParcelableArrayListExtra(ActivityExtras.GENRES_KEY);
        mDialogContainer = new DialogContainer(mGenres);

        mMovies = new LinkedHashMap<>();

        mFilterItemContainer = new FilterItemContainer();

        mSortItemContainer = new SortItemContainer();
        mSortSelected = mSortItemContainer.getDefault();

        mCurrentPageNumber = 1;
        mPresenter = new SearchActivityPresenter(this);

        String[] filterItems = getResources().getStringArray(R.array.filter_options_array);
        mPresenter.setFilterOptionsSpinnerViewAdapter(filterItems);
        mPresenter.setFilterSpinnerItemClickListener(spinnerItemClickListener());

        String[] sortItems = getResources().getStringArray(R.array.sort_options_array);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, new ArrayList<>(Arrays.asList(sortItems)));
        mSortBySpinnerView.setAdapter(adapter);

        mSortBySpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position > 0) {

                    mSortSelected = mSortItemContainer.get(position);

                    mMovies.clear();
                    mCurrentPageNumber = 1;

                    MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
                    movieSearchAdapter.removeData();

                    requestMovies(mCurrentPageNumber);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPresenter.setFilterItemRecyclerViewAdapter(new FilterableItem[]{});
        mPresenter.setRecyclerViewManager(mFiltersRecyclerView, 1, LinearLayoutManager.HORIZONTAL);
        mPresenter.setRecyclerSize(mFiltersRecyclerView, true);

        mPresenter.setMoviesRecyclerViewAdapter(mMovies.values().toArray(new Movie[]{}));
        mPresenter.setRecyclerViewManager(mMovieRecyclerView, 3, LinearLayoutManager.VERTICAL);
        mPresenter.setRecyclerSize(mMovieRecyclerView, true);
        mPresenter.setMovieRecyclerScrollListener(scrollListener());
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        mTotalPages = totalPages;
        mMovies.clear();

        for(Movie movie : movies) {

            mMovies.put(movie.getPosterPath(), movie);
        }

        mPresenter.setProgressBarVisibility(View.INVISIBLE);
        mPresenter.updateRecyclerViewAdapter(new ArrayList<>(mMovies.values()));

        Bitmap defaultBitmap = BitmapFactory.decodeResource(
            this.getResources(),
            R.drawable.no_poster_image
            );

        for(Movie movie : mMovies.values()) {

            new PostersAsyncTask(
                this,
                getSupportFragmentManager(),
                ActivityExtras.POSTER_RESOLUTION_342,
                defaultBitmap
            ).execute(movie.getPosterPath());
        }
    }

    @Override
    public void onPostersCompleted(String path, Bitmap poster) {

        Movie updatedMovie = mMovies.get(path);
        updatedMovie.setPoster(poster);
        MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMovieRecyclerView.getAdapter();
        adapter.updateMoviePoster(updatedMovie);
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

        MovieDialogFragment movieDialogFragment = MovieDialogFragment.newInstance(
            movie,
            Genre.getSelectedGenres(movie.getGenreIds(), mGenres)
        );

        movieDialogFragment.show(getSupportFragmentManager(), "movie_dialog");
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

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(
            this
            , new LinkedList<>(Arrays.asList(items)));

        mFilterMovieSpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void setFilterSpinnerItemClickListener(AdapterView.OnItemSelectedListener listener) {

        mFilterMovieSpinner.setOnItemSelectedListener(listener);
    }

    @Override
    public void onFilterItemCreated(Integer key, FilterableItem... newItems) {

        mFilterMovieSpinner.setSelection(0);
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

        mMovies.clear();
        mCurrentPageNumber = 1;
        requestMovies(mCurrentPageNumber);
    }

    @Override
    public void onFilterItemDeleted(FilterableItem itemRemoved) {

        for(List<FilterableItem> items : mFilterItemContainer.getAll()) {

            if(items.contains(itemRemoved)) {

                items.remove(itemRemoved);
                break;
            }
        }

        MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieSearchAdapter.removeData();

        mCurrentPageNumber = 1;
        mMovies.clear();

        FilterItemRecyclerAdapter filterItemAdapter = (FilterItemRecyclerAdapter)mFiltersRecyclerView.getAdapter();
        if(filterItemAdapter.getItemCount() == 0) {

            Toast.makeText(this, "No movies to request.", Toast.LENGTH_SHORT).show();
        }
        else {

            requestMovies(mCurrentPageNumber);
        }
    }

    private MovieUrl createUrl(int pageNumber) {

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

        String sortOption = mSortSelected.getName();

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

    private AdapterView.OnItemSelectedListener spinnerItemClickListener() {

        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position > 0) {

                    List<FilterableItem> selectedItems = mFilterItemContainer.get(position);
                    DialogFragment dialog = mDialogContainer.getDialog(position, selectedItems);
                    dialog.show(getSupportFragmentManager(), "dialog");
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
}