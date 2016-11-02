package movienight.javi.com.movienight;

import android.widget.DatePicker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import movienight.javi.com.movienight.dialogs.DateRangeDialog.DateRangeDialogFragmentPresenter;
import movienight.javi.com.movienight.dialogs.DateRangeDialog.DateRangeDialogFragmentView;

/**
 * Created by Javi on 11/2/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DateRangeDialogFragmentPresenterTest {

    @Mock
    public DateRangeDialogFragmentView mView;

    private DateRangeDialogFragmentPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new DateRangeDialogFragmentPresenter(mView);
    }

    @Test
    public void updateDatePickerDate() throws Exception {

        // Arrange
        DatePicker view = null;
        Date date = null;

        // Act
        mPresenter.updateDatePickerDate(view, date);

        // Assert
        Mockito.verify(mView).updateDatePickerDate(view, date);
    }
}
