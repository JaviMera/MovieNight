package movienight.javi.com.movienight;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import movienight.javi.com.movienight.dialogs.GenresDialog.GenresDialogFragmentPresenter;
import movienight.javi.com.movienight.dialogs.GenresDialog.GenresDialogFragmentView;
import movienight.javi.com.movienight.model.FilterItems.Genre;

/**
 * Created by Javi on 11/2/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GenresDialogFragmentPresenterTest {

    @Mock
    public GenresDialogFragmentView mView;

    private GenresDialogFragmentPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new GenresDialogFragmentPresenter(mView);
    }

    @Test
    public void setRecyclerAdapter() throws Exception {

        // Arrange
        Context context = null;
        List<Genre> genres = null;

        // Act
        mPresenter.setRecyclerAdapter(context, genres);

        // Assert
        Mockito.verify(mView).setRecyclerAdapter(context, genres);
    }

    @Test
    public void setRecyclerManager() throws Exception {

        // Arrange
        Context context = null;

        // Act
        mPresenter.setRecyclerManager(context);

        // Assert
        Mockito.verify(mView).setRecyclerManager(context);
    }

    @Test
    public void setRecyclerSize() throws Exception {

        // Arrange
        boolean fixedSize = true;

        // Act
        mPresenter.setRecyclerSize(fixedSize);

        // Assert
        Mockito.verify(mView).setRecyclerSize(fixedSize);
    }
}
