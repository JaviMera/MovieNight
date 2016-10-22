package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import movienight.javi.com.movienight.R;

/**
 * Created by Javi on 10/21/2016.
 */

public class PageSpinnerAdapter extends ArrayAdapter {

    private Context mContext;
    private String[] mItems;

    public PageSpinnerAdapter(Context context, String[] items) {

        super(context, 0);
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getItem(int position) {
        return mItems[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerItemViewHolder holder;

        if(convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_layout, null);
            holder = new SpinnerItemViewHolder();
            holder.mItemTextView = (TextView) convertView.findViewById(R.id.genreDescriptionTextView);

            convertView.setTag(holder);
        }
        else {

            holder = (SpinnerItemViewHolder) convertView.getTag();
        }

        String current = getItem(position);
        holder.mItemTextView.setText(current);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        SpinnerItemDropdownViewHolder holder;

        if(convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_dropdown_layout, null);
            holder = new SpinnerItemDropdownViewHolder();
            holder.mDropdownItemTextView = (TextView) convertView.findViewById(R.id.genreDescriptionDropdownTextView);

            convertView.setTag(holder);
        }
        else {

            holder = (SpinnerItemDropdownViewHolder) convertView.getTag();
        }

        String current = getItem(position);
        holder.mDropdownItemTextView.setText(current);

        return convertView;
    }

    private static class SpinnerItemViewHolder {

        TextView mItemTextView;
    }

    private static class SpinnerItemDropdownViewHolder {

        TextView mDropdownItemTextView;
    }
}
