package movienight.javi.com.movienight;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivityPresenter;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivityView;

/**
 * Created by Javi on 10/18/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class SearchActivityPresenterTest {

    private SearchActivityPresenter mPresenter;

    @Before
    public void SetUp () throws Exception {

        mPresenter = new SearchActivityPresenter(mView);
    }

    @Mock
    private SearchActivityView mView;

    @Test
    public void setGenreSpinnerAdapter() throws Exception {

        // Arrange
        Context ctx = null;
        Genre[] someGenres = new Genre[2];

        // Act
        mPresenter.setGenreSpinnerAdapter(ctx, someGenres);

        // Assert
        Mockito.verify(mView).setGenreSpinnerAdapter(ctx, someGenres);
    }
}