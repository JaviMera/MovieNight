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

import movienight.javi.com.movienight.listeners.FilmAsyncTaskListener;
import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.model.jsonvalues.JSONFilm;
import movienight.javi.com.movienight.model.jsonvalues.JSONMovie;
import movienight.javi.com.movienight.model.jsonvalues.JSONMovieDiscover;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javi on 10/21/2016.
 */


public class MoviesAsyncTask extends AsyncTask<AbstractUrl, Void, Page> {

    private FilmAsyncTaskListener mListener;
    private Integer mTotalPages;

    public MoviesAsyncTask(FilmAsyncTaskListener listener) {

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
            Date startDateRequest = null;
            Date endDateRequest = null;

            if(!(url.getStartDate().isEmpty() && url.getEndDate().isEmpty())) {

                startDateRequest = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT).parse(url.getStartDate());
                endDateRequest = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT).parse(url.getEndDate());
            }

            Response response = call.execute();
            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);
            mTotalPages = jsonObject.getInt(JSONMovieDiscover.TOTAL_PAGES_KEY);

            JSONArray resultsArray = jsonObject.getJSONArray(JSONMovieDiscover.RESULTS_KEY);
            List<Film> movies = new LinkedList<>();

            for(int result = 0 ; result < resultsArray.length(); result++) {

                JSONObject currentJSONObject = resultsArray.getJSONObject(result);

                String releaseDateJson = currentJSONObject.getString(JSONMovie.RELEASE_DATE_KEY);

                if(startDateRequest != null && endDateRequest != null) {

                    if(!releaseDateJson.isEmpty()) {

                        Date releaseDate = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT).parse(releaseDateJson);

                        if ((releaseDate.compareTo(startDateRequest) == 0 || releaseDate.compareTo(endDateRequest) == 0)
                                || (releaseDate.compareTo(startDateRequest) == 1 && releaseDate.compareTo(endDateRequest) == -1)) {

                            movies.add(Movie.fromJSON(currentJSONObject));
                        }
                    }
                }

                else
                    movies.add(Movie.fromJSON(currentJSONObject));
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

        mListener.onCompleted(mTotalPages, page.getFilms());
    }
}
