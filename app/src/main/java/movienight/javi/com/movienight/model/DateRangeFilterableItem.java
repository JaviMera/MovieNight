package movienight.javi.com.movienight.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Javi on 10/24/2016.
 */

public class DateRangeFilterableItem implements FilterableItem {

    private Date mStartDate;
    private Date mEndDate;

    public DateRangeFilterableItem(Date startDate, Date endDate) {

        mStartDate = startDate;
        mEndDate = endDate;
    }

    @Override
    public String getTitle() {

        return "With dates between: ";
    }

    @Override
    public String getValue() {

        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
        return formatter.format(mStartDate) + " and " + formatter.format(mEndDate);
    }
}
