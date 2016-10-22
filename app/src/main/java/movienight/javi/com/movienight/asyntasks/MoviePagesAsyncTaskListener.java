package movienight.javi.com.movienight.asyntasks;

import movienight.javi.com.movienight.model.Page;

/**
 * Created by Javi on 10/21/2016.
 */

public interface MoviePagesAsyncTaskListener {

    void onCompleted(Integer totalPages, Page page);
}
