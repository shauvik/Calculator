package com.shauvik.calc.model;

import com.shauvik.calc.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

public class Calculator {

    private static Calculator instance;
    private Calculator() { }

    public static Calculator get() {
        if(instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    public Calculator clickNumber(int number) {
        onView(withText(String.format("%d", number))).perform(click());
        return this;
    }

    public Calculator clickOperator(String op) {
        onView(withText(op)).perform(click());
        return this;
    }

    public Calculator clickEquals() {
        onView(withText("=")).perform(click());
        return this;
    }

    public Calculator checkResult(String result) {
        onView(withId(R.id.textView)).check(matches(withText(result)));
        return this;
    }
}
