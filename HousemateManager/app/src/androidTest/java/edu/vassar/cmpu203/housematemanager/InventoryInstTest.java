
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
import edu.vassar.cmpu203.housematemanager.model.Inventory;

@RunWith(AndroidJUnit4.class)
public class InventoryInstTest {

    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * A system test for adding inventory item.
     */
    @org.junit.Test
    public void testItemAdd() {

        InstTestHelper helper = new InstTestHelper();
        ViewInteraction inventory = Espresso.onView(ViewMatchers.withId(R.id.inventoryTextView));

        String item1 = "Apple";
        int qty1 = 3;
        int qty2 = 5;
        int qtySum = qty1 + qty2;
        StringBuilder sb1 = new StringBuilder().append(qty1).append(" units of ").append(item1);
        StringBuilder sb2 = new StringBuilder().append(qtySum).append(" units of ").append(item1);

        // login and go to inventory view
        helper.login();
        helper.goToInventory();

        // check initial text
        inventory.check(ViewAssertions.matches(
                ViewMatchers.withText(Inventory.INVENT_DEFAULT)));

        // add 3 apples
        helper.addInventoryItem(item1, qty1);

        // check whether 3 Apple shows up on view
        inventory.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(sb1.toString())));

        // add 5 apples
        helper.addInventoryItem(item1, qty2);

        // check whether 8 Apple shows up on view
        inventory.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(sb2.toString())));
    }

    /**
     * A system test for removing inventory items.
     */
    @org.junit.Test
    public void testItemRm() {

        InstTestHelper helper = new InstTestHelper();
        ViewInteraction inventory = Espresso.onView(ViewMatchers.withId(R.id.inventoryTextView));
        
        String item1 = "Apple";
        int qty1 = 3;
        int qty2 = 2;
        int qtyDiff = qty1 - qty2;
        StringBuilder sb1 = new StringBuilder().append(qty1).append(" units of ").append(item1);
        StringBuilder sb2 = new StringBuilder().append(qtyDiff).append(" units of ").append(item1);
        
        // login and go to inventory view
        helper.login();
        helper.goToInventory();

        // check initial text
        inventory.check(ViewAssertions.matches(
                ViewMatchers.withText(Inventory.INVENT_DEFAULT)));

        helper.addInventoryItem(item1, qty1);

        // check whether 3 Apple shows up on view
        inventory.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(sb1.toString())));

        helper.rmInventoryItem(item1, qty2);

        // check whether 1 Apple shows up on view
        inventory.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(sb2.toString())));
    }

}