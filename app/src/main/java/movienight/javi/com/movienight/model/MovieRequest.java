package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewDebug;

/**
 * Created by Javi on 10/18/2016.
 */

public class MovieRequest implements Parcelable{

    private Genre[] mGenreSelected;
    private String mStartDateReleaseSelected;
    private String mEndDateReleaseSelected;
    private Integer mVoteCountSelected;
    private Float mRatingSelected;

    public MovieRequest() {}

    protected MovieRequest(Parcel in) {
        mGenreSelected = in.createTypedArray(Genre.CREATOR);
        mStartDateReleaseSelected = in.readString();
        mEndDateReleaseSelected = in.readString();
        mVoteCountSelected = in.readInt();
        mRatingSelected = in.readFloat();
    }

    public static final Creator<MovieRequest> CREATOR = new Creator<MovieRequest>() {
        @Override
        public MovieRequest createFromParcel(Parcel in) {
            return new MovieRequest(in);
        }

        @Override
        public MovieRequest[] newArray(int size) {
            return new MovieRequest[size];
        }
    };

    public String getStartDateReleaseSelected() {
        return mStartDateReleaseSelected;
    }

    public Genre[] getGenreSelected() {
        return mGenreSelected;
    }

    public Float getRatingSelected() {
        return mRatingSelected;
    }

    public int getVoteCountSelected() {
        return mVoteCountSelected;
    }

    public String getEndDateReleaseSelected() {
        return mEndDateReleaseSelected;
    }

    public void setGenre(Genre[] genre) {

        mGenreSelected = genre;
    }

    public void setRating(Float rating) {

        mRatingSelected = rating;
    }

    public void setVoteCount(Integer voteCount) {

        mVoteCountSelected = voteCount;
    }

    public void setStartDateRelease(String releaseDate) {

        mStartDateReleaseSelected = releaseDate;
    }

    public void setEndDateReleaseSelected(String endDateReleaseSelected) {
        mEndDateReleaseSelected = endDateReleaseSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedArray(mGenreSelected, 0);
        dest.writeString(mStartDateReleaseSelected);
        dest.writeString(mEndDateReleaseSelected);
        dest.writeInt(mVoteCountSelected);
        dest.writeFloat(mRatingSelected);
    }
}
