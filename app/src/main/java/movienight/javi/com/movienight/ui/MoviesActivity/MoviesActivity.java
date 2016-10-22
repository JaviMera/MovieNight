package movienight.javi.com.movienight.ui.MoviesActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.asyntasks.MoviePageAsyncTask;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class MoviesActivity extends AppCompatActivity implements AsyncTaskListener<Page>{

    private List<Page> mMoviePages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mMoviePages = new ArrayList<>();

        MovieUrlBuilder builder = new MovieUrlBuilder();
        builder
                .withPageNumber("1")
                .withGenres("12")
                .withStartReleaseDate("2015-10-1")
                .withEndReleaseDate("2016-10-1")
                .withVoteCount("1000")
                .withRating("5.0");

        MovieUrl url = builder.createMovieUrl();

        new MoviePageAsyncTask(this).execute(url);
    }

    @Override
    public void onTaskCompleted(Page[] result) {

        mMoviePages.add(result[0]);
    }
}

