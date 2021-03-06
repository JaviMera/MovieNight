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
import movienight.javi.com.movienight.listeners.FilmSelectedListener;
import movienight.javi.com.movienight.model.FilmBase;

/**
 * Created by Javi on 10/21/2016.
 */

public class FilmRecyclerViewAdapter extends RecyclerView.Adapter<FilmRecyclerViewAdapter.FilmViewHolder>{

    private Context mContext;
    private List<FilmBase> mFilms;
    private FilmSelectedListener mListener;

    public FilmRecyclerViewAdapter(Context context, List<FilmBase> films, FilmSelectedListener listener) {

        mContext = context;
        mFilms = films;
        mListener = listener;
    }

    public void updateData(List<FilmBase> films) {

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

    public void updateMoviePoster(FilmBase updateFilm) {

        for(FilmBase film : mFilms) {

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

        public void bindMovie(final FilmBase film, final FilmSelectedListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFilmSelectedItem(film);
                }
            });

            mFilmTextView.setText(film.getTitle());
            mFilmPoster.setImageBitmap(film.getPoster());
        }
    }
}
