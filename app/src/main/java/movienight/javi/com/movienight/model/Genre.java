package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/16/2016.
 */
public class Genre {

    private Integer mId;
    private String mDescription;

    public Genre(Integer id, String description) {

        mId = id;
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public Integer getId() {
        return mId;
    }
}
