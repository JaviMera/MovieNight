package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/24/2016.
 */

public class RateFIlterableItem implements FilterableItem {

    private float mRate;

    public RateFIlterableItem(float rate) {

        mRate = rate;
    }

    @Override
    public String getTitle() {

        return "With an average of: ";
    }

    @Override
    public String getValue() {

        return String.format("%.1f",mRate);
    }
}
