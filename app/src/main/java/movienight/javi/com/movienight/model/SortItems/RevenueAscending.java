package movienight.javi.com.movienight.model.SortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class RevenueAscending extends SortItemBase {

    public RevenueAscending() {

        super("revenue.asc");
    }

    @Override
    public String toString() {

        return "Revenue asc...";
    }
}
