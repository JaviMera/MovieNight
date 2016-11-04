package movienight.javi.com.movienight.fragments;

import android.view.Menu;
import android.view.MenuInflater;

import movienight.javi.com.movienight.R;

/**
 * Created by Javi on 11/4/2016.
 */

public class MovieFragment extends FilmFragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.movie_fragment_menu_layout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
