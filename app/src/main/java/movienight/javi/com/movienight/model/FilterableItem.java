package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/24/2016.
 */

public interface FilterableItem<T> {

    T[] getValue();
    void update(T newItem);
}
