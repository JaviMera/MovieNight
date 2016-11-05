package movienight.javi.com.movienight.urls;

/**
 * Created by Javi on 10/16/2016.
 */

public class MovieGenreUrl extends AbstractUrl {

    private final String mLanguage = "&language=en-US";
    private final String mUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=";

    public MovieGenreUrl() {}

    @Override
    public String toString() {

        String url = mUrl;
        url += mApiKeyParameter;
        url += mLanguage;

        return url;
    }
}
