package movienight.javi.com.movienight.dialogs.DateRangeDialog;

import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by Javi on 11/2/2016.
 */

public class DateRangeDialogFragmentPresenter {

    private DateRangeDialogFragmentView mView;

    public DateRangeDialogFragmentPresenter(DateRangeDialogFragmentView view) {

        mView = view;
    }

    public void updateDatePickerDate(DatePicker view, Date date) {

        mView.updateDatePickerDate(view, date);
    }
}
