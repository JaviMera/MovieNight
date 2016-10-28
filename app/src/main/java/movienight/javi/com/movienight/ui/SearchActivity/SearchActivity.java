package movienight.javi.com.movienight.ui.SearchActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.adapters.FilterItemRecyclerAdapter;
import movienight.javi.com.movienight.adapters.FilterSpinnerAdapter;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.PopularMoviesAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.DaterangeDialogFragment;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.GenresFragmentDialog;
import movienight.javi.com.movienight.dialogs.RateDialogFragment;
import movienight.javi.com.movienight.dialogs.VoteCountDialogFragment;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.FilterableItemKeys;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.GenreFilterableItem;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.MovieRequest;
import movienight.javi.com.movienight.model.RateFilterableItem;
import movienight.javi.com.movienight.model.VoteCountFilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.MoviesActivity.MoviesActivity;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class SearchActivity extends AppCompatActivity
    implements SearchActivityView, MovieSelectedListener, MoviesAsyncTaskListener, FilterItemAddedListener, FilterItemRemovedListener, MoviePostersListener{

    private Movie[] mMovies;
    private Map<Integer, List<FilterableItem>> mFilters;
    private SearchActivityPresenter mPresenter;

    @BindView(R.id.filterMoviesSpinnerView)
    Spinner mFilterMovieSpinner;

    @BindView(R.id.moviesGridView)
    RecyclerView mMoviesRecyclerView;

    @BindView(R.id.filtersRecyclerView)
    RecyclerView mFiltersRecyclerView;

    @BindView(R.id.popularMoviesProgressBar)
    ProgressBar mPopularMoviesProgressBar;

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

        RecyclerView.LayoutManager filterRecyclerLayout = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mFiltersRecyclerView.setLayoutManager(filterRecyclerLayout);

        mFiltersRecyclerView.setHasFixedSize(true);

        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(this, new ArrayList<Movie>(), this);
        mMoviesRecyclerView.setAdapter(adapter);

        final RecyclerView.LayoutManager movieRecyclerLayout = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mMoviesRecyclerView.setLayoutManager(movieRecyclerLayout);

        mPopularMoviesProgressBar.setVisibility(View.VISIBLE);

        mMoviesRecyclerView.setHasFixedSize(true);
        PopularMoviesUrl url = new PopularMoviesUrl();
            new PopularMoviesAsyncTask(this)
                .execute(url);
    }

    @Override
    public void setFilterSpinnerAdapter(String[] items) {

        FilterSpinnerAdapter adapter = new FilterSpinnerAdapter(this, new LinkedList<>(Arrays.asList(items)));
        mFilterMovieSpinner.setAdapter(adapter);
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        mMovies = movies;

        String[] posterPaths = new String[movies.length];
        for(int i = 0 ; i < movies.length ; i++) {

            posterPaths[i] = movies[i].getPosterPath();
        }

        new PostersAsyncTask(this, getSupportFragmentManager()).execute(posterPaths);
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

    @Override
    public void onPostersCompleted(Bitmap[] posters) {

        for(int i = 0 ; i < mMovies.length ; i++) {

            mMovies[i].setPoster(posters[i]);
        }

        mPopularMoviesProgressBar.setVisibility(View.INVISIBLE);

        MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMoviesRecyclerView.getAdapter();
        adapter.updateData(new ArrayList(Arrays.asList(mMovies)));
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

    }

    @OnClick(R.id.findMoviesButtonView)
    public void onFindMoviesButtonClick(View view) {

        MovieRequest request = new MovieRequest();

        List<FilterableItem> genresSelected = mFilters.get(FilterableItemKeys.GENRE);

        if(!genresSelected.isEmpty()) {

            List<Genre> genres = new ArrayList<>();
            for(FilterableItem item : genresSelected) {

                GenreFilterableItem genre = (GenreFilterableItem)item;
                    genres.add(genre.getObject());
            }

            request.setGenre(genres.toArray(new Genre[genres.size()]));
        }
        else {

            request.setGenre(new Genre[]{});
        }

        List<FilterableItem> datesSelected = mFilters.get(FilterableItemKeys.DATE_RANGE);

        if(!datesSelected.isEmpty()) {

            DateRangeFilterableItem item = (DateRangeFilterableItem)datesSelected.get(0);
            SimpleDateFormat formatter = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT);
            request.setStartReleasedDate(formatter.format(item.getStartDate()));
            request.setEndReleaseDate(formatter.format(item.getEndDate()));
        }
        else {

            request.setStartReleasedDate("");
            request.setEndReleaseDate("");
        }

        List<FilterableItem> rateSelected = mFilters.get(FilterableItemKeys.RATE);

        if(!rateSelected.isEmpty()) {

            RateFilterableItem item = (RateFilterableItem)rateSelected.get(0);
            request.setRating(item.getObject());
        }
        else
            request.setRating(-1.0f);

        List<FilterableItem> voteCountSelected = mFilters.get(FilterableItemKeys.VOTE_COUNT);

        if(!voteCountSelected.isEmpty()) {

            VoteCountFilterableItem item = (VoteCountFilterableItem)voteCountSelected.get(0);
            request.setVoteCount(item.getObject());
        }
        else
            request.setVoteCount(-1);

        Intent intent = new Intent(SearchActivity.this, MoviesActivity.class);
        intent.putExtra(ActivityExtras.MOVIE_REQUEST_KEY, request);

        startActivity(intent);
    }
}
