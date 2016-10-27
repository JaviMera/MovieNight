package movienight.javi.com.movienight.listeners;

import movienight.javi.com.movienight.model.FilterableItem;

/**
 * Created by Javier on 10/27/2016.
 */

public interface FilterItemRemovedListener {

    void onFilterItemDeleted(FilterableItem itemRemoved);
}
