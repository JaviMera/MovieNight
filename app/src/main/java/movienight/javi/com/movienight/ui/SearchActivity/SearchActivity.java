package movienight.javi.com.movienight.ui.SearchActivity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.adapters.DefaultMoviesRecyclerAdapter;
import movienight.javi.com.movienight.adapters.FilterSpinnerAdapter;
import movienight.javi.com.movienight.asyntasks.PopularMoviesAsyncTask;
import movienight.javi.com.movienight.dialogs.DaterangeDialogFragment;
import movienight.javi.com.movienight.dialogs.GenresFragmentDialog;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.RateDialogFragment;
import movienight.javi.com.movienight.dialogs.VoteCountDialogFragment;
import movienight.javi.com.movienight.listeners.DateSelectedListener;
import movienight.javi.com.movienight.listeners.GenresSelectedListener;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.listeners.RateSelectedListener;
import movienight.javi.com.movienight.listeners.VoteCountSelectedListener;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.MovieRequest;
import movienight.javi.com.movienight.model.ReleaseDate;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.MoviesActivity.MoviesActivity;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class SearchActivity extends AppCompatActivity
    implements SearchActivityView, GenresSelectedListener, DateSelectedListener,
    RateSelectedListener, VoteCountSelectedListener, MoviesAsyncTaskListener {

    private List<Genre> mSelectedGenres;
    private Date mStartDate;
    private Date mEndDate;
    private Float mRate;

    private SearchActivityPresenter mPresenter;

    @BindView(R.id.filterMoviesSpinnerView)
    Spinner mFilterMovieSpinner;

    @BindView(R.id.filterItemsRecyclerView)
    RecyclerView mFilterRecyclerView;
    private Integer mVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mSelectedGenres = new ArrayList<>();
        mStartDate = new Date();
        mEndDate = new Date();
        mRate = 0.0f;
        mVoteCount = 0;

        mPresenter = new SearchActivityPresenter(this);

        String[] filterItems = getResources().getStringArray(R.array.filter_options_array);
        mPresenter.setFilterSpinnerAdapter(filterItems);

        mFilterMovieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DialogFragment dialog;

                switch(position) {

                    case 1:
                        dialog = GenresFragmentDialog.newInstance(mSelectedGenres);
                        dialog.show(getSupportFragmentManager(), "genre_dialog");
                        break;

                    case 2:
                        dialog = DaterangeDialogFragment.newInstance(mStartDate, mEndDate);
                        dialog.show(getSupportFragmentManager(), "daterange_dialog");
                        break;

                    case 3:
                        dialog = RateDialogFragment.newInstance(mRate);
                        dialog.show(getSupportFragmentManager(), "rate_dialog");
                        break;

                    case 4:
                        dialog = VoteCountDialogFragment.newInstance(mVoteCount);
                        dialog.show(getSupportFragmentManager(), "votecount_dialog");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
    public void onGenreSelectionCompleted(List<Genre> genres) {

        mFilterMovieSpinner.setSelection(0);

        mSelectedGenres.clear();
        mSelectedGenres = genres;
    }

    @Override
    public void onDateRangePickerDone(Date startDate, Date endDate) {

        mFilterMovieSpinner.setSelection(0);
        mStartDate = startDate;
        mEndDate = endDate;
    }

    @Override
    public void onRateDone(float rate) {

        mFilterMovieSpinner.setSelection(0);
        mRate = rate;
    }

    @Override
    public void onVoteCountDone(Integer voteCount) {

        mFilterMovieSpinner.setSelection(0);
        mVoteCount = voteCount;
    }

    @OnClick(R.id.findMoviesButtonView)
    public void onFindMoviesButtonClick(View view) {

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setGenre(mSelectedGenres.toArray(new Genre[mSelectedGenres.size()]));

        SimpleDateFormat formatter = new SimpleDateFormat(ReleaseDate.FORMAT);

        movieRequest.setStartDateRelease(formatter.format(mStartDate));
        movieRequest.setEndDateReleaseSelected(formatter.format(mEndDate));
        movieRequest.setVoteCount(mVoteCount);
        movieRequest.setRating(mRate);

        Intent intent = new Intent(SearchActivity.this, MoviesActivity.class);
        intent.putExtra(ActivityExtras.MOVIE_REQUEST_KEY, movieRequest);
        startActivity(intent);
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        final DefaultMoviesRecyclerAdapter recyclerAdapter = new DefaultMoviesRecyclerAdapter(this, movies);
        mFilterRecyclerView.setAdapter(recyclerAdapter);

        RecyclerView.LayoutManager gridManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mFilterRecyclerView.setLayoutManager(gridManager);

        mFilterRecyclerView.setHasFixedSize(true);
    }
}