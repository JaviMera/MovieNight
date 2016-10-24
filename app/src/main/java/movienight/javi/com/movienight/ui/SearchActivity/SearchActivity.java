package movienight.javi.com.movienight.ui.SearchActivity;

import android.support.v4.app.DialogFragment;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.adapters.FilterRecyclerViewAdapter;
import movienight.javi.com.movienight.adapters.FilterSpinnerAdapter;
import movienight.javi.com.movienight.dialogs.DaterangeDialogFragment;
import movienight.javi.com.movienight.dialogs.GenresFragmentDialog;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.RateDialogFragment;
import movienight.javi.com.movienight.listeners.DateRangePickerListener;
import movienight.javi.com.movienight.listeners.GenresSelectedListener;
import movienight.javi.com.movienight.listeners.RateSelectedListener;
import movienight.javi.com.movienight.model.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.GenreFilterableItem;
import movienight.javi.com.movienight.model.RateFIlterableItem;

public class SearchActivity extends AppCompatActivity implements SearchActivityView, GenresSelectedListener, DateRangePickerListener, RateSelectedListener{

    private final double mProgressDivider = 10.0;

    private List<Genre> mSelectedGenres;
    private String mStartDate;
    private String mEndDate;

    private SearchActivityPresenter mPresenter;

    @BindView(R.id.filterMoviesSpinnerView)
    Spinner mFilterMovieSpinner;

    @BindView(R.id.filterItemsRecyclerView)
    RecyclerView mFilterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mSelectedGenres = new ArrayList<>();
        mPresenter = new SearchActivityPresenter(this);

        String[] filterItems = getResources().getStringArray(R.array.filter_options_array);
        mPresenter.setFilterSpinnerAdapter(filterItems);

        mFilterMovieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DialogFragment dialog;

                switch(position) {

                    case 1:
                        dialog = GenresFragmentDialog.newInstance(mSelectedGenres);
                        dialog.show(getSupportFragmentManager(), "genre_dialog");
                        break;

                    case 2:
                        dialog = DaterangeDialogFragment.newInstance();
                        dialog.show(getSupportFragmentManager(), "daterange_dialog");
                        break;

                    case 3:
                        dialog = RateDialogFragment.newInstance();
                        dialog.show(getSupportFragmentManager(), "rate_dialog");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final FilterRecyclerViewAdapter adapter = new FilterRecyclerViewAdapter(this);
        mFilterRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mFilterRecyclerView.setLayoutManager(manager);

        mFilterRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void setFilterSpinnerAdapter(String[] items) {

        FilterSpinnerAdapter adapter = new FilterSpinnerAdapter(this, new LinkedList<>(Arrays.asList(items)));
        mFilterMovieSpinner.setAdapter(adapter);
    }

    @Override
    public void onGenreSelectionCompleted(List<Genre> genres) {

        mFilterMovieSpinner.setSelection(0);

        mSelectedGenres.clear();
        mSelectedGenres = genres;

        FilterableItem item = new GenreFilterableItem(mSelectedGenres.toArray(new Genre[mSelectedGenres.size()]));
        FilterRecyclerViewAdapter adapter = (FilterRecyclerViewAdapter)mFilterRecyclerView.getAdapter();
        adapter.addFilterItem(item);
    }

    @Override
    public void onDateRangePickerDone(String startDate, String endDate) {

        mFilterMovieSpinner.setSelection(0);
        mStartDate = startDate;
        mEndDate = endDate;

        FilterableItem item = new DateRangeFilterableItem(mStartDate, mEndDate);
        FilterRecyclerViewAdapter adapter = (FilterRecyclerViewAdapter)mFilterRecyclerView.getAdapter();
        adapter.addFilterItem(item);
    }

    @Override
    public void onRateDone(Double rate) {

        mFilterMovieSpinner.setSelection(0);

        FilterableItem item = new RateFIlterableItem(rate);
        FilterRecyclerViewAdapter adapter = (FilterRecyclerViewAdapter)mFilterRecyclerView.getAdapter();
        adapter.addFilterItem(item);
    }
//
//    @OnClick(R.id.findMoviesButtonView)
//    public void onFindMoviesButtonClick(View view) {
//
//        MovieRequest movieRequest = new MovieRequest();
//        movieRequest.setGenre(getSelectedGenres(mGenres));
//        movieRequest.setStartDateRelease(mStartReleaseDateButtonView.getText().toString());
//        movieRequest.setEndDateReleaseSelected(mEndReleaseDateButtonView.getText().toString());
//        movieRequest.setVoteCount(Integer.parseInt(mVoteCountEditText.getText().toString()));
//        movieRequest.setRating(Double.parseDouble(mRatingValueTextView.getText().toString()));
//
//        Intent intent = new Intent(SearchActivity.this, MoviesActivity.class);
//        intent.putExtra(ActivityExtras.MOVIE_REQUEST_KEY, movieRequest);
//        startActivity(intent);
//    }
}
