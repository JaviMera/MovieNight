package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.FilterItemRemovedListener;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.GenreFilterableItem;

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

    public void updateData(Collection<List<FilterableItem>> newItem) {

        mItems.clear();

        for(List<FilterableItem> filters : newItem) {

            mItems.addAll(filters);
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

            mFilterableItemTextView.setText(filterableItem.getValue());
            mFilterableItemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String textClicked = ((TextView)view).getText().toString();
                    FilterableItem itemToRemove = null;

                    for(FilterableItem item : mItems) {

                        if(item.getValue().equals(textClicked)) {

                            itemToRemove = item;
                            break;
                        }
                    }

                    mItems.remove(itemToRemove);
                    mListener.onFilterItemDeleted(itemToRemove);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
