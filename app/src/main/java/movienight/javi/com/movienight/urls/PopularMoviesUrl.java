package movienight.javi.com.movienight.urls;

/**
 * Created by Javier on 10/25/2016.
 */

public class PopularMoviesUrl extends AbstractUrl {

    private final String mUrl = "https://api.themoviedb.org/3/movie/popular";
    private final String mApiKeyField = "?api_key";
    private final String mLanguageField = "&language";
    private final String mLanguageParameter = "en-US";

    @Override
    public String toString() {

        String urlRequest = mUrl;
        urlRequest += mApiKeyField + "=" + mApiKeyParameter;
        urlRequest += mLanguageField + "=" + mLanguageParameter;

        return urlRequest;
    }
}
