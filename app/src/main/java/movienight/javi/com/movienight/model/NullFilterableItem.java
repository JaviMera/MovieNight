package movienight.javi.com.movienight.model;

/**
 * Created by Javier on 10/27/2016.
 */

public class NullFilterableItem implements FilterableItem {

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void update(Object newItem) {

    }
}
