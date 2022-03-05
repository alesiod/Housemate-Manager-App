
package edu.vassar.cmpu203.housematemanager;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import edu.vassar.cmpu203.housematemanager.controller.ControllerActivity;
import edu.vassar.cmpu203.housematemanager.model.Inventory;

@RunWith(AndroidJUnit4.class)
public class ShoppingListInstTest {

    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * A system test for adding an item to shopping list
     */
    @org.junit.Test
    public void testItemAdd() {

        InstTestHelper helper = new InstTestHelper();
        ViewInteraction shoppingList = Espresso.onView(ViewMatchers.withId(R.id.shoppingListTextView));

        // go to shopping list view
        helper.goToShoppingList();

        // check initial text
        shoppingList.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(Inventory.SHOP_LIST_DEFAULT)));

        // add 3 apples
        helper.addShoppingListItem("Apple", 3);

        // check whether 3 Apple shows up on view
        shoppingList.check(ViewAssertions.matches(
                ViewMatchers.withSubstring("3 units of Apple")));

        // add 5 apples
        helper.addShoppingListItem("Apple", 5);

        // check whether 8 Apple shows up on view
        shoppingList.check(ViewAssertions.matches(
                ViewMatchers.withSubstring("8 units of Apple")));
    }

    /**
     * A system test for removing items from shopping list
     */
    @org.junit.Test
    public void testItemRm() {


        InstTestHelper helper = new InstTestHelper();
        ViewInteraction shoppingList = Espresso.onView(ViewMatchers.withId(R.id.shoppingListTextView));

        // go to inventory screen
        helper.goToShoppingList();

        // check initial text
        shoppingList.check(ViewAssertions.matches(
                ViewMatchers.withSubstring(Inventory.SHOP_LIST_DEFAULT)));

        // add 3 apples
        helper.addShoppingListItem("Apple", 3);

        // check whether 3 Apple shows up on view
        shoppingList.check(ViewAssertions.matches(
                ViewMatchers.withSubstring("3 units of Apple")));

        // remove 2 apples
        helper.rmShoppingListItem("Apple", 2);

        // check whether 1 Apple shows up on view
        shoppingList.check(ViewAssertions.matches(
                ViewMatchers.withSubstring("1 units of Apple")));
    }

}