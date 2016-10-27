package movienight.javi.com.movienight.asyntasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import movienight.javi.com.movienight.listeners.MoviePostersListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Javier on 10/27/2016.
 */

public class PostersAsyncTask extends AsyncTask<Void, Void, Bitmap[]> {

    private MoviePostersListener mListener;

    public PostersAsyncTask(MoviePostersListener listener) {

        mListener = listener;
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {

        mListener.onPostersCompleted(bitmaps);
    }

    @Override
    protected Bitmap[] doInBackground(Void... voids) {

        List<Bitmap> posters = new ArrayList<>();

        try {
            URL imageurl = new URL("http://image.tmdb.org/t/p/original/mLrQMqyZgLeP8FrT5LCobKAiqmK.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
            posters.add(bitmap);

            return posters.toArray(new Bitmap[posters.size()]);

        } catch (Exception e) {
        }

        return null;
    }
}
