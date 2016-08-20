package com.shareqube.todo;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.clearText ;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by judeebene on 8/7/16.
 */

/*
What the test is doing

1. This test will first clear all the todos on the list.
2. Add one todos and saved
3. read all the todos on the list and check if it matches exact one to you added earlier
4. if its yes, click it and go to edit Activity and modify the todos and saved

5  read back the modify todos
 5. compare the current todos with the modify if its the same , the test pass
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TodoExpressoTest {


    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);


    //A collection of hamcrest matchers that match Views.
    //There are a number of situations where matchers are invaluble, such as UI validation, or data filtering,
    public static String getTodoFromEditText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
             //   Returns a matcher that matches Views which are an instance of or subclass of the provided class
                return isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "getting text from Todo EditText";
            }

            @Override
            public void perform(UiController uiController, View view) {


              //  Provides base-level UI operations (such as injection of MotionEvents) that can be used to build user actions such as clicks, scrolls, swipes, etc.

                        EditText tv = (EditText) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }




    public static Matcher<View> compareTodos(final String modifyTodo){
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("The modify todo  expected is wrong");
            }

            @Override
            protected boolean matchesSafely(View view) {
                if(!(view instanceof EditText)) return false;

               String readTodo = ((EditText) view).getText().toString();

                Log.e("TESTTTTT" , readTodo);


                return readTodo.equals(modifyTodo) ;
            }


        };
    }


    public static Matcher<View> todoListSize (final int size) {
        return new TypeSafeMatcher<View> () {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText ("ListView should have " + size + " items");

            }

            @Override public boolean matchesSafely (final View view) {

            //Convenient base method for Matchers that require a non-null value of a specific type. This simply implements the null check, checks the type and then casts.

                return ((ListView) view).getCount () == size;
            }


        };
    }

    /*

    In your Espresso tests we can not directly call methods  or fieil of our Activity. we can only use Espresso to interact with the UI like a real user.
  Note: for us clear the list data  runOnUiThread is required:
     */

    @Before
    public void setUp(){
        main.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        main.getActivity().todoItemAdapter.clear();
                    }
                });
    }





    @Test()
    public void addAndCheckSize(){


        //add entry
        String oneEntry = " one todo";

        onView(withId(R.id.edit_todo)).perform(typeText(oneEntry),closeSoftKeyboard());

        // Click on the add todo button
        onView(withId(R.id.add_todo_btn)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listView));

        // read list size
        int readSize = main.getActivity().todoItemAdapter.getCount();

        // check if the size is the same
        onView(withId(R.id.listView)).check (matches (todoListSize(readSize)));


        // if  yes click the todo
        onData(allOf(is(instanceOf(String.class))))
                .perform(click());




        //now modify the entry

        onView(withId(R.id.editText_todo))
        .perform(clearText(), typeText("modify Todo"));

        //read  the modify TodoList

        String read_modify_todo =  getTodoFromEditText(withId(R.id.editText_todo));


        //compare it.

        onView(withId(R.id.editText_todo)).check (matches (compareTodos(read_modify_todo)));






    }



}
