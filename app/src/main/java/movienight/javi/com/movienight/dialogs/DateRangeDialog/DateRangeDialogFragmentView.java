package movienight.javi.com.movienight.dialogs.DateRangeDialog;

import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by Javi on 11/2/2016.
 */

public interface DateRangeDialogFragmentView {

    void updateDatePickerDate(DatePicker view, Date date);
}
