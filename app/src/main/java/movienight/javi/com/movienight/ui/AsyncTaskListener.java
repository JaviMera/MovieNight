package movienight.javi.com.movienight.ui;

/**
 * Created by Javi on 10/18/2016.
 */

public interface AsyncTaskListener<T> {

    void onTaskCompleted(T[] result);
}
