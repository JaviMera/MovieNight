package movienight.javi.com.movienight.dialogs.GenresDialog;

import android.content.Context;

import java.util.List;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 11/2/2016.
 */

public interface GenresDialogFragmentView {

    void setRecyclerAdapter(Context context, List<Genre> genres);
    void setRecyclerManager(Context context);
    void setRecyclerSize(boolean fixedSize);
}
