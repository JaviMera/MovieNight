package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.listeners.SortItemAddedListener;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.SortItemContainer;

/**
 * Created by Javi on 11/4/2016.
 */

public class SortRecyclerViewAdapter extends RecyclerView.Adapter<SortRecyclerViewAdapter.SortViewHolder> {

    private SortItemAddedListener mListener;
    private SortItemContainer mContainer;
    private Context mContext;
    private List<String> mItems;

    public SortRecyclerViewAdapter(Context context, List<String> items, SortItemAddedListener listener) {

        mContext = context;
        mItems = items;
        mListener = listener;
        mContainer = new SortItemContainer();
    }

    @Override
    public SortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.sort_recycler_item_layout, parent, false);
        SortViewHolder holder = new SortViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(SortViewHolder holder, int position) {

        holder.bind(mItems.get(position), position);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class SortViewHolder extends RecyclerView.ViewHolder {

        public TextView mSortTextView;

        public SortViewHolder(View itemView) {
            super(itemView);

            mSortTextView = (TextView) itemView.findViewById(R.id.sortItemTextView);
        }

        public void bind(final String s, final int position) {

            mSortTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                mListener.onSortItemAdded(
                    FilterableItemKeys.SORT,
                    mContainer.get(mSortTextView.getText().toString())
                );
                }
            });
            mSortTextView.setText(s);
        }
    }

}
