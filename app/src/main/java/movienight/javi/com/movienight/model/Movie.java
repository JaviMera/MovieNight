package movienight.javi.com.movienight.model;

import android.graphics.Bitmap;
import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import movienight.javi.com.movienight.model.jsonvalues.JSONFilm;
import movienight.javi.com.movienight.model.jsonvalues.JSONMovie;

/**
 * Created by Javi on 10/21/2016.
 */
public class Movie implements Film{

    private int mId;
    private String mOverview;
    private String mOriginalTitle;
    private String mTitle;
    private String mReleaseDate;
    private double mPopularity;
    private int mVoteCount;
    private double mRating;
    private int[] mGenreIds;
    private String mPosterPath;
    private Bitmap mPoster;

    protected Movie(Parcel in) {
        mId = in.readInt();
        mOverview = in.readString();
        mOriginalTitle = in.readString();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mPopularity = in.readDouble();
        mVoteCount = in.readInt();
        mGenreIds = in.createIntArray();
        mPosterPath = in.readString();
        mPoster = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {

        this(-1, "insert overview", "insert originalTitle", "insert title", "Insert year", 0.0, 0, 0.0, new int[]{}, "insert poster path", null);
    }

    public static Film fromJSON(JSONObject jsonObject) throws JSONException{

        int movieId = jsonObject.getInt(JSONFilm.ID_KEY);
        String movieOverview = jsonObject.getString(JSONFilm.OVERVIEW_KEY);
        String movieOriginalTitle = jsonObject.getString(JSONMovie.ORIGINAL_TITLE_KEY);
        String movieTitle = jsonObject.getString(JSONMovie.TITLE_KEY);
        String movieReleaseDate = jsonObject.getString(JSONMovie.RELEASE_DATE_KEY);

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

        return new Movie(
            movieId,
            movieOverview,
            movieOriginalTitle,
            movieTitle,
            movieReleaseDate,
            moviePopularity,
            movieVotes,
            movieRating,
            genreIds,
            moviePosterPath,
            null
        );
    }

    public Movie(
        int id,
        String overview,
        String originalTitle,
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
        mOriginalTitle = originalTitle;
        mTitle = title;
        mReleaseDate = releaseDate;
        mPopularity = popularity;
        mVoteCount = voteCount;
        mRating = rating;
        mGenreIds = genreIds;
        mPosterPath = posterPath;
        mPoster = poster;
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public String getName() {
        return mTitle;
    }

    @Override
    public String getOverview() {
        return mOverview;
    }

    @Override
    public String getReleaseDate() {return mReleaseDate;}

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
    public Bitmap getPoster() {

        return mPoster;
    }

    @Override
    public void setPoster(Bitmap poster) {

        mPoster = poster;
    }

    @Override
    public String getPosterPath() {
        return mPosterPath;
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
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mPopularity);
        dest.writeInt(mVoteCount);
        dest.writeIntArray(mGenreIds);
        dest.writeString(mPosterPath);
        dest.writeParcelable(mPoster, flags);
    }

    @Override
    public String getYearRelease() {

        return mReleaseDate.isEmpty()
            ? ""
            :mReleaseDate.split("-")[0];
    }

    @Override
    public String getMonthRelease() {

        return mReleaseDate.isEmpty()
            ? ""
            :mReleaseDate.split("-")[1];
    }

    @Override
    public String getDayRelease() {

        return mReleaseDate.isEmpty()
            ? ""
            :mReleaseDate.split("-")[2];
    }
}
