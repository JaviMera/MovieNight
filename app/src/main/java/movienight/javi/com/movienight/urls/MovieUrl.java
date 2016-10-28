package movienight.javi.com.movienight.urls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 10/21/2016.
 */

public class MovieUrl extends AbstractUrl {

    private final String mUrl = "https://api.themoviedb.org/3/discover/movie?api_key=";
    private final String language = "language";
    private final String sort = "sort_by";
    private final String adult = "include_adult";
    private final String video = "include_video";
    private final String page = "page";
    private final String minRelaseDate = "release_date.gte";
    private final String maxReleaseDate = "release_date.lte";
    private final String vote = "vote_count.gte";
    private final String rating = "vote_average.gte";
    private final String genres = "with_genres";

    private String mLanguage;
    private String mAdult;
    private String mSort;
    private String mVideo;
    private String mPage;
    private String mGenres;
    private String mStartDate;
    private String mEndDate;
    private String mVoteCount;
    private String mRating;

    public MovieUrl(
        String language,
        String sort,
        String adult,
        String video,
        String page,
        String genres,
        String startDate,
        String endDate,
        String voteCount,
        String rating
        )
    {
        mLanguage = language;
        mSort = sort;
        mAdult = adult;
        mVideo = video;
        mPage = page;
        mGenres = genres;
        mStartDate = startDate;
        mEndDate = endDate;
        mVoteCount = voteCount;
        mRating = rating;
    }

    public String getStartDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT);

        return mStartDate.isEmpty()
            ? formatter.format(new Date())
            : mStartDate;
    }

    public String getEndDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT);

        return mEndDate.isEmpty()
            ? formatter.format(new Date())
            : mEndDate;
    }

    @Override
    public String toString() {

        String request = mUrl;
        request += mApiKeyParameter;
        request += "&" + language + "=" + mLanguage;
        request += "&" + sort + "=" + mSort;
        request += "&" + adult + "=" + mAdult;
        request += "&" + video + "=" + mVideo;
        request += "&" + page + "=" + mPage;
        request += "&" + minRelaseDate + "=";
        if(!mStartDate.isEmpty())
             request += mStartDate;

        request += "&" + maxReleaseDate + "=";
        if(!mEndDate.isEmpty())
             request += mEndDate;

        request += "&" + vote + "=";
        if(!mVoteCount.isEmpty())
            request += mVoteCount;

        request += "&" + rating + "=";
        if(!mRating.isEmpty())
            request += mRating;

        request += "&" + genres + "=";
        if(!mGenres.isEmpty())
            request += mGenres;

        return request;
    }
}
