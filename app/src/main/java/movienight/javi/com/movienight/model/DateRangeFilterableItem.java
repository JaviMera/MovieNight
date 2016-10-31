package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Javi on 10/24/2016.
 */

public class DateRangeFilterableItem implements FilterableItem<Date>, Parcelable {

    private Date mStartDateSelected;
    private Date mEndDateSelected;

    public DateRangeFilterableItem(Date start, Date end) {

        mStartDateSelected = start;
        mEndDateSelected = end;
    }

    protected DateRangeFilterableItem(Parcel in) {

        mStartDateSelected = new Date(in.readLong());
        mEndDateSelected = new Date(in.readLong());
    }

    public static final Creator<DateRangeFilterableItem> CREATOR = new Creator<DateRangeFilterableItem>() {
        @Override
        public DateRangeFilterableItem createFromParcel(Parcel in) {
            return new DateRangeFilterableItem(in);
        }

        @Override
        public DateRangeFilterableItem[] newArray(int size) {
            return new DateRangeFilterableItem[size];
        }
    };

    @Override
    public Date[] getValue() {

        return new Date[]{mStartDateSelected, mEndDateSelected};
    }

    @Override
    public String toString() {

        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
        return "Between " + formatter.format(mStartDateSelected) + " and " + formatter.format(mEndDateSelected);
    }

    @Override
    public void update(Date newItem) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(mStartDateSelected.getTime());
        parcel.writeLong(mEndDateSelected.getTime());
    }
}
