package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.FilterableItem;

/**
 * Created by Javi on 10/24/2016.
 */

public class FilterRecyclerViewAdapter extends RecyclerView.Adapter<FilterRecyclerViewAdapter.FilterViewHolder> {

    private Context mContext;
    List<FilterableItem> mItems;

    public FilterRecyclerViewAdapter(Context context) {

        mContext = context;
        mItems = new LinkedList<>();
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_item_recycler_layout, parent, false);
        FilterViewHolder holder = new FilterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {

        holder.bindItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public void addFilterItem(FilterableItem item) {

        FilterableItem existingItem = isItemClassInList(item);

        if(existingItem != null) {

            mItems.remove(existingItem);
        }

        mItems.add(item);
        notifyDataSetChanged();;
    }

    private FilterableItem isItemClassInList(FilterableItem item) {

        for(int i = 0 ; i < mItems.size() ; i++) {

            FilterableItem fi = mItems.get(i);
            if(fi.getClass().equals(item.getClass())) {

                return fi;
            }
        }

        return null;
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;
        public TextView mValueTextView;

        public FilterViewHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.filterItemTitle);
            mValueTextView = (TextView) itemView.findViewById(R.id.filterItemTextView);
        }

        public void bindItem(FilterableItem filterableItem) {

            if(filterableItem != null) {

                mTitleTextView.setText(filterableItem.getTitle());
                mValueTextView.setText(filterableItem.getValue());
            }
        }
    }
}
