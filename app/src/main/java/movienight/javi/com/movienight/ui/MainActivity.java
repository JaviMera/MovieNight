package movienight.javi.com.movienight.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.asyntasks.MovieGenresAsyncTask;
import movienight.javi.com.movienight.asyntasks.TVShowGenresAsyncTask;
import movienight.javi.com.movienight.dialogs.LoadingFilterDialog;
import movienight.javi.com.movienight.fragments.MovieFragment;
import movienight.javi.com.movienight.fragments.TVShowFragment;
import movienight.javi.com.movienight.model.FilmCatetory;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.urls.MovieGenreUrl;
import movienight.javi.com.movienight.urls.TVShowGenreUrl;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        GenreListener
    {

    private ActionBarDrawerToggle mToggle;
    private FragmentManager mFragmentManager;
    private List<Genre> mMovieGenres;
    private List<Genre> mTVShowGenres;
    private int mGenresCount;
    private String[] mMovieSortItems;
    private String[] mTVShowSortItems;

    @BindView(R.id.toolbar) Toolbar mToolBar;
    @BindView(R.id.navigationView) NavigationView mNavigationView;
    @BindView(R.id.drawerLayout) DrawerLayout mDrawerLayout;
    private LoadingFilterDialog mDialog;

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

        mFragmentManager = getSupportFragmentManager();

        mMovieSortItems = getResources().getStringArray(R.array.movie_sort_options_array);
        mTVShowSortItems = getResources().getStringArray(R.array.tv_show_sort_options_array);

        mGenresCount = 0;
        new MovieGenresAsyncTask(this).execute(new MovieGenreUrl());
        new TVShowGenresAsyncTask(this).execute(new TVShowGenreUrl());

        mDialog = LoadingFilterDialog.newInstance();
        mDialog.show(mFragmentManager, "loading_dialog");
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

        Fragment fragment = null;

        switch(item.getItemId()) {

            case R.id.movieItemNavigationView:

                mToolBar.setTitle("Movies");
                fragment = MovieFragment.newInstance(mMovieGenres, mMovieSortItems);

                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                break;

            case R.id.tvShowItemNavigationItem:

                mToolBar.setTitle("TV Shows");
                fragment = TVShowFragment.newInstance(mTVShowGenres, mTVShowSortItems);

                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

        @Override
        public void onTaskCompleted(Integer category, Genre[] result) {

            if(category.equals(FilmCatetory.MOVIE)) {

                mMovieGenres = new ArrayList<>(Arrays.asList(result));
                mGenresCount++;
            }
            else if(category.equals(FilmCatetory.TV_SHOW)) {

                mTVShowGenres = new ArrayList<>(Arrays.asList(result));
                mGenresCount++;
            }

            if(mGenresCount == 2) {

                onNavigationItemSelected(
                        mNavigationView.getMenu().getItem(0)
                );
                mDialog.dismiss();
            }
        }
    }