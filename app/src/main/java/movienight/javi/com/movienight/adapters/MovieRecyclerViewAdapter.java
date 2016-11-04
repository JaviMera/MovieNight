package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.listeners.FilmSelectedListener;

/**
 * Created by Javi on 10/21/2016.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.FilmViewHolder>{

    private Context mContext;
    private List<Film> mFilms;
    private FilmSelectedListener mListener;

    public MovieRecyclerViewAdapter(Context context, List<Film> films, FilmSelectedListener listener) {

        mContext = context;
        mFilms = films;
        mListener = listener;
    }

    public void updateData(List<Film> films) {

        mFilms.addAll(films);

        for(int i = mFilms.size(); i < mFilms.size() + films.size() ; i++)
            notifyItemInserted(i);
    }

    public void removeData() {

        mFilms.clear();
        notifyDataSetChanged();
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_recycler_item_layout, parent, false);

        FilmViewHolder holder = new FilmViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {

        holder.bindMovie(mFilms.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }

    public void updateMoviePoster(Film updateFilm) {

        for(Film film : mFilms) {

            if(film.getPosterPath().equals(updateFilm.getPosterPath())) {

                film.setPoster(updateFilm.getPoster());
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public long getItemId(int position) {

        return mFilms.get(position).getId();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {

        public ImageView mFilmPoster;
        public TextView mFilmTextView;

        public FilmViewHolder(View itemView) {

            super(itemView);

            mFilmPoster = (ImageView) itemView.findViewById(R.id.filmPosterImageView);
            mFilmTextView = (TextView) itemView.findViewById(R.id.filmItemTextView);
        }

        public void bindMovie(final Film film, final FilmSelectedListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFilmSelectedItem(film);
                }
            });

            mFilmTextView.setText(film.getName());
            mFilmPoster.setImageBitmap(film.getPoster());
        }
    }
}
