package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Javi on 10/24/2016.
 */

public class RateFIlterableItem implements FilterableItem<Float>, Parcelable {

    private float mRate;

    public RateFIlterableItem(float rate) {

        mRate = rate;
    }

    protected RateFIlterableItem(Parcel in) {
        mRate = in.readFloat();
    }

    public static final Creator<RateFIlterableItem> CREATOR = new Creator<RateFIlterableItem>() {
        @Override
        public RateFIlterableItem createFromParcel(Parcel in) {
            return new RateFIlterableItem(in);
        }

        @Override
        public RateFIlterableItem[] newArray(int size) {
            return new RateFIlterableItem[size];
        }
    };

    @Override
    public Float getValue() {

        return mRate;
    }

    @Override
    public String toString() {

        return String.format("%.1f",mRate);
    }

    @Override
    public void update(Float newRate) {

        mRate = newRate;
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
