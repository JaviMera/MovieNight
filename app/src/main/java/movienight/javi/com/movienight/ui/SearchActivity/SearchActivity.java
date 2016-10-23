package movienight.javi.com.movienight.ui.SearchActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.asyntasks.GenreAsyncTask;
import movienight.javi.com.movienight.dialogs.GenresFragmentDialog;
import movienight.javi.com.movienight.listeners.DatePickerListener;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.dialogs.DatePickerFragmentDialog;
import movienight.javi.com.movienight.listeners.GenresSelectedListener;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.MovieRequest;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.AsyncTaskListener;
import movienight.javi.com.movienight.ui.MoviesActivity.MoviesActivity;
import movienight.javi.com.movienight.urls.GenreUrl;

public class SearchActivity extends AppCompatActivity implements SearchActivityView, DatePickerListener, AsyncTaskListener<Genre>, GenresSelectedListener{

    private final double mProgressDivider = 10.0;

    private Genre[] mGenres;
    private SearchActivityPresenter mPresenter;
    private AppCompatButton mDateButtonClicked;

    @BindView(R.id.seekBarView) SeekBar mSeekBarView;
    @BindView(R.id.genreButtonView) AppCompatButton mGenresPickerButtonView;
    @BindView(R.id.startReleaseDateButtonView) AppCompatButton mStartReleaseDateButtonView;
    @BindView(R.id.endReleaseDateButtonView) AppCompatButton mEndReleaseDateButtonView;
    @BindView(R.id.votesCountEditTextView) EditText mVoteCountEditText;
    @BindView(R.id.seekbarResultTextView) TextView mRatingValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mPresenter = new SearchActivityPresenter(this);

        mSeekBarView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mPresenter.updateSeekBarProgressTextView(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        GenreUrl genresUrl = new GenreUrl();
        new GenreAsyncTask(getSupportFragmentManager(),this)
                .execute(genresUrl);
    }

    @Override
    public void setSeekBarProgressTextView(int progress) {

        DecimalFormat df = new DecimalFormat("#.#");
        String progressValue = df.format(progress / mProgressDivider);
        mRatingValueTextView.setText(progressValue);
    }

    @OnClick(R.id.genreButtonView)
    public void onGenresButtonClick(View view) {

        GenresFragmentDialog dialog = GenresFragmentDialog.newInstance(mGenres);
        dialog.show(getSupportFragmentManager(), "genres_tag");
    }

    @OnClick(R.id.startReleaseDateButtonView)
    public void onDatepickerImageClick(View view) {

        mDateButtonClicked = mStartReleaseDateButtonView;
        DatePickerFragmentDialog dialog = DatePickerFragmentDialog.newInstance(mDateButtonClicked.getText().toString());
        dialog.show(getSupportFragmentManager(), "start_tag");
    }

    @OnClick(R.id.endReleaseDateButtonView)
    public void onEndReleaseDateButtonClick(View view) {

        mDateButtonClicked = mEndReleaseDateButtonView;
        DatePickerFragmentDialog dialog = DatePickerFragmentDialog.newInstance(mDateButtonClicked.getText().toString());
        dialog.show(getSupportFragmentManager(), "start_tag");
    }

    @OnClick(R.id.findMoviesButtonView)
    public void onFindMoviesButtonClick(View view) {

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setGenre(getSelectedGenres(mGenres));
        movieRequest.setStartDateRelease(mStartReleaseDateButtonView.getText().toString());
        movieRequest.setEndDateReleaseSelected(mEndReleaseDateButtonView.getText().toString());
        movieRequest.setVoteCount(Integer.parseInt(mVoteCountEditText.getText().toString()));
        movieRequest.setRating(Double.parseDouble(mRatingValueTextView.getText().toString()));

        Intent intent = new Intent(SearchActivity.this, MoviesActivity.class);
        intent.putExtra(ActivityExtras.MOVIE_REQUEST_KEY, movieRequest);
        startActivity(intent);
    }

    @Override
    public void OnDatePickerDone(String date) {

        mDateButtonClicked.setText(date);
    }

    @Override
    public void onGenreSelectionCompleted(Genre[] genres) {

        String genresSelectedTexts = "";

        for(int i = 0 ; i < genres.length ; i++) {

            Genre currentGenre = genres[i];
            if(currentGenre.isChecked()) {

                if(i == genres.length - 1) {

                    genresSelectedTexts += currentGenre.getDescription();
                }
                else {

                    genresSelectedTexts += currentGenre.getDescription() + ", ";
                }
            }
        }

        mGenresPickerButtonView.setText(genresSelectedTexts);
    }

    @Override
    public void onTaskCompleted(Genre[] result) {

        mGenres = result;
    }

    private Genre[] getSelectedGenres(Genre[] genres) {

        List<Genre> selectedGenres = new LinkedList<>();

        for(Genre genre : genres) {

            if(genre.isChecked()) {

                selectedGenres.add(genre);
            }
        }

        return selectedGenres.toArray(new Genre[selectedGenres.size()]);
    }
}
