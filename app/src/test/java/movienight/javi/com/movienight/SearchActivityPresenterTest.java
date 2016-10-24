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
    public void setFilterSpinnerAdapter() throws Exception {

        // Arrange
        String[] someItems = new String[]{"this","monday","is","awesome"};

        // Act
        mPresenter.setFilterSpinnerAdapter(someItems);

        // Assert
        Mockito.verify(mView).setFilterSpinnerAdapter(someItems);
    }
}
