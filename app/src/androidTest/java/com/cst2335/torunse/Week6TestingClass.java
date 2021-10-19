package com.cst2335.torunse;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.cst2335.torunse.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Week6TestingClass {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Unit test:
    @Test
    public void week6TestingClass() {
        //finding an object on screen:       //find the view:
        ViewInteraction appCompatEditText = onView(withId(R.id.editText) );


        //replacing with "errr" on the EditText
        appCompatEditText.perform(replaceText("errr"), closeSoftKeyboard());

        //finding a new View:
        ViewInteraction materialButton = onView(withId(R.id.button));

        //clicking the mouse on view R.id.button
        materialButton.perform(click());

        //click back on the edit text
        appCompatEditText.perform(click());

        //typing in new text
        appCompatEditText.perform(replaceText("jhyffhjjrddcvg"));


        appCompatEditText.perform(closeSoftKeyboard());

        ViewInteraction textView = onView(withId(R.id.textView) );

        //assertion
        textView.check(matches(withText("Type in your password:")));

        //change to check that matches "Login"
        ViewInteraction button = onView(withId(R.id.button) );
        button.check(matches(withText("Login"))); //will fail because it's actually Login
    }

    /** Checking for upperCase */
    @Test
    public void week6TestingClassUpperCase() {
        //find the edittext:
        ViewInteraction appCompatEditText = onView(withId(R.id.editText) );
        appCompatEditText.perform(replaceText("ITITITIE"), closeSoftKeyboard());

        //click the button:
        //finding a new View:
        ViewInteraction materialButton = onView(withId(R.id.button));
        //clicking the mouse on view R.id.button
        materialButton.perform(click());

        //assert
        //assertion
        ViewInteraction textView = onView(withId(R.id.textView) );
        textView.check(matches(withText("Type in your password:")));

    }

    /** Checking for upperCase */
    @Test
    public void week6TestingClassComplexEnough() {
        //find the edittext:
        ViewInteraction appCompatEditText = onView(withId(R.id.editText) );
        appCompatEditText.perform(replaceText("abc%^"), closeSoftKeyboard());

        //click the button:
        //finding a new View:
        ViewInteraction materialButton = onView(withId(R.id.button));
        //clicking the mouse on view R.id.button
        materialButton.perform(click());

        //assert
        //assertion
        ViewInteraction textView = onView(withId(R.id.textView) );
        textView.check(matches(withText("Your password is complex enough")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
