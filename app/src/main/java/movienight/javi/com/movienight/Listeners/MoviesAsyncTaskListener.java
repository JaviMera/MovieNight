package movienight.javi.com.movienight.Listeners;

import movienight.javi.com.movienight.model.Page;

/**
 * Created by Javi on 10/21/2016.
 */

public interface MoviesAsyncTaskListener {

    void onCompleted(Integer totalPages, Page page);
}
