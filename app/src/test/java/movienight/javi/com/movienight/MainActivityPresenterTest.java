package movienight.javi.com.movienight;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.MainActivity.MainActivityPresenter;
import movienight.javi.com.movienight.ui.MainActivity.MainActivityView;

/**
 * Created by Javier on 10/31/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    public MainActivityView mView;

    private MainActivityPresenter mPresenter;

    @Before
    public void SetUp() throws Exception {

        mPresenter = new MainActivityPresenter(mView);
    }

    @Test
    public void setTopMoviesRecyclerViewAdapter() throws Exception {

        // Arrange
        Movie[] items = null;

        // Act
        mPresenter.setTopMoviesRecyclerViewAdapter(items);

        // Assert
        Mockito.verify(mView).setTopMoviesRecyclerViewAdapter(items);
    }

    @Test
    public void setTopMoviesRecyclerViewLayoutManager() throws Exception {

        // Arrange
        int numberOfColumns = 1;
        int orientation = 1;

        // Act
        mPresenter.setTopMoviesRecyclerViewLayoutManager(numberOfColumns, orientation);

        // Assert
        Mockito.verify(mView).setTopMoviesRecyclerViewLayoutManager(numberOfColumns, orientation);
    }

    @Test
    public void setTopMoviesRecyclerViewSize() throws Exception {

        // Arrange
        boolean fixedSize = true;

        // Act
        mPresenter.setTopMoviesRecyclerViewSize(fixedSize);

        // Assert
        Mockito.verify(mView).setTopMoviesRecyclerViewSize(fixedSize);
    }

    @Test
    public void updateTopMoviesRecyclerViewAdapter() throws Exception {

        // Arrange
        Movie[] movies = null;

        // Act
        mPresenter.updateMoviesRecyclerViewAdapter(movies);

        // Assert
        Mockito.verify(mView).updateMoviesRecyclerViewAdapter(movies);
    }
}
