package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.GenreRecyclerViewAdapter;
import movienight.javi.com.movienight.asyntasks.GenreAsyncTask;
import movienight.javi.com.movienight.listeners.FilterItemAddedListener;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.GenreFilterableItem;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;
import movienight.javi.com.movienight.urls.GenreUrl;

/**
 * Created by Javi on 10/22/2016.
 */

public class GenresFragmentDialog extends DialogFragmentBase implements AsyncTaskListener<Genre>{

    private View mDialogLayoutView;
    private ProgressBar mGenresProgressBar;
    private RecyclerView mGenresRecyclerView;
    private List<Genre> mGenres;
    private List<Genre> mSelectedGenres;

    public static GenresFragmentDialog newInstance(List<FilterableItem> selectedGenres) {

        GenresFragmentDialog dialogFragment = new GenresFragmentDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ActivityExtras.SELECTED_GENRES_KEY, (ArrayList)selectedGenres);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SearchActivity activity = (SearchActivity)getContext();

        GenreUrl genresUrl = new GenreUrl();
        new GenreAsyncTask(activity.getSupportFragmentManager(),this)
                .execute(genresUrl);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<GenreFilterableItem> fItems = getArguments().getParcelableArrayList(ActivityExtras.SELECTED_GENRES_KEY);
        mSelectedGenres = new ArrayList<>();
        for(GenreFilterableItem item : fItems) {

            mSelectedGenres.add(item.getObject());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        mDialogLayoutView = LayoutInflater.from(context).inflate(R.layout.genre_recycler_view_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(mDialogLayoutView);

        mGenresProgressBar = (ProgressBar) mDialogLayoutView.findViewById(R.id.genresProgressBar);
        mGenresProgressBar.setVisibility(View.VISIBLE);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setOnKeyListener(onBackButtonPressed());

        return dialog;
    }

    @Override
    public void onTaskCompleted(Genre[] result) {

        mGenres = new LinkedList<>(Arrays.asList(result));

        for(Genre genre: mSelectedGenres) {

            int genreToCheck = mGenres.indexOf(genre);
            mGenres.get(genreToCheck).setChecked(true);
        }

        mGenresProgressBar.setVisibility(View.INVISIBLE);

        setRecyclerView(mGenres.toArray(new Genre[mGenres.size()]));
    }

    private void setRecyclerView(Genre[] genres) {

        mGenresRecyclerView = (RecyclerView) mDialogLayoutView.findViewById(R.id.genresRecyclerView);

        final GenreRecyclerViewAdapter adapter = new GenreRecyclerViewAdapter(getContext(), new LinkedList<>(Arrays.asList(genres)));
        mGenresRecyclerView.setAdapter(adapter);

        final Button genreButtonView = (Button) mDialogLayoutView.findViewById(R.id.genresDoneButtonView);
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
