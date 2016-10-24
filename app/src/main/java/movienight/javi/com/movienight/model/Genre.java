package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Javi on 10/16/2016.
 */
public class Genre implements Parcelable{

    private Integer mId;
    private String mDescription;
    private boolean mChecked;

    public Genre(Integer id, String description) {

        mId = id;
        mDescription = description;
    }

    private Genre(Parcel in) {

        mId = in.readInt();
        mDescription = in.readString();
        mChecked = in.readByte() != 0;
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
        dest.writeByte((byte)(mChecked ? 1 : 0));
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

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {

        mChecked = checked;
    }

    @Override
    public boolean equals(Object obj) {

        Genre otherGenre = (Genre)obj;

        if(mId.equals(otherGenre.getId()))
            return true;

        return false;
    }
}
