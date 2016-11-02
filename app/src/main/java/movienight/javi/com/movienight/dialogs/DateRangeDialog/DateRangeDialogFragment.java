package movienight.javi.com.movienight.dialogs.DateRangeDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.model.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.FilterableItemKeys;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 10/24/2016.
 */

public class DateRangeDialogFragment extends FilterDialogBase implements DateRangeDialogFragmentView {

    private Date mStartDate;
    private Date mEndDate;
    private DateRangeDialogFragmentPresenter mPresenter;

    @BindView(R.id.startDateRangePickerView) DatePicker mStartDateRangePickerView;
    @BindView(R.id.endDateRangePickerView) DatePicker mEndDateRangePickerView;

    public static DateRangeDialogFragment newInstance(List<FilterableItem> dateItems) {

        DateRangeDialogFragment dialog = new DateRangeDialogFragment();

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

            Date[] datesSelected = dateItems.get(0).getValue();
            mStartDate = datesSelected[0];
            mEndDate = datesSelected[1];
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreateDialog(savedInstanceState);

        Context context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.date_range_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mPresenter = new DateRangeDialogFragmentPresenter(this);

        mPresenter.updateDatePickerDate(mStartDateRangePickerView, mStartDate);
        mPresenter.updateDatePickerDate(mEndDateRangePickerView, mEndDate);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @OnClick(R.id.dateRangeDoneButtonView)
    public void onDateRangePickerButtonClick(View view) {

        List<FilterableItem> dateItems = new ArrayList<>();

        dateItems.add(new DateRangeFilterableItem(
            getDate(mStartDateRangePickerView),
            getDate(mEndDateRangePickerView))
        );

        mListener.onFilterItemCreated(
                FilterableItemKeys.DATE_RANGE,
                dateItems.toArray(new FilterableItem[dateItems.size()]));

        dismiss();
    }

    @Override
    public void updateDatePickerDate(DatePicker view, Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        view.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    private Date getDate(DatePicker picker) {

        Calendar c = Calendar.getInstance();
        c.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());

        return c.getTime();
    }
}
