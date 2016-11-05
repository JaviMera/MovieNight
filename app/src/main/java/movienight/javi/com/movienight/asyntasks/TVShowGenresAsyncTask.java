package movienight.javi.com.movienight.asyntasks;

import movienight.javi.com.movienight.model.FilmCatetory;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.GenreListener;

/**
 * Created by Javi on 11/5/2016.
 */

public class TVShowGenresAsyncTask extends GenreAsyncTaskBase {

    public TVShowGenresAsyncTask(GenreListener listener) {

        super(listener);
    }

    @Override
    protected void onPostExecute(Genre[] genres) {

        mListener.onTaskCompleted(FilmCatetory.TV_SHOW, genres);
    }
}
