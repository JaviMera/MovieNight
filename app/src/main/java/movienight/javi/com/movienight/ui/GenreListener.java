package movienight.javi.com.movienight.ui;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/18/2016.
 */

public interface GenreListener {

    void onTaskCompleted(Integer category, Genre[] result);
}
