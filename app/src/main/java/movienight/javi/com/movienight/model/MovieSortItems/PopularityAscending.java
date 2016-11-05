package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class PopularityAscending extends SortItemBase {

    public PopularityAscending() {
        super("popularity.asc");
    }

    @Override
    public String toString() {

        return "Popularity asc..";
    }
}
