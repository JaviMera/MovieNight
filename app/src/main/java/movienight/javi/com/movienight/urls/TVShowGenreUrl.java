package movienight.javi.com.movienight.urls;

/**
 * Created by Javi on 11/4/2016.
 */
public class TVShowGenreUrl extends AbstractUrl{

    private final String mLanguage = "&language=en-US";
    private final String mUrl = "https://api.themoviedb.org/3/genre/tv/list?api_key=";

    @Override
    public String toString() {

        String url = mUrl;
        url += mApiKeyParameter;
        url += mLanguage;

        return url;
    }
}
