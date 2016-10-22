package movienight.javi.com.movienight.asyntasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.Listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javi on 10/21/2016.
 */


public class MovieAsyncTask extends AsyncTask<AbstractUrl, Void, Page> {

    private MoviesAsyncTaskListener mListener;
    private Integer mTotalPages;

    public MovieAsyncTask(MoviesAsyncTaskListener listener) {

        mListener = listener;
    }

    @Override
    protected Page doInBackground(AbstractUrl... params) {

        MovieUrl url = (MovieUrl)params[0];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.toString())
                .build();

        Call call = client.newCall(request);

        try
        {
            Date startDateRquest = new SimpleDateFormat("yyyy-M-dd").parse(url.getStartDate());
            Date endDateRequest = new SimpleDateFormat("yyyy-M-dd").parse(url.getEndDate());

            Response response = call.execute();
            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);
            mTotalPages = jsonObject.getInt("total_pages");

            JSONArray resultsArray = jsonObject.getJSONArray("results");
            List<Movie> movies = new LinkedList<>();

            for(int result = 0 ; result < resultsArray.length(); result++) {

                JSONObject currentJSONObject = resultsArray.getJSONObject(result);

                String release_date = currentJSONObject.getString("release_date");

                Date releaseDate = new SimpleDateFormat("yyyy-M-dd").parse(release_date);

                if((releaseDate.compareTo(startDateRquest) == 0 || releaseDate.compareTo(endDateRequest) == 0)
                    || (releaseDate.compareTo(startDateRquest) == 1 && releaseDate.compareTo(endDateRequest) == -1)){

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

                    movies.add(newMovie);
                }
            }

            int pageNumber = jsonObject.getInt("page");

            return new Page(pageNumber, movies.toArray(new Movie[movies.size()]));
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Page page) {

        mListener.onCompleted(mTotalPages, page);
    }
}
