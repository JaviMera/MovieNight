package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by Javi on 10/24/2016.
 */

public class GenreFilterableItem implements FilterableItem<Genre>, Parcelable {

    private Genre mSelectedGenre;

    public GenreFilterableItem(Genre genre) {

        mSelectedGenre = genre;
    }

    protected GenreFilterableItem(Parcel in) {
        mSelectedGenre = in.readParcelable(Genre.class.getClassLoader());
    }

    public static final Creator<GenreFilterableItem> CREATOR = new Creator<GenreFilterableItem>() {
        @Override
        public GenreFilterableItem createFromParcel(Parcel in) {
            return new GenreFilterableItem(in);
        }

        @Override
        public GenreFilterableItem[] newArray(int size) {
            return new GenreFilterableItem[size];
        }
    };

    @Override
    public Genre getObject() {

        return mSelectedGenre;
    }

    @Override
    public String getValue() {

        return mSelectedGenre.getDescription();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mSelectedGenre, i);
    }

    @Override
    public int hashCode() {

        return mSelectedGenre.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof  GenreFilterableItem) {

            GenreFilterableItem otherItem = (GenreFilterableItem)obj;
            if(mSelectedGenre.getId().equals(otherItem.getObject().getId())) {

                return true;
            }

            return false;
        }

        return false;
    }
}