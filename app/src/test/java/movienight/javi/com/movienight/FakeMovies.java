package movienight.javi.com.movienight;

import java.util.HashMap;
import java.util.Map;

import movienight.javi.com.movienight.model.Movie;

/**
 * Created by Javi on 10/22/2016.
 */

public class FakeMovies {

    private static Map<Integer, Movie> mMovies = new HashMap<Integer, Movie>(){
        {
            put(1, new Movie(
                1234,
                "Story about the life of an amazing gorilla that was shot by humans at a zoo",
                "Hashtag Harambe",
                "Harambe",
                9000.0,
                9999999,
                10.0,
                new int[]{12,14}));

            put(2, new Movie(
                    456,
                    "Some amazing fake movie not being made",
                    "Fake mega title",
                    "Fake title",
                    0.000,
                    0,
                    0.0,
                    new int[]{878}));
        }
    };

    public static Movie get(Integer index) {

        return mMovies.get(index);
    }
}