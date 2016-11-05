package movienight.javi.com.movienight.model;

import java.util.LinkedHashMap;
import java.util.Map;

import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.MovieSortItems.FirstAirDateAscending;
import movienight.javi.com.movienight.model.MovieSortItems.FirstAirDateDescending;
import movienight.javi.com.movienight.model.MovieSortItems.PopularityAscending;
import movienight.javi.com.movienight.model.MovieSortItems.PopularityDescending;
import movienight.javi.com.movienight.model.MovieSortItems.RatingAscending;
import movienight.javi.com.movienight.model.MovieSortItems.RatingDescending;
import movienight.javi.com.movienight.model.MovieSortItems.ReleaseDateAscending;
import movienight.javi.com.movienight.model.MovieSortItems.ReleaseDateDescending;
import movienight.javi.com.movienight.model.MovieSortItems.RevenueAscending;
import movienight.javi.com.movienight.model.MovieSortItems.VoteCountAscending;
import movienight.javi.com.movienight.model.MovieSortItems.VoteCountDescending;

/**
 * Created by Javi on 11/3/2016.
 */

public class SortItemContainer {

    private Map<String, FilterableItem> mSortItemsMap;

    public SortItemContainer() {

        mSortItemsMap = new LinkedHashMap<>();
        mSortItemsMap.put("Popularity Ascending", new PopularityAscending());
        mSortItemsMap.put("Popularity Descending", new PopularityDescending());
        mSortItemsMap.put("Release Date Ascending", new ReleaseDateAscending());
        mSortItemsMap.put("Release Date Descending", new ReleaseDateDescending());
        mSortItemsMap.put("Revenue Ascending", new RevenueAscending());
        mSortItemsMap.put("Revenue Descending", new ReleaseDateDescending());
        mSortItemsMap.put("Rating Ascending", new RatingAscending());
        mSortItemsMap.put("Rating Descending", new RatingDescending());
        mSortItemsMap.put("Vote Count Asending", new VoteCountAscending());
        mSortItemsMap.put("Vote Count Descending", new VoteCountDescending());
        mSortItemsMap.put("First Air Date Ascending", new FirstAirDateAscending());
        mSortItemsMap.put("First Air Date Descending", new FirstAirDateDescending());
    }

    public FilterableItem get(String key) {

        return mSortItemsMap.get(key);
    }
}
