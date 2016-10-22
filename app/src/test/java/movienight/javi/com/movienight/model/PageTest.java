package movienight.javi.com.movienight.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Javi on 10/21/2016.
 */

public class PageTest {

    private Page mPage;

    @Test
    public void pageInit() throws Exception {

        // Arrange
        Integer expectedNumber = 1;
        Movie expectedMovie1 = createFirstMovie();
        Movie expectedMovie2 = createSecondMovie();
        mPage = new Page(expectedNumber, new Movie[]{expectedMovie1, expectedMovie2});

        // Act
        Integer actualNumber = mPage.getNumber();
        Movie[] actualMovies = mPage.getMovies();

        // Assert
        Assert.assertEquals(expectedNumber, actualNumber);
        Assert.assertEquals(expectedMovie1, actualMovies[0]);
        Assert.assertEquals(expectedMovie2, actualMovies[1]);
    }

    private Movie createFirstMovie() {

        return new Movie(
            1234,
            "Story about the life of an amazing gorilla that was shot by humans at a zoo",
            "Hashtag Harambe",
            "Harambe",
            9000.0,
            9999999,
            10.0,
            new int[]{12,14});
    }

    private Movie createSecondMovie() {

        return new Movie(
                456,
                "Some amazing fake movie not being made",
                "Fake mega title",
                "Fake title",
                0.000,
                0,
                0.0,
                new int[]{878});
    }
}
