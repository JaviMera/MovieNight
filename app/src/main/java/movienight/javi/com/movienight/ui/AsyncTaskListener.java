package movienight.javi.com.movienight.ui;

import java.util.List;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/18/2016.
 */

public interface AsyncTaskListener<T> {

    void done(T[] genres);
}
