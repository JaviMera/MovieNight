package movienight.javi.com.movienight.urls;

import android.view.LayoutInflater;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String toString() {

        String request = mUrl;
        request += mApiKey;
        request += "&" + language + "=" + mLanguage;
        request += "&" + sort + "=" + mSort;
        request += "&" + adult + "=" + mAdult;
        request += "&" + video + "=" + mVideo;
        request += "&" + page + "=" + mPage;
        request += "&" + minRelaseDate + "=" + mStartDate;
        request += "&" + maxReleaseDate + "=" + mEndDate;
        request += "&" + vote + "=" + mVoteCount;
        request += "&" + rating + "=" + mRating;
        request += "&" + genres + "=" + mGenres;

        return request;
    }
}
