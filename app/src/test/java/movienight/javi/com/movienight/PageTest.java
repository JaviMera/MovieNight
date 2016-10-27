package movienight.javi.com.movienight;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import movienight.javi.com.movienight.model.Movie;
import movienight.javi.com.movienight.model.Page;

/**
 * Created by Javi on 10/21/2016.
 */

public class PageTest {

    private Page mPage;

    @Test
    public void pageInit() throws Exception {

        // Arrange
        Integer expectedNumber = 1;
        Movie expectedMovie1 = FakeMovies.get(0);
        Movie expectedMovie2 = FakeMovies.get(1);
        mPage = new Page(expectedNumber, new Movie[]{expectedMovie1, expectedMovie2});

        // Act
        Integer actualNumber = mPage.getNumber();
        Movie[] actualMovies = mPage.getMovies();

        // Assert
        Assert.assertEquals(expectedNumber, actualNumber);
        Assert.assertEquals(expectedMovie1, actualMovies[0]);
        Assert.assertEquals(expectedMovie2, actualMovies[1]);
    }
}
