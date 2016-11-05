package movienight.javi.com.movienight.dialogs.MovieDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Film;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 11/1/2016.
 */
public class FilmDialogFragment extends DialogFragment implements MovieDialogFramgnetView {

    private Film mFilm;
    private List<String> mMovieGenres;
    private MovieDialogFragmentPresenter mPresenter;

    @BindView(R.id.movieTitleDialogTextView) TextView mTitleTextView;
    @BindView(R.id.movieOverviewDialogTextView) TextView mOverviewTextView;
    @BindView(R.id.moviePosterDialogImageView) ImageView mPosterImageView;
    @BindView(R.id.movieRatingDialogTextView) TextView mRatingTextView;
    @BindView(R.id.movieVoteCountDialogTextView) TextView mVoteCountTextView;
    @BindView(R.id.movieGenresDialogTextView) TextView mGenresTextView;
    @BindView(R.id.movieReleaseDateDialogTextView) TextView mReleaseDateTextView;

    public static FilmDialogFragment newInstance(Film film, List<String> genreDescriptions) {

        FilmDialogFragment dialog = new FilmDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ActivityExtras.SELECTED_MOVIE_KEY, film);
        bundle.putStringArrayList(ActivityExtras.GENRES_KEY, (ArrayList)genreDescriptions);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieGenres = new ArrayList<>();
        mFilm = getArguments().getParcelable(ActivityExtras.SELECTED_MOVIE_KEY);
        mMovieGenres = getArguments().getStringArrayList(ActivityExtras.GENRES_KEY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();
        Resources resources = context.getResources();

        View view = LayoutInflater.from(context).inflate(R.layout.movie_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mPresenter = new MovieDialogFragmentPresenter(this);

        mPresenter.setTextViewText(
            mTitleTextView,
            resources.getString(R.string.movie_title_dialog),
            mFilm.getName(),
            mFilm.getYearRelease()
        );

        mPresenter.setTextViewText(
            mOverviewTextView,
            "%s",
            mFilm.getOverview()
        );

        mPresenter.setTextViewText(
            mRatingTextView,
            resources.getString(R.string.movie_rating_text_dialog),
            mFilm.getRating()
        );

        mPresenter.setTextViewText(
            mVoteCountTextView,
            resources.getString(R.string.movie_vote_count_text_dialog),
            mFilm.getVoteCount()
        );

        mPresenter.setPosterImageView(mFilm.getPoster());
        mPresenter.setGenresDescriptionsTextViewText(mMovieGenres);

        mPresenter.setReleaseDateTextViewText(
            mFilm.getYearRelease(),
            mFilm.getMonthRelease(),
            mFilm.getDayRelease()
        );

        return dialogBuilder.create();
    }

    @Override
    public void setTextViewText(TextView view, String text, Object... params) {

        String titleFormat = getStringFormat(text, params);
        view.setText(titleFormat);
    }

    @Override
    public void setPosterImageView(Bitmap poster) {

        mPosterImageView.setImageBitmap(poster);
    }

    @Override
    public void setGenresDescriptionsTextViewText(List<String> genres) {

        String genresDescription = getGenresDescriptions(genres);
        mGenresTextView.setText(genresDescription);
    }

    @Override
    public void setReleaseDateTextViewText(String year, String month, String day) {

        String format = "Unknown";

        if(!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {

            Calendar c = getCalendar(year, month, day);
            format = getReleaseDateFormat(c, ActivityExtras.MOVIE_DIALOG_RELEASE_DATE_FORMAT);
        }

        mReleaseDateTextView.setText(format);
    }

    private String getStringFormat(String text, Object... formatParameters) {

        return String.format(Locale.ENGLISH, text, formatParameters);
    }

    private Calendar getCalendar(String year, String month, String day) {

        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yearInt);
        c.set(Calendar.MONTH, monthInt);
        c.set(Calendar.DAY_OF_MONTH, dayInt);

        return c;
    }

    private String getGenresDescriptions(List<String> genres) {

        String genresDescriptions = "";
        for(String genre : genres) {

            genresDescriptions += genre + "  ";
        }

        return genresDescriptions;
    }

    private String getReleaseDateFormat(Calendar c, String dateFormat) {

        return new SimpleDateFormat(dateFormat)
                .format(c.getTime());
    }

}