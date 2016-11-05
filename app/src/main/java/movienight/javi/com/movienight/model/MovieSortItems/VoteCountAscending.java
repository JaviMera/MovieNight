package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class VoteCountAscending extends SortItemBase {

    public VoteCountAscending() {

        super("vote_count.asc");
    }

    @Override
    public String toString() {

        return "Vote Count asc...";
    }
}
