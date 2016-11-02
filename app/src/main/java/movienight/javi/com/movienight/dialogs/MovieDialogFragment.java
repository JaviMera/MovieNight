package movienight.javi.com.movienight.dialogs;

import android.app.Dialog;
import android.content.Context;
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

import movienight.javi.com.movienight.R;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.ui.ActivityExtras;

/**
 * Created by Javi on 11/1/2016.
 */
public class MovieDialogFragment extends DialogFragment {

    private Movie mMovie;
    private List<Genre> mMovieGenres;

    public static MovieDialogFragment newInstance(Movie movie, List<Genre> genres) {

        MovieDialogFragment dialog = new MovieDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ActivityExtras.SELECTED_MOVIE_KEY, movie);
        bundle.putParcelableArrayList(ActivityExtras.GENRES_KEY, (ArrayList)genres);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovie = new Movie();
        mMovieGenres = new ArrayList<>();

        mMovie = getArguments().getParcelable(ActivityExtras.SELECTED_MOVIE_KEY);
        mMovieGenres = getArguments().getParcelableArrayList(ActivityExtras.GENRES_KEY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.movie_dialog_layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        final TextView movieTitleTextView = (TextView) view.findViewById(R.id.movieTitleDialogTextView);
        String movieTitleFormat = context.getResources().getString(R.string.movie_title_dialog);
        movieTitleTextView.setText(String.format(movieTitleFormat, mMovie.getTitle(), mMovie.getYearRelease()));

        final TextView movieOverviewTextView = (TextView) view.findViewById(R.id.movieOverviewDialogTextView);
        movieOverviewTextView.setText(mMovie.getOverview());

        final ImageView moviePosterImageView = (ImageView) view.findViewById(R.id.moviePosterDialogImageView);
        moviePosterImageView.setImageBitmap(mMovie.getPoster());

        final TextView movieVoteCountTextView = (TextView) view.findViewById(R.id.movieVoteCountDialogTextView);
        String voteCountText = context.getResources().getString(R.string.movie_vote_count_text_dialog);
        String voteCountFormat = String.format(Locale.ENGLISH, voteCountText, mMovie.getVoteCount());
        movieVoteCountTextView.setText(voteCountFormat);

        final TextView movieRatingTextView = (TextView) view.findViewById(R.id.movieRatingDialogTextView);
        String ratingText = context.getResources().getString(R.string.movie_rating_text_dialog);
        String ratingFormat = String.format(Locale.ENGLISH, ratingText, mMovie.getRating());
        movieRatingTextView.setText(ratingFormat);

        final TextView movieGenresTextView = (TextView) view.findViewById(R.id.movieGenresDialogTextView);
        String genresDescription = "";
        for(Genre genre : mMovieGenres) {

            genresDescription += genre.getDescription() + "  ";
        }

        movieGenresTextView.setText(genresDescription);

        final TextView movieReleaseDateTextView = (TextView) view.findViewById(R.id.movieReleaseDateDialogTextView);
        int year = Integer.parseInt(mMovie.getYearRelease());
        int month = Integer.parseInt(mMovie.getMonthRelease());
        int day = Integer.parseInt(mMovie.getDayRelease());

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String format = new SimpleDateFormat(ActivityExtras.MOVIE_DIALOG_RELEASE_DATE_FORMAT).format(c.getTime());
        movieReleaseDateTextView.setText(format);

        return dialogBuilder.create();
    }
}