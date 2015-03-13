package com.shauvik.calc;

import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

/**
 * Created by shauvik on 3/12/15.
 */
public class CalcEspressoTest extends ActivityInstrumentationTestCase2<CalcActivity> {
    
    public CalcEspressoTest() {
        super(CalcActivity.class);
    }
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testDivByZero() {
        onView(withId(R.id.btn5)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("5")));
        onView(withId(R.id.divide)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("/")));
        onView(withId(R.id.btn0)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("0")));
        onView(withId(R.id.equals)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("ERROR")));
    }
}
