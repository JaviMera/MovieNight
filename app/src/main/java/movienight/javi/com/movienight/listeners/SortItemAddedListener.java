package movienight.javi.com.movienight.listeners;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;

/**
 * Created by Javi on 11/4/2016.
 */

public interface SortItemAddedListener {

    void onSortItemAdded(Integer key, FilterableItem item);
}
