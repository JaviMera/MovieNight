package movienight.javi.com.movienight.urls;

/**
 * Created by Javi on 11/5/2016.
 */

public abstract class FilmPopularUrl extends AbstractUrl {

    private final String mUrl = "https://api.themoviedb.org/3/";
    private final String mPopular = "popular";
    private final String mApiKeyField = "?api_key";
    private final String mPageField = "&page";
    private final String mLanguageField = "&language";
    private final String mLanguageParameter = "en-US";

    private final int mPageNumber;
    private final String mCategory;

    protected FilmPopularUrl(int pageNumber, String category) {

        mPageNumber = pageNumber;
        mCategory = category;
    }

    @Override
    public String toString() {

        String urlRequest = mUrl;
        urlRequest += mCategory +"/";
        urlRequest += mPopular;
        urlRequest += mApiKeyField + "=" + mApiKeyParameter;
        urlRequest += mLanguageField + "=" + mLanguageParameter;
        urlRequest += mPageField + "=" + mPageNumber;

        return urlRequest;
    }
}
