package movienight.javi.com.movienight.model;

import junit.framework.Assert;

import org.junit.Test;

import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.GenreUrl;

/**
 * Created by Javi on 10/16/2016.
 */

public class GenreUrlTest {

    private AbstractUrl mUrl;

    @Test
    public void genreUrl() throws Exception {

        // Arrange
        String expectedUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=cc6069d3c0583db3ab20f687c76e9ffd&language=en-US";
        mUrl = new GenreUrl();

        // Act
        String actualUrl = mUrl.toString();

        // Assert
        Assert.assertEquals(expectedUrl, actualUrl);
    }
}
