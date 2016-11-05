package movienight.javi.com.movienight.model.MovieSortItems;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;

/**
 * Created by Javi on 11/4/2016.
 */

public class FirstAirDateAscending extends SortItemBase {

    public FirstAirDateAscending() {

        super("first_air_date.asc");
    }

    @Override
    public String toString() {

        return "First Air Date asc...";
    }
}
