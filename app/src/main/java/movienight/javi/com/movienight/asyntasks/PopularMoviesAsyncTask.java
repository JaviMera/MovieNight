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
import movienight.javi.com.movienight.model.jsonvalues.JSONMovieDiscover;
import movienight.javi.com.movienight.urls.AbstractUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javier on 10/25/2016.
 */

public class PopularMoviesAsyncTask extends AsyncTask<AbstractUrl, Void, Movie[]> {

    private FilmAsyncTaskListener mListener;
    private int mTotalPages;
    public PopularMoviesAsyncTask(FilmAsyncTaskListener listener) {

        mListener = listener;
    }

    @Override
    protected Movie[] doInBackground(AbstractUrl... abstractUrls) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(abstractUrls[0].toString())
                .build();

        Call call = client.newCall(request);

        try
        {
            Response response = call.execute();
            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray resultsArray = jsonObject.getJSONArray(JSONMovieDiscover.RESULTS_KEY);
            List<FilmBase> movies = new LinkedList<>();

            for(int result = 0 ; result < resultsArray.length(); result++) {

                JSONObject currentJSONObject = resultsArray.getJSONObject(result);
                FilmBase newMovie = Movie.fromJSON(currentJSONObject);

                movies.add(newMovie);
            }

            mTotalPages = jsonObject.getInt("page");
            return movies.toArray(new Movie[movies.size()]);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {

        mListener.onCompleted(mTotalPages, movies);
    }
}
