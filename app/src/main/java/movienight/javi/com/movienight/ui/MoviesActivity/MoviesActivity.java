package movienight.javi.com.movienight.ui.MoviesActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.adapters.PageSpinnerAdapter;
import movienight.javi.com.movienight.asyntasks.MovieAsyncTask;
import movienight.javi.com.movienight.asyntasks.MoviePagesAsyncTaskListener;
import movienight.javi.com.movienight.model.Page;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

public class MoviesActivity extends AppCompatActivity implements MoviePagesAsyncTaskListener{

    private Map<Integer, Page> mMapPages;
    private Integer mTotalPages;

    @BindView(R.id.pageSpinnerView) Spinner mPageSpinnerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);
        mMapPages = new LinkedHashMap<>();

        MovieUrlBuilder builder = new MovieUrlBuilder();
        builder
                .withPageNumber("1")
                .withGenres("12")
                .withStartReleaseDate("2000-10-1")
                .withEndReleaseDate("2016-10-1")
                .withVoteCount("1000")
                .withRating("5.0");

        MovieUrl url = builder.createMovieUrl();
        new MovieAsyncTask(this).execute(url);
    }

    @Override
    public void onCompleted(Integer totalPages, Page page) {

        if(null == mTotalPages){

            mTotalPages = totalPages;
            String[] spinnerItems = new String[mTotalPages];

            for(int p = 0 ; p < mTotalPages ; p++) {

                spinnerItems[p] = "Page " + (p + 1);
            }

            PageSpinnerAdapter adapter = new PageSpinnerAdapter(this, spinnerItems);
            mPageSpinnerView.setAdapter(adapter);
        }

        mMapPages.put(page.getNumber(), page);
    }
}

