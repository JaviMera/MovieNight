package movienight.javi.com.movienight.ui.SearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.GenreSpinnerAdapter;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;

public class SearchActivity extends AppCompatActivity implements SearchActivityView, OnDoneListener{

    private SearchActivityPresenter mPresenter;
    private DatePickerFragmentDialog mDialog;

    @BindView(R.id.ratingBarView) RatingBar mRatingBar;
    @BindView(R.id.genreSpinnerView) Spinner mGenreSpinner;
    @BindView(R.id.datePickerEditTextView) EditText mReleaseDateEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mDialog = new DatePickerFragmentDialog();

        mPresenter = new SearchActivityPresenter(this);

        Genre[] genres = getGenresFromIntent(this, ActivityExtras.GENRE_ARRAY_KEY);
        mPresenter.setGenreSpinnerAdapter(this, genres);
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

    @OnClick(R.id.datepickerImageView)
    public void onDatepickerImageClick(View view) {

        mDialog.show(getSupportFragmentManager(), "dialog_tag");
    }

    @Override
    public void OnDone(String date) {

        mReleaseDateEditTextView.setText(date);
        mDialog.dismiss();
    }
}
