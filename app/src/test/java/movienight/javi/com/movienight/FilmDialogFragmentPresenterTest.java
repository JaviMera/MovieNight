package movienight.javi.com.movienight;

import android.graphics.Bitmap;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import movienight.javi.com.movienight.dialogs.MovieDialog.MovieDialogFragmentPresenter;
import movienight.javi.com.movienight.dialogs.MovieDialog.MovieDialogFramgnetView;
import movienight.javi.com.movienight.model.FilterItems.Genre;

/**
 * Created by Javi on 11/1/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilmDialogFragmentPresenterTest {

    @Mock
    public MovieDialogFramgnetView mView;

    private MovieDialogFragmentPresenter mPresenter;

    @Before
    public void SetUp() throws Exception {

        mPresenter = new MovieDialogFragmentPresenter(mView);
    }

    @Test
    public void setTextViewText() throws Exception {

        // Arrange
        TextView view = null;
        String text = "lol";
        Object[] params = null;

        // Act
        mPresenter.setTextViewText(view, text, params);

        // Assert
        Mockito.verify(mView).setTextViewText(view, text, params);
    }

    @Test
    public void setPosterImageView() throws Exception {

        // Arrange
        Bitmap poster = null;

        // Act
        mPresenter.setPosterImageView(poster);

        // Assert
        Mockito.verify(mView).setPosterImageView(poster);
    }

    @Test
    public void setGenresDescriptionTextViewText() throws Exception {

        // Arrange
        List<String> genres = null;

        // Act
        mPresenter.setGenresDescriptionsTextViewText(genres);

        // Assert
        Mockito.verify(mView).setGenresDescriptionsTextViewText(genres);
    }

    @Test
    public void setReleaseDateTextViewText() throws Exception {

        // Arrange
        String year = "";
        String month = "";
        String day = "";

        // Act
        mPresenter.setReleaseDateTextViewText(year, month, day);

        // Assert
        Mockito.verify(mView).setReleaseDateTextViewText(year, month, day);
    }
}
