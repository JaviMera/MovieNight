package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.model.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.GenreFilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

/**
 * Created by Javi on 10/24/2016.
 */

public class DaterangeDialogFragment extends DialogFragmentBase {

    private Date mStartDate;
    private Date mEndDate;

    public static DaterangeDialogFragment newInstance(List<FilterableItem> dateItems) {

        DaterangeDialogFragment dialog = new DaterangeDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.SELECTED_DATE_RANGE_KEY, (ArrayList)dateItems);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStartDate = new Date();
        mEndDate = new Date();

        List<DateRangeFilterableItem> dateItems = getArguments().getParcelableArrayList(ActivityExtras.SELECTED_DATE_RANGE_KEY);

        if(!dateItems.isEmpty()) {

            mStartDate = dateItems.get(0).getStartDate();
            mEndDate = dateItems.get(0).getEndDate();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.date_range_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        final DatePicker startDatePicker = (DatePicker) view.findViewById(R.id.startDateRangePickerView);
        final DatePicker endDatePicker = (DatePicker) view.findViewById(R.id.endDateRangePickerView);

        Calendar c = Calendar.getInstance();
        c.setTime(mStartDate);
        startDatePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        c.setTime(mEndDate);
        endDatePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        final Button doneButton = (Button) view.findViewById(R.id.dateRangeDoneButtonView);

        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mListener.onFilterItemCreated(2,
                    new DateRangeFilterableItem(getDate(startDatePicker),getDate(endDatePicker))
                );

                dismiss();
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());

        return dialog;
    }

    private Date getDate(DatePicker picker) {

        Calendar c = Calendar.getInstance();
        c.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());

        return c.getTime();
    }
}
