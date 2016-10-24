package movienight.javi.com.movienight.ui.SearchActivity;

import android.support.v4.app.DialogFragment;
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
import movienight.javi.com.movienight.listeners.DateRangePickerListener;
import movienight.javi.com.movienight.listeners.GenresSelectedListener;
import movienight.javi.com.movienight.model.DateRangeFilterableItem;
import movienight.javi.com.movienight.model.FilterableItem;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.GenreFilterableItem;

public class SearchActivity extends AppCompatActivity implements SearchActivityView, GenresSelectedListener, DateRangePickerListener{

    private final double mProgressDivider = 10.0;

    private List<Genre> mSelectedGenres;
    private String mStartDate;
    private String mEndDate;

    private SearchActivityPresenter mPresenter;

    @BindView(R.id.filterMoviesSpinnerView)
    Spinner mFilterMovieSpinner;

    @BindView(R.id.filterItemsRecyclerView)
    RecyclerView mFilterRecyclerView;

//    @BindView(R.id.seekBarView) SeekBar mSeekBarView;
//    @BindView(R.id.genreButtonView) AppCompatButton mGenresPickerButtonView;
//    @BindView(R.id.startReleaseDateButtonView) AppCompatButton mStartReleaseDateButtonView;
//    @BindView(R.id.endReleaseDateButtonView) AppCompatButton mEndReleaseDateButtonView;
//    @BindView(R.id.votesCountEditTextView) EditText mVoteCountEditText;
//    @BindView(R.id.seekbarResultTextView) TextView mRatingValueTextView;

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
//        mSeekBarView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                mPresenter.updateSeekBarProgressTextView(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });
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

//    @Override
//    public void setSeekBarProgressTextView(int progress) {
//
//        DecimalFormat df = new DecimalFormat("#.#");
//        String progressValue = df.format(progress / mProgressDivider);
//        mRatingValueTextView.setText(progressValue);
//    }
//
//    @OnClick(R.id.genreButtonView)
//    public void onGenresButtonClick(View view) {
//
//        GenresFragmentDialog dialog = GenresFragmentDialog.newInstance(mGenres);
//        dialog.show(getSupportFragmentManager(), "genres_tag");
//    }
//
//    @OnClick(R.id.startReleaseDateButtonView)
//    public void onDatepickerImageClick(View view) {
//
//        mDateButtonClicked = mStartReleaseDateButtonView;
//        DatePickerFragmentDialog dialog = DatePickerFragmentDialog.newInstance(mDateButtonClicked.getText().toString());
//        dialog.show(getSupportFragmentManager(), "start_tag");
//    }
//
//    @OnClick(R.id.endReleaseDateButtonView)
//    public void onEndReleaseDateButtonClick(View view) {
//
//        mDateButtonClicked = mEndReleaseDateButtonView;
//        DatePickerFragmentDialog dialog = DatePickerFragmentDialog.newInstance(mDateButtonClicked.getText().toString());
//        dialog.show(getSupportFragmentManager(), "start_tag");
//    }
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
//
//    @Override
//    public void OnDatePickerDone(String date) {
//
//        mDateButtonClicked.setText(date);
//    }
//
//    @Override
//    public void onGenreSelectionCompleted(Genre[] genres) {
//
//        String genresSelectedTexts = "";
//
//        for(int i = 0 ; i < genres.length ; i++) {
//
//            Genre currentGenre = genres[i];
//            if(currentGenre.isChecked()) {
//
//                if(i == genres.length - 1) {
//
//                    genresSelectedTexts += currentGenre.getDescription();
//                }
//                else {
//
//                    genresSelectedTexts += currentGenre.getDescription() + ", ";
//                }
//            }
//        }
//
//        mGenresPickerButtonView.setText(genresSelectedTexts);
//    }
//
//    @Override
//    public void onTaskCompleted(Genre[] result) {
//
//        mGenres = result;
//    }
//
//    private Genre[] getSelectedGenres(Genre[] genres) {
//
//        List<Genre> selectedGenres = new LinkedList<>();
//
//        for(Genre genre : genres) {
//
//            if(genre.isChecked()) {
//
//                selectedGenres.add(genre);
//            }
//        }
//
//        return selectedGenres.toArray(new Genre[selectedGenres.size()]);
//    }
}
