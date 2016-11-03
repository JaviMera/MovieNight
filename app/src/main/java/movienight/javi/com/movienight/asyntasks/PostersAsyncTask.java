package movienight.javi.com.movienight.asyntasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import movienight.javi.com.movienight.listeners.MoviePostersListener;

/**
 * Created by Javier on 10/27/2016.
 */

public class PostersAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private FragmentManager mManager;
    private MoviePostersListener mListener;
    private String mPosterResolution;
    private String mPath;
    private Bitmap mDefault;

    public PostersAsyncTask(MoviePostersListener listener, FragmentManager manager, String posterResolution, Bitmap defaultBitmap) {

        mListener = listener;
        mManager = manager;
        mPosterResolution = posterResolution;
        mDefault = defaultBitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        mListener.onPostersCompleted(mPath, bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... posterPaths) {

        try {
            mPath = posterPaths[0];

            if(!mPath.isEmpty()) {

                URL imageurl = new URL("http://image.tmdb.org/t/p/" + mPosterResolution + "/" + mPath);
                return BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
            }
            else {

                return mDefault;
            }

        }
        catch (Exception e) {
        }

        return null;
    }
}
