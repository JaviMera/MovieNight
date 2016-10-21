package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/21/2016.
 */

public class Page {

    private Integer mNumber;
    private Movie[] mMovies;

    public Page(Integer number, Movie[] movies) {

        mNumber = number;
        mMovies = movies;
    }

    public Integer getNumber() {
        return mNumber;
    }

    public Movie[] getMovies() {
        return mMovies;
    }
}
