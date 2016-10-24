package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/24/2016.
 */

public class VoteCountFilterableItem implements FilterableItem {

    private Integer mVoteCount;

    public VoteCountFilterableItem(Integer voteCount) {

        mVoteCount = voteCount;
    }

    @Override
    public String getValue() {

        return "Searching movies with a minimum vote count of " + mVoteCount;
    }
}
