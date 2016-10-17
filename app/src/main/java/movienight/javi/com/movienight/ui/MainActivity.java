package movienight.javi.com.movienight.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.jsonvalues.JSONGenre;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.GenreUrl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GenreUrl genresUrl = new GenreUrl();

        try
        {
            List<Genre> genres = new GenreAsyncTask()
                .execute(genresUrl)
                .get();

            Toast.makeText(this, String.valueOf(genres.size()), Toast.LENGTH_SHORT).show();
        }
        catch (InterruptedException e) {
        }
        catch (ExecutionException e) {
        }
    }

    private class GenreAsyncTask extends AsyncTask<AbstractUrl, Void, List<Genre>> {

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
                genresResult = getGenres(response.body().string());
            }
            catch (IOException e)
            {
            }
            catch (JSONException e)
            {
            }

            return genresResult;
        }

        private List<Genre> getGenres(String jsonString) throws JSONException{

            JSONObject genresObject = new JSONObject(jsonString);
            JSONArray dataArray = genresObject.getJSONArray(JSONGenre.OBJECT_KEY);
            Genre[] genres = new Genre[dataArray.length()];

            for(int i = 0 ; i < dataArray.length() ; i++) {

                Integer genreId = dataArray.getJSONObject(i).getInt(JSONGenre.ID_KEY);
                String genreDesc = dataArray.getJSONObject(i).getString(JSONGenre.NAME_KEY);

                genres[i] = new Genre(genreId, genreDesc);
            }

            return Arrays.asList(genres);
        }
    }
}
