package movienight.javi.com.movienight;

import android.graphics.Bitmap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javi on 10/21/2016.
 */

public class MovieTest {

    private Movie mMovie;
    private int mExpectedId = 246655;
    private String mExpectedOverview = "After the re-emergence of the world's first mutant, world-destroyer Apocalypse, the X-Men must unite to defeat his extinction level plan.";
    private String mExpectedOriginalTitle = "X-Men: Apocalypse";
    private String mExpectedTitle = "X-Men: Apocalypse";
    private String mExpectedDate = "1999-02-02";
    private double mExpectedPopularity = 13.733509;
    private int mExpectedVoteCount = 2067;
    private Double mExpectedRating = 6.12;
    private int[] mExpectedGenreIds = new int[]{28,12,14,878};
    private String mExpectedPosterPath = "/someposter.jpg";
    private Bitmap mExpectedBitmap = null;

    @Before
    public void SetUp() throws Exception {

        mMovie = new Movie(
            mExpectedId,
            mExpectedOverview,
            mExpectedOriginalTitle,
            mExpectedTitle,
            mExpectedDate,
            mExpectedPopularity,
            mExpectedVoteCount,
            mExpectedRating,
            mExpectedGenreIds,
            mExpectedPosterPath,
            mExpectedBitmap
        );
    }

    @Test
    public void getMovieId() throws Exception {

        // Act
        int actualId = mMovie.getId();

        // Assert
        Assert.assertEquals(mExpectedId, actualId);
    }

    @Test
    public void getMovieOverview() throws Exception {

        // Act
        String actualOverview = mMovie.getOverview();

        // Assert
        Assert.assertEquals(mExpectedOverview, actualOverview);
    }

    @Test
    public void getMovieOriginalTitle() throws Exception {

        // Act
        String actualOriginalTitle = mMovie.getOriginalTitle();

        // Assert
        Assert.assertEquals(mExpectedOriginalTitle, actualOriginalTitle);
    }

    @Test
    public void getMovieTitle() throws Exception {

        // Act
        String actualTitle = mMovie.getTitle();

        // Assert
        Assert.assertEquals(mExpectedTitle, actualTitle);
    }

    @Test
    public void getMoviePopularity() throws Exception {

        // Act
        Double actualPopularity = mMovie.getPopularity();

        // Assert
        Assert.assertEquals(mExpectedPopularity, actualPopularity);
    }

    @Test
    public void getMovieVoteCount() throws Exception {

        // Act
        int actualVoteCount = mMovie.getVoteCount();

        // Assert
        Assert.assertEquals(mExpectedVoteCount, actualVoteCount);
    }

    @Test
    public void getMovieRating() throws Exception {

        // Act
        Double actualRating = mMovie.getRating();

        // Assert
        Assert.assertEquals(mExpectedRating, actualRating);
    }

    @Test
    public void getMovieGenreIds() throws Exception {

        // Act
        int[] actualIds = mMovie.getGenreIds();

        // Assert
        Assert.assertEquals(mExpectedGenreIds, actualIds);
    }

    @Test
    public void emptyReleaseDateReturnsEmptyYearMonthDay() throws Exception {

        // Arrange
        mMovie = new Movie(
            mExpectedId,
            mExpectedOverview,
            mExpectedOriginalTitle,
            mExpectedTitle,
            "",
            mExpectedPopularity,
            mExpectedVoteCount,
            mExpectedRating,
            mExpectedGenreIds,
            mExpectedPosterPath,
            mExpectedBitmap
        );

        // Act
        String actualYear = mMovie.getYearRelease();
        String actualMonth = mMovie.getMonthRelease();
        String actualDay = mMovie.getDayRelease();

        // Assert
        Assert.assertTrue(actualYear.isEmpty());
        Assert.assertTrue(actualMonth.isEmpty());
        Assert.assertTrue(actualDay.isEmpty());
    }
}
