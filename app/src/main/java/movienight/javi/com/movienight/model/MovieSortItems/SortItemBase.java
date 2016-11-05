package movienight.javi.com.movienight.model.MovieSortItems;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;

/**
 * Created by Javi on 11/2/2016.
 */

public abstract class SortItemBase implements FilterableItem<String> {

    private final String mSortName;

    protected SortItemBase(String name) {

        mSortName = name;
    }

    public String getName() {

        return mSortName;
    }

    @Override
    public String[] getValue() {
        return new String[]{mSortName};
    }

    @Override
    public void update(String newItem) {

    }
}
