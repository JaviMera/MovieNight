package movienight.javi.com.movienight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.parsers.GenreParser;
import movienight.javi.com.movienight.model.urls.GenreUrl;
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
                                GenreParser parser = new GenreParser();
                                List<Genre> genres = parser.parse(json);

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
}
