package movienight.javi.com.movienight.model;

import android.graphics.Bitmap;
import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import movienight.javi.com.movienight.model.jsonvalues.JSONFilm;
import movienight.javi.com.movienight.model.jsonvalues.JSONTVShow;

/**
 * Created by Javi on 11/4/2016.
 */

public class TVShow implements Film {

    private int mId;
    private String mOverview;
    private String mOriginalTitle;
    private String mName;
    private String mAirDate;
    private double mPopularity;
    private int mVoteCount;
    private double mRating;
    private int[] mGenreIds;
    private String mPosterPath;
    private Bitmap mPoster;

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public static Film fromJSON(JSONObject jsonObject) throws JSONException{

        int movieId = jsonObject.getInt(JSONFilm.ID_KEY);
        String movieOverview = jsonObject.getString(JSONFilm.OVERVIEW_KEY);
        String movieOriginalTitle = jsonObject.getString(JSONTVShow.ORIGINAL_TITLE_KEY);
        String movieTitle = jsonObject.getString(JSONTVShow.TITLE_KEY);
        String tvShowAirDate = jsonObject.getString(JSONTVShow.RELEASE_DATE_KEY);

        String moviePosterPath = jsonObject.isNull(JSONFilm.POSTER_PATH_KEY)
                ? ""
                : jsonObject.getString(JSONFilm.POSTER_PATH_KEY);

        double moviePopularity = jsonObject.getDouble(JSONFilm.POPULARITY_KEY);
        int movieVotes = jsonObject.getInt(JSONFilm.VOTE_COUNT_KEY);
        double movieRating = jsonObject.getDouble(JSONFilm.VOTE_AVERAGE_KEY);

        JSONArray genreIdsArray = jsonObject.getJSONArray(JSONFilm.GENRE_IDS_KEY);
        int[] genreIds = new int[genreIdsArray.length()];

        for(int g = 0 ; g < genreIdsArray.length() ; g++) {

            genreIds[g] = genreIdsArray.getInt(g);
        }

        return new TVShow(
                movieId,
                movieOverview,
                movieOriginalTitle,
                movieTitle,
                tvShowAirDate,
                moviePopularity,
                movieVotes,
                movieRating,
                genreIds,
                moviePosterPath,
                null
        );
    }

    public TVShow(
            int id,
            String overview,
            String originalTitle,
            String name,
            String airDate,
            double popularity,
            int voteCount,
            double rating,
            int[] genreIds,
            String posterPath,
            Bitmap poster)
    {
        mId = id;
        mOverview = overview;
        mOriginalTitle = originalTitle;
        mName = name;
        mAirDate = airDate;
        mPopularity = popularity;
        mVoteCount = voteCount;
        mRating = rating;
        mGenreIds = genreIds;
        mPosterPath = posterPath;
        mPoster = poster;
    }

    public TVShow(Parcel in) {

        mId = in.readInt();
        mOverview = in.readString();
        mOriginalTitle = in.readString();
        mName = in.readString();
        mAirDate = in.readString();
        mPopularity = in.readDouble();
        mVoteCount = in.readInt();
        mGenreIds = in.createIntArray();
        mPosterPath = in.readString();
        mPoster = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public int getId() {

        return getId();
    }

    @Override
    public String getName() {

        return mName;
    }

    @Override
    public String getOverview() {
        return mOverview;
    }

    @Override
    public String getReleaseDate() {
        return mAirDate;
    }

    @Override
    public double getPopularity() {
        return mPopularity;
    }

    @Override
    public int getVoteCount() {
        return mVoteCount;
    }

    @Override
    public double getRating() {
        return mRating;
    }

    @Override
    public int[] getGenres() {
        return mGenreIds;
    }

    @Override
    public String getPosterPath() {
        return mPosterPath;
    }

    @Override
    public Bitmap getPoster() {
        return mPoster;
    }

    @Override
    public void setPoster(Bitmap poster) {

        mPoster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mId);
        dest.writeString(mOverview);
        dest.writeString(mOriginalTitle);
        dest.writeString(mName);
        dest.writeString(mAirDate);
        dest.writeDouble(mPopularity);
        dest.writeInt(mVoteCount);
        dest.writeIntArray(mGenreIds);
        dest.writeString(mPosterPath);
        dest.writeParcelable(mPoster, flags);
    }

    public String getYearRelease() {

        return mAirDate.isEmpty()
                ? ""
                :mAirDate.split("-")[0];
    }

    public String getMonthRelease() {

        return mAirDate.isEmpty()
                ? ""
                :mAirDate.split("-")[1];
    }

    public String getDayRelease() {

        return mAirDate.isEmpty()
                ? ""
                :mAirDate.split("-")[2];
    }
}
