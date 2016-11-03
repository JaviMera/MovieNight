package movienight.javi.com.movienight.model;

import java.util.LinkedHashMap;
import java.util.Map;

import movienight.javi.com.movienight.model.SortItems.PopularityAscending;
import movienight.javi.com.movienight.model.SortItems.PopularityDescending;
import movienight.javi.com.movienight.model.SortItems.RatingAscending;
import movienight.javi.com.movienight.model.SortItems.RatingDescending;
import movienight.javi.com.movienight.model.SortItems.ReleaseDateAscending;
import movienight.javi.com.movienight.model.SortItems.ReleaseDateDescending;
import movienight.javi.com.movienight.model.SortItems.RevenueAscending;
import movienight.javi.com.movienight.model.SortItems.SortItemBase;
import movienight.javi.com.movienight.model.SortItems.VoteCountAscending;
import movienight.javi.com.movienight.model.SortItems.VoteCountDescending;

/**
 * Created by Javi on 11/3/2016.
 */

public class SortItemContainer {

    private static Integer DEFAULT_SORT_ITEM = 2;

    private Map<Integer, SortItemBase> mSortItemsMap;

    public SortItemContainer() {

        mSortItemsMap = new LinkedHashMap<>();
        mSortItemsMap.put(1, new PopularityAscending());
        mSortItemsMap.put(2, new PopularityDescending());
        mSortItemsMap.put(3, new ReleaseDateAscending());
        mSortItemsMap.put(4, new ReleaseDateDescending());
        mSortItemsMap.put(5, new RevenueAscending());
        mSortItemsMap.put(6, new ReleaseDateDescending());
        mSortItemsMap.put(7, new RatingAscending());
        mSortItemsMap.put(8, new RatingDescending());
        mSortItemsMap.put(9, new VoteCountAscending());
        mSortItemsMap.put(10, new VoteCountDescending());
    }

    public SortItemBase getDefault() {

        return mSortItemsMap.get(DEFAULT_SORT_ITEM);
    }

    public SortItemBase get(Integer key) {

        return mSortItemsMap.get(key);
    }
}
