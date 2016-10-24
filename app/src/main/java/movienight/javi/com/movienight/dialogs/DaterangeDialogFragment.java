package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.DateSelectedListener;
import movienight.javi.com.movienight.model.ReleaseDate;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

/**
 * Created by Javi on 10/24/2016.
 */

public class DaterangeDialogFragment extends DialogFragment {

    private String mStartDate;
    private String mEndDate;
    private DateSelectedListener mListener;

    public static DaterangeDialogFragment newInstance() {

        DaterangeDialogFragment dialog = new DaterangeDialogFragment();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (SearchActivity)getActivity();
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
        final Button doneButton = (Button) view.findViewById(R.id.dateRangeDoneButtonView);

        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mListener.onDateRangePickerDone(
                    getDate(startDatePicker),
                    getDate(endDatePicker)
                );

                dismiss();
            }
        });

        return dialogBuilder.create();
    }

    private Date getDate(DatePicker picker) {

        Calendar c = Calendar.getInstance();
        c.set(picker.getYear(), picker.getMonth() + 1, picker.getDayOfMonth());

        return c.getTime();
    }
}
