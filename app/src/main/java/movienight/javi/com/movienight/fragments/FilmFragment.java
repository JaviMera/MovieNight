package movienight.javi.com.movienight.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.FilterItemRecyclerAdapter;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.MoviesFilterAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.MovieDialog.FilmDialogFragment;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.FilmSelectedListener;
import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.FilterItemContainer;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.MainActivity;
import movienight.javi.com.movienight.urls.AbstractUrl;

public abstract class FilmFragment extends Fragment implements
        FilterItemRemovedListener,
        FilmFragmentView,
        FilmAsyncTaskListener,
        FilmSelectedListener,
        MoviePostersListener {

    protected MainActivity mParentActivity;
    protected int mCurrentPageNumber;
    protected Map<String, Film> mFilms;
    protected FilmFragmentPresenter mPresenter;
    protected DialogContainer mDialogContainer;
    protected FilterItemContainer mFilterItemContainer;

    private Integer mTotalPages;
    private List<Genre> mGenres;
    private AsyncTask mMovieAsyncTask;

    @BindView(R.id.moviesSearchRecyclerView) RecyclerView mMovieRecyclerView;
    @BindView(R.id.updateRecyclerViewProgressBar) ProgressBar mMoviesProgressBar;
    @BindView(R.id.filtersRecyclerView) RecyclerView mFiltersRecyclerView;

    protected abstract AbstractUrl createUrl(int pageNumber);

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
//        mSortBySpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if(position > 0) {
//
//                    mSortSelected = mSortItemContainer.get(position);
//
//                    mFilms.clear();
//                    mCurrentPageNumber = 1;
//
//                    MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
//                    movieSearchAdapter.removeData();
//
//                    requestFilms(mCurrentPageNumber);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        mPresenter.setFilterItemRecyclerViewAdapter(new FilterableItem[]{});
        mPresenter.setRecyclerViewManager(mFiltersRecyclerView, 1, LinearLayoutManager.HORIZONTAL);
        mPresenter.setRecyclerSize(mFiltersRecyclerView, true);

        mPresenter.setMoviesRecyclerViewAdapter(mFilms.values().toArray(new Film[]{}));
        mPresenter.setRecyclerViewManager(mMovieRecyclerView, 3, LinearLayoutManager.VERTICAL);
        mPresenter.setRecyclerSize(mMovieRecyclerView, true);
        mPresenter.setMovieRecyclerScrollListener(scrollListener());

        return fragmentLayout;
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

            requestFilms(mCurrentPageNumber);
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

    protected void requestFilms(int pageNumber) {

        mPresenter.setProgressBarVisibility(View.VISIBLE);
        AbstractUrl url = createUrl(pageNumber);

        mMovieAsyncTask = new MoviesFilterAsyncTask(this).execute(url);
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
                        requestFilms(mCurrentPageNumber);
                    }
                }
            }
        };
    }
}
