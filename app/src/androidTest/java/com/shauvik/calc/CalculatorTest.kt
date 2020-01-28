package com.shauvik.calc

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.gson.GsonBuilder
import com.moquality.android.RoboConfig
import com.moquality.android.RoboTest
import com.moquality.android.genModels
import com.shauvik.calc.model.Calculator
import org.junit.Rule
import org.junit.Test

@LargeTest
class CalculatorTest {
    @get:Rule
    val activityRule = ActivityTestRule(CalcActivity::class.java)

    @Test
    fun divByZero() {
        Espresso.onView(ViewMatchers.withId(R.id.btn5)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(ViewAssertions.matches(ViewMatchers.withText("5")))
        Espresso.onView(ViewMatchers.withId(R.id.divide)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(ViewAssertions.matches(ViewMatchers.withText("/")))
        Espresso.onView(ViewMatchers.withId(R.id.btn0)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(ViewAssertions.matches(ViewMatchers.withText("0")))
        Espresso.onView(ViewMatchers.withId(R.id.equals)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(ViewAssertions.matches(ViewMatchers.withText("ERROR")))
    }

    @Test
    fun additionTest() {
        Calculator.get().run {
            clickNumber(5)
            clickOperator("+")
            clickNumber(2)
            clickEquals()
            checkResult("7")
        }
    }

    @Test
    fun roboTest() {
        val config = RoboConfig("""{
          "Calculator": {
            "methods": {
              "clickNumber": {
                "params": [
                  {
                    "type": "int",
                    "valid": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
                  }
                ],
                "returns": "Calculator",
                "weight": 3
              },
              "clickOperator": {
                "params": [
                  {
                    "type": "java.lang.String",
                    "valid": ["+", "-"]
                  }
                ],
                "returns": "Calculator",
                "weight": 1
              }
            }
          }
        }""")

        RoboTest(config).run {
            registerPageObject(Calculator.get())
        }.run("Calculator")
    }

    @Test
    fun generate() {
        val models = genModels(Calculator::class.java)

        val gson = GsonBuilder().run {
            setPrettyPrinting()
        }.create()
        Log.d("MODEL OUTPUT", gson.toJson(models))
    }
}