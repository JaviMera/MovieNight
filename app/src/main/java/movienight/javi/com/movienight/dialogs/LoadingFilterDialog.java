package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import movienight.javi.com.movienight.R;

/**
 * Created by Javier on 10/30/2016.
 */

public class LoadingFilterDialog extends DialogFragment {

    public static LoadingFilterDialog newInstance() {

        LoadingFilterDialog dialog = new LoadingFilterDialog();
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.LoadDialog);
        dialogBuilder.setView(view);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    private DialogInterface.OnKeyListener onBackButtonPressed() {

        return new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {

                    return true;
                }

                return false;
            }
        };
    }
}
