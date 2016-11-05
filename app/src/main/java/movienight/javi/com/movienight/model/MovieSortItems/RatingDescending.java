package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class RatingDescending extends SortItemBase {

    public RatingDescending() {

        super("vote_average.desc");
    }

    @Override
    public String toString() {

        return "Rating desc...";
    }
}
