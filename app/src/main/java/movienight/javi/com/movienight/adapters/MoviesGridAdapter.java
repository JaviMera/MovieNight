package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javier on 10/27/2016.
 */

public class MoviesGridAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private Movie[] mItems;

    public MoviesGridAdapter(Context context, Movie[] movies) {
        super(context, 0);

        mContext = context;
        mItems = movies;
    }

    @Override
    public int getCount() {

        return mItems.length;
    }

    @Nullable
    @Override
    public Movie getItem(int position) {

        return mItems[position];
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieAdapterViewHolder holder;

        if(null == convertView) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_grid_item_layout, parent, false);
            holder = new MovieAdapterViewHolder();

            holder.mMoviePosterImageView = (ImageView) convertView.findViewById(R.id.moviePosterImageView);

            convertView.setTag(holder);
        }
        else {

            holder = (MovieAdapterViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);
        holder.mMoviePosterImageView.setImageBitmap(movie.getPoster());

        return convertView;
    }

    public void updateData(Movie[] movies) {

        mItems = movies;
        notifyDataSetChanged();
    }

    private static class MovieAdapterViewHolder {

        ImageView mMoviePosterImageView;
    }
}
