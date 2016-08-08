package com.shareqube.todo;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by judeebene on 8/7/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TodoExpressoTest {

    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    @Test()
    public  void TodoListTest(){

        onView(withId(R.id.add_todo_btn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.add_todo_btn)).check(ViewAssertions.matches(isClickable()));
        onView(withId(R.id.add_todo_btn)).perform(click());
    }
}
