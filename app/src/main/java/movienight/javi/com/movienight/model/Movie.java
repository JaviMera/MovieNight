package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/21/2016.
 */
public class Movie {

    private int mId;
    private String mOverview;
    private String mOriginalTitle;
    private String mTitle;
    private double mPopularity;
    private int mVoteCount;
    private Double mRating;
    private int[] mGenreIds;

    public Movie(
        int id,
        String overview,
        String originalTitle,
        String title,
        double popularity,
        int voteCount,
        double rating,
        int[] genreIds)
    {
        mId = id;
        mOverview = overview;
        mOriginalTitle = originalTitle;
        mTitle = title;
        mPopularity = popularity;
        mVoteCount = voteCount;
        mRating = rating;
        mGenreIds = genreIds;
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
}
