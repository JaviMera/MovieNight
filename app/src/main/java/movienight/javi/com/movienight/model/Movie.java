package movienight.javi.com.movienight.model;

import android.graphics.Bitmap;
import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import movienight.javi.com.movienight.model.jsonvalues.JSONFilm;
import movienight.javi.com.movienight.model.jsonvalues.JSONMovie;

/**
 * Created by Javi on 10/21/2016.
 */
public class Movie extends FilmBase {

    public Movie(Parcel in) {
        super(in);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public static FilmBase fromJSON(JSONObject jsonObject) throws JSONException{

        int movieId = jsonObject.getInt(JSONFilm.ID_KEY);
        String movieOverview = jsonObject.getString(JSONFilm.OVERVIEW_KEY);
        String movieTitle = jsonObject.getString(JSONMovie.TITLE_KEY);
        String movieReleaseDate = jsonObject.getString(JSONMovie.RELEASE_DATE_KEY);

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

        return new Movie(
            movieId,
            movieOverview,
            movieTitle,
            movieReleaseDate,
            moviePopularity,
            movieVotes,
            movieRating,
            genreIds,
            moviePosterPath,
            null
        );
    }

    public Movie(
        int id,
        String overview,
        String title,
        String releaseDate,
        double popularity,
        int voteCount,
        double rating,
        int[] genreIds,
        String posterPath,
        Bitmap poster)
    {
        super(
            id,
            overview,
            title,
            releaseDate,
            popularity,
            voteCount,
            rating,
            genreIds,
            posterPath,
            poster
        );
    }
}
