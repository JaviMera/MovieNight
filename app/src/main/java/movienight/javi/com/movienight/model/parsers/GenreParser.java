package movienight.javi.com.movienight.model.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/16/2016.
 */

public class GenreParser implements JSONParser<Genre> {

    private static final String JSON_GENRE_OBJECT = "genres";
    private static final String JSON_GENRE_ID = "id";
    private static final String JSON_GENRE_NAME = "name";

    @Override
    public List<Genre> parse(String jsonString) throws JSONException{

        JSONObject genresObject = new JSONObject(jsonString);
        JSONArray dataArray = genresObject.getJSONArray(JSON_GENRE_OBJECT);
        Genre[] genres = new Genre[dataArray.length()];

        for(int i = 0 ; i < dataArray.length() ; i++) {

            Integer genreId = dataArray.getJSONObject(i).getInt(JSON_GENRE_ID);
            String genreDesc = dataArray.getJSONObject(i).getString(JSON_GENRE_NAME);

            genres[i] = new Genre(genreId, genreDesc);
        }

        return Arrays.asList(genres);
    }
}
