package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/24/2016.
 */

public class GenreFilterableItem implements FilterableItem {

    private Genre[] mSelectedGenres;

    public GenreFilterableItem(Genre[] genres) {

        mSelectedGenres = genres;
    }

    @Override
    public String getValue() {

        String selectedGenresText = "";

        for(int i = 0 ; i < mSelectedGenres.length ; i++) {

            Genre currentGenre = mSelectedGenres[i];
            if(currentGenre.isChecked()) {

                if(i == mSelectedGenres.length - 1) {

                    selectedGenresText += currentGenre.getDescription();
                }
                else {

                    selectedGenresText += currentGenre.getDescription() + ", ";
                }
            }
        }

        return selectedGenresText;
    }
}
