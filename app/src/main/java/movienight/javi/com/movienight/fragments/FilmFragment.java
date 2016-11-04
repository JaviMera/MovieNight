package movienight.javi.com.movienight.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.CustomSpinnerAdapter;
import movienight.javi.com.movienight.adapters.FilterItemRecyclerAdapter;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.MoviesFilterAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.MovieDialog.FilmDialogFragment;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.FilmSelectedListener;
import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.FilterItemContainer;
import movienight.javi.com.movienight.model.FilterItems.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.SortItemContainer;
import movienight.javi.com.movienight.model.SortItems.SortItemBase;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.MainActivity;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFragment extends Fragment implements FilterItemAddedListener,
        FilterItemRemovedListener,
        FilmFragmentView,
        FilmAsyncTaskListener,
        FilmSelectedListener,
        MoviePostersListener {

    private MainActivity mParentActivity;

    private Integer mTotalPages;
    private int mCurrentPageNumber;
    private Map<String, Film> mFilms;
    private List<Genre> mGenres;
    private FilmFragmentPresenter mPresenter;
    private MovieUrl mUrl;
    private DialogContainer mDialogContainer;
    private FilterItemContainer mFilterItemContainer;
    private SortItemContainer mSortItemContainer;
    private SortItemBase mSortSelected;
    private AsyncTask mMovieAsyncTask;

    @BindView(R.id.moviesSearchRecyclerView) RecyclerView mMovieRecyclerView;
    @BindView(R.id.updateRecyclerViewProgressBar)
    ProgressBar mMoviesProgressBar;
    @BindView(R.id.filterMoviesSpinnerView)
    Spinner mFilterMovieSpinner;
    @BindView(R.id.filtersRecyclerView) RecyclerView mFiltersRecyclerView;
    @BindView(R.id.sortBySpinnerView) Spinner mSortBySpinnerView;

    public FilmFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(List<Genre> genres) {

        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ActivityExtras.GENRES_KEY, (ArrayList)genres);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParentActivity = (MainActivity)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGenres = getArguments().getParcelableArrayList(ActivityExtras.GENRES_KEY);

        mDialogContainer = new DialogContainer(mGenres);

        mFilms = new LinkedHashMap<>();

        mFilterItemContainer = new FilterItemContainer();

        mSortItemContainer = new SortItemContainer();
        mSortSelected = mSortItemContainer.getDefault();

        mCurrentPageNumber = 1;

        setTargetFragment(this, 1);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_film, container, false);



        ButterKnife.bind(this, fragmentLayout);

        mPresenter = new FilmFragmentPresenter(this);

        String[] filterItems = getResources().getStringArray(R.array.filter_options_array);
        mPresenter.setFilterOptionsSpinnerViewAdapter(filterItems);
        mPresenter.setFilterSpinnerItemClickListener(spinnerItemClickListener());

        String[] sortItems = getResources().getStringArray(R.array.sort_options_array);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(mParentActivity, new ArrayList<>(Arrays.asList(sortItems)));
        mSortBySpinnerView.setAdapter(adapter);

        mSortBySpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position > 0) {

                    mSortSelected = mSortItemContainer.get(position);

                    mFilms.clear();
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

        mPresenter.setMoviesRecyclerViewAdapter(mFilms.values().toArray(new Movie[]{}));
        mPresenter.setRecyclerViewManager(mMovieRecyclerView, 3, LinearLayoutManager.VERTICAL);
        mPresenter.setRecyclerSize(mMovieRecyclerView, true);
        mPresenter.setMovieRecyclerScrollListener(scrollListener());

        return fragmentLayout;
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

        mFilms.clear();
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
        mFilms.clear();

        if(mMovieAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

            mMovieAsyncTask.cancel(true);
            mPresenter.setProgressBarVisibility(View.INVISIBLE);
        }

        FilterItemRecyclerAdapter filterItemAdapter = (FilterItemRecyclerAdapter)mFiltersRecyclerView.getAdapter();
        if(filterItemAdapter.getItemCount() == 0) {

            Toast.makeText(mParentActivity, "No movies to request.", Toast.LENGTH_SHORT).show();
        }
        else {

            requestMovies(mCurrentPageNumber);
        }
    }

    @Override
    public void onPostersCompleted(String path, Bitmap poster) {

        if(!mFilms.isEmpty()) {

            Film updatedMovie = mFilms.get(path);
            updatedMovie.setPoster(poster);
            MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMovieRecyclerView.getAdapter();
            adapter.updateMoviePoster(updatedMovie);
        }
    }

    @Override
    public void onFilmSelectedItem(Film film) {

        FilmDialogFragment filmDialogFragment = FilmDialogFragment.newInstance(
                film,
                Genre.getSelectedGenres(film.getGenres(), mGenres)
        );

        filmDialogFragment.show(
            mParentActivity.getSupportFragmentManager(),
            "movie_dialog");
    }

    @Override
    public void onCompleted(Integer totalPages, Film[] films) {

        mTotalPages = totalPages;
        mFilms.clear();

        for(Film film : films) {

            mFilms.put(film.getPosterPath(), film);
        }

        mPresenter.setProgressBarVisibility(View.INVISIBLE);
        mPresenter.updateRecyclerViewAdapter(new ArrayList<>(mFilms.values()));

        Bitmap defaultBitmap = BitmapFactory.decodeResource(
                this.getResources(),
                R.drawable.no_poster_image
        );

        for(Film film : mFilms.values()) {

            new PostersAsyncTask(
                    this,
                    mParentActivity.getSupportFragmentManager(),
                    ActivityExtras.POSTER_RESOLUTION_342,
                    defaultBitmap
            ).execute(film.getPosterPath());
        }
    }

    @Override
    public void setMoviesRecyclerViewAdapter(Film[] films) {

        MovieRecyclerViewAdapter movieAdapter = new MovieRecyclerViewAdapter(
                mParentActivity,
                new LinkedList<>(Arrays.asList(films)),
                this);

        mMovieRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void updateRecyclerAdapter(List<Film> films) {

        MovieRecyclerViewAdapter movieAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieAdapter.updateData(films);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {

        mMoviesProgressBar.setVisibility(visibility);
    }

    @Override
    public void setRecyclerViewManager(RecyclerView view, int numberOfColumns, int orientation) {

        RecyclerView.LayoutManager manager = new GridLayoutManager(
                mParentActivity,
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
                mParentActivity,
                new ArrayList(Arrays.asList(items)),
                this
        );

        mFiltersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setMovieRecyclerScrollListener(RecyclerView.OnScrollListener listener) {

        mMovieRecyclerView.addOnScrollListener(listener);
    }

    @Override
    public void setFilterOptionsSpinnerViewAdapter(String[] items) {

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(
                mParentActivity,
                new LinkedList<>(Arrays.asList(items))
    );

        mFilterMovieSpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void setFilterSpinnerItemClickListener(AdapterView.OnItemSelectedListener listener) {

        mFilterMovieSpinner.setOnItemSelectedListener(listener);
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
                    dialog.setTargetFragment(getTargetFragment(), 1);
                    dialog.show(
                        mParentActivity.getSupportFragmentManager(),
                        "dialog"
                    );
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

        mMovieAsyncTask = new MoviesFilterAsyncTask(this).execute(mUrl);
    }

    private RecyclerView.OnScrollListener scrollListener() {

        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                GridLayoutManager linearManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int itemCount = linearManager.getItemCount();
                // Check if the scroll happened when the adapter's data was cleared
                // In such case, we don't want to call the endless scroll code.
                if (itemCount == 0)
                    return;

                int lastPositionVisible = linearManager.findLastCompletelyVisibleItemPosition();
                if (itemCount == lastPositionVisible + 1) {

                    if (mCurrentPageNumber < mTotalPages) {

                        mCurrentPageNumber++;
                        requestMovies(mCurrentPageNumber);
                    }
                }
            }
        };
    }
}
