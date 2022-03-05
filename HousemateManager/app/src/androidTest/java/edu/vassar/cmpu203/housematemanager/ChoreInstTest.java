
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
public class ChoreInstTest {

    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * A system test for adding a chore
     */
    @org.junit.Test
    public void testChoreAdd() {

        ViewInteraction choreView = Espresso.onView(ViewMatchers.withId(R.id.choreListTextView));
        InstTestHelper helper = new InstTestHelper();

        String chore = "cleaning";
        String assigner = "Alice";
        String owner = "Bob";
        StringBuilder sb = new StringBuilder().append(chore).append(" assigned by ").append(assigner).append(" to ").append(owner);

        // login and add housemate A and B
        helper.login();
        helper.goToHouse();
        helper.addHousemate(assigner);
        helper.addHousemate(owner);

        // go to chore
        helper.goToChoreList();

        // check initial text
        choreView.check(ViewAssertions.matches(
                ViewMatchers.withText("")));

        // alice give cleaning job to bob
        helper.addChore(chore, owner, assigner);

        // check text
        choreView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(sb.toString())));
    }

    /**
     * A system test for adding then removing chore
     */
    @org.junit.Test
    public void testChoreRm() {

        ViewInteraction choreView = Espresso.onView(ViewMatchers.withId(R.id.choreListTextView));
        InstTestHelper helper = new InstTestHelper();

        String chore = "cleaning";
        String assigner = "Alice";
        String owner = "Bob";
        StringBuilder sb = new StringBuilder().append(chore).append(" assigned by ").append(assigner).append(" to ").append(owner);

        // login and add housemate A and B
        helper.login();
        helper.goToHouse();
        helper.addHousemate(assigner);
        helper.addHousemate(owner);

        // go to chore
        helper.goToChoreList();

        // check initial text
        choreView.check(ViewAssertions.matches(
                ViewMatchers.withText("")));

        // alice give cleaning job to bob
        helper.addChore(chore, owner, assigner);

        // check text
        choreView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(sb.toString())));

        // remove chore
        helper.rmChore(chore, owner, assigner);

        // check that chore is gone
        choreView.check(ViewAssertions.matches(
                ViewMatchers.withText("")));
    }


}