package movienight.javi.com.movienight.asyntasks;

import movienight.javi.com.movienight.model.FilmCatetory;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.GenreListener;

/**
 * Created by Javi on 10/19/2016.
 */

public class MovieGenresAsyncTask extends GenreAsyncTaskBase {

    public MovieGenresAsyncTask(GenreListener listener) {
        super(listener);
    }

    @Override
    protected void onPostExecute(Genre[] genres) {

        mListener.onTaskCompleted(FilmCatetory.MOVIE, genres);
    }
}
