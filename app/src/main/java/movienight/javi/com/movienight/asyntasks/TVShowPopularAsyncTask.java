package movienight.javi.com.movienight.asyntasks;

import org.json.JSONException;
import org.json.JSONObject;

import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.TVShow;

/**
 * Created by Javi on 11/5/2016.
 */
public class TVShowPopularAsyncTask extends FilmPopularAsyncTask{

    public TVShowPopularAsyncTask(FilmAsyncTaskListener listener) {

        super(listener);
    }

    @Override
    protected FilmBase createFilm(JSONObject jsonObject) throws JSONException {

        return TVShow.fromJSON(jsonObject);
    }
}
