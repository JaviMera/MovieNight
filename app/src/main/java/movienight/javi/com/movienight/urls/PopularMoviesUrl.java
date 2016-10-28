package movienight.javi.com.movienight.urls;

/**
 * Created by Javier on 10/25/2016.
 */

public class PopularMoviesUrl extends AbstractUrl {

    private final String mUrl = "https://api.themoviedb.org/3/movie/popular";
    private final String mApiKeyField = "?api_key";
    private final String mPageField = "page";
    private final String mLanguageField = "&language";
    private final String mLanguageParameter = "en-US";
    private final int mPageNumber = 1;

    public PopularMoviesUrl() {

    }

    @Override
    public String toString() {

        String urlRequest = mUrl;
        urlRequest += mApiKeyField + "=" + mApiKeyParameter;
        urlRequest += mLanguageField + "=" + mLanguageParameter;
        urlRequest += mPageField + "=" + mPageNumber;

        return urlRequest;
    }
}
