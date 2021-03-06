package movienight.javi.com.movienight.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

import movienight.javi.com.movienight.fragments.FilmFragment;
import movienight.javi.com.movienight.fragments.MovieFragment;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.model.FilterItems.NullFilterableItem;

/**
 * Created by Javier on 10/27/2016.
 */

public abstract class FilterDialogBase extends DialogFragment {

    protected FilterItemAddedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FilmFragment)getTargetFragment();
    }

    protected DialogInterface.OnKeyListener onBackButtonPressed() {

        return new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {

                    mListener.onFilterItemCreated(-1, new NullFilterableItem[]{});
                }

                return false;
            }
        };
    }
}
