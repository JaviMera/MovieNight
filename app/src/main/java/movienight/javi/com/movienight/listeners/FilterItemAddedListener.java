package movienight.javi.com.movienight.listeners;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;

/**
 * Created by Javier on 10/25/2016.
 */

public interface FilterItemAddedListener {

    void onFilterItemCreated(Integer key, FilterableItem... item);
}
