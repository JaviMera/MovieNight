package movienight.javi.com.movienight.ui.SearchActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.R;

public class DatePickerFragmentDialog extends DialogFragment {

    private OnDoneListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (OnDoneListener)context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ButterKnife.bind(getActivity());
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
                int month = mReleaseDatePicker.getMonth() + 1;
                int year = mReleaseDatePicker.getYear();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.DAY_OF_MONTH, day);
                cal.set(Calendar.MONTH, month);
                String formatedDate = new SimpleDateFormat("M/dd/yyyy").format(cal.getTime());
                mListener.OnDone(formatedDate);
            }
        });

        return dialogBuilder.create();
    }
}