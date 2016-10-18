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
import android.widget.DatePicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import movienight.javi.com.movienight.R;

/**
 * Created by Javi on 10/18/2016.
 */

public class DatePickerFragmentDialog extends DialogFragment {

    @BindView(R.id.releaseDatePickerView) DatePicker mReleaseDatePicker;
    @BindView(R.id.doneDatepickerButtonView) AppCompatButton mDoneDatePickerButtonView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        View datePickerLayout = LayoutInflater.from(context).inflate(R.layout.date_picker_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(datePickerLayout);

        return dialogBuilder.create();
    }
}