package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/21/2016.
 */

public class Page {

    private Integer mNumber;
    private FilmBase[] mFilms;

    public Page(Integer number, FilmBase[] films) {

        mNumber = number;
        mFilms = films;
    }

    public Integer getNumber() {
        return mNumber;
    }

    public FilmBase[] getFilms() {
        return mFilms;
    }
}
