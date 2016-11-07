package movienight.javi.com.movienight;

import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.fragments.FilmFragmentPresenter;
import movienight.javi.com.movienight.fragments.FilmFragmentView;

/**
 * Created by Javi on 10/22/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class FilmFragmentPresenterTest {

    private FilmFragmentPresenter mPresenter;

    @Mock
    public FilmFragmentView mView;

    @Before
    public void SetUp() throws Exception {

        mPresenter = new FilmFragmentPresenter(mView);
    }

    @Test
    public void setFilterItemRecyclerAdapter() throws Exception {

        // Arrange
        FilterableItem[] items = null;

        // Act
        mPresenter.setFilterItemRecyclerViewAdapter(items);

        // Assert
        Mockito.verify(mView).setFilterItemRecyclerViewAdapter(items);
    }

    @Test
    public void setFilmRecyclerViewAdapter() throws Exception {

        // Arrange
        FilmBase[] films = new Movie[]{FakeMovies.get(1)};

        // Act
        mPresenter.setFilmRecyclerViewAdapter(films);

        // Assert
        Mockito.verify(mView).setFilmRecyclerViewAdapter(films);
    }

    @Test
    public void updateFilmRecyclerViewAdapter() throws Exception {

        // Arrange
        List<FilmBase> movies = new LinkedList<>();

        // Act
        mPresenter.updateFilmRecyclerViewAdapter(movies);

        // Assert
        Mockito.verify(mView).updateFilmRecyclerAdapter(movies);
    }

    @Test
    public void updateFilterItemsRecyclerViewAdapter() throws Exception {

        // Arrange
        Collection<List<FilterableItem>> items = null;

        // Act
        mPresenter.updateFilterItemsRecyclerViewAdapter(items);

        // Assert
        Mockito.verify(mView).updateFilterItemsRecyclerViewAdapter(items);
    }

    @Test
    public void setViewProgressBarVisibility() throws Exception {

        // Arrange
        int someVisibility = 1;

        // Act
        mPresenter.setProgressBarVisibility(someVisibility);

        // // Assert
        Mockito.verify(mView).setProgressBarVisibility(someVisibility);
    }

    @Test
    public void setRecyclerViewManager() throws Exception {

        // Arrange
        int numberOfColumns = 1;
        int orientation = 1;
        RecyclerView view = null;

        // Act
        mPresenter.setRecyclerViewManager(view, numberOfColumns, orientation);

        // Assert
        Mockito.verify(mView).setRecyclerViewManager(view, numberOfColumns, orientation);
    }

    @Test
    public void setRecyclerViewSize() throws Exception {

        // Arrange
        RecyclerView view = null;
        boolean fixedSize = true;

        // Act
        mPresenter.setRecyclerSize(view, fixedSize);

        // Assert
        Mockito.verify(mView).setRecyclerSize(view, fixedSize);
    }

    @Test
    public void setFilmRecyclerScrollListener() throws Exception {

        // Arrange
        RecyclerView.OnScrollListener listener = null;

        // Act
        mPresenter.setFilmRecyclerScrollListener(listener);

        // Assert
        Mockito.verify(mView).setFilmRecyclerScrollListener(listener);
    }

    @Test
    public void updateFilmPosterRecyclerViewAdapter() throws Exception {

        // Arrange
        FilmBase film = null;

        // Act
        mPresenter.updateFilmPoster(film);

        // Assert
        Mockito.verify(mView).updateFilmPoster(film);
    }
}
