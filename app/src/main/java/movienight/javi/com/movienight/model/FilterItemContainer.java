package movienight.javi.com.movienight.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;

/**
 * Created by Javi on 11/3/2016.
 */

public class FilterItemContainer {

    private Map<Integer, List<FilterableItem>> mFilterItemMap;

    public FilterItemContainer() {

        mFilterItemMap = new LinkedHashMap<>();
        mFilterItemMap.put(-1, new ArrayList<FilterableItem>());
        mFilterItemMap.put(1, new ArrayList<FilterableItem>());
        mFilterItemMap.put(2, new ArrayList<FilterableItem>());
        mFilterItemMap.put(3, new ArrayList<FilterableItem>());
        mFilterItemMap.put(4, new ArrayList<FilterableItem>());
    }

    public void put(Integer key, List<FilterableItem> items) {

        mFilterItemMap.put(key, items);
    }

    public Collection<List<FilterableItem>> getAll() {

        return mFilterItemMap.values();
    }

    public List<FilterableItem> get(Integer key) {

        return mFilterItemMap.get(key);
    }
}
