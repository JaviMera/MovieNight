package movienight.javi.com.movienight.asyntasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import movienight.javi.com.movienight.listeners.MoviePostersListener;

/**
 * Created by Javier on 10/27/2016.
 */

public class PostersAsyncTask extends AsyncTask<String, Void, Bitmap[]> {

    private FragmentManager mManager;
    private MoviePostersListener mListener;
    private String mPosterResolution;

    public PostersAsyncTask(MoviePostersListener listener, FragmentManager manager, String posterResolution) {

        mListener = listener;
        mManager = manager;
        mPosterResolution = posterResolution;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {

        mListener.onPostersCompleted(bitmaps);
    }

    @Override
    protected Bitmap[] doInBackground(String... posterPaths) {

        List<Bitmap> posters = new ArrayList<>();

        try {
            for(String path : posterPaths) {

                URL imageurl = new URL("http://image.tmdb.org/t/p/" + mPosterResolution + "/" + path);
                Bitmap bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
                posters.add(bitmap);
            }

            return posters.toArray(new Bitmap[posters.size()]);

        } catch (Exception e) {
        }

        return null;
    }
}
