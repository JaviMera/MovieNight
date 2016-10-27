package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javier on 10/25/2016.
 */
public class DefaultMoviesRecyclerAdapter extends RecyclerView.Adapter<DefaultMoviesRecyclerAdapter.DefaulMoviesViewHolder>{

    private Context mContext;
    private Movie[] mItems;

    public DefaultMoviesRecyclerAdapter(Context context, Movie[] items) {

        mContext = context;
        mItems = items;
    }

    @Override
    public DefaulMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.default_movie_item_layout, parent, false);
        DefaulMoviesViewHolder holder = new DefaulMoviesViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DefaulMoviesViewHolder holder, int position) {

        holder.bind(mItems[position]);
    }

    @Override
    public int getItemCount()
    {
        return mItems.length;
    }

    public class DefaulMoviesViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMoviePosterImageView;

        public DefaulMoviesViewHolder(View itemView) {
            super(itemView);

            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.moviePosterImageView);
        }

        public void bind(Movie mItem) {

            mMoviePosterImageView.setImageBitmap(mItem.getPoster());
        }
    }
}