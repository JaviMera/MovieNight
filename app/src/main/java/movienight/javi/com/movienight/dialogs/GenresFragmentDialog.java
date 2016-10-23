package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.GenreRecyclerViewAdapter;
import movienight.javi.com.movienight.listeners.DatePickerListener;
import movienight.javi.com.movienight.listeners.GenresSelectedListener;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

/**
 * Created by Javi on 10/22/2016.
 */

public class GenresFragmentDialog extends DialogFragment {

    private RecyclerView mGenresRecyclerView;
    private GenresSelectedListener mListener;
    private Genre[] mGenres;

    public static GenresFragmentDialog newInstance(Genre[] genres) {

        GenresFragmentDialog dialogFragment = new GenresFragmentDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelableArray(ActivityExtras.GENRE_ARRAY_KEY, genres);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (SearchActivity)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGenres = (Genre[]) getArguments().getParcelableArray(ActivityExtras.GENRE_ARRAY_KEY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        View genresDialogLayout = LayoutInflater.from(context).inflate(R.layout.genre_recycler_view_layout, null);

        mGenresRecyclerView = (RecyclerView) genresDialogLayout.findViewById(R.id.genresRecyclerView);

        final GenreRecyclerViewAdapter adapter = new GenreRecyclerViewAdapter(getContext(), new LinkedList<>(Arrays.asList(mGenres)));
        mGenresRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mGenresRecyclerView.setLayoutManager(manager);

        mGenresRecyclerView.setHasFixedSize(true);

        final Button genreButtonView = (Button) genresDialogLayout.findViewById(R.id.genresButtonView);
        genreButtonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Genre[] selectedGenres = adapter.getSelectedGenres();

            }
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(genresDialogLayout);

        return dialogBuilder.create();
    }
}
