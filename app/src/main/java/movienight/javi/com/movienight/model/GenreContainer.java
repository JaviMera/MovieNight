package movienight.javi.com.movienight.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import movienight.javi.com.movienight.model.FilterItems.Genre;

/**
 * Created by Javi on 11/4/2016.
 */

public class GenreContainer {

    private static Map<Integer, Genre> mGenresMap;
    static
    {
        mGenresMap = new LinkedHashMap<>();
        mGenresMap.put(28, new Genre(28, "Action"));
        mGenresMap.put(12, new Genre(12, "Adventure"));
        mGenresMap.put(16, new Genre(16, "Animation"));
        mGenresMap.put(35, new Genre(35, "Comedy"));
        mGenresMap.put(80, new Genre(80, "Crime"));
        mGenresMap.put(99, new Genre(99, "Documentary"));
        mGenresMap.put(18, new Genre(18, "Drama"));
        mGenresMap.put(10751, new Genre(10751, "Family"));
        mGenresMap.put(14, new Genre(14, "Fantasy"));
        mGenresMap.put(36, new Genre(36, "History"));
        mGenresMap.put(27, new Genre(27, "Horror"));
        mGenresMap.put(10402, new Genre(10402, "Music"));
        mGenresMap.put(9648, new Genre(9648, "Mystery"));
        mGenresMap.put(10749, new Genre(10749, "Romance"));
        mGenresMap.put(878, new Genre(878, "Science Fiction"));
        mGenresMap.put(10770, new Genre(10770, "TV Movie"));
        mGenresMap.put(53, new Genre(53, "Thriller"));
        mGenresMap.put(10752, new Genre(10752, "War"));
        mGenresMap.put(37, new Genre(37, "Western"));
    }

    public static List<Genre> getAll() {

        return new ArrayList(mGenresMap.values());
    }

    public static List<String> getGenres(int[] genreIds) {

        List<String> genreDescriptions = new ArrayList<>();

        for(Integer id : genreIds) {

            genreDescriptions.add(mGenresMap.get(id).getDescription());
        }

        return genreDescriptions;
    }
}
