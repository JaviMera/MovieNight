package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class ReleaseDateAscending extends SortItemBase {

    public ReleaseDateAscending() {

        super("release_date.asc");
    }

    @Override
    public String toString() {

        return "Release Date asc...";
    }
}
