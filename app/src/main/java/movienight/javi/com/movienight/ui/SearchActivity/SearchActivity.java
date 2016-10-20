package movienight.javi.com.movienight.ui.SearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.GenreSpinnerAdapter;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;

public class SearchActivity extends AppCompatActivity implements SearchActivityView, OnDoneListener{

    private final double mProgressDivider = 10.0;
    private Drawable mArrowUpDrawable;
    private Drawable mArrowDownDrawable;

    private SearchActivityPresenter mPresenter;
    private DatePickerFragmentDialog mDialog;
    private AppCompatButton mDateButtonClicked;

    @BindView(R.id.seekBarView) SeekBar mSeekBarView;
    @BindView(R.id.seekbarResultTextView) TextView mSeekBarResultTextView;
    @BindView(R.id.genreSpinnerView) Spinner mGenreSpinner;
    @BindView(R.id.startReleaseDateButtonView) AppCompatButton mStartReleaseDateButtonView;
    @BindView(R.id.endReleaseDateButtonView) AppCompatButton mEndReleaseDateButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mDialog = new DatePickerFragmentDialog();
        mPresenter = new SearchActivityPresenter(this);

        Genre[] genres = getGenresFromIntent(this, ActivityExtras.GENRE_ARRAY_KEY);
        mPresenter.setGenreSpinnerAdapter(this, genres);

        mSeekBarView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mPresenter.updateSeekBarProgressTextView(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Genre[] getGenresFromIntent(Activity act, String genresKey) {

        Intent intent = act.getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(genresKey);

        return Arrays.copyOf(parcelables, parcelables.length, Genre[].class);
    }

    @Override
    public void setGenreSpinnerAdapter(Context ctx, Genre[] genres) {

        GenreSpinnerAdapter adapter = new GenreSpinnerAdapter(this, genres);
        mGenreSpinner.setAdapter(adapter);
    }

    @Override
    public void setSeekBarProgressTextView(int progress) {

        DecimalFormat df = new DecimalFormat("#.#");
        String progressValue = df.format(progress / mProgressDivider);
        mSeekBarResultTextView.setText(progressValue);
    }

    @OnClick(R.id.startReleaseDateButtonView)
    public void onDatepickerImageClick(View view) {

        mDateButtonClicked = mStartReleaseDateButtonView;
        mDialog.show(getSupportFragmentManager(), "start_tag");
    }

    @OnClick(R.id.endReleaseDateButtonView)
    public void onEndReleaseDateButtonClick(View view) {

        mDateButtonClicked = mEndReleaseDateButtonView;
        mDialog.show(getSupportFragmentManager(), "end_dialog");
    }

    @Override
    public void OnDatePickerDone(String date) {

        mDateButtonClicked.setText(date);
        mDialog.dismiss();
    }

}
