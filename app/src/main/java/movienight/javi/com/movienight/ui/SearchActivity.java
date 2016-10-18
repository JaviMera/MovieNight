package movienight.javi.com.movienight.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.InputDevice;
import android.widget.RatingBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.ratingBarView) RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        ((LayerDrawable)mRatingBar.getProgressDrawable()).getDrawable(2).setColorFilter(ContextCompat.getColor(this, R.color.movie_title_text_color), PorterDuff.Mode.SRC_ATOP);
    }
}
