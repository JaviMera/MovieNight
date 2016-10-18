package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/18/2016.
 */

public class MovieRequest {

    private Genre mGenreSelected;
    private double mRatingSelected;
    private String mDateReleaseSelected;
    private int mVoteCountSelected;

    public MovieRequest() {}

    public String getDateReleaseSelected() {
        return mDateReleaseSelected;
    }

    public Genre getGenreSelected() {
        return mGenreSelected;
    }

    public double getRatingSelected() {
        return mRatingSelected;
    }

    public int getVoteCountSelected() {
        return mVoteCountSelected;
    }

    public void setGenre(Genre genre) {

        mGenreSelected = genre;
    }

    public void setRating(double rating) {

        mRatingSelected = rating;
    }

    public void setVoteCount(int voteCount) {

        mVoteCountSelected = voteCount;
    }

    public void setReleaseDate(String releaseDate) {

        mDateReleaseSelected = releaseDate;
    }
}
