package com.shareqube.todo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by judeebene on 8/8/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class JUnit4RulesTest {

   /* Rules are interceptors which are executed for each test method and are important building
    * blocks of Junit tests.
            */
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addNoteToNotesList() throws Exception {

        String newTodo = "Coding";


        // Type new todolist
        onView(withId(R.id.edit_todo)).perform(typeText(newTodo), closeSoftKeyboard());

        // Click on the add todo button
        onView(withId(R.id.add_todo_btn)).perform(click());



    }



}
