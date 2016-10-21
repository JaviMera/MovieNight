package movienight.javi.com.movienight.ui.MoviesActivity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        MovieUrlBuilder builder = new MovieUrlBuilder();
        builder
                .withPageNumber("1")
                .withGenres("12")
                .withStartReleaseDate("2015-10-1")
                .withEndReleaseDate("2016-10-1")
                .withVoteCount("1000")
                .withRating("5.0");

        MovieUrl url = builder.createMovieUrl();

        new MovieAsyncTask().execute(url);
    }

    private class MovieAsyncTask extends AsyncTask<AbstractUrl, Void, Void> {

        @Override
        protected Void doInBackground(AbstractUrl... params) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(params[0].toString())
                .build();

            Call call = client.newCall(request);

            try
            {
                Response response = call.execute();
                String jsonString = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonString);
                int numberOfPages = jsonObject.getInt("total_pages");

                Log.v("Size of results", String.valueOf(numberOfPages));
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

