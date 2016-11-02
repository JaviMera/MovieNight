package movienight.javi.com.movienight.dialogs.GenresDialog;

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
import butterknife.OnClick;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.GenreRecyclerViewAdapter;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.GenreFilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 10/22/2016.
 */

public class GenresDialogFragment extends FilterDialogBase implements GenresDialogFragmentView {

    private List<Genre> mGenres;
    private GenresDialogFragmentPresenter mPresenter;

    @BindView(R.id.genresRecyclerView) RecyclerView mRecyclerView;

    public static GenresDialogFragment newInstance(List<Genre> genres, List<FilterableItem> selectedGenres) {

        GenresDialogFragment dialogFragment = new GenresDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.GENRES_KEY, (ArrayList)genres);
        bundle.putParcelableArrayList(ActivityExtras.SELECTED_GENRES_KEY, (ArrayList)selectedGenres);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGenres = getArguments().getParcelableArrayList(ActivityExtras.GENRES_KEY);

        for(Genre genre : mGenres) {

            genre.setChecked(false);
        }

        List<GenreFilterableItem> selectedGenres = getArguments().getParcelableArrayList(ActivityExtras.SELECTED_GENRES_KEY);

        for(GenreFilterableItem item : selectedGenres) {

            int position = mGenres.indexOf(item.getValue()[0]);
            Genre genre = mGenres.get(position);
            genre.setChecked(true);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        View view = LayoutInflater.from(context).inflate(R.layout.genre_recycler_view_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mPresenter = new GenresDialogFragmentPresenter(this);

        mPresenter.setRecyclerAdapter(context, mGenres);
        mPresenter.setRecyclerManager(context);
        mPresenter.setRecyclerSize(true);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @OnClick(R.id.genresDoneButtonView)
    public void onDialogButtonClick(View view) {

        List<Genre> selectedGenres = getSelectedGenres();

        FilterableItem[] newSelectedGenres = new FilterableItem[selectedGenres.size()];

        for(int i = 0 ; i < selectedGenres.size() ; i++) {

            newSelectedGenres[i] = new GenreFilterableItem(selectedGenres.get(i));
        }

        mListener.onFilterItemCreated(1, newSelectedGenres);
        dismiss();
    }

    @Override
    public void setRecyclerAdapter(Context context, List<Genre> genres) {

        GenreRecyclerViewAdapter adapter = new GenreRecyclerViewAdapter(context, genres);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setRecyclerManager(Context context) {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void setRecyclerSize(boolean fixedSize) {

        mRecyclerView.setHasFixedSize(fixedSize);
    }

    private List<Genre> getSelectedGenres() {

        List<Genre> selectedGenres = new ArrayList<>();
        for(Genre genre : mGenres) {

            if(genre.isChecked()) {

                selectedGenres.add(genre);
            }
        }

        return selectedGenres;
    }
}