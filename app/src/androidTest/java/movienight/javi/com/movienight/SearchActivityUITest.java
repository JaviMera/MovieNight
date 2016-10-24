package movienight.javi.com.movienight;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityUITest {

    private String[] mFilterItems;

    @Rule
    public ActivityTestRule<SearchActivity> activityRule =
            new ActivityTestRule<SearchActivity>(SearchActivity.class);

    @Before
    public void SetUp () throws Exception {

        mFilterItems = activityRule.getActivity().getResources().getStringArray(R.array.filter_options_array);
    }

    @Test
    public void genreSpinnerItemClickDisplaysGenreDialog() throws Exception {

        // Arrange
        String expectedItem = mFilterItems[1];

        // Act
        onView(withId(R.id.filterMoviesSpinnerView)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(expectedItem))).perform(click());

        // Assert
        onView(withId(R.id.genresRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.genresDoneButtonView)).check(matches(isDisplayed()));

        // Assert that the spinner goes back to showing filter by item
        onView(withId(R.id.genresDoneButtonView)).perform(click()); // close the genre dialog
        onView(withId(R.id.filterMoviesSpinnerView)).check(matches(withSpinnerText(mFilterItems[0])));
    }

    @Test
    public void dateRangeSpinnerItemClickDisplaysDateRangeDialog() throws Exception {

        // Arrange
        String expectedItem = mFilterItems[2];

        // Act
        onView(withId(R.id.filterMoviesSpinnerView)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(expectedItem))).perform(click());

        // Assert
        onView(withId(R.id.startDateRangePickerView)).check(matches(isDisplayed()));
        onView(withId(R.id.endDateRangePickerView)).check(matches(isDisplayed()));

        // Assert that the spinner goes back to showing filter by item
        onView(withId(R.id.dateRangeDoneButtonView)).perform(click()); // close the genre dialog
        onView(withId(R.id.filterMoviesSpinnerView)).check(matches(withSpinnerText(mFilterItems[0])));
    }

    @Test
    public void ratingSpinnerItemClickDisplaysDateRangeDialog() throws Exception {

        // Arrange
        String expectedItem = mFilterItems[3];

        // Act
        onView(withId(R.id.filterMoviesSpinnerView)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(expectedItem))).perform(click());

        // Assert
        onView(withId(R.id.rateBarView)).check(matches(isDisplayed()));

        // Assert that the spinner goes back to showing filter by item
        onView(withId(R.id.rateDoneButtonView)).perform(click()); // close the genre dialog
        onView(withId(R.id.filterMoviesSpinnerView)).check(matches(withSpinnerText(mFilterItems[0])));
    }

    @Test
    public void voteCountSpinnerItemClickDisplaysDateRangeDialog() throws Exception {

        // Arrange
        String expectedItem = mFilterItems[4];

        // Act
        onView(withId(R.id.filterMoviesSpinnerView)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(expectedItem))).perform(click());

        // Assert
        onView(withId(R.id.voteCountEditTextView)).check(matches(isDisplayed()));

        // Assert that the spinner goes back to showing filter by item
        onView(withId(R.id.voteCountDoneButtonView)).perform(click()); // close the genre dialog
        onView(withId(R.id.filterMoviesSpinnerView)).check(matches(withSpinnerText(mFilterItems[0])));
    }
}
