package com.shauvik.calc;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.runner.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.moquality.android.MoQuality;
import com.shauvik.calc.model.Calculator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shauvik on 3/12/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalcEspressoTest {

    @Rule
    public ActivityTestRule<CalcActivity> activityRule
            = new ActivityTestRule<>(CalcActivity.class);

    @Test
    public void divByZero() {
        onView(withId(R.id.btn5)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("5")));
        onView(withId(R.id.divide)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("/")));
        onView(withId(R.id.btn0)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("0")));
        onView(withId(R.id.equals)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("ERROR")));
    }

    @Test
    public void additionTest() {
        Calculator.get()
                .clickNumber(5)
                .clickOperator("+")
                .clickNumber(2)
                .clickEquals()
                .checkResult("7");
    }

    @Test
    public void roboTest() {
        // TODO: Parse POM - Calculator into JSON

        // Page Object Model
        String pom="{" +
                "objects=[" +
                    "{" +
                    "class=\"com.shauvik.calc.model.Calculator\"," +
                    "methods={" +
                        "\"clickNumber(Integer)\":\"Calculator\"," +
                        "\"clickOperator(String)\":\"Calculator\"," +
                        "\"clickEquals()\":\"Calculator\"," +
                        "\"checkResult(String)\":\"Calculator\"" +
                        "}" +
                    "}" +
                "[" +
            "}";

        // Ordered rule list to match screens to Page Objects
        // * = matches anything
        // id(foo) - matches id foo
        // text(bla) - matches text
        // class(LinearLayout) - matches class
        // desc(text) - matches content desc
        // allOf(<one>,<two>) - AND
        // anyOf(<one>,<two>) - OR
        String pageObjectMapping = "[" +
                "[\"*\", \"Calculator\"]" +
                "]";

        String traces = "clickNumber(5)\n" +
                "clickOperator(\"+\")\n" +
                "clickNumber(2)\n" +
                "clickEquals()\n" +
                "checkResult(\"7\")";

        // Filter - white/blacklist
        String filter = "{'positive':[], 'negative':[]}";

        Map<String, String> config = new HashMap();
        config.put("pom", pom);
        config.put("mapping", pageObjectMapping);
        config.put("traces", traces);
        config.put("filter", filter);


        MoQuality.get()
                .registerPageObjects(Calculator.get())
                .startRoboTest(config);
    }
}
