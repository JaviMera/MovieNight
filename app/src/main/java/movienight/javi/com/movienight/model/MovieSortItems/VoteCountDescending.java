package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class VoteCountDescending extends SortItemBase {

    public VoteCountDescending() {

        super("vote_count.desc");
    }

    @Override
    public String toString() {

        return "Vote Count desc...";
    }
}
