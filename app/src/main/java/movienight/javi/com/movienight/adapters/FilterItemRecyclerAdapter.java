package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.model.FilterableItem;

/**
 * Created by Javier on 10/26/2016.
 */

public class FilterItemRecyclerAdapter extends RecyclerView.Adapter<FilterItemRecyclerAdapter.FilterItemViewHolder> {

    private Context mContext;
    private List<FilterableItem> mItems;
    private FilterItemRemovedListener mListener;

    public FilterItemRecyclerAdapter(Context context, List<FilterableItem> items, FilterItemRemovedListener listener) {

        mContext = context;
        mItems = items;
        mListener = listener;
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

    public void updateData(Collection<List<FilterableItem>> newItems) {

        mItems.clear();

        for(List<FilterableItem> mapFilters: newItems) {

            mItems.addAll(mapFilters);
        }

        notifyDataSetChanged();
    }

    public class FilterItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mFilterableItemTextView;

        public FilterItemViewHolder(View itemView) {
            super(itemView);

            mFilterableItemTextView = (TextView) itemView.findViewById(R.id.filterItemTextView);
        }


        public void bindItem(FilterableItem filterableItem) {

            mFilterableItemTextView.setText(filterableItem.toString());
            mFilterableItemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String textClicked = ((TextView)view).getText().toString();

                    FilterableItem itemClicked = getItemClicked(textClicked);
                    FilterableItem itemToRemove = getItemToDelete(itemClicked);

                    if(null != itemToRemove) {

                        mItems.remove(itemToRemove);
                        mListener.onFilterItemDeleted(itemToRemove);
                        notifyDataSetChanged();
                    }
                }
            });
        }

        private FilterableItem getItemClicked(String text) {

            for(FilterableItem item : mItems) {

                if(item.toString().equals(text)) {

                    return item;
                }
            }

            return null;
        }

        private FilterableItem getItemToDelete(FilterableItem itemClicked) {

            for(FilterableItem item : mItems) {

                if(item.equals(itemClicked)) {

                    return item;
                }
            }

            return null;
        }
    }
}
