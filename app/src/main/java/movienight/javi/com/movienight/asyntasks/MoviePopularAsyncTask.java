package movienight.javi.com.movienight.asyntasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.jsonvalues.JSONFilmDiscover;
import movienight.javi.com.movienight.urls.AbstractUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javier on 10/25/2016.
 */

public class MoviePopularAsyncTask extends FilmPopularAsyncTask {

    public MoviePopularAsyncTask(FilmAsyncTaskListener listener) {
        super(listener);
    }

    @Override
    protected FilmBase createFilm(JSONObject jsonObject) throws JSONException {

        return Movie.fromJSON(jsonObject);
    }
}
