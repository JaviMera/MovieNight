package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.FilterableItem;

/**
 * Created by Javier on 10/26/2016.
 */

public class FilterItemRecyclerAdapter extends RecyclerView.Adapter<FilterItemRecyclerAdapter.FilterItemViewHolder> {

    private Context mContext;
    private List<FilterableItem> mItems;

    public FilterItemRecyclerAdapter(Context context, List<FilterableItem> items) {

        mContext = context;
        mItems = items;
    }

    @Override
    public FilterItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_recycler_item_layout, parent, false);
        FilterItemViewHolder holder = new FilterItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FilterItemViewHolder holder, int position) {

        holder.bindItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public void updateData(FilterableItem newItem) {

        mItems.add(newItem);
        notifyDataSetChanged();
    }

    public class FilterItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mFilterableItemTextView;

        public FilterItemViewHolder(View itemView) {
            super(itemView);

            mFilterableItemTextView = (TextView) itemView.findViewById(R.id.filterItemTextView);
        }

        public void bindItem(FilterableItem filterableItem) {

            mFilterableItemTextView.setText(filterableItem.getValue());
            mFilterableItemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
