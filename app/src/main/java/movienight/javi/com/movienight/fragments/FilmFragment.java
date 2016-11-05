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
import movienight.javi.com.movienight.asyntasks.MoviesAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.asyntasks.TVShowAsyncTask;
import movienight.javi.com.movienight.dialogs.MovieDialog.FilmDialogFragment;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.FilmSelectedListener;
import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.FilterItemContainer;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.MainActivity;
import movienight.javi.com.movienight.urls.AbstractUrl;

public abstract class FilmFragment extends Fragment implements
        FilterItemAddedListener,
        FilterItemRemovedListener,
        FilmFragmentView,
        FilmAsyncTaskListener,
        FilmSelectedListener,
        MoviePostersListener {

    protected int category;
    protected MainActivity mParentActivity;
    protected int mCurrentPageNumber;
    protected Map<String, FilmBase> mFilms;
    protected FilmFragmentPresenter mPresenter;
    protected DialogContainer mDialogContainer;
    protected FilterItemContainer mFilterItemContainer;
    protected List<Genre> mGenres;

    private Integer mTotalPages;
    private AsyncTask mFilmAsyncTask;

    protected abstract AbstractUrl createUrl(int pageNumber);

    @BindView(R.id.filmsRecyclerView) RecyclerView mFilmsRecyclerView;
    @BindView(R.id.updateRecyclerViewProgressBar) ProgressBar mMoviesProgressBar;
    @BindView(R.id.filtersRecyclerView) RecyclerView mFiltersRecyclerView;

    public FilmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParentActivity = (MainActivity)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mPresenter.setFilterItemRecyclerViewAdapter(new FilterableItem[]{});
        mPresenter.setRecyclerViewManager(mFiltersRecyclerView, 1, LinearLayoutManager.HORIZONTAL);
        mPresenter.setRecyclerSize(mFiltersRecyclerView, true);

        mPresenter.setMoviesRecyclerViewAdapter(mFilms.values().toArray(new FilmBase[]{}));
        mPresenter.setRecyclerViewManager(mFilmsRecyclerView, 3, LinearLayoutManager.VERTICAL);
        mPresenter.setRecyclerSize(mFilmsRecyclerView, true);
        mPresenter.setMovieRecyclerScrollListener(scrollListener());

        return fragmentLayout;
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

        if(mFilmAsyncTask != null)
            if(mFilmAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

                mFilmAsyncTask.cancel(true);
                mPresenter.setProgressBarVisibility(View.INVISIBLE);
            }


        FilterItemRecyclerAdapter adapter = (FilterItemRecyclerAdapter) mFiltersRecyclerView.getAdapter();
        adapter.updateData(mFilterItemContainer.getAll());

        MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
        movieSearchAdapter.removeData();

        mFilms.clear();
        mCurrentPageNumber = 1;
        requestFilms(mCurrentPageNumber);
    }

    @Override
    public void onFilterItemDeleted(FilterableItem itemRemoved) {

        for(List<FilterableItem> items : mFilterItemContainer.getAll()) {

            if(items.contains(itemRemoved)) {

                items.remove(itemRemoved);
                break;
            }
        }

        MovieRecyclerViewAdapter movieSearchAdapter = (MovieRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
        movieSearchAdapter.removeData();

        mCurrentPageNumber = 1;
        mFilms.clear();

        if(mFilmAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

            mFilmAsyncTask.cancel(true);
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

            FilmBase updatedMovie = mFilms.get(path);
            updatedMovie.setPoster(poster);
            MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
            adapter.updateMoviePoster(updatedMovie);
        }
    }

    @Override
    public void onFilmSelectedItem(FilmBase film) {

        FilmDialogFragment filmDialogFragment = FilmDialogFragment.newInstance(
                film,
                getGenres(film.getGenreIds())
        );

        filmDialogFragment.show(
            mParentActivity.getSupportFragmentManager(),
            "movie_dialog");
    }

    @Override
    public void onCompleted(Integer totalPages, FilmBase[] films) {

        mTotalPages = totalPages;
        mFilms.clear();

        for(FilmBase film : films) {

            mFilms.put(film.getPosterPath(), film);
        }

        mPresenter.setProgressBarVisibility(View.INVISIBLE);
        mPresenter.updateRecyclerViewAdapter(new ArrayList<>(mFilms.values()));

        Bitmap defaultBitmap = BitmapFactory.decodeResource(
                this.getResources(),
                R.drawable.no_poster_image
        );

        for(FilmBase film : mFilms.values()) {

            new PostersAsyncTask(
                    this,
                    mParentActivity.getSupportFragmentManager(),
                    ActivityExtras.POSTER_RESOLUTION_342,
                    defaultBitmap
            ).execute(film.getPosterPath());
        }
    }

    @Override
    public void setMoviesRecyclerViewAdapter(FilmBase[] films) {

        MovieRecyclerViewAdapter movieAdapter = new MovieRecyclerViewAdapter(
                mParentActivity,
                new LinkedList<>(Arrays.asList(films)),
                this);

        mFilmsRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void updateRecyclerAdapter(List<FilmBase> films) {

        MovieRecyclerViewAdapter movieAdapter = (MovieRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
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

        mFilmsRecyclerView.addOnScrollListener(listener);
    }

    protected void requestFilms(int pageNumber) {

        mPresenter.setProgressBarVisibility(View.VISIBLE);
        AbstractUrl url = createUrl(pageNumber);

        if(category == 0)
            mFilmAsyncTask = new MoviesAsyncTask(this).execute(url);
        else if(category == 1){
            mFilmAsyncTask = new TVShowAsyncTask(this).execute(url);
        }
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

    private List<String> getGenres(int[] genreIds) {

        List<String> genreDescriptions = new ArrayList<>();

        for(Integer id : genreIds) {

            for(Genre genre : mGenres) {

                if(genre.getId().equals(id)) {

                    genreDescriptions.add(genre.getDescription());
                    break;
                }
            }
        }

        return genreDescriptions;
    }
}
