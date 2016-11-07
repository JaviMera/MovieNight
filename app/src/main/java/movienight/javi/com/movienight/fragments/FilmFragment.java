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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.FilterItemRecyclerAdapter;
import movienight.javi.com.movienight.adapters.FilmRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.dialogs.FilmDialog.FilmDialogFragment;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.FilmSelectedListener;
import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.DateRange;
import movienight.javi.com.movienight.model.DialogContainer;
import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.FilterItemContainer;
import movienight.javi.com.movienight.model.FilterItems.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.Genre;
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

    protected MainActivity mParentActivity;
    protected Map<String, FilmBase> mFilms;
    protected FilmFragmentPresenter mPresenter;
    protected DialogContainer mDialogContainer;
    protected List<Genre> mGenres;
    protected int mCurrentPageNumber;

    private boolean isLoading;
    private boolean isFiltering;
    private FilterItemContainer mFilterItemContainer;
    private Integer mTotalPages;
    private AsyncTask mFilmAsyncTask;
    private Bitmap mDefaultPoster;

    protected abstract AbstractUrl createUrl(
        int pageNumber,
        String genreIds,
        String startDate,
        String endDate,
        String rating,
        String voteCount,
        String sort
    );

    protected abstract AsyncTask callPopularFilmAsyncTask(
        FilmAsyncTaskListener listener,
        AbstractUrl url
    );

    protected abstract AsyncTask callFilmAsyncTask(
        FilmAsyncTaskListener listener,
        AbstractUrl url
    );

    protected abstract AbstractUrl getFilmPopularUrl(int pageNumber);

    protected FilterDialogBase getDialog(int position) {

        List<FilterableItem> selectedItems = mFilterItemContainer.get(position);

        return mDialogContainer.getDialog(position, selectedItems);
    }

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

        isLoading = false;
        isFiltering = false;
        mFilms = new LinkedHashMap<>();
        mFilterItemContainer = new FilterItemContainer();
        mCurrentPageNumber = 1;

        setTargetFragment(this, 1);
        setHasOptionsMenu(true);

        if (mParentActivity.isNetworkedConnected()) {

            AbstractUrl url = getFilmPopularUrl(mCurrentPageNumber);
            callPopularFilmAsyncTask(this,url);

        } else {

            mParentActivity.removeFragment(this);
        }
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

        mPresenter.setFilmRecyclerViewAdapter(mFilms.values().toArray(new FilmBase[]{}));
        mPresenter.setRecyclerViewManager(mFilmsRecyclerView, 3, LinearLayoutManager.VERTICAL);
        mPresenter.setRecyclerSize(mFilmsRecyclerView, true);
        mPresenter.setFilmRecyclerScrollListener(scrollListener());

        mDefaultPoster = BitmapFactory.decodeResource(
                mParentActivity.getResources(),
                R.drawable.no_poster_image
        );

        return fragmentLayout;
    }

    @Override
    public void onFilterItemCreated(Integer key, FilterableItem... newItems) {

        isFiltering = true;

        //mFilterMovieSpinner.setSelection(0);
        mFilterItemContainer.put(key, new ArrayList<>(Arrays.asList(newItems)));

        // If the user pressed on the back button on any filter item dialog, then return from method
        // and don't request for movies
        if(key == -1) {
            return;
        }

        if(mFilmAsyncTask != null){

            if(mFilmAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

                mFilmAsyncTask.cancel(true);
                mPresenter.setProgressBarVisibility(View.INVISIBLE);
            }
        }

        mPresenter.updateFilterItemsRecyclerViewAdapter(mFilterItemContainer.getAll());
        FilmRecyclerViewAdapter filmSearchAdapter = (FilmRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
        filmSearchAdapter.removeData();

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

        FilmRecyclerViewAdapter movieSearchAdapter = (FilmRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
        movieSearchAdapter.removeData();

        mCurrentPageNumber = 1;
        mFilms.clear();

        if(mFilmAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

            mFilmAsyncTask.cancel(true);
            mPresenter.setProgressBarVisibility(View.INVISIBLE);
        }

        FilterItemRecyclerAdapter filterItemAdapter = (FilterItemRecyclerAdapter)mFiltersRecyclerView.getAdapter();
        if(filterItemAdapter.getItemCount() == 0) {

            isFiltering = false;
        }

        requestFilms(mCurrentPageNumber);
    }

    @Override
    public void onPostersCompleted(String path, Bitmap poster) {

        if(!mFilms.isEmpty()) {

            FilmBase film = mFilms.get(path);
            film.setPoster(poster);
            mPresenter.updateFilmPoster(film);
            mParentActivity.addBitmapToMemoryCache(path, poster);
        }
    }

    @Override
    public void onFilmSelectedItem(FilmBase film) {

        FilmDialogFragment filmDialogFragment = FilmDialogFragment.newInstance(
                film,
                Genre.getSelectedGenres(film.getGenreIds(), mGenres)
        );

        filmDialogFragment.show(
            mParentActivity.getSupportFragmentManager(),
            "movie_dialog");
    }

    @Override
    public void onCompleted(Integer totalPages, FilmBase[] films) {

        isLoading = false;

        mTotalPages = totalPages;
        mFilms.clear();

        for(FilmBase film : films) {

            mFilms.put(film.getPosterPath(), film);
        }

        mPresenter.setProgressBarVisibility(View.INVISIBLE);
        mPresenter.updateFilmRecyclerViewAdapter(new ArrayList<>(mFilms.values()));

        for(FilmBase film : mFilms.values()) {

            Bitmap bitmap = mParentActivity.getBitmapFromMemoryCache(film.getPosterPath());
            if(bitmap == null){

                new PostersAsyncTask(
                        this,
                        mParentActivity.getSupportFragmentManager(),
                        ActivityExtras.POSTER_RESOLUTION_342,
                        mDefaultPoster
                ).execute(film.getPosterPath());
            }
            else {

                film.setPoster(bitmap);
                mPresenter.updateFilmPoster(film);
            }
        }
    }

    @Override
    public void updateFilmPoster(FilmBase film) {

        FilmRecyclerViewAdapter adapter = (FilmRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
        adapter.updateMoviePoster(film);
    }

    @Override
    public void updateFilterItemsRecyclerViewAdapter(Collection<List<FilterableItem>> items) {

        FilterItemRecyclerAdapter adapter = (FilterItemRecyclerAdapter) mFiltersRecyclerView.getAdapter();
        adapter.updateData(items);
    }

    @Override
    public void setFilmRecyclerViewAdapter(FilmBase[] films) {

        FilmRecyclerViewAdapter movieAdapter = new FilmRecyclerViewAdapter(
                mParentActivity,
                new LinkedList<>(Arrays.asList(films)),
                this);

        mFilmsRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void updateFilmRecyclerAdapter(List<FilmBase> films) {

        FilmRecyclerViewAdapter filmAdapter = (FilmRecyclerViewAdapter) mFilmsRecyclerView.getAdapter();
        filmAdapter.updateData(films);
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
    public void setFilmRecyclerScrollListener(RecyclerView.OnScrollListener listener) {

        mFilmsRecyclerView.addOnScrollListener(listener);
    }

    protected void requestFilms(int pageNumber) {

        if(!mParentActivity.isNetworkedConnected()) {

            mParentActivity.removeFragment(this);
        }

        mPresenter.setProgressBarVisibility(View.VISIBLE);

        AbstractUrl url;

        if(isFiltering) {

            List<FilterableItem> items = mFilterItemContainer.get(FilterableItemKeys.GENRE);
            String genreIds = getFilterIds(items);

            items = mFilterItemContainer.get(FilterableItemKeys.DATE_RANGE);
            String[] dates = getFilterDates(items);

            items = mFilterItemContainer.get(FilterableItemKeys.RATE);
            String rate = getFilterRate(items);

            items = mFilterItemContainer.get(FilterableItemKeys.VOTE_COUNT);
            String voteCount = getFilterVoteCount(items);

            items = mFilterItemContainer.get(FilterableItemKeys.SORT);
            String sort = getSort(items);

            url = createUrl(
                pageNumber,
                genreIds,
                dates[DateRange.START],
                dates[DateRange.END],
                rate,
                voteCount,
                sort
            );

            mFilmAsyncTask = callFilmAsyncTask(this, url);
        }
        else {

            url = getFilmPopularUrl(mCurrentPageNumber);
            mFilmAsyncTask = callPopularFilmAsyncTask(this, url);
        }
    }

    private RecyclerView.OnScrollListener scrollListener() {

        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            GridLayoutManager linearManager = (GridLayoutManager) recyclerView.getLayoutManager();

            if(!mParentActivity.isNetworkedConnected()) {

                mParentActivity.removeFragment(getTargetFragment());
                return;
            }

            if(isLoading)
                return;

            int itemCount = linearManager.getItemCount();
            // Check if the scroll happened when the adapter's data was cleared
            // In such case, we don't want to call the endless scroll code.
            if (itemCount == 0)
                return;

            int lastPositionVisible = linearManager.findLastCompletelyVisibleItemPosition();
            if (itemCount == lastPositionVisible + 1) {

                if (mCurrentPageNumber < mTotalPages) {

                    isLoading = true;
                    mCurrentPageNumber++;
                    requestFilms(mCurrentPageNumber);
                }
            }
            }
        };
    }

    private String getFilterIds(List<FilterableItem> items) {

        String genresIds = "";

        for(int i = 0 ; i < items.size() ; i++ ) {

            if(i == items.size() - 1) {
                genresIds += ((Genre)items.get(i).getValue()[0]).getId();
            }
            else {
                genresIds += ((Genre)items.get(i).getValue()[0]).getId() + ",";
            }
        }

        return genresIds;
    }

    private String[] getFilterDates (List<FilterableItem> items) {

        SimpleDateFormat formatter = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT);
        String startDate = "";
        String endDate = "";

        if(!items.isEmpty()) {

            Date[] dates = ((DateRangeFilterableItem)items.get(0)).getValue();
            startDate = formatter.format(dates[DateRange.START]);
            endDate = formatter.format(dates[DateRange.END]);
        }

        return new String[]{startDate, endDate};
    }

    private String getFilterRate(List<FilterableItem> items) {

        Float rateSelected = null;

        for(FilterableItem item : items) {

            rateSelected = (Float)item.getValue()[0];
        }

        return rateSelected == null
            ? ""
            : String.valueOf(rateSelected);
    }

    private String getFilterVoteCount(List<FilterableItem> items) {

        Integer votesCount = null;

        for(FilterableItem item : mFilterItemContainer.get(FilterableItemKeys.VOTE_COUNT)) {

            votesCount = (Integer)item.getValue()[0];
        }

        return votesCount == null
            ? ""
            : String.valueOf(votesCount);
    }

    private String getSort(List<FilterableItem> items) {

        String sortOption = "";

        for(FilterableItem item : mFilterItemContainer.get(FilterableItemKeys.SORT)) {

            sortOption = (String) item.getValue()[0];
        }

        return sortOption;
    }
}
