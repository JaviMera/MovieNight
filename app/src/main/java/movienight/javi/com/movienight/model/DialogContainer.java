package movienight.javi.com.movienight.model;

import android.app.DialogFragment;

import java.util.List;

import movienight.javi.com.movienight.dialogs.DateRangeDialog.DateRangeDialogFragment;
import movienight.javi.com.movienight.dialogs.FilterDialogBase;
import movienight.javi.com.movienight.dialogs.GenresDialog.GenresDialogFragment;
import movienight.javi.com.movienight.dialogs.RateDialog.RateDialogFragment;
import movienight.javi.com.movienight.dialogs.VoteDialog.VoteDialogFragment;
import movienight.javi.com.movienight.model.FilterItems.FilterableItem;
import movienight.javi.com.movienight.model.FilterItems.FilterableItemKeys;
import movienight.javi.com.movienight.model.FilterItems.Genre;

/**
 * Created by Javi on 11/3/2016.
 */

public class DialogContainer {

    private List<Genre> mGenres;

    public DialogContainer(List<Genre> genres) {

        mGenres = genres;
    }

    public FilterDialogBase getDialog(Integer key, List<FilterableItem> items) {

        switch(key) {

            case FilterableItemKeys.GENRE:
                return GenresDialogFragment.newInstance(mGenres, items);

            case FilterableItemKeys.DATE_RANGE:
                return DateRangeDialogFragment.newInstance(items);

            case FilterableItemKeys.RATE:
                return RateDialogFragment.newInstance(items);

            case FilterableItemKeys.VOTE_COUNT:
                return VoteDialogFragment.newInstance(items);
        }

        return null;
    }
}
