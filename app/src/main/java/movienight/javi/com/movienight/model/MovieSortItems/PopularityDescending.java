package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class PopularityDescending extends SortItemBase {

    public PopularityDescending() {

        super("popularity.desc");
    }

    @Override
    public String toString() {

        return "Popularity desc...";
    }
}
