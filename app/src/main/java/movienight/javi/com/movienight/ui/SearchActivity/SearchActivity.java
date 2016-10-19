package movienight.javi.com.movienight.ui.SearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

    private final double mProgressDivider = 10.0;
    private Drawable mArrowUpDrawable;
    private Drawable mArrowDownDrawable;

    private SearchActivityPresenter mPresenter;
    private DatePickerFragmentDialog mDialog;

    @BindView(R.id.seekBarView) SeekBar mSeekBarView;
    @BindView(R.id.seekbarResultTextView) TextView mSeekBarResultTextView;
    @BindView(R.id.genreSpinnerView) Spinner mGenreSpinner;
    @BindView(R.id.datePickerButtonView) AppCompatButton mReleaseDateEditTextView;
    @BindView(R.id.arrowReleaseDateImageView) ImageView mArrowReleaseDateImageView;
    @BindView(R.id.arrowVoteCountImageView) ImageView mArrowVoteCountImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mArrowUpDrawable = ContextCompat.getDrawable(this, R.drawable.arrow_up);
        mArrowDownDrawable = ContextCompat.getDrawable(this, R.drawable.arrow_down);

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

    @OnClick(R.id.datePickerButtonView)
    public void onDatepickerImageClick(View view) {

        mDialog.show(getSupportFragmentManager(), "dialog_tag");
    }

    @OnClick(R.id.arrowReleaseDateImageView)
    public void onImageViewClick(View view) {

        setImage(mArrowReleaseDateImageView);
    }

    @OnClick(R.id.arrowVoteCountImageView)
    public void onVoteCountArrowClick(View view ) {

        setImage(mArrowVoteCountImageView);
    }

    private void setImage(ImageView view) {

        Bitmap currentBitmap = getBitmapFromDrawable(view.getDrawable());
        Bitmap arrowUpBitmap = getBitmapFromDrawable(mArrowUpDrawable);
        Bitmap arrowDownBitmap = getBitmapFromDrawable(mArrowDownDrawable);

        if(currentBitmap.sameAs(arrowUpBitmap)){

            view.setImageDrawable(mArrowDownDrawable);
        }
        else if (currentBitmap.sameAs(arrowDownBitmap)){

            view.setImageDrawable(mArrowUpDrawable);
        }
    }

    @Override
    public void OnDatePickerDone(String date) {

        mReleaseDateEditTextView.setText(date);
        mDialog.dismiss();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {

        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        return bitmap;
    }
}
