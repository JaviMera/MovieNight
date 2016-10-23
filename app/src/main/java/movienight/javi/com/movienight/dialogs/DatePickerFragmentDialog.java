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

import movienight.javi.com.movienight.listeners.DatePickerListener;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.ReleaseDate;

public class DatePickerFragmentDialog extends DialogFragment {

    private DatePickerListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DatePickerListener)context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();

        View datePickerLayout = LayoutInflater.from(context).inflate(R.layout.date_picker_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(datePickerLayout);

        final DatePicker mReleaseDatePicker = (DatePicker) datePickerLayout.findViewById(R.id.releaseDatePickerView);
        final Button doneButton = (Button) datePickerLayout.findViewById(R.id.doneDatepickerButtonView);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = mReleaseDatePicker.getDayOfMonth();
                int month = mReleaseDatePicker.getMonth();
                int year = mReleaseDatePicker.getYear();

                Calendar cal = createCalendar(year, month, day);
                String formatedDate = new SimpleDateFormat(ReleaseDate.FORMAT).format(cal.getTime());
                mListener.OnDatePickerDone(formatedDate);
            }
        });

        return dialogBuilder.create();
    }

    private Calendar createCalendar(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);

        return cal;
    }
}