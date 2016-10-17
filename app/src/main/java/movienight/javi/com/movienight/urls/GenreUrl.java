package movienight.javi.com.movienight.urls;

/**
 * Created by Javi on 10/16/2016.
 */

public class GenreUrl extends AbstractUrl {

    private final String mLanguage = "&language=en-US";
    private final String mUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=";

    public GenreUrl() {}

    @Override
    public String toString() {

        String url = mUrl;
        url += mApiKey;
        url += mLanguage;

        return url;
    }
}
