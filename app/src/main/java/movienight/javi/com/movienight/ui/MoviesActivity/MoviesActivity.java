package movienight.javi.com.movienight.ui.MoviesActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.MoviesFilterAsyncTask;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.MovieRequest;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class MoviesActivity extends AppCompatActivity
        implements MoviesActivityView, MoviesAsyncTaskListener, MovieSelectedListener, MoviePostersListener {

    private Integer mTotalPages;
    private int mCurrentPageNumber;
    private List<Movie> mMovies;
    private MovieRequest mMovieRequest;
    private MoviesActivityPresenter mPresenter;

    private RecyclerView.LayoutManager mRecyclerViewManager;

    @BindView(R.id.moviesSearchRecyclerView) RecyclerView mMovieRecyclerView;
    @BindView(R.id.updateRecyclerViewProgressBar) ProgressBar mMoviesProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);

        mCurrentPageNumber = 1;
        mMovies = new LinkedList<>();
        mPresenter = new MoviesActivityPresenter(this);

        Intent intent = getIntent();
        mMovieRequest = intent.getParcelableExtra(ActivityExtras.MOVIE_REQUEST_KEY);

        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(this, mMovies, this);
        mMovieRecyclerView.setAdapter(adapter);

        mRecyclerViewManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        mPresenter.setRecyclerViewLayoutManager(mRecyclerViewManager);

        mMovieRecyclerView.setHasFixedSize(true);

        mMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager linearManager = (LinearLayoutManager)mRecyclerViewManager;
                if(linearManager.getItemCount() == linearManager.findLastCompletelyVisibleItemPosition() + 1) {

                    if(mCurrentPageNumber < mTotalPages) {

                        mCurrentPageNumber++;
                        mPresenter.setProgressBarVisibility(View.VISIBLE);

                        MovieUrl url = createMovieUrl(mCurrentPageNumber, mMovieRequest);
                        new MoviesFilterAsyncTask((MoviesActivity)recyclerView.getContext()).execute(url);
                    }
                    else {

                        Toast.makeText(recyclerView.getContext(), "No more data to request", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mPresenter.setProgressBarVisibility(View.VISIBLE);

        MovieUrl url = createMovieUrl(mCurrentPageNumber, mMovieRequest);
        new MoviesFilterAsyncTask(this).execute(url);
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        mTotalPages = totalPages;
        mMovies = new LinkedList<>(Arrays.asList(movies));
        List<String> posterPaths = new LinkedList<>();

        for(Movie m : mMovies) {

            posterPaths.add(m.getPosterPath());
        }

        new PostersAsyncTask(this, getSupportFragmentManager())
            .execute(posterPaths.toArray(new String[posterPaths.size()]));
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
    public void updateRecyclerAdapter(List<Movie> movies) {

        MovieRecyclerViewAdapter movieAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movieAdapter.updateData(movies);
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

    @Override
    public void onPostersCompleted(Bitmap[] posters) {

        for(int i = 0 ; i < mMovies.size() ; i++) {

            mMovies.get(i).setPoster(posters[i]);
        }

        mPresenter.updateRecyclerViewAdapter(mMovies);
        mPresenter.setProgressBarVisibility(View.INVISIBLE);
    }
}

