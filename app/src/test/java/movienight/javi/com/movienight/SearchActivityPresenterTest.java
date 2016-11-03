package movienight.javi.com.movienight;

import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivityPresenter;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivityView;

/**
 * Created by Javi on 10/22/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class SearchActivityPresenterTest {

    private SearchActivityPresenter mPresenter;

    @Mock
    public SearchActivityView mView;

    @Before
    public void SetUp() throws Exception {

        mPresenter = new SearchActivityPresenter(mView);
    }

    @Test
    public void setFilterSpinnerViewAdapter() throws Exception {

        // Arrange
        String[] items = null;

        // Act
        mPresenter.setFilterOptionsSpinnerViewAdapter(items);

        // Assert
        Mockito.verify(mView).setFilterOptionsSpinnerViewAdapter(items);
    }


    @Test
    public void setFilterSpinnerItemClickListener() throws Exception {

        // Arrange
        AdapterView.OnItemSelectedListener listener = null;

        // Act
        mPresenter.setFilterSpinnerItemClickListener(listener);

        // Assert
        Mockito.verify(mView).setFilterSpinnerItemClickListener(listener);
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
    public void setMoviesRecyclerViewAdapter() throws Exception {

        // Arrange
        Movie[] movies = new Movie[]{FakeMovies.get(1)};

        // Act
        mPresenter.setMoviesRecyclerViewAdapter(movies);

        // Assert
        Mockito.verify(mView).setMoviesRecyclerViewAdapter(movies);
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
    public void setRecyclerViewProgressBarVisibility() throws Exception {

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
    public void setMovieRecyclerScrollListener() throws Exception {

        // Arrange
        RecyclerView.OnScrollListener listener = null;

        // Act
        mPresenter.setMovieRecyclerScrollListener(listener);

        // Assert
        Mockito.verify(mView).setMovieRecyclerScrollListener(listener);
    }
}
