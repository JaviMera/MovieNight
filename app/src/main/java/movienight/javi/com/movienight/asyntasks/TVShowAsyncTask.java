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
import movienight.javi.com.movienight.model.FilmBase;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.model.TVShow;
import movienight.javi.com.movienight.model.jsonvalues.JSONFilmDiscover;
import movienight.javi.com.movienight.model.jsonvalues.JSONTVShow;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.TVShowUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javi on 11/4/2016.
 */
public class TVShowAsyncTask extends AsyncTask<AbstractUrl, Void, Page> {

    private FilmAsyncTaskListener mListener;
    private Integer mTotalPages;

    public TVShowAsyncTask(FilmAsyncTaskListener listener) {

        mListener = listener;
    }

    @Override
    protected Page doInBackground(AbstractUrl... params) {

        TVShowUrl url = (TVShowUrl)params[0];
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
            mTotalPages = jsonObject.getInt(JSONFilmDiscover.TOTAL_PAGES_KEY);

            JSONArray resultsArray = jsonObject.getJSONArray(JSONFilmDiscover.RESULTS_KEY);
            List<FilmBase> films = new LinkedList<>();

            for(int result = 0 ; result < resultsArray.length(); result++) {

                JSONObject currentJSONObject = resultsArray.getJSONObject(result);

                String releaseDateJson = currentJSONObject.getString(JSONTVShow.RELEASE_DATE_KEY);

                if(startDateRequest != null && endDateRequest != null) {

                    if(!releaseDateJson.isEmpty()) {

                        Date releaseDate = new SimpleDateFormat(ActivityExtras.RELEASE_DATE_FORMAT).parse(releaseDateJson);

                        if ((releaseDate.compareTo(startDateRequest) == 0 || releaseDate.compareTo(endDateRequest) == 0)
                                || (releaseDate.compareTo(startDateRequest) == 1 && releaseDate.compareTo(endDateRequest) == -1)) {

                            films.add(TVShow.fromJSON(currentJSONObject));
                        }
                    }
                }

                else
                    films.add(TVShow.fromJSON(currentJSONObject));
            }

            int pageNumber = jsonObject.getInt("page");

            return new Page(pageNumber, films.toArray(new FilmBase[films.size()]));
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
