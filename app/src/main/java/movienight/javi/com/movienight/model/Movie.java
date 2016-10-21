package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/21/2016.
 */
public class Movie {

    private int mId;
    private String mOverview;
    private String mOriginalTitle;
    private String mTitle;
    private Double mPopularity;
    private Integer mVoteCount;
    private Double mRating;
    private Integer[] mGenreIds;

    public Movie(
        Integer id,
        String overview,
        String originalTitle,
        String title,
        Double popularity,
        Integer voteCount,
        Double rating,
        Integer[] genreIds)
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

    public Integer getVoteCount() {
        return mVoteCount;
    }

    public Double getRating() {
        return mRating;
    }

    public Integer[] getGenreIds() {

        return mGenreIds;
    }
}
