package movienight.javi.com.movienight.listeners;

import movienight.javi.com.movienight.model.FilmBase;

/**
 * Created by Javi on 10/21/2016.
 */

public interface FilmAsyncTaskListener {

    void onCompleted(Integer totalPages, FilmBase[] films);
}
