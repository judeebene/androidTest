package com.shareqube.todo;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by judeebene on 8/8/16.
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
public class JUnit4RulesTest {

   /* Rules are interceptors which are executed for each test method and are important building
    * blocks of Junit tests.
    *
    *
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


    public static String getTodoFromEditText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "getting text from Todo EditText";
            }

            @Override
            public void perform(UiController uiController, View view) {

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



                return ((ListView) view).getCount () == size;
            }


        };
    }


    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public  void setup(){
        mMainActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMainActivityTestRule.getActivity().todoItemAdapter.clear();
            }
        });

    }


    @Test
    public void todoA(){

        //add entry
        String oneEntry = " one todo";

        onView(withId(R.id.edit_todo)).perform(typeText(oneEntry),closeSoftKeyboard());



    }

    @Test
    public void todoB(){
        // Click on the add todo button
        onView(withId(R.id.add_todo_btn)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listView));


        // read list size
        int readSize = mMainActivityTestRule.getActivity().todoItemAdapter.getCount();

        // check if the size is the same
        onView(withId(R.id.listView)).check (matches (todoListSize(readSize)));

        onData(allOf(is(instanceOf(String.class))))
                .perform(click());

        //now modify the entry

        onView(withId(R.id.editText_todo))
                .perform(clearText(), typeText("modify Todo"));


        //read  the modify TodoList

        String read_modify_todo =  getTodoFromEditText(withId(R.id.editText_todo));



        onView(withId(R.id.editText_todo)).check (matches (compareTodos(read_modify_todo)));
    }







}
