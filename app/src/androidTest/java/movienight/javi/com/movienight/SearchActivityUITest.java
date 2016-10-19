package movienight.javi.com.movienight;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import movienight.javi.com.movienight.adapters.GenreSpinnerAdapter;
import movienight.javi.com.movienight.model.Genre;
import movienight.javi.com.movienight.ui.ActivityExtras;
import movienight.javi.com.movienight.ui.SearchActivity.SearchActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static okhttp3.internal.Internal.instance;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityUITest {

    private Intent mIntent;

    @Rule
    public ActivityTestRule<SearchActivity> activityRule =
            new ActivityTestRule<SearchActivity>(SearchActivity.class, true, false);

    @Before
    public void SetUp () throws Exception {

        Genre[] someGenres = new Genre[1];
        someGenres[0] = new Genre(1, "Say my name");

        mIntent = new Intent();
        mIntent.putExtra(ActivityExtras.GENRE_ARRAY_KEY, someGenres);
    }

    @Test
    public void datePickerButtonPressShowsDatePickerDialog() throws Exception {

        // Act
        activityRule.launchActivity(mIntent);
        onView(withId(R.id.datePickerButtonView)).perform(click());

        // Assert
        onView(withId(R.id.releaseDatePickerView)).check(matches(isDisplayed()));
        onView(withId(R.id.doneDatepickerButtonView)).check(matches(isDisplayed()));
    }

    @Test
    public void datePickerDoneButtonSetsDateEditTextView() throws Exception {

        // Arrange
        // let the default date be the current date when the date picker is opened
        String expectedDate = "10/19/2016";

        // Act
        activityRule.launchActivity(mIntent);
        onView(withId(R.id.datePickerButtonView)).perform(click());
        onView(withId(R.id.doneDatepickerButtonView)).perform(click());

        // Assert
        onView(withId(R.id.datePickerButtonView)).check(matches(new ButtonTextMatcher(expectedDate)));
    }

    @Test
    public void seekBarSwipeUpdatesValueTextView() throws Exception {

        // Arrange
        int progress = 25;
        String expectedSeekBarValue = "2.5";

        // Act
        activityRule.launchActivity(mIntent);
        onView(withId(R.id.seekBarView)).perform(SetProgress(progress));

        // Assert
        onView(withId(R.id.seekbarResultTextView)).check(matches(withText(expectedSeekBarValue)));
    }

    private ViewAction SetProgress(int progress) {

        final int p = progress;
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }

            @Override
            public String getDescription() {
                return "Set a custom progress on Seek Bar";
            }

            @Override
            public void perform(UiController uiController, View view) {

                SeekBar bar = (SeekBar)view;
                bar.setProgress(p);
            }
        };
    }

    private class ButtonTextMatcher extends BaseMatcher {

        private String mExpectedText;
        private String mActualText;

        public ButtonTextMatcher(String text) {

            mExpectedText = text;
        }

        @Override
        public boolean matches(Object item) {
            AppCompatButton button = (AppCompatButton)item;
            mActualText = button.getText().toString();

            return mActualText.equals(mExpectedText);
        }

        @Override
        public void describeTo(Description description) {

            description.appendText(mActualText + " does not equal " + mExpectedText);
        }
    }
}
