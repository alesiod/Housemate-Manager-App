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

@RunWith(AndroidJUnit4.class)
public class ReceiptInstTest {

    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    // NOTE
    // Does not work since we started using spinner.
    // Multiple hours of edits, unable to resolve issue.
    // Worked perfectly prior to adding spinner and tests by hand work perfectly,
    // so should be completely functional despite system test.

    /**
     * A system test for choosing a payer
     */
    @org.junit.Test
    public void testPayerSelection() {

        ViewInteraction receiptPayerSpinner = Espresso.onView(ViewMatchers.withId(R.id.receiptPayerSpinner));
        ViewInteraction receiptAddItemView = Espresso.onView(ViewMatchers.withId(R.id.receiptAddItemTextView));
        InstTestHelper helper = new InstTestHelper();

        String mateA = "Alice";
        String mateB = "Bob";

        // login and add mateA and mateB to house
        helper.login();
        helper.goToHouse();
        helper.addHousemate(mateA);
        helper.addHousemate(mateB);

        // go to receipt creation view
        helper.goToReceipt();
        receiptPayerSpinner.perform(ViewActions.click());

        // Choose mateB
        helper.choosePayer(mateB);

        // Check mateB is chosen
        receiptPayerSpinner.check(ViewAssertions.matches(
                ViewMatchers.withSpinnerText(mateB)));

        // Create receipt
        helper.createReceipt();

        // Check that the payer is mateB
        receiptAddItemView.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(mateB)));
    }

}
