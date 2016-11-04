package movienight.javi.com.movienight.model;

import android.graphics.Bitmap;
import android.os.Parcelable;

/**
 * Created by Javi on 11/4/2016.
 */

public interface Film extends Parcelable {

    int getId();
    String getName();
    String getOverview();
    String getReleaseDate();
    double getPopularity();
    int getVoteCount();
    double getRating();
    int[] getGenres();
    String getPosterPath();
    Bitmap getPoster();
    void setPoster(Bitmap poster);
}
