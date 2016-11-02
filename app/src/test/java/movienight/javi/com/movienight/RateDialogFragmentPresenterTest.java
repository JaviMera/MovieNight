package movienight.javi.com.movienight;

import android.content.res.Resources;
import android.graphics.PorterDuff;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import movienight.javi.com.movienight.dialogs.RateDialog.RateDialogFragmentPresenter;
import movienight.javi.com.movienight.dialogs.RateDialog.RateDialogFragmentView;

/**
 * Created by Javi on 11/2/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class RateDialogFragmentPresenterTest {

    @Mock
    public RateDialogFragmentView mView;

    private RateDialogFragmentPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new RateDialogFragmentPresenter(mView);
    }

    @Test
    public void setRatingBarProgressDrawable() throws Exception {

        // Arrange
        int index = 0;
        int color = 1234;
        PorterDuff.Mode mode = null;

        // Act
        mPresenter.setRatingBarProgressDrawable(index, color, mode);

        // Assert
        Mockito.verify(mView).setRatingBarProgressDrawable(index, color, mode);
    }

    @Test
    public void setRatingBar() throws Exception {

        // Assert
        float value = 0.0f;

        // Act
        mPresenter.setRatingBar(value);

        // Assert
        Mockito.verify(mView).setRatingBar(value);
    }

    @Test
    public void setRatingTextViewText() throws Exception {

        // Arrange
        float value = 0.0f;

        // Act
        mPresenter.setRatingTextViewText(value);

        // Assert
        Mockito.verify(mView).setRatingTextViewText(value);
    }

    @Test
    public void setRatingBarOnChangeListener() throws Exception {

        // Assert
        Resources resources = null;
        int textId = 1234;

        // Act
        mPresenter.setRatingBarOnChangeListener(resources, textId);

        // Assert
        Mockito.verify(mView).setRatingBarOnChangeListener(resources, textId);
    }
}