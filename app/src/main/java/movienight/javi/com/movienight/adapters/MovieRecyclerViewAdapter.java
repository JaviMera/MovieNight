package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.Listeners.MovieSelectedListener;

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
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_layout, parent, false);

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

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView mMovieTitleTextView;

        public MovieViewHolder(View itemView) {

            super(itemView);

            mMovieTitleTextView = (TextView) itemView.findViewById(R.id.movieTitleTextView);
        }

        public void bindMovie(final Movie movie, final MovieSelectedListener listener) {

            if(null != movie){
                mMovieTitleTextView.setText(movie.getOriginalTitle());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onMovieSelectedListener(movie);
                    }
                });
            }
        }
    }
}
