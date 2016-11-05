package movienight.javi.com.movienight.model.FilterItems;

/**
 * Created by Javier on 10/27/2016.
 */

public class NullFilterableItem implements FilterableItem {

    @Override
    public Object[] getValue() {
        return null;
    }
}
