
Android ReadMe

Project Name: Android support library test cases:


set up environment for testing:

1.According to espresso doc, To avoid flakiness,  turn off system animations on the virtual or physical device(s) used for testing.

    On your device, under Settings->Developer options disable the following 3 settings:
        Window animation scale
        Transition animation scale
        Animator duration scale

2. Note: Make sure you have installed the latest Android Support Repository under Extras in your android studio

3. Add the following to build.gradle
testCompile 'junit:junit:4.12'
    androidTestCompile 'com.android.support:support-annotations:23.4.0'
// Testing-only dependencies
    androidTestCompile 'com.android.support.test:runner:0.5'
    androidTestCompile 'com.android.support.test.espresso:espresso-core:2.2.2'
    // UiAutomator Testing
    // uiAutomator require minSdk of 18
    androidTestCompile 'com.android.support.test.uiautomator:uiautomator-v18:2.1.1'
    androidTestCompile 'org.hamcrest:hamcrest-integration:1.3'
    //robo testing
    testCompile 'org.robolectric:robolectric:3.0'


4.Set the instrumentation runner
    Add to the same build.gradle file the following line in android.defaultConfig:
testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"


5. Create a test configuration

In Android Studio:

    Open Run menu -> Edit Configurations
    Add a new Android Tests configuration
    Choose a module
    Add a specific instrumentation runner:

  android.support.test.runner.AndroidJUnitRunner




 The Junit testing with Robolectric

What the  sample test is set to do

1. The test  first cleared all the todos in the list.

2. then Add 3 entries to the list

3. Read the size of the list

4 using assertTrue to  test if the todos list size equal to  3 and output failed if its false.

A set of assertion methods useful for writing tests. Only failed assertions are recorded.
http://junit.sourceforge.net/javadoc/org/junit/Assert.htm



@FixMethodOrder(MethodSorters.NAME_ASCENDING) annotation is added optionally
to change the test execution order . MethodSorters.NAME_ASCENDING: Sorts the test methods by method name, in lexicographic order.
(good in the case of multiple test).
e.g
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMethodOrder {

    @Test
    public void testA() {
        System.out.println("first");
    }
    @Test
    public void testB() {
        System.out.println("second");
    }
    @Test
    public void testC() {
        System.out.println("third");
    }
}

Above code will execute the test methods in the order of their names, sorted in ascending order



UI testing with Espresso, AndroidUnitRunner , JUnit4 Rules with the ATS



What the test is doing(UI testing)

1. This test will first clear all the todos on the list.
2. Add one todos and saved
3. read all the todos on the list and check if it matches exact one  added earlier
4. if its yes, click it and go to edit Activity and modify the todos and saved

5  read back the modify todos
 5. compare the current todos with the modify todos if its the same , the test passes.

  
    In Espresso tests we can not directly call methods  or field of our Activity. we can only use Espresso to interact with the UI like a real user.
  So  for us to clear the list data on our sample todo app runOnUiThread is required before the actual test is conducted.

@Before Annotataion help us to setup, What ever we need to do before the actually test is conducted.

***Espresso – Entry point to interactions with views (via onView and onData)

*** A collection of hamcrest matchers that match Views.This matchers can be used for UI validation, or data filtering,
You can pass one or more of these to the onView method to locate a view within the current view hierarchy.

e.g in our todo app: "withId(R.id.edit_todo)"
nView(withId(R.id.edit_todo)).perform(typeText(oneEntry),closeSoftKeyboard());

getting our edittext view to perform an action, in our case typing one todo entry.

****checking if a view fulfills an assertion
  check()  and matches() method are used with a ViewMatcher to assert the state of the currently selected view.

e.g in our espresso test
// check if the size is the same
        onView(withId(R.id.listView)).check (matches (todoListSize(readSize)));

*** Some time custom ViewMatcher are written like our "todoListSize" method that return Matchers

in our sample app, we have two custom Matcher 
1. todoListSize and 2 compareTodos




(Important)  During my little reseach on the 4 test cases given.
i realised that **AndroidJunitRunner** is not another testing framework just like espresso but its an  "Instrumentation Testing Runner"
  that runs JUnit3 and JUnit4 tests against an Android package and its still make use of espresso framework for testing with added features.
https://google.github.io/android-testing-support-library/docs/androidjunitrunner-guide/index.html

In our sample app, i extends ActivityInstrumentationTestCase2<MainActivity> for that purpose but its still make use of espresso syntax

I already  set the our test Intrumation runner to used AndroidJUnitRunner in the gradle android.defaultConfig block;

 defaultConfig{
testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner";
}



(Important) JUnit4 Rules with the ATSL is  not another testing framework just like espresso but a set of JUnit rules to be used with the AndroidJUnitRunner that provide more flexibility and reduce the boilerplate code required in tests.
 JUnit4Rules with ATSL stills make use of espresso testing framework but with ActivityTestRule or ServiceTestRule.
This rule provides functional testing of a single activity. The activity under test will be launched before each test annotated with @Test and before any method annotated with @Before 
https://google.github.io/android-testing-support-library/docs/rules/index.html



TO run the  test cases
Right click on  any given test and click Run.

     

(IMportant)  UI Automator: (UI)  test did not suit our  sample to app. UI Automator lets  verify that  app behaves correctly when the user flow crosses into other apps or into the system UI. (i.e we can only test the SYSTEM UI)
