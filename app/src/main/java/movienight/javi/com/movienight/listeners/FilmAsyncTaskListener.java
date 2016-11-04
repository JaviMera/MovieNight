package movienight.javi.com.movienight.listeners;

import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;

/**
 * Created by Javi on 10/21/2016.
 */

public interface FilmAsyncTaskListener {

    void onCompleted(Integer totalPages, Film[] films);
}
