package movienight.javi.com.movienight.model.parsers;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Javi on 10/16/2016.
 */

public interface JSONParser<T> {

    List<T> parse(String jsonString) throws JSONException;
}
