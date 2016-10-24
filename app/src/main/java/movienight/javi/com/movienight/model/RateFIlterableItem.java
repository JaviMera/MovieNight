package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/24/2016.
 */

public class RateFIlterableItem implements FilterableItem {

    private Double mRate;

    public RateFIlterableItem(Double rate) {

        mRate = rate;
    }

    @Override
    public String getValue() {

        return "Searching movies with a minimum rate of " + String.format("%.1f",mRate);
    }
}
