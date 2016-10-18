package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Genre;

/**
 * Created by Javi on 10/18/2016.
 */

public class GenreSpinnerAdapter extends ArrayAdapter<Genre> {

    private Context mContext;
    private Genre[] mGenres;

    public GenreSpinnerAdapter(Context context, Genre[] genres) {

        super(context, 0);

        mContext = context;
        mGenres = genres;
    }

    @Override
    public int getCount() {

        return mGenres.length;
    }

    @Override
    public Genre getItem(int position) {

        return mGenres[position];
    }

    @Override
    public long getItemId(int position) {

        return mGenres[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerItemViewHolder holder;

        if(convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.genre_spinner_item_layout, null);
            holder = new SpinnerItemViewHolder();
            holder.mGenreDescriptionTextView = (TextView) convertView.findViewById(R.id.genreDescriptionTextView);

            convertView.setTag(holder);
        }
        else {

            holder = (SpinnerItemViewHolder) convertView.getTag();
        }

        Genre current = getItem(position);
        holder.mGenreDescriptionTextView.setText(current.getDescription());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        SpinnerItemDropdownViewHolder holder;

        if(convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.genre_spinner_item_dropdown_layout, null);
            holder = new SpinnerItemDropdownViewHolder();
            holder.mGenreDescriptionDropdownTextView = (TextView) convertView.findViewById(R.id.genreDescriptionDropdownTextView);

            convertView.setTag(holder);
        }
        else {

            holder = (SpinnerItemDropdownViewHolder) convertView.getTag();
        }

        Genre current = getItem(position);
        holder.mGenreDescriptionDropdownTextView.setText(current.getDescription());

        return convertView;
    }

    private static class SpinnerItemViewHolder {

        TextView mGenreDescriptionTextView;
    }

    private static class SpinnerItemDropdownViewHolder {

        TextView mGenreDescriptionDropdownTextView;
    }
}
