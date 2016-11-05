package movienight.javi.com.movienight.model.MovieSortItems;

/**
 * Created by Javi on 11/2/2016.
 */

public class ReleaseDateDescending extends SortItemBase {


    public ReleaseDateDescending() {

        super("release_date.desc");
    }

    @Override
    public String toString() {

        return "Release Date desc...";
    }
}
