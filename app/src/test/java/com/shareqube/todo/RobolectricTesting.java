package com.shareqube.todo;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
/**
 * Created by judeebene on 8/8/16.
 */

/*


What the test is doing

1. The test  first cleared all the todos in the list.

2. Add 3 entries to the list

3. Read the size of the list

4 use assertTrue to the test if the todos list size equal to  3 and output failed if its false.

A set of assertion methods useful for writing tests. Only failed assertions are recorded.
http://junit.sourceforge.net/javadoc/org/junit/Assert.htm


 */
@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@Config(constants = BuildConfig.class, sdk = 21)

public class RobolectricTesting {

    private MainActivity activity;
    private EditItemActivity editActivity;

    @Before
    public void setup(){
        //activity = Robolectric.setupActivity(MainActivity.class);
        activity = Robolectric.buildActivity(MainActivity.class).create().get();

    }

    @Test
    public void addTodoBtn(){


        Button btn = (Button) activity.findViewById(R.id.add_todo_btn);


        // clear
        activity.todoItem.clear();

        //add 3 entry
        String entries[] = {" entry1 ", "todo oo" , " todooo"};

        EditText editText = (EditText) activity.findViewById(R.id.edit_todo);

        for(String todo: entries){

            editText.setText(todo);

            btn.performClick();

        }

        // read the size of the list

        int todoSize =  activity.todoItem.size();

        assertTrue(" failed" , todoSize==3);



    }




}
