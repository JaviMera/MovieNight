package movienight.javi.com.movienight.fragments;


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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.MovieRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.PopularMoviesAsyncTask;
import movienight.javi.com.movienight.asyntasks.PostersAsyncTask;
import movienight.javi.com.movienight.dialogs.MovieDialog.FilmDialogFragment;
import movienight.javi.com.movienight.listeners.MoviePostersListener;
import movienight.javi.com.movienight.listeners.FilmSelectedListener;
import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.GenreContainer;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.MainActivity;
import movienight.javi.com.movienight.urls.PopularMoviesUrl;

public class HomeFragment extends Fragment implements
        HomeFragmentView,
        FilmSelectedListener,
        FilmAsyncTaskListener,
        MoviePostersListener
    {

    private GenreContainer mGenreContainer;
    private MainActivity mParentActivity;
    private Map<String, Film> mFilms;
    private HomeFragmentPresenter mPresenter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFilms = new LinkedHashMap<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PopularMoviesUrl url = new PopularMoviesUrl();
        new PopularMoviesAsyncTask(this)
                .execute(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, fragmentLayout);

        mPresenter = new HomeFragmentPresenter(this);

        mPresenter.setTopMoviesRecyclerViewAdapter(new ArrayList<Film>(){});
        mPresenter.setTopMoviesRecyclerViewLayoutManager(3, GridLayoutManager.VERTICAL);
        mPresenter.setTopMoviesRecyclerViewSize(true);

        return fragmentLayout;
    }

    @Override
    public void onPostersCompleted(String path, Bitmap poster) {

        Film updatedMovie = mFilms.get(path);
        updatedMovie.setPoster(poster);
        MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMoviesRecyclerView.getAdapter();
        adapter.updateMoviePoster(updatedMovie);
    }

    @Override
    public void setTopMoviesRecyclerViewAdapter(List<Film> films) {

        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(
                mParentActivity,
                films,
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
    public void updateMoviesRecyclerViewAdapter(List<Film> films) {

        MovieRecyclerViewAdapter adapter = (MovieRecyclerViewAdapter) mMoviesRecyclerView.getAdapter();
        adapter.updateData(films);
    }

    @Override
    public void onCompleted(Integer totalPages, Film[] films) {

        mFilms.clear();

        for(Film film : films) {

            mFilms.put(film.getPosterPath(), film);
        }

        mPresenter.updateMoviesRecyclerViewAdapter(new ArrayList<>(mFilms.values()));

        Bitmap defaultBitmap = BitmapFactory.decodeResource(
                this.getResources(),
                R.drawable.no_poster_image);

        for(Film film : mFilms.values()) {

            new PostersAsyncTask(
                    this,
                    mParentActivity.getSupportFragmentManager(),
                    ActivityExtras.POSTER_RESOLUTION_342,
                    defaultBitmap
            ).execute(film.getPosterPath());
        }
    }

    @Override
    public void onFilmSelectedItem(Film film) {

        FilmDialogFragment dialog = FilmDialogFragment.newInstance(
                film,
                mGenreContainer.getGenres(film.getGenres()));

        dialog.show(
                mParentActivity.getSupportFragmentManager(),
                "movie_dialog"
        );
    }
}
