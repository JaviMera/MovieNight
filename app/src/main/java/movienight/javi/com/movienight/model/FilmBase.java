package movienight.javi.com.movienight.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Javi on 11/5/2016.
 */

public abstract class FilmBase<T> implements Parcelable {

    protected int mId;
    protected String mOverview;
    protected String mTitle;
    protected String mReleaseDate;
    protected double mPopularity;
    protected int mVoteCount;
    protected double mRating;
    protected int[] mGenreIds;
    protected String mPosterPath;
    protected Bitmap mPoster;

    protected FilmBase(
        int id,
        String overview,
        String title,
        String releaseDate,
        double popularity,
        int voteCount,
        double rating,
        int[] genreIds,
        String posterPath,
        Bitmap poster)
    {
        mId = id;
        mOverview = overview;
        mTitle = title;
        mReleaseDate = releaseDate;
        mPopularity = popularity;
        mVoteCount = voteCount;
        mRating = rating;
        mGenreIds = genreIds;
        mPosterPath = posterPath;
        mPoster = poster;
    }

    protected FilmBase(Parcel in) {

        mId = in.readInt();
        mOverview = in.readString();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mPopularity = in.readDouble();
        mVoteCount = in.readInt();
        mGenreIds = in.createIntArray();
        mPosterPath = in.readString();
        mPoster = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mOverview);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mPopularity);
        dest.writeInt(mVoteCount);
        dest.writeIntArray(mGenreIds);
        dest.writeString(mPosterPath);
        dest.writeParcelable(mPoster, flags);
    }

    public int[] getGenreIds() {
        return mGenreIds;
    }

    public int getId() {
        return mId;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public Bitmap getPoster() {
        return mPoster;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public double getRating() {
        return mRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setPoster(Bitmap poster) {
        mPoster = poster;
    }

    public String getYearRelease() {

        return mReleaseDate.isEmpty()
            ? ""
            :mReleaseDate.split("-")[0];
    }

    public String getMonthRelease() {

        return mReleaseDate.isEmpty()
            ? ""
            :mReleaseDate.split("-")[1];
    }

    public String getDayRelease() {

        return mReleaseDate.isEmpty()
            ? ""
            :mReleaseDate.split("-")[2];
    }
}
