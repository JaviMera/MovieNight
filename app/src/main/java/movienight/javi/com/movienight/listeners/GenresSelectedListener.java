package movienight.javi.com.movienight.listeners;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/22/2016.
 */

public interface GenresSelectedListener {

    void onGenreSelectionCompleted(Genre[] genres);
}
