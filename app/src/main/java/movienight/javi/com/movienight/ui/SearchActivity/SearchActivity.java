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
    private DatePickerFragmentDialog mDatepickerDialog;
    private GenresFragmentDialog mGenresDialog;
    private AppCompatButton mDateButtonClicked;

    @BindView(R.id.seekBarView) SeekBar mSeekBarView;
    @BindView(R.id.genreButtonView) AppCompatButton mGenresButtonView;
    @BindView(R.id.startReleaseDateButtonView) AppCompatButton mStartReleaseDateButtonView;
    @BindView(R.id.endReleaseDateButtonView) AppCompatButton mEndReleaseDateButtonView;
    @BindView(R.id.votesCountEditTextView) EditText mVoteCountEditText;
    @BindView(R.id.seekbarResultTextView) TextView mRatingValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mDatepickerDialog = new DatePickerFragmentDialog();
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

        mGenresDialog = GenresFragmentDialog.newInstance(mGenres);
        mGenresDialog.show(getSupportFragmentManager(), "genres_tag");
    }

    @OnClick(R.id.startReleaseDateButtonView)
    public void onDatepickerImageClick(View view) {

        mDateButtonClicked = mStartReleaseDateButtonView;
        mDatepickerDialog.show(getSupportFragmentManager(), "start_tag");
    }

    @OnClick(R.id.endReleaseDateButtonView)
    public void onEndReleaseDateButtonClick(View view) {

        mDateButtonClicked = mEndReleaseDateButtonView;
        mDatepickerDialog.show(getSupportFragmentManager(), "end_dialog");
    }

    @OnClick(R.id.findMoviesButtonView)
    public void onFindMoviesButtonClick(View view) {

        MovieRequest movieRequest = new MovieRequest();

        List<Genre> selectedGenres = new LinkedList<>();

        for(Genre genre : mGenres) {

            if(genre.isChecked()) {

                selectedGenres.add(genre);
            }
        }

        movieRequest.setGenre(selectedGenres.toArray(new Genre[selectedGenres.size()]));
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
        mDatepickerDialog.dismiss();
    }

    @Override
    public void onGenreSelectionCompleted(Genre[] genres) {

        mGenresDialog.dismiss();
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

        mGenresButtonView.setText(genresSelectedTexts);
    }

    @Override
    public void onTaskCompleted(Genre[] result) {

        mGenres = result;
    }
}
