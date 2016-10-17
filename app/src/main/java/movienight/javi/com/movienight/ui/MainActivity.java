package movienight.javi.com.movienight.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.jsonvalues.JSONGenre;
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
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(genresUrl.toString())
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Failed request", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                String json = response.body().string();
                                List<Genre> genres = getGenres(json);

                                Toast.makeText(getApplicationContext(), Integer.toString(genres.size()), Toast.LENGTH_SHORT).show();
                            }
                            catch (IOException e) {
                            }
                            catch (JSONException e) {
                            }
                        }
                    });
                }
            }
        });
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
