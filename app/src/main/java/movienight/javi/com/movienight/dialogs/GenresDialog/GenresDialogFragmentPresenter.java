package movienight.javi.com.movienight.dialogs.GenresDialog;

import android.content.Context;

import java.util.List;

import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 11/2/2016.
 */

public class GenresDialogFragmentPresenter {

    private GenresDialogFragmentView mView;

    public GenresDialogFragmentPresenter(GenresDialogFragmentView view) {

        mView = view;
    }

    public void setRecyclerAdapter(Context context, List<Genre> genres) {

        mView.setRecyclerAdapter(context, genres);
    }

    public void setRecyclerManager(Context context) {

        mView.setRecyclerManager(context);
    }

    public void setRecyclerSize(boolean fixedSize) {

        mView.setRecyclerSize(fixedSize);
    }
}
