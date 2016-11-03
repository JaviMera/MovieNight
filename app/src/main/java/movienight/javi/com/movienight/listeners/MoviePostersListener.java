package movienight.javi.com.movienight.listeners;

import android.graphics.Bitmap;

/**
 * Created by Javier on 10/27/2016.
 */

public interface MoviePostersListener {

    void onPostersCompleted(String path, Bitmap posters);
}
