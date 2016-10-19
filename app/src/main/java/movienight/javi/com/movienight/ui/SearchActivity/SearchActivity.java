package movienight.javi.com.movienight.ui.SearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
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

    private SearchActivityPresenter mPresenter;
    private DatePickerFragmentDialog mDialog;
    
    @BindView(R.id.seekBarView) SeekBar mSeekBarView;
    @BindView(R.id.seekbarResultTextView) TextView mSeekBarResultTextView;
    @BindView(R.id.genreSpinnerView) Spinner mGenreSpinner;
    @BindView(R.id.datePickerButtonView) AppCompatButton mReleaseDateEditTextView;

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

                DecimalFormat df = new DecimalFormat("#.#");
                String progressValue = df.format(progress / 10.0);
                mSeekBarResultTextView.setText(progressValue);
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

    @OnClick(R.id.datePickerButtonView)
    public void onDatepickerImageClick(View view) {

        mDialog.show(getSupportFragmentManager(), "dialog_tag");
    }


    @Override
    public void OnDatePickerDone(String date) {

        mReleaseDateEditTextView.setText(date);
        mDialog.dismiss();
    }
}
