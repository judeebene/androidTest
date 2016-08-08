package com.shareqube.todo;

import android.content.Intent;
import android.widget.Button;

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
/**
 * Created by judeebene on 8/8/16.
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
        assertNotNull("Add Item", btn);
        assertNotNull("Add Item", btn);
        assertTrue("Add Item", "Add Item".equals(btn.getText().toString()));
    }




}
