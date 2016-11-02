package movienight.javi.com.movienight.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.SortItem;

/**
 * Created by Javi on 11/2/2016.
 */

public class SortItemRecyclerAdapter extends RecyclerView.Adapter<SortItemRecyclerAdapter.SortItemViewHolder> {

    private Context mContext;
    private List<SortItem> mItems;

    public SortItemRecyclerAdapter(Context context, List<SortItem> items) {

        mContext = context;
        mItems = items;
    }

    @Override
    public SortItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.sort_dialog_layout, parent, false);
        SortItemViewHolder holder = new SortItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(SortItemViewHolder holder, int position) {

        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class SortItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mSortItemNameTextView;
        public RadioButton mSortITemRadioButtonView;

        public SortItemViewHolder(View itemView) {
            super(itemView);

            mSortItemNameTextView = (TextView) itemView.findViewById(R.id.sortItemTextView);
            mSortITemRadioButtonView = (RadioButton) itemView.findViewById(R.id.sortItemRadioButtonView);
        }

        public void bind(final SortItem sortItem) {

            mSortItemNameTextView.setText(sortItem.getName());

            mSortITemRadioButtonView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    sortItem.setChecked(isChecked);
                }
            });

            mSortITemRadioButtonView.setChecked(sortItem.isSelected());
        }
    }
}
