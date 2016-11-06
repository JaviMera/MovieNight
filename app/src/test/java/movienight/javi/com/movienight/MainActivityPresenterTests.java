package movienight.javi.com.movienight;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import movienight.javi.com.movienight.ui.MainActivityPresenter;
import movienight.javi.com.movienight.ui.MainActivityView;

/**
 * Created by Javi on 11/6/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTests {

    private MainActivityPresenter mPresenter;

    @Mock
    public MainActivityView mView;

    @Before
    public void setUp() throws Exception {

        mPresenter = new MainActivityPresenter(mView);
    }

    @Test
    public void setToolBarTitle() throws Exception {

        // Arrange
        String title = "";

        // Act
        mPresenter.setToolBarTitle(title);

        // Assert
        Mockito.verify(mView).setToolBarTitle(title);
    }
}
