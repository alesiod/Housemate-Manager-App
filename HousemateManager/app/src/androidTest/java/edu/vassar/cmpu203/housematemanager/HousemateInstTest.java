
package edu.vassar.cmpu203.housematemanager;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import edu.vassar.cmpu203.housematemanager.controller.ControllerActivity;
import edu.vassar.cmpu203.housematemanager.model.House;

@RunWith(AndroidJUnit4.class)
public class HousemateInstTest {

    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * A system test for adding housemate
     */
    @org.junit.Test
    public void testHousemateAdd() {

        ViewInteraction houseView = Espresso.onView(ViewMatchers.withId(R.id.houseTextView));
        InstTestHelper helper = new InstTestHelper();

        String mateA = "Alice";
        String mateB = "Bob";

        // login and goto house view
        helper.login();
        helper.goToHouse();

        // check initial text
        houseView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(House.DEFAULT_TEXT)));

        // add mateA
        helper.addHousemate(mateA);

        // add mateB
        helper.addHousemate(mateB);

        // check whether mateA shows up on view
        houseView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(mateA)));

        // check whether mateB shows up on view
        houseView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(mateB)));
    }

    /**
     * A system test for removing housemate
     */
    @org.junit.Test
    public void testHousemateRemove() {

        InstTestHelper helper = new InstTestHelper();
        ViewInteraction houseView = Espresso.onView(ViewMatchers.withId(R.id.houseTextView));

        String mateA = "Alice";

        // login and go to house view
        helper.login();
        helper.goToHouse();

        // check initial text
        houseView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(House.DEFAULT_TEXT)));

        // add mateA
        helper.addHousemate(mateA);

        // check whether mateA shows up on view
        houseView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(mateA)));

        // remove mateA
        helper.rmHousemate(mateA);

        // check initial text
        houseView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(House.DEFAULT_TEXT)));
    }

}