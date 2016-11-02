package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.model.NullFilterableItem;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

/**
 * Created by Javier on 10/27/2016.
 */

public abstract class FilterDialogBase extends DialogFragment {

    protected FilterItemAddedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (SearchActivity)context;
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
