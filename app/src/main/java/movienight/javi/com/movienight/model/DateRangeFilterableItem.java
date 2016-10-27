package movienight.javi.com.movienight.model;

import android.app.FragmentManager;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Javi on 10/24/2016.
 */

public class DateRangeFilterableItem implements FilterableItem<Date>, Parcelable {

    private Date mStartDate;
    private Date mEndDate;

    public DateRangeFilterableItem(Date startDate, Date endDate) {

        mStartDate = startDate;
        mEndDate = endDate;
    }

    protected DateRangeFilterableItem(Parcel in) {

        mStartDate = new Date(in.readLong());
        mEndDate = new Date(in.readLong());
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
    public Date getObject() {
        return null;
    }

    @Override
    public String getValue() {

        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
        return "Between " + formatter.format(mStartDate) + " and " + formatter.format(mEndDate);
    }

    public Date getEndDate() {

        return mEndDate;
    }

    public Date getStartDate() {

        return mStartDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(mStartDate.getTime());
        parcel.writeLong(mEndDate.getTime());
    }
}
