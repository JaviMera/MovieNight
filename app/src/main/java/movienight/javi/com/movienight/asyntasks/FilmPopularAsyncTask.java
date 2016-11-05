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
import movienight.javi.com.movienight.model.jsonvalues.JSONFilmDiscover;
import movienight.javi.com.movienight.urls.AbstractUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javi on 11/5/2016.
 */

public abstract class FilmPopularAsyncTask extends AsyncTask<AbstractUrl, Void, FilmBase[]> {

    private FilmAsyncTaskListener mListener;
    private int mTotalPages;

    protected abstract FilmBase createFilm(JSONObject jsonObject) throws JSONException;

    public FilmPopularAsyncTask(FilmAsyncTaskListener listener) {

        mListener = listener;
    }

    @Override
    protected FilmBase[] doInBackground(AbstractUrl... abstractUrls) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(abstractUrls[0].toString())
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray resultsArray = jsonObject.getJSONArray(JSONFilmDiscover.RESULTS_KEY);
            mTotalPages = jsonObject.getInt(JSONFilmDiscover.TOTAL_PAGES_KEY);

            List<FilmBase> movies = new LinkedList<>();

            for (int result = 0; result < resultsArray.length(); result++) {

                JSONObject currentJSONObject = resultsArray.getJSONObject(result);
                FilmBase newMovie = createFilm(currentJSONObject);

                movies.add(newMovie);
            }

            return movies.toArray(new FilmBase[movies.size()]);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(FilmBase[] films) {

        mListener.onCompleted(mTotalPages, films);
    }
}
