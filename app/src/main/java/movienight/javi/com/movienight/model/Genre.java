package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javi on 10/16/2016.
 */
public class Genre implements Parcelable{

    private Integer mId;
    private String mDescription;
    private int mChecked;

    public static List<Genre> getSelectedGenres(int[] genreIds, List<Genre> genres) {

        List<Genre> movieGenres = new ArrayList<>();
        for(int genreId : genreIds) {

            for(Genre genre : genres) {

                if(genre.getId().equals(genreId)) {

                    movieGenres.add(genre);
                }
            }
        }

        return movieGenres;
    }

    public Genre(Integer id, String description) {

        mId = id;
        mDescription = description;
        mChecked = 0;
    }

    private Genre(Parcel in) {

        mId = in.readInt();
        mDescription = in.readString();
        mChecked = in.readInt();
    }

    public Genre() {
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
        dest.writeInt(mChecked);
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

        return mChecked == 1;
    }

    public void setChecked(boolean checked) {

        mChecked = checked ? 1 : 0;
    }

    @Override
    public int hashCode() {

        return mId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Genre) {

            Genre otherGenre = (Genre)obj;

            if(mId.equals(otherGenre.getId()))
                return true;

            return false;
        }

        return false;
    }
}
