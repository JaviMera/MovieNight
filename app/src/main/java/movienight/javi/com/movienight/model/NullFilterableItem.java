package movienight.javi.com.movienight.model;

/**
 * Created by Javier on 10/27/2016.
 */

public class NullFilterableItem implements FilterableItem {

    @Override
    public Object getObject() {
        return null;
    }

    @Override
    public String getValue() {
        return "";
    }
}
