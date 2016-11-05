package movienight.javi.com.movienight.asyntasks;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import movienight.javi.com.movienight.model.FilmCatetory;
import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.jsonvalues.JSONGenre;
import movienight.javi.com.movienight.ui.GenreListener;
import movienight.javi.com.movienight.urls.AbstractUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
