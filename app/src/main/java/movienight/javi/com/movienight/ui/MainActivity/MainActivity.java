package movienight.javi.com.movienight.ui.MainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.GenreAsyncTask;
import movienight.javi.com.movienight.asyntasks.PopularMoviesAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.LoadingFilterDialog;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;
import movienight.javi.com.movienight.urls.GenreUrl;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class MainActivity extends AppCompatActivity
    implements
        MainActivityView,
        MovieSelectedListener,
        MoviesAsyncTaskListener,
        MoviePostersListener,
        AsyncTaskListener<Genre>
    {

    private Movie[] mMovies;
    private List<Genre> mGenres;
    private LoadingFilterDialog mDialog;
    private MainActivityPresenter mPresenter;

    @BindView(R.id.popularMoviesRecyclerView) RecyclerView mMoviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPresenter = new MainActivityPresenter(this);

        mPresenter.setTopMoviesRecyclerViewAdapter(new Movie[]{});
        mPresenter.setTopMoviesRecyclerViewLayoutManager(2, GridLayoutManager.VERTICAL);
        mPresenter.setTopMoviesRecyclerViewSize(true);

        new GenreAsyncTask(getSupportFragmentManager(), this)
            .execute(new GenreUrl());

        mDialog = LoadingFilterDialog.newInstance();
        mDialog.show(getSupportFragmentManager(), "loading_dialog");
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        mMovies = movies;

        String[] posterPaths = new String[movies.length];
        for(int i = 0 ; i < movies.length ; i++) {

            posterPaths[i] = movies[i].getPosterPath();
        }

        new PostersAsyncTask(this, getSupportFragmentManager(), ActivityExtras.POSTER_RESOLUTION_500).execute(posterPaths);
    }

    @Override
    public void onPostersCompleted(Bitmap[] posters) {

        for(int i = 0 ; i < mMovies.length ; i++) {

            mMovies[i].setPoster(posters[i]);
        }

        mPresenter.updateMoviesRecyclerViewAdapter(mMovies);
        mDialog.dismiss();
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

    }

    @OnClick(R.id.findMoviesButtonView)
    public void onFindMoviesButtonClick(View view) {

        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putParcelableArrayListExtra(ActivityExtras.GENRES_KEY, (ArrayList)mGenres);

        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(Genre[] result) {

        mGenres = new ArrayList<>(Arrays.asList(result));

        PopularMoviesUrl url = new PopularMoviesUrl();
        new PopularMoviesAsyncTask(this)
                .execute(url);
    }

    @Override
    public void setTopMoviesRecyclerViewAdapter(Movie[] items) {

        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(
            this,
            new ArrayList(Arrays.asList(items)),
            this);

        mMoviesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setTopMoviesRecyclerViewLayoutManager(int numberOfColumns, int orientation) {

        final RecyclerView.LayoutManager movieRecyclerLayout = new GridLayoutManager(
            this,
            numberOfColumns,
            orientation,
            false);

        mMoviesRecyclerView.setLayoutManager(movieRecyclerLayout);
    }

    @Override
    public void setTopMoviesRecyclerViewSize(boolean fixedSize) {

        mMoviesRecyclerView.setHasFixedSize(fixedSize);
    }

    @Override
    public void updateMoviesRecyclerViewAdapter(Movie[] movies) {

        MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMoviesRecyclerView.getAdapter();
        adapter.updateData(new ArrayList(Arrays.asList(movies)));
    }
}