package movienight.javi.com.movienight.model;

import android.graphics.Bitmap;
import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import movienight.javi.com.movienight.model.jsonvalues.JSONFilm;
import movienight.javi.com.movienight.model.jsonvalues.JSONTVShow;

/**
 * Created by Javi on 11/4/2016.
 */

public class TVShow extends FilmBase {

    public TVShow(Parcel in) {

        super(in);
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public static FilmBase fromJSON(JSONObject jsonObject) throws JSONException{

        int movieId = jsonObject.getInt(JSONFilm.ID_KEY);
        String movieOverview = jsonObject.getString(JSONFilm.OVERVIEW_KEY);
        String movieTitle = jsonObject.getString(JSONTVShow.TITLE_KEY);
        String tvShowAirDate = jsonObject.getString(JSONTVShow.RELEASE_DATE_KEY);

        String moviePosterPath = jsonObject.isNull(JSONFilm.POSTER_PATH_KEY)
                ? ""
                : jsonObject.getString(JSONFilm.POSTER_PATH_KEY);

        double moviePopularity = jsonObject.getDouble(JSONFilm.POPULARITY_KEY);
        int movieVotes = jsonObject.getInt(JSONFilm.VOTE_COUNT_KEY);
        double movieRating = jsonObject.getDouble(JSONFilm.VOTE_AVERAGE_KEY);

        JSONArray genreIdsArray = jsonObject.getJSONArray(JSONFilm.GENRE_IDS_KEY);
        int[] genreIds = new int[genreIdsArray.length()];

        for(int g = 0 ; g < genreIdsArray.length() ; g++) {

            genreIds[g] = genreIdsArray.getInt(g);
        }

        return new TVShow(
            movieId,
            movieOverview,
            movieTitle,
            tvShowAirDate,
            moviePopularity,
            movieVotes,
            movieRating,
            genreIds,
            moviePosterPath,
            null
        );
    }

    public TVShow(
        int id,
        String overview,
        String name,
        String airDate,
        double popularity,
        int voteCount,
        double rating,
        int[] genreIds,
        String posterPath,
        Bitmap poster
    )
    {
        super(
            id,
            overview,
            name,
            airDate,
            popularity,
            voteCount,
            rating,
            genreIds,
            posterPath,
            poster
        );
    }

}
