package com.shareqube.todo;

/**
 * Created by judeebene on 8/7/16.
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
        import android.support.test.runner.AndroidJUnitRunner;
        import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class JRunnerTodoTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Button add_btn ;
    private MainActivity mActivity ;


    public JRunnerTodoTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();


    }

    @Test
    public void checkActivity() {
        assertThat(mActivity, notNullValue());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }



}


