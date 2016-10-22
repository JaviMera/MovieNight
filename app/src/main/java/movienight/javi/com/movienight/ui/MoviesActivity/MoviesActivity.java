package movienight.javi.com.movienight.ui.MoviesActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.adapters.PageSpinnerAdapter;
import movienight.javi.com.movienight.asyntasks.MovieAsyncTask;
import movienight.javi.com.movienight.asyntasks.MoviePageAsyncTaskListener;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class MoviesActivity extends AppCompatActivity implements MoviePageAsyncTaskListener, MovieSelectedListener {

    private Map<Integer, Page> mMapPages;
    private Integer mTotalPages;

    @BindView(R.id.pageSpinnerView) Spinner mPageSpinnerView;
    @BindView(R.id.movieRecyclerListView) RecyclerView mMovieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);
        mMapPages = new LinkedHashMap<>();

        mPageSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pageNumber = position + 1;

                Page pageSelected = mMapPages.get(pageNumber);

                if(null == pageSelected) {

                    MovieUrlBuilder builder = new MovieUrlBuilder();
                    builder
                            .withPageNumber(pageNumber + "")
                            .withGenres("12")
                            .withStartReleaseDate("2000-10-1")
                            .withEndReleaseDate("2016-10-1")
                            .withVoteCount("1000")
                            .withRating("5.0");

                    MovieUrl url = builder.createMovieUrl();
                    MoviesActivity activity = (MoviesActivity)parent.getContext();
                    new MovieAsyncTask(activity).execute(url);
                }
                else {

                    MovieRecyclerViewAdapter movieAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();

                    LinkedList<Movie> movies = new LinkedList<>(Arrays.asList(pageSelected.getMovies()));
                    movieAdapter.updateData(movies);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MovieUrlBuilder builder = new MovieUrlBuilder();
        builder
                .withPageNumber("1")
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

        MovieRecyclerViewAdapter movieAdapter;
        List<Movie> movies;

        if(mMapPages.isEmpty()){

            mTotalPages = totalPages;
            String[] spinnerItems = new String[mTotalPages];

            for(int p = 0 ; p < mTotalPages ; p++) {

                spinnerItems[p] = "Page " + (p + 1);
            }

            movieAdapter = new MovieRecyclerViewAdapter(this, new LinkedList<Movie>(){}, this);
            mMovieRecyclerView.setAdapter(movieAdapter);

            PageSpinnerAdapter adapter = new PageSpinnerAdapter(this, spinnerItems);
            mPageSpinnerView.setAdapter(adapter);

        }
        else {

            movieAdapter = (MovieRecyclerViewAdapter)mMovieRecyclerView.getAdapter();
        }

        mMapPages.put(page.getNumber(), page);

        movies = new LinkedList<>(Arrays.asList(page.getMovies()));
        movieAdapter.updateData(movies);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mMovieRecyclerView.setLayoutManager(manager);

        mMovieRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

        Toast.makeText(this, movie.getOverview(), Toast.LENGTH_SHORT).show();
    }
}

