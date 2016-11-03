package movienight.javi.com.movienight;

import junit.framework.Assert;

import org.junit.Test;

import movienight.javi.com.movienight.model.FilterItems.Genre;

/**
 * Created by Javi on 10/16/2016.
 */

public class GenreTests {

    private Genre mGenre;

    @Test
    public void genreInit() throws Exception {

        // Arrange
        Integer expectedId = 1234;
        String expectedDesc = "Action";
        mGenre = new Genre(expectedId, expectedDesc);

        // Act
        Integer actualId = mGenre.getId();
        String actualDesc = mGenre.getDescription();

        // Assert
        Assert.assertEquals(expectedId, actualId);
        Assert.assertEquals(expectedDesc, actualDesc);
    }
}
