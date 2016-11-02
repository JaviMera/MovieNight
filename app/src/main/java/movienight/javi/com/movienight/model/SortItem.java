package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Javi on 11/2/2016.
 */

public class SortItem implements Parcelable {

    private String mName;
    private boolean mSelected;

    public SortItem(String name) {

        mName = name;
        mSelected = false;
    }

    protected SortItem(Parcel in) {
        mName = in.readString();
        mSelected = in.readByte() != 0;
    }

    public static final Creator<SortItem> CREATOR = new Creator<SortItem>() {
        @Override
        public SortItem createFromParcel(Parcel in) {
            return new SortItem(in);
        }

        @Override
        public SortItem[] newArray(int size) {
            return new SortItem[size];
        }
    };

    public String getName() {

        return mName;
    }

    public boolean isSelected() {

        return mSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeByte((byte) (mSelected ? 1 : 0));
    }

    public void setChecked(boolean isChecked) {

        mSelected = isChecked;
    }
}
