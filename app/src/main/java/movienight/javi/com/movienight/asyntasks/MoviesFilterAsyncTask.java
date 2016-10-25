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

import movienight.javi.com.movienight.listeners.MoviesAsyncTaskListener;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.model.ReleaseDate;
import movienight.javi.com.movienight.model.jsonvalues.JSONMovie;
import movienight.javi.com.movienight.model.jsonvalues.JSONMovieDiscover;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javi on 10/21/2016.
 */


public class MoviesFilterAsyncTask extends AsyncTask<AbstractUrl, Void, Movie[]> {

    private MoviesAsyncTaskListener mListener;
    private Integer mTotalPages;

    public MoviesFilterAsyncTask(MoviesAsyncTaskListener listener) {

        mListener = listener;
    }

    @Override
    protected Movie[] doInBackground(AbstractUrl... params) {

        MovieUrl url = (MovieUrl)params[0];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.toString())
                .build();

        Call call = client.newCall(request);

        try
        {
            Date startDateRquest = new SimpleDateFormat(ReleaseDate.FORMAT).parse(url.getStartDate());
            Date endDateRequest = new SimpleDateFormat(ReleaseDate.FORMAT).parse(url.getEndDate());

            Response response = call.execute();
            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);
            mTotalPages = jsonObject.getInt(JSONMovieDiscover.TOTAL_PAGES_KEY);

            JSONArray resultsArray = jsonObject.getJSONArray(JSONMovieDiscover.RESULTS_KEY);
            List<Movie> movies = new LinkedList<>();

            for(int result = 0 ; result < resultsArray.length(); result++) {

                JSONObject currentJSONObject = resultsArray.getJSONObject(result);

                String release_date = currentJSONObject.getString(JSONMovie.RELEASE_DATE_KEY);

                Date releaseDate = new SimpleDateFormat(ReleaseDate.FORMAT).parse(release_date);

                if((releaseDate.compareTo(startDateRquest) == 0 || releaseDate.compareTo(endDateRequest) == 0)
                    || (releaseDate.compareTo(startDateRquest) == 1 && releaseDate.compareTo(endDateRequest) == -1)){

                    int movieId = currentJSONObject.getInt(JSONMovie.ID_KEY);
                    String movieOverview = currentJSONObject.getString(JSONMovie.OVERVIEW_KEY);
                    String movieOriginalTitle = currentJSONObject.getString(JSONMovie.ORIGINAL_TITLE_KEY);
                    String movieTitle = currentJSONObject.getString(JSONMovie.TITLE_KEY);
                    double moviePopularity = currentJSONObject.getDouble(JSONMovie.POPULARITY_KEY);
                    int movieVotes = currentJSONObject.getInt(JSONMovie.VOTE_COUNT_KEY);
                    double movieRating = currentJSONObject.getDouble(JSONMovie.VOTE_AVERAGE_KEY);

                    JSONArray genreIdsArray = currentJSONObject.getJSONArray(JSONMovie.GENRE_IDS_KEY);
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

//            int pageNumber = jsonObject.getInt("page");
//
//            return new Page(pageNumber, movies.toArray(new Movie[movies.size()]));

            return movies.toArray(new Movie[movies.size()]);
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
    protected void onPostExecute(Movie[] movies) {

        mListener.onCompleted(mTotalPages, movies);
    }
}
