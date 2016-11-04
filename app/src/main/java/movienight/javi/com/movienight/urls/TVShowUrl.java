package movienight.javi.com.movienight.urls;

/**
 * Created by Javi on 11/4/2016.
 */

public class TVShowUrl extends AbstractUrl {

    private final String mUrl = "https://api.themoviedb.org/3/discover/tv?api_key=";
    private final String language = "language";
    private final String sort = "sort_by";
    private final String page = "page";
    private final String timezone = "timezone";
    private final String includeNullFirstAirDates = "include_null_first_air_dates";

    private String mLanguage;
    private String mSort;
    private String mPage;
    private String mGenres;
    private String mStartDate;
    private String mEndDate;
    private String mVoteCount;
    private String mRating;
    private String mTimezone;
    private String mFirstAirDates;

    public TVShowUrl(

        String language,
        String sort,
        String page,
        String genres,
        String startDate,
        String endDate,
        String voteCount,
        String rating,
        String timezone,
        String firstAirDates
    )
    {
        mLanguage = language;
        mSort = sort;
        mPage = page;
        mGenres = genres;
        mStartDate = startDate;
        mEndDate = endDate;
        mVoteCount = voteCount;
        mRating = rating;
        mTimezone = timezone;
        mFirstAirDates = firstAirDates;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public String getStartDate() {
        return mStartDate;
    }

    @Override
    public String toString() {

        String request = mUrl;
        request += mApiKeyParameter;
        request += "&" + language + "=" + mLanguage;
        request += "&" + sort + "=" + mSort;
        request += "&" + page + "+" + mPage;
        request += "&" + timezone + "+" + mTimezone;
        request += "&" + includeNullFirstAirDates + "=" + mFirstAirDates;

        return request;
    }
}
