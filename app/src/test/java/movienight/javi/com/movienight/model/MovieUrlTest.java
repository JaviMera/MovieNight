package movienight.javi.com.movienight.model;

import junit.framework.Assert;

import org.junit.Test;

import movienight.javi.com.movienight.urls.AbstractUrl;
import movienight.javi.com.movienight.urls.MovieUrl;
import movienight.javi.com.movienight.urls.MovieUrlBuilder;

/**
 * Created by Javi on 10/21/2016.
 */

public class MovieUrlTest {

    private AbstractUrl mUrl;

    @Test
    public void movieUrl() throws Exception {

        // Arrange
        String expectedUrl =  "https://api.themoviedb.org/3/discover/movie?api_key=cc6069d3c0583db3ab20f687c76e9ffd" +
                "&language=en-US" +
                "&sort_by=popularity.desc" +
                "&include_adult=false" +
                "&include_video=true" +
                "&page=1" +
                "&release_date.gte=2015-10-1" +
                "&release_date.lte=2016-10-1" +
                "&vote_count.gte=1000" +
                "&vote_average.gte=5.0" +
                "&with_genres=12";

        // Act
        MovieUrlBuilder builder = new MovieUrlBuilder();
        builder
            .withPageNumber("1")
            .withGenres("12")
            .withStartReleaseDate("2015-10-1")
            .withEndReleaseDate("2016-10-1")
            .withVoteCount("1000")
            .withRating("5.0");

        MovieUrl movieUrl = builder.createMovieUrl();

        // Assert
        Assert.assertEquals(expectedUrl, movieUrl.toString());
    }
}
