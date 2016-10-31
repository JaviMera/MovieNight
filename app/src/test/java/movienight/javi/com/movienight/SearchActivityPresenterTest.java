package movienight.javi.com.movienight;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;
import movienight.javi.com.movienight.ui.SearchActivity.MoviesActivityPresenter;
import movienight.javi.com.movienight.ui.SearchActivity.MoviesActivityView;

/**
 * Created by Javi on 10/22/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class SearchActivityPresenterTest {

    private MoviesActivityPresenter mPresenter;

    @Mock
    public MoviesActivityView mView;

    @Before
    public void SetUp() throws Exception {

        mPresenter = new MoviesActivityPresenter(mView);
    }

    @Test
    public void setRecyclerViewAdapter() throws Exception {

        // Arrange
        Context context = null;
        Movie[] movies = new Movie[]{FakeMovies.get(1)};
        MovieSelectedListener listener = null;

        // Act
        mPresenter.setRecyclerViewAdapter(context, movies, listener);

        // Assert
        Mockito.verify(mView).setRecyclerViewAdapter(context, movies, listener);
    }

    @Test
    public void updateRecyclerViewAdapter() throws Exception {

        // Arrange
        List<Movie> movies = new LinkedList<>();

        // Act
        mPresenter.updateRecyclerViewAdapter(movies);

        // Assert
        Mockito.verify(mView).updateRecyclerAdapter(movies);
    }

    @Test
    public void setRecyclerViewLayoutManager() throws Exception {

        // Arrange
        RecyclerView.LayoutManager manager = new LinearLayoutManager(null);

        // Act
        mPresenter.setRecyclerViewLayoutManager(manager);

        // Assert
        Mockito.verify(mView).setRecyclerViewManager(manager);
    }

    @Test
    public void setRecyclerViewProgressBarVisibility() throws Exception {

        // Arrange
        int someVisibility = 1;

        // Act
        mPresenter.setProgressBarVisibility(someVisibility);

        // // Assert
        Mockito.verify(mView).setProgressBarVisibility(someVisibility);
    }
}
