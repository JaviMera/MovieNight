package movienight.javi.com.movienight.ui.MoviesActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.MovieAsyncTask;
import movienight.javi.com.movienight.asyntasks.MoviePageAsyncTaskListener;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class MoviesActivity extends AppCompatActivity implements MoviePageAsyncTaskListener, MovieSelectedListener {

    private Integer mTotalPages;
    private int mCurrentPageNumber;

    @BindView(R.id.movieRecyclerListView) RecyclerView mMovieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);

        MovieRecyclerViewAdapter movieAdapter = new MovieRecyclerViewAdapter(this, new LinkedList<Movie>(){}, this);
        mMovieRecyclerView.setAdapter(movieAdapter);

        final RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mMovieRecyclerView.setLayoutManager(manager);

        mMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager linearManager = (LinearLayoutManager)manager;
                if(linearManager.getItemCount() == linearManager.findLastCompletelyVisibleItemPosition() + 1) {

                    if(mCurrentPageNumber < mTotalPages) {

                        mCurrentPageNumber++;

                        MovieUrlBuilder builder = new MovieUrlBuilder();
                        builder
                                .withPageNumber(String.valueOf(mCurrentPageNumber))
                                .withGenres("12")
                                .withStartReleaseDate("2000-10-1")
                                .withEndReleaseDate("2016-10-1")
                                .withVoteCount("1000")
                                .withRating("5.0");

                        MovieUrl url = builder.createMovieUrl();
                        new MovieAsyncTask((MoviesActivity)recyclerView.getContext()).execute(url);
                    }else {

                        Toast.makeText(recyclerView.getContext(), "No more data to request", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mMovieRecyclerView.setHasFixedSize(true);

        mCurrentPageNumber = 1;
        MovieUrlBuilder builder = new MovieUrlBuilder();
        builder
                .withPageNumber(String.valueOf(mCurrentPageNumber))
                .withGenres("12")
                .withStartReleaseDate("2000-10-1")
                .withEndReleaseDate("2016-10-1")
                .withVoteCount("1000")
                .withRating("5.0");

        MovieUrl url = builder.createMovieUrl();
        new MovieAsyncTask(this).execute(url);
    }

    @Override
    public void onCompleted(Integer totalPages, Page page) {

        if(null == mTotalPages) {

            mTotalPages = totalPages;
        }

        List<Movie> movies;

        MovieRecyclerViewAdapter movieAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        movies = new LinkedList<>(Arrays.asList(page.getMovies()));
        movieAdapter.updateData(movies);
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

        Toast.makeText(this, movie.getOverview(), Toast.LENGTH_SHORT).show();
    }
}

