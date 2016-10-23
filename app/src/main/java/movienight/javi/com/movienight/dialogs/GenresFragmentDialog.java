package movienight.javi.com.movienight.dialogs;

import android.app.Activity;
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
import movienight.javi.com.movienight.asyntasks.GenreAsyncTask;
import movienight.javi.com.movienight.listeners.DatePickerListener;
import movienight.javi.com.movienight.listeners.GenresSelectedListener;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;
import movienight.javi.com.movienight.urls.GenreUrl;

/**
 * Created by Javi on 10/22/2016.
 */

public class GenresFragmentDialog extends DialogFragment implements AsyncTaskListener<Genre> {

    private SearchActivity mParentActivity;
    private View mDialogLayoutView;
    private RecyclerView mGenresRecyclerView;
    private GenresSelectedListener mListener;

    public static GenresFragmentDialog newInstance() {

        GenresFragmentDialog dialogFragment = new GenresFragmentDialog();

        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParentActivity = (SearchActivity)context;
        mListener = mParentActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GenreUrl genresUrl = new GenreUrl();
        new GenreAsyncTask(mParentActivity.getSupportFragmentManager(),this)
                .execute(genresUrl);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        mDialogLayoutView = LayoutInflater.from(context).inflate(R.layout.genre_recycler_view_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(mDialogLayoutView);
        return dialogBuilder.create();
    }

    @Override
    public void onTaskCompleted(Genre[] result) {

        mGenresRecyclerView = (RecyclerView) mDialogLayoutView.findViewById(R.id.genresRecyclerView);

        final GenreRecyclerViewAdapter adapter = new GenreRecyclerViewAdapter(getContext(), new LinkedList<>(Arrays.asList(result)));
        mGenresRecyclerView.setAdapter(adapter);

        final Button genreButtonView = (Button) mDialogLayoutView.findViewById(R.id.genresButtonView);
        genreButtonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Genre[] selectedGenres = adapter.getSelectedGenres();
                mListener.onGenreSelectionCompleted(selectedGenres);
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mGenresRecyclerView.setLayoutManager(manager);

        mGenresRecyclerView.setHasFixedSize(true);
    }
}
