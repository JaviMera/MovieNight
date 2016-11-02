package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import movienight.javi.com.movienight.R;

/**
 * Created by Javi on 10/24/2016.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mFilterOptions;

    public CustomSpinnerAdapter(Context context, List<String> filterOptions) {
        super(context, 0);

        mContext = context;
        mFilterOptions = filterOptions;
    }

    @Override
    public int getCount() {

        return mFilterOptions.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {

        return mFilterOptions.get(position);
    }

    @Override
    public int getPosition(String item) {

        return mFilterOptions.indexOf(item);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        SpinnerDropdownViewHolder holder;

        if(convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_filter_dropdown_item_layout, parent, false);
            holder = new SpinnerDropdownViewHolder();

            holder.mFilterDropdownItemView = (TextView) convertView.findViewById(R.id.filterDropdownItemTextView);

            convertView.setTag(holder);
        }
        else {

            holder = (SpinnerDropdownViewHolder) convertView.getTag();
        }

        String filterItem = getItem(position);
        holder.mFilterDropdownItemView.setText(filterItem);

        if(position == 0) {

            holder.mFilterDropdownItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.movie_dark_purple));
            holder.mFilterDropdownItemView.setTextColor(Color.WHITE);
            holder.mFilterDropdownItemView.setClickable(false);
        }

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerViewHolder holder;

        if(convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_filter_item_layout, parent, false);
            holder = new SpinnerViewHolder();

            holder.mFilterItemTextView = (TextView) convertView.findViewById(R.id.filterItemTextView);

            convertView.setTag(holder);
        }
        else {

            holder = (SpinnerViewHolder) convertView.getTag();
        }

        String filterItem = getItem(position);
        holder.mFilterItemTextView.setText(filterItem);

        return convertView;
    }

    private static class SpinnerViewHolder {

        TextView mFilterItemTextView;
    }

    private static class SpinnerDropdownViewHolder {

        TextView mFilterDropdownItemView;
    }
}
