package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class RatingAscending extends SortItemBase{

    public RatingAscending() {

        super("vote_average.asc");
    }

    @Override
    public String toString() {

        return "Rating asc...";
    }
}
