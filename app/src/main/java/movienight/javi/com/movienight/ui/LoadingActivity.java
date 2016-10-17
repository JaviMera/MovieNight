package movienight.javi.com.movienight.ui;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.jsonvalues.JSONGenre;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.GenreUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.loadingActivityProgressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        ButterKnife.bind(this);

        GenreUrl genresUrl = new GenreUrl();

        new GenreAsyncTask(mProgressBar, mProgressBar.getMax())
            .execute(genresUrl);
    }

    private class GenreAsyncTask extends AsyncTask<AbstractUrl, Integer, List<Genre>> {

        private ProgressBar mBar;
        private int mProgressBarMax;

        public GenreAsyncTask(ProgressBar bar, int max) {
            mBar = bar;
            mProgressBarMax = max;
        }

        @Override
        protected List<Genre> doInBackground(AbstractUrl... abstractUrls) {

            List<Genre> genresResult = new ArrayList<>();

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

                Integer progress = 0;
                for(int i = 0 ; i < dataArray.length() ; i++) {

                    Integer genreId = dataArray.getJSONObject(i).getInt(JSONGenre.ID_KEY);
                    String genreDesc = dataArray.getJSONObject(i).getString(JSONGenre.NAME_KEY);

                    genres[i] = new Genre(genreId, genreDesc);

                    if(i == dataArray.length() - 1) {

                        Integer finalProgress = progress + (mProgressBarMax - progress);
                        publishProgress(finalProgress);
                    }
                    else {

                        progress += mProgressBarMax / dataArray.length();
                        publishProgress(progress);
                    }

                    Thread.sleep(50);
                }

                genresResult = Arrays.asList(genres);
            }
            catch (IOException e)
            {
            }
            catch (JSONException e)
            {
            }
            catch (InterruptedException e)
            {
            }

            return genresResult;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mBar.setProgress(values[0]);
        }
    }
}
