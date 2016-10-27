package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Javi on 10/24/2016.
 */

public class RateFilterableItem implements FilterableItem<Float>, Parcelable {

    private float mRate;

    public RateFilterableItem(float rate) {

        mRate = rate;
    }

    protected RateFilterableItem(Parcel in) {
        mRate = in.readFloat();
    }

    public static final Creator<RateFilterableItem> CREATOR = new Creator<RateFilterableItem>() {
        @Override
        public RateFilterableItem createFromParcel(Parcel in) {
            return new RateFilterableItem(in);
        }

        @Override
        public RateFilterableItem[] newArray(int size) {
            return new RateFilterableItem[size];
        }
    };

    @Override
    public Float getObject() {
        return null;
    }

    @Override
    public String getValue() {

        return String.format("%.1f",mRate);
    }

    public float getRate() {

        return mRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(mRate);
    }
}
