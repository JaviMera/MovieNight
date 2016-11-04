package movienight.javi.com.movienight.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.SortRecyclerViewAdapter;
import movienight.javi.com.movienight.listeners.SortItemAddedListener;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 11/4/2016.
 */

public class SortDialogFragment extends FilterDialogBase implements SortItemAddedListener {

    private String[] mSortItems;

    @BindView(R.id.sortByRecyclerView)
    RecyclerView mSortRecyclerView;

    public static SortDialogFragment newInstance(String[] items) {

        SortDialogFragment fragment = new SortDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(ActivityExtras.SORT_OPTIONS_KEY, items);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSortItems = getArguments().getStringArray(ActivityExtras.SORT_OPTIONS_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.sort_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        SortRecyclerViewAdapter adapter = new SortRecyclerViewAdapter(
            context,
            new ArrayList<>(Arrays.asList(mSortItems)),
            this
        );

        mSortRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        mSortRecyclerView.setLayoutManager(layoutManager);
        mSortRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onSortItemAdded(Integer key, FilterableItem item) {

        mListener.onFilterItemCreated(key, item);
        dismiss();
    }
}
