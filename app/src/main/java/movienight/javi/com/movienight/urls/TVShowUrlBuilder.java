package movienight.javi.com.movienight.urls;

/**
 * Created by Javi on 11/4/2016.
 */

public class TVShowUrlBuilder {

    private final String DEFAULT_SORT = "popularity.desc";
    private final String DEFAULT_PAGE = "1";
    private final String DEFAULT_LANGUAGE = "en-US";
    private final String DEFAULT_TIMZONE = "America/New_York";
    private final String DEFAULT_FIRST_AIR_DATE = "false";

    private String mSort;
    private String mLanguage;
    private String mTimezone;
    private String mFirstAirDate;
    private String mPage;
    private String mGenreIds;
    private String mStartReleaseDate;
    private String mEndReleaseDate;
    private String mVoteCount;
    private String mRating;

    public TVShowUrlBuilder() {

        mLanguage = DEFAULT_LANGUAGE;
        mPage = DEFAULT_PAGE;
        mSort = DEFAULT_SORT;
        mTimezone = DEFAULT_TIMZONE;
        mFirstAirDate = DEFAULT_FIRST_AIR_DATE;
    }

    public TVShowUrlBuilder withPageNumber(String pageNumber) {

        mPage = pageNumber;
        return this;
    }

    public TVShowUrlBuilder withGenres(String ids) {

        mGenreIds = ids;
        return this;
    }

    public TVShowUrlBuilder withStartAirDate(String date) {

        mStartReleaseDate = date;
        return this;
    }

    public TVShowUrlBuilder withEndAirDate(String date) {

        mEndReleaseDate = date;
        return this;
    }

    public TVShowUrlBuilder withVoteCount(String voteCount) {

        mVoteCount = voteCount;
        return this;
    }

    public TVShowUrlBuilder withRating(String rating) {

        mRating = rating;
        return this;
    }

    public TVShowUrlBuilder sortBy(String sortBy) {

        mSort = sortBy;
        return this;
    }

    public TVShowUrl createTVShowUrl() {

        return new TVShowUrl(
            mLanguage,
            mSort,
            mPage,
            mGenreIds,
            mStartReleaseDate,
            mEndReleaseDate,
            mVoteCount,
            mRating,
            mTimezone,
            mFirstAirDate
        );
    }
}
