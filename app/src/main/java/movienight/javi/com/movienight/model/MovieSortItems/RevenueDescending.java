package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class RevenueDescending extends SortItemBase {

    public RevenueDescending() {

        super("revenue.desc");
    }

    @Override
    public String toString() {

        return "Revenue desc...";
    }
}
