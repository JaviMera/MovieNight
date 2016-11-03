package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.listeners.MovieSelectedListener;

/**
 * Created by Javi on 10/21/2016.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>{

    private Context mContext;
    private List<Movie> mMovies;
    private MovieSelectedListener mListener;

    public MovieRecyclerViewAdapter(Context context, List<Movie> movies, MovieSelectedListener listener) {

        mContext = context;
        mMovies = movies;
        mListener = listener;
    }

    public void updateData(List<Movie> movies) {

        mMovies.addAll(movies);

        for(int i = mMovies.size() ; i < mMovies.size() + movies.size() ; i++)
            notifyItemInserted(i);
    }

    public void removeData() {

        mMovies.clear();
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_recycler_item_layout, parent, false);

        MovieViewHolder holder = new MovieViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.bindMovie(mMovies.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void updateMoviePoster(Movie updatedMovie) {

        for(Movie movie : mMovies) {

            if(movie.getPosterPath().equals(updatedMovie.getPosterPath())) {

                movie.setPoster(updatedMovie.getPoster());
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public long getItemId(int position) {

        return mMovies.get(position).getId();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMoviePoster;
        public TextView mMovieTitleTextView;

        public MovieViewHolder(View itemView) {

            super(itemView);

            mMoviePoster = (ImageView) itemView.findViewById(R.id.moviePosterImageView);
            mMovieTitleTextView = (TextView) itemView.findViewById(R.id.movieItemTitleTextView);
        }

        public void bindMovie(final Movie movie, final MovieSelectedListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMovieSelectedListener(movie);
                }
            });

            mMovieTitleTextView.setText(movie.getTitle());
            mMoviePoster.setImageBitmap(movie.getPoster());
        }
    }
}
