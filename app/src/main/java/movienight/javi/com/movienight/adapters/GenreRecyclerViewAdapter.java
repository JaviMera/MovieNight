package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/22/2016.
 */

public class GenreRecyclerViewAdapter extends RecyclerView.Adapter<GenreRecyclerViewAdapter.GenreViewHolder> {

    private Context mContext;
    private List<Genre> mGenres;

    public GenreRecyclerViewAdapter(Context context, List<Genre> genres) {

        mContext = context;
        mGenres = genres;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.genre_item_layout, parent, false);
        GenreViewHolder holder = new GenreViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {

        holder.bindGenre(mGenres.get(position));
    }

    @Override
    public int getItemCount() {

        return mGenres.size();
    }

    public Integer[] getSelectedGenres() {

        List<Integer> selectedGenres = new LinkedList<>();
        for(int i = 0 ; i < mGenres.size() ; i++) {

            if(mGenres.get(i).isChecked()) {

                selectedGenres.add(i);
            }
        }

        return selectedGenres.toArray(new Integer[selectedGenres.size()]);
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {

        public TextView mGenreTextView;
        public CheckBox mGenreChecbox;

        public GenreViewHolder(View itemView) {
            super(itemView);

            mGenreTextView = (TextView) itemView.findViewById(R.id.genreDescriptionTextView);
            mGenreChecbox = (CheckBox) itemView.findViewById(R.id.genreCheckBoxView);
        }

        public void bindGenre(final Genre genre) {

            mGenreTextView.setText(genre.getDescription());

            mGenreChecbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    genre.setChecked(isChecked);
                }
            });

            mGenreChecbox.setChecked(genre.isChecked());
        }
    }
}
