package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import movienight.javi.com.movienight.R;

/**
 * Created by Javi on 10/19/2016.
 */

public class LoadDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        View loadingDialogLayout = LayoutInflater.from(context).inflate(R.layout.loading_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(loadingDialogLayout);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                // Don't close the dialog if the back button is pressed
                if((keyCode == KeyEvent.KEYCODE_BACK))
                    return true;

                return false;
            }
        });

        return dialog;
    }
}
