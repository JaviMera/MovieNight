package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Javi on 10/16/2016.
 */
public class Genre implements Parcelable, Comparable<Genre>{

    private Integer mId;
    private String mDescription;

    public Genre(Integer id, String description) {

        mId = id;
        mDescription = description;
    }

    private Genre(Parcel in) {

        mId = in.readInt();
        mDescription = in.readString();
    }

    public String getDescription() {
        return mDescription;
    }

    public Integer getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mId);
        dest.writeString(mDescription);
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    @Override
    public int compareTo(Genre o) {

        if(mId > o.getId()) {

            return 1;
        }
        else if(mId < o.getId()) {
            return -1;
        }

        return 0;
    }
}
