package movienight.javi.com.movienight.ui.MoviesActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.MovieAsyncTask;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.MovieRequest;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class MoviesActivity extends AppCompatActivity implements MoviesActivityView, MoviesAsyncTaskListener, MovieSelectedListener {

    private Integer mTotalPages;
    private int mCurrentPageNumber;
    private MovieRequest mMovieRequest;
    private MoviesActivityPresenter mPresenter;

    private RecyclerView.LayoutManager mRecyclerViewManager;

    @BindView(R.id.movieRecyclerListView) RecyclerView mMovieRecyclerView;
    @BindView(R.id.updateRecyclerViewProgressBar) ProgressBar mMoviesProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);

        mPresenter = new MoviesActivityPresenter(this);

        Intent intent = getIntent();
        mMovieRequest = intent.getParcelableExtra(ActivityExtras.MOVIE_REQUEST_KEY);

        mRecyclerViewManager = new LinearLayoutManager(this);
        mPresenter.setRecyclerViewLayoutManager(mRecyclerViewManager);

        mMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager linearManager = (LinearLayoutManager)mRecyclerViewManager;
                if(linearManager.getItemCount() == linearManager.findLastCompletelyVisibleItemPosition() + 1) {

                    if(mCurrentPageNumber < mTotalPages) {

                        mCurrentPageNumber++;
                        mPresenter.setProgressBarVisibility(View.VISIBLE);

                        MovieUrl url = createMovieUrl(mCurrentPageNumber, mMovieRequest);
                        new MovieAsyncTask((MoviesActivity)recyclerView.getContext()).execute(url);
                    }
                    else {

                        Toast.makeText(recyclerView.getContext(), "No more data to request", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mMovieRecyclerView.setHasFixedSize(true);

        mCurrentPageNumber = 1;
        mPresenter.setProgressBarVisibility(View.VISIBLE);

        MovieUrl url = createMovieUrl(mCurrentPageNumber, mMovieRequest);
        new MovieAsyncTask(this).execute(url);
    }

    @Override
    public void onCompleted(Integer totalPages, Page page) {

        if(null == mTotalPages) {

            mTotalPages = totalPages;
            mPresenter.setRecyclerViewAdapter(this, page.getMovies(), this);
        }
        else {

            mPresenter.updateRecyclerViewAdapter(page.getMovies());
        }

        mPresenter.setProgressBarVisibility(View.INVISIBLE);
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

        Toast.makeText(this, movie.getOverview(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRecyclerViewManager(RecyclerView.LayoutManager manager) {

        mMovieRecyclerView.setLayoutManager(mRecyclerViewManager);
    }

    @Override
    public void setRecyclerViewAdapter(Context context, Movie[] movies, MovieSelectedListener listener) {

        MovieRecyclerViewAdapter movieAdapter = new MovieRecyclerViewAdapter(
            this,
            new LinkedList<>(Arrays.asList(movies)),
            this);

        mMovieRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void updateRecyclerAdapter(Movie[] movies) {

        MovieRecyclerViewAdapter movieAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieAdapter.updateData(new LinkedList<>(Arrays.asList(movies)));
    }

    @Override
    public void setProgressBarVisibility(int visibility) {

        mMoviesProgressBar.setVisibility(visibility);
    }

    private MovieUrl createMovieUrl(Integer pageNumber, MovieRequest request) {

        String page = String.valueOf(pageNumber);
        String startDate = request.getStartDateReleaseSelected();
        String endDate = request.getEndDateReleaseSelected();
        String voteCount = String.valueOf(request.getVoteCountSelected());
        String rating = String.valueOf(request.getRatingSelected());
        String genresIds = parseGenresIds(request.getGenreSelected());

        MovieUrlBuilder builder = new MovieUrlBuilder();
        builder
            .withPageNumber(page)
            .withStartReleaseDate(startDate)
            .withEndReleaseDate(endDate)
            .withVoteCount(voteCount)
            .withRating(rating)
            .withGenres(genresIds);

        return builder.createMovieUrl();
    }

    private String parseGenresIds(Genre[] genres) {

        String genresIds = "";
        for(int i = 0 ; i < genres.length ; i++) {

            Genre currentGenre = genres[i];
            if(i == genres.length - 1) {

                genresIds += String.valueOf(currentGenre.getId());
            }
            else {

                genresIds += String.valueOf(currentGenre.getId()) + ",";
            }
        }

        return genresIds;
    }
}

