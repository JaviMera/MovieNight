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
    public String getTitle() {

        return "With a popularity of: ";
    }

    @Override
    public String getValue() {

        return mVoteCount + " votes.";
    }
}
