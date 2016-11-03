package movienight.javi.com.movienight.asyntasks;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import movienight.javi.com.movienight.model.FilterItems.Genre;
import movienight.javi.com.movienight.model.jsonvalues.JSONGenre;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.urls.AbstractUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javi on 10/19/2016.
 */

public class GenreAsyncTask extends AsyncTask<AbstractUrl, Integer, Genre[]> {

    private AsyncTaskListener mListener;
    private FragmentManager mFragmentManager;

    public GenreAsyncTask(FragmentManager fragmentManager, AsyncTaskListener delegate) {
        mListener = delegate;
        mFragmentManager = fragmentManager;
    }

    @Override
    protected Genre[] doInBackground(AbstractUrl... abstractUrls) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(abstractUrls[0].toString())
                .build();

        Call call = client.newCall(request);

        try
        {
            Response response = call.execute();
            String jsonString = response.body().string();

            JSONObject genresObject = new JSONObject(jsonString);
            JSONArray dataArray = genresObject.getJSONArray(JSONGenre.OBJECT_KEY);
            Genre[] genres = new Genre[dataArray.length()];

            for(int i = 0 ; i < dataArray.length() ; i++) {

                Integer genreId = dataArray.getJSONObject(i).getInt(JSONGenre.ID_KEY);
                String genreDesc = dataArray.getJSONObject(i).getString(JSONGenre.NAME_KEY);

                genres[i] = new Genre(genreId, genreDesc);
            }

            return genres;
        }
        catch (IOException e)
        {
        }
        catch (JSONException e)
        {
        }

        return null;
    }

    @Override
    protected void onPostExecute(Genre[] genres) {

        mListener.onTaskCompleted(genres);
    }
}
