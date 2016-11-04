package movienight.javi.com.movienight.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.GenreAsyncTask;
import movienight.javi.com.movienight.asyntasks.PopularMoviesAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.LoadingFilterDialog;
import movienight.javi.com.movienight.dialogs.MovieDialog.MovieDialogFragment;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.ui.MainActivity.MainActivity;
import movienight.javi.com.movienight.ui.MainActivity.MainActivityPresenter;
import movienight.javi.com.movienight.ui.MainActivity.MainActivityView;
import movienight.javi.com.movienight.urls.GenreUrl;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class HomeFragment extends Fragment implements
        MainActivityView,
        MovieSelectedListener,
        MoviesAsyncTaskListener,
        MoviePostersListener,
        AsyncTaskListener<Genre> {

    private MainActivity mParentActivity;
    private Map<String, Movie> mMovies;
    private List<Genre> mGenres;
    private LoadingFilterDialog mDialog;
    private MainActivityPresenter mPresenter;

    @BindView(R.id.popularMoviesRecyclerView)
    RecyclerView mMoviesRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParentActivity = (MainActivity)getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMovies = new LinkedHashMap<>();
        mPresenter = new MainActivityPresenter(this);

        mPresenter.setTopMoviesRecyclerViewAdapter(new ArrayList<Movie>(){});
        mPresenter.setTopMoviesRecyclerViewLayoutManager(3, GridLayoutManager.VERTICAL);
        mPresenter.setTopMoviesRecyclerViewSize(true);

        new GenreAsyncTask(mParentActivity.getSupportFragmentManager(), this)
                .execute(new GenreUrl());

        mDialog = LoadingFilterDialog.newInstance();
        mDialog.show(
            mParentActivity.getSupportFragmentManager(),
            "loading_dialog"
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, fragmentLayout);

        return fragmentLayout;
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

        dialog.show(
            mParentActivity.getSupportFragmentManager(),
            "movie_dialog"
        );
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
                    mParentActivity.getSupportFragmentManager(),
                    ActivityExtras.POSTER_RESOLUTION_342,
                    defaultBitmap
            ).execute(movie.getPosterPath());
        }
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
                mParentActivity,
                items,
                this);

        mMoviesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setTopMoviesRecyclerViewLayoutManager(int numberOfColumns, int orientation) {

        final RecyclerView.LayoutManager movieRecyclerLayout = new GridLayoutManager(
                mParentActivity,
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
}
