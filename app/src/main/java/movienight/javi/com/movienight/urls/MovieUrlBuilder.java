package movienight.javi.com.movienight.urls;

/**
 * Created by Javi on 10/21/2016.
 */

public class MovieUrlBuilder {

    private final String DEFAULT_SORT = "popularity.desc";
    private final String DEFAULT_PAGE = "1";

    private String mSort;
    private String mLanguage;
    private String mAdult;
    private String mVideo;
    private String mPage;
    private String mGenreIds;
    private String mStartReleaseDate;
    private String mEndReleaseDate;
    private String mVoteCount;
    private String mRating;

    public MovieUrlBuilder() {

        mLanguage = "en-US";
        mAdult = "false";
        mVideo = "true";
        mSort = DEFAULT_SORT;
        mPage = DEFAULT_PAGE;
    }

    public MovieUrlBuilder withSortBy(String sortBy) {

        mSort = sortBy;
        return this;
    }

    public MovieUrlBuilder withPageNumber(String pageNumber) {

        mPage = pageNumber;
        return this;
    }

    public MovieUrlBuilder withGenres(String ids) {

        mGenreIds = ids;
        return this;
    }

    public MovieUrlBuilder withStartReleaseDate(String date) {

        mStartReleaseDate = date;
        return this;
    }

    public MovieUrlBuilder withEndReleaseDate(String date) {

        mEndReleaseDate = date;
        return this;
    }

    public MovieUrlBuilder withVoteCount(String voteCount) {

        mVoteCount = voteCount;
        return this;
    }

    public MovieUrlBuilder withRating(String rating) {

        mRating = rating;
        return this;
    }

    public MovieUrl createMovieUrl() {

        return new MovieUrl(
            mLanguage,
            mSort,
            mAdult,
            mVideo,
            mPage,
            mGenreIds,
            mStartReleaseDate,
            mEndReleaseDate,
            mVoteCount,
            mRating
        );
    }
}
