package movienight.javi.com.movienight.dialogs;

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
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.GenreRecyclerViewAdapter;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.GenreFilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 10/22/2016.
 */

public class GenresFragmentFilterDialog extends FilterDialogBase {

    private RecyclerView mGenresRecyclerView;
    private List<Genre> mGenres;

    public static GenresFragmentFilterDialog newInstance(List<Genre> genres, List<FilterableItem> selectedGenres) {

        GenresFragmentFilterDialog dialogFragment = new GenresFragmentFilterDialog();

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
        View dialogLayoutView = LayoutInflater.from(context).inflate(R.layout.genre_recycler_view_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(dialogLayoutView);

        mGenresRecyclerView = (RecyclerView) dialogLayoutView.findViewById(R.id.genresRecyclerView);

        final GenreRecyclerViewAdapter adapter = new GenreRecyclerViewAdapter(getContext(), mGenres);
        mGenresRecyclerView.setAdapter(adapter);

        final Button genreButtonView = (Button) dialogLayoutView.findViewById(R.id.genresDoneButtonView);
        genreButtonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                List<Genre> selectedGenres = getSelectedGenres();

                FilterableItem[] newSelectedGenres = new FilterableItem[selectedGenres.size()];

                for(int i = 0 ; i < selectedGenres.size() ; i++) {

                    newSelectedGenres[i] = new GenreFilterableItem(selectedGenres.get(i));
                }

                mListener.onFilterItemCreated(1, newSelectedGenres);
                dismiss();
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mGenresRecyclerView.setLayoutManager(manager);

        mGenresRecyclerView.setHasFixedSize(true);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());

        return dialog;
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