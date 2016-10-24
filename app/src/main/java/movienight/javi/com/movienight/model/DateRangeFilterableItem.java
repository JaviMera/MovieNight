package movienight.javi.com.movienight.model;

/**
 * Created by Javi on 10/24/2016.
 */

public class DateRangeFilterableItem implements FilterableItem {

    private String mStartDate;
    private String mEndDate;

    public DateRangeFilterableItem(String startDate, String endDate) {

        mStartDate = startDate;
        mEndDate = endDate;
    }

    @Override
    public String getValue() {

        return "Searching from " + mStartDate + " to " + mEndDate;
    }
}
