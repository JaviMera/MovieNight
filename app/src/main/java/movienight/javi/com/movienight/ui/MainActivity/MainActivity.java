package movienight.javi.com.movienight.ui.MainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.GenreAsyncTask;
import movienight.javi.com.movienight.asyntasks.PopularMoviesAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.LoadingFilterDialog;
import movienight.javi.com.movienight.dialogs.MovieDialog.MovieDialogFragment;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;
import movienight.javi.com.movienight.urls.GenreUrl;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class MainActivity extends AppCompatActivity
    implements
        NavigationView.OnNavigationItemSelectedListener,
        MainActivityView,
        MovieSelectedListener,
        MoviesAsyncTaskListener,
        MoviePostersListener,
        AsyncTaskListener<Genre>
    {

    private Map<String, Movie> mMovies;
    private List<Genre> mGenres;
    private LoadingFilterDialog mDialog;
    private MainActivityPresenter mPresenter;
    private ActionBarDrawerToggle mToggle;

    @BindView(R.id.popularMoviesRecyclerView) RecyclerView mMoviesRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolBar;
    @BindView(R.id.navigationView) NavigationView mNavigationView;
    @BindView(R.id.drawerLayout) DrawerLayout mDrawerLayout;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mToggle.syncState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mNavigationView.setNavigationItemSelectedListener(this);

        mMovies = new LinkedHashMap<>();
        mPresenter = new MainActivityPresenter(this);

        mPresenter.setTopMoviesRecyclerViewAdapter(new ArrayList<Movie>(){});
        mPresenter.setTopMoviesRecyclerViewLayoutManager(3, GridLayoutManager.VERTICAL);
        mPresenter.setTopMoviesRecyclerViewSize(true);

        new GenreAsyncTask(getSupportFragmentManager(), this)
            .execute(new GenreUrl());

        mDialog = LoadingFilterDialog.newInstance();
        mDialog.show(getSupportFragmentManager(), "loading_dialog");
    }

    @Override
    public void onCompleted(Integer totalPages, Movie[] movies) {

        mMovies.clear();

        for(Movie movie : movies) {

            mMovies.put(movie.getPosterPath(), movie);
        }

        mPresenter.updateMoviesRecyclerViewAdapter(new ArrayList<>(mMovies.values()));
        mDialog.dismiss();

        Bitmap defaultBitmap = BitmapFactory.decodeResource(
            this.getResources(),
            R.drawable.no_poster_image);

        for(Movie movie : mMovies.values()) {

            new PostersAsyncTask(
                this,
                getSupportFragmentManager(),
                ActivityExtras.POSTER_RESOLUTION_342,
                defaultBitmap
            ).execute(movie.getPosterPath());
        }
    }

    @Override
    public void onPostersCompleted(String path, Bitmap poster) {

        Movie updatedMovie = mMovies.get(path);
        updatedMovie.setPoster(poster);
        MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMoviesRecyclerView.getAdapter();
        adapter.updateMoviePoster(updatedMovie);
    }

    @Override
    public void onMovieSelectedListener(Movie movie) {

        MovieDialogFragment dialog = MovieDialogFragment.newInstance(
            movie,
            Genre.getSelectedGenres(movie.getGenreIds(), mGenres));

        dialog.show(getSupportFragmentManager(), "movie_dialog");
    }

    @OnClick(R.id.searchImageView)
    public void onSearchImageClick(View view) {

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
    public void setTopMoviesRecyclerViewAdapter(List<Movie> items) {

        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(
            this,
            items,
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
    public void updateMoviesRecyclerViewAdapter(List<Movie> movies) {

        MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMoviesRecyclerView.getAdapter();
        adapter.updateData(movies);
    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setChecked(true);

        switch(item.getItemId()) {

            case R.id.movieItemNavigationView:

                break;

            case R.id.tvShowItemNavigationItem:
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}