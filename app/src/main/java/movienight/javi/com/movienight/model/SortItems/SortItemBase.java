package movienight.javi.com.movienight.model.SortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public abstract class SortItemBase {

    private final String mSortName;

    protected SortItemBase(String name) {

        mSortName = name;
    }

    public String getName() {

        return mSortName;
    }
}
