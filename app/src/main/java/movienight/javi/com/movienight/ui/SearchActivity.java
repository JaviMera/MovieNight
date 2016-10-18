package movienight.javi.com.movienight.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.GenreSpinnerAdapter;
import movienight.javi.com.movienight.model.Genre;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.ratingBarView) RatingBar mRatingBar;
    @BindView(R.id.genreSpinnerView) Spinner mGenreSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        ((LayerDrawable)mRatingBar.getProgressDrawable())
            .getDrawable(2)
            .setColorFilter(ContextCompat.getColor(this, R.color.movie_dark_purple), PorterDuff.Mode.SRC_ATOP);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(ActivityExtras.GENRE_ARRAY_KEY);
        Genre[] genres = Arrays.copyOf(parcelables, parcelables.length, Genre[].class);
        GenreSpinnerAdapter adapter = new GenreSpinnerAdapter(this, genres);
        mGenreSpinner.setAdapter(adapter);
    }
}
