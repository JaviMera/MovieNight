package movienight.javi.com.movienight.model;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import movienight.javi.com.movienight.model.jsonvalues.JSONMovie;

/**
 * Created by Javi on 10/21/2016.
 */
public class Movie{

    private int mId;
    private String mOverview;
    private String mOriginalTitle;
    private String mTitle;
    private double mPopularity;
    private int mVoteCount;
    private Double mRating;
    private int[] mGenreIds;
    private String mPosterPath;
    private Bitmap mPoster;

    public static Movie fromJSON(JSONObject jsonObject) throws JSONException{

        int movieId = jsonObject.getInt(JSONMovie.ID_KEY);
        String movieOverview = jsonObject.getString(JSONMovie.OVERVIEW_KEY);
        String movieOriginalTitle = jsonObject.getString(JSONMovie.ORIGINAL_TITLE_KEY);
        String movieTitle = jsonObject.getString(JSONMovie.TITLE_KEY);
        String moviePosterPath = jsonObject.getString("poster_path");
        double moviePopularity = jsonObject.getDouble(JSONMovie.POPULARITY_KEY);
        int movieVotes = jsonObject.getInt(JSONMovie.VOTE_COUNT_KEY);
        double movieRating = jsonObject.getDouble(JSONMovie.VOTE_AVERAGE_KEY);

        JSONArray genreIdsArray = jsonObject.getJSONArray(JSONMovie.GENRE_IDS_KEY);
        int[] genreIds = new int[genreIdsArray.length()];

        for(int g = 0 ; g < genreIdsArray.length() ; g++) {

            genreIds[g] = genreIdsArray.getInt(g);
        }

        return new Movie(
            movieId,
            movieOverview,
            movieOriginalTitle,
            movieTitle,
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
        mPopularity = popularity;
        mVoteCount = voteCount;
        mRating = rating;
        mGenreIds = genreIds;
        mPosterPath = posterPath;
        mPoster = poster;
    }

    public int getId() {
        return mId;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public Double getRating() {
        return mRating;
    }

    public int[] getGenreIds() {

        return mGenreIds;
    }

    public Bitmap getPoster() {

        return mPoster;
    }

    public void setPoster(Bitmap poster) {

        mPoster = poster;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        mPosterPath = mPosterPath;
    }
}
