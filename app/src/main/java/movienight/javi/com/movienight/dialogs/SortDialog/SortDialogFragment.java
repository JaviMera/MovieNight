package movienight.javi.com.movienight.dialogs.SortDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.SortItemRecyclerAdapter;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.model.SortItem;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 11/2/2016.
 */

public class SortDialogFragment extends FilterDialogBase {

    private List<SortItem> mItems;

    @BindView(R.id.sortRecyclerView) RecyclerView mSortRecyclerView;

    public static SortDialogFragment newInstance(List<SortItem> items) {

        SortDialogFragment dialog = new SortDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.SORT_OPTIONS_KEY, (ArrayList)items);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = getArguments().getParcelableArrayList(ActivityExtras.SORT_OPTIONS_KEY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.sort_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());
        dialog.setCanceledOnTouchOutside(true);

        String[] sortItems = context.getResources().getStringArray(R.array.sort_options_array);
        List<SortItem> items = new ArrayList<>();

        for(String item : sortItems) {

            items.add(new SortItem(item));
        }

        SortItemRecyclerAdapter adapter = new SortItemRecyclerAdapter(context, items);
        mSortRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        mSortRecyclerView.setLayoutManager(layoutManager);

        mSortRecyclerView.setHasFixedSize(true);

        return dialog;
    }
}
