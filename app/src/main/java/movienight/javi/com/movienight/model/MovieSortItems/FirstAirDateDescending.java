package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/4/2016.
 */

public class FirstAirDateDescending extends SortItemBase {

    public FirstAirDateDescending() {

        super("first_air_date.desc");
    }

    @Override
    public String toString() {

        return "First Air Date desc...";
    }
}
