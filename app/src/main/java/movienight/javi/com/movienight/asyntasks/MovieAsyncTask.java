package movienight.javi.com.movienight.asyntasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.urls.AbstractUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javi on 10/21/2016.
 */


public class MovieAsyncTask extends AsyncTask<AbstractUrl, Void, Page> {

    private MoviePagesAsyncTaskListener mListener;
    private Integer mTotalPages;

    public MovieAsyncTask(MoviePagesAsyncTaskListener listener) {

        mListener = listener;
    }

    @Override
    protected Page doInBackground(AbstractUrl... params) {

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
            mTotalPages = jsonObject.getInt("total_pages");

            JSONArray resultsArray = jsonObject.getJSONArray("results");
            Movie[] movies = new Movie[resultsArray.length()];

            for(int result = 0 ; result < resultsArray.length(); result++) {

                JSONObject currentJSONObject = resultsArray.getJSONObject(result);

                int movieId = currentJSONObject.getInt("id");
                String movieOverview = currentJSONObject.getString("overview");
                String movieOriginalTitle = currentJSONObject.getString("original_title");
                String movieTitle = currentJSONObject.getString("title");
                double moviePopularity = currentJSONObject.getDouble("popularity");
                int movieVotes = currentJSONObject.getInt("vote_count");
                double movieRating = currentJSONObject.getDouble("vote_average");

                JSONArray genreIdsArray = currentJSONObject.getJSONArray("genre_ids");
                int[] genreIds = new int[genreIdsArray.length()];

                for(int g = 0 ; g < genreIdsArray.length() ; g++) {

                    genreIds[g] = genreIdsArray.getInt(g);
                }

                Movie newMovie = new Movie(
                        movieId,
                        movieOverview,
                        movieOriginalTitle,
                        movieTitle,
                        moviePopularity,
                        movieVotes,
                        movieRating,
                        genreIds
                );

                movies[result] = newMovie;
            }

            int pageNumber = jsonObject.getInt("page");

            return new Page(pageNumber, movies);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Page page) {

        mListener.onCompleted(mTotalPages, page);
    }
}
