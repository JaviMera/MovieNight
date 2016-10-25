package movienight.javi.com.movienight;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.model.MovieRequest;

/**
 * Created by Javi on 10/18/2016.
 */

public class MovieRequestTest {

    private MovieRequest mMovieRequest;

    @Before
    public void SetUp () throws Exception {

        mMovieRequest = new MovieRequest();
    }

    @Test
    public void setMovieRequestGenre() throws Exception {

        // Arrange
        Genre[] expectedGenre = new Genre[]{new Genre(1235, "Lol")};

        // Act
        mMovieRequest.setGenre(expectedGenre);

        // Assert
        Assert.assertEquals(expectedGenre, mMovieRequest.getGenreSelected());
    }

    @Test
    public void setMovieRequestRating() throws Exception {

        // Arrange
        Float expectedRating = 6.5f;

        // Act
        mMovieRequest.setRating(expectedRating);

        // Assert
        Assert.assertEquals(expectedRating, mMovieRequest.getRatingSelected());
    }

    @Test
    public void setMovieRequestVoteCount() throws Exception {

        // Arrange
        int expectedVoteCount = 3245;

        // Act
        mMovieRequest.setVoteCount(expectedVoteCount);

        // Assert
        Assert.assertEquals(expectedVoteCount, mMovieRequest.getVoteCountSelected());
    }

    @Test
    public void setMovieRequestReleaseDate() throws Exception {

        // Arrange
        String expectedDate = "10/18/2016";

        // Act
        mMovieRequest.setStartDateRelease(expectedDate);

        // Assert
        Assert.assertEquals(expectedDate, mMovieRequest.getStartDateReleaseSelected());
    }
}
