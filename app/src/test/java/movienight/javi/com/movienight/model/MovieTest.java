package movienight.javi.com.movienight.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Javi on 10/21/2016.
 */

public class MovieTest {

    private Movie mMovie;
    private Integer mExpectedId = 246655;
    private String mExpectedOverview = "After the re-emergence of the world's first mutant, world-destroyer Apocalypse, the X-Men must unite to defeat his extinction level plan.";
    private String mExpectedOriginalTitle = "X-Men: Apocalypse";
    private String mExpectedTitle = "X-Men: Apocalypse";
    private Double mExpectedPopularity = 13.733509;
    private Integer mExpectedVoteCount = 2067;
    private Double mExpectedRating = 6.12;
    private Integer[] mExpectedGenreIds = new Integer[]{28,12,14,878};

    @Before
    public void SetUp() throws Exception {

        mMovie = new Movie(
            mExpectedId,
            mExpectedOverview,
            mExpectedOriginalTitle,
            mExpectedTitle,
            mExpectedPopularity,
            mExpectedVoteCount,
            mExpectedRating,
            mExpectedGenreIds
        );
    }

    @Test
    public void getMovieId() throws Exception {

        // Act
        Integer actualId = mMovie.getId();

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
        Integer actualVoteCount = mMovie.getVoteCount();

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
        Integer[] actualIds = mMovie.getGenreIds();

        // Assert
        Assert.assertEquals(mExpectedGenreIds, actualIds);
    }
}
