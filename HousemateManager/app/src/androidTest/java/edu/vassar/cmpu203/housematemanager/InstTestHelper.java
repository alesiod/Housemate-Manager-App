
package edu.vassar.cmpu203.housematemanager;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matchers;

import edu.vassar.cmpu203.housematemanager.controller.ContextActivity;
import edu.vassar.cmpu203.housematemanager.model.Housemate;

public class InstTestHelper {

    private final Context context = ContextActivity.context;
    private final String TEST_USERNAME = context.getResources().getString(R.string.test_username);
    private final String TEST_PASSWORD = context.getResources().getString(R.string.test_password);

    /**
     * A system test for logging in
     */
    public void login() {
        Log.d("System Test", "test helper logging in as tester");
        // enter username
        Espresso.onView(ViewMatchers.withId(R.id.houseNameEditText))
                .perform(ViewActions.typeText(TEST_USERNAME));

        Espresso.pressBack();

        // enter password
        Espresso.onView(ViewMatchers.withId(R.id.HousePasswordEditText))
                .perform(ViewActions.typeText(TEST_PASSWORD));

        Espresso.pressBack();

        // press login button
        Espresso.onView(ViewMatchers.withId(R.id.SignInButton))
                .perform(ViewActions.click());
    }

    /**
     * A system test for navigating to inventory
     */
    public void goToInventory() {
        Log.d("System Test", "test helper navigating to inventory");

        // navigate to home screen
        Espresso.pressBack();

        // press inventory button
        Espresso.onView(ViewMatchers.withId(R.id.homeInventoryButton))
                .perform(ViewActions.click());
    }

    /**
     * A system test for adding an item to inventory
     * @param itemID
     * @param qty
     */
    public void addInventoryItem(String itemID, int qty) {
        Log.d("System Test", "test helper adding item to inventory");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.inventoryNameEditText)).perform(
                ViewActions.typeText(itemID));
        Espresso.pressBack();

        // type qty
        Espresso.onView(ViewMatchers.withId(R.id.inventoryQtyEditText)).perform(
                ViewActions.typeText(Integer.toString(qty)));
        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.inventoryAddItemButton))).perform(
                ViewActions.click());
    }

    /**
     * A system test for removing an item from inventory
     * @param itemID
     * @param qty
     */
    public void rmInventoryItem(String itemID, int qty) {
        Log.d("System Test", "test helper removing item from inventory");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.inventoryNameEditText)).perform(
                ViewActions.typeText(itemID));
        Espresso.pressBack();

        // type qty
        Espresso.onView(ViewMatchers.withId(R.id.inventoryQtyEditText)).perform(
                ViewActions.typeText(Integer.toString(qty)));
        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.inventoryRmItemButton))).perform(
                ViewActions.click());
    }

    /**
     * A system test for navigating to shopping list
     */
    public void goToShoppingList() {
        Log.d("System Test", "test helper navigating to shopping list");

        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.homeShoppingListButton)).perform(
                ViewActions.click());
    }

    /**
     * A system test for adding an item to shopping list
     * @param itemID
     * @param qty
     */
    public void addShoppingListItem(String itemID, int qty) {
        Log.d("System Test", "test helper adding item to shopping list");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.shoppingListNameEditText)).perform(
                ViewActions.typeText(itemID));
        Espresso.pressBack();

        // type qty
        Espresso.onView(ViewMatchers.withId(R.id.shoppingLIstQtyEditText)).perform(
                ViewActions.typeText(Integer.toString(qty)));
        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.shoppingListAddItemButton))).perform(
                ViewActions.click());
    }

    /**
     * A system test for removing an item from shopping list
     * @param itemID
     * @param qty
     */
    public void rmShoppingListItem(String itemID, int qty) {
        Log.d("System Test", "test helper removing item from shopping list");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.shoppingListNameEditText)).perform(
                ViewActions.typeText(itemID));

        Espresso.pressBack();

        // type qty
        Espresso.onView(ViewMatchers.withId(R.id.shoppingLIstQtyEditText)).perform(
                ViewActions.typeText(Integer.toString(qty)));

        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.shoppingListRmItemButton))).perform(
                ViewActions.click());
    }

    /**
     * A system test for navigating to house
     */
    public void goToHouse() {
        Log.d("System Test", "test helper navigating to house");

        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.homeHouseButton)).perform(
                ViewActions.click());
    }

    /**
     * A system test for adding a housemate to house
     * @param name
     */
    public void addHousemate(String name) {
        Log.d("System Test", "test helper adding housemate to house");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.houseNameEditText)).perform(
                ViewActions.typeText(name));

        Espresso.pressBack();

        // press add housemate button
        Espresso.onView((ViewMatchers.withId(R.id.houseAddButton))).perform(
                ViewActions.click());
    }

    /**
     * A system test for removing a housemate from a house
     * @param name
     */
    public void rmHousemate(String name) {
        Log.d("System Test", "test helper removing housemate to house");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.houseNameEditText)).perform(
                ViewActions.typeText(name));

        Espresso.pressBack();

        // press add housemate button
        Espresso.onView((ViewMatchers.withId(R.id.houseRmButton)))
                .perform(ViewActions.click());
    }

    /**
     * A system test for navigating to receipt
     */
    public void goToReceipt() {
        Log.d("System Test", "test helper navigating to receipt");

        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.homeReceiptButton))
                .perform(ViewActions.click());
    }

    /**
     * A system test for choosing a payer
     * @param name
     */
    public void choosePayer(String name) {
        Log.d("System Test", "test helper choosing payer");

        Housemate mate = new Housemate(name);

        Espresso.onData(Matchers.allOf(
                Matchers.is(Matchers.instanceOf(Housemate.class)),
                Matchers.is(mate)))
                .perform(ViewActions.click());

    }

    /**
     * A system test for creating a new receipt
     */
    public void createReceipt() {
        Log.d("System Test", "test helper creating receipt");

        Espresso.onView(ViewMatchers.withId(R.id.receiptCreateButton))
                .perform(ViewActions.click());
    }

    /**
     * a system test for adding an item to receipt
     * @param name
     * @param qty
     * @param price
     */
    public void addReceiptItem(String name, int qty, double price) {
        Log.d("System Test", "test helper adding item to receipt");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.receiptAddItemNameEditText)).perform(
                ViewActions.typeText(name));
        Espresso.pressBack();

        // type qty
        Espresso.onView(ViewMatchers.withId(R.id.receiptAddItemQtyEditText)).perform(
                ViewActions.typeText(Integer.toString(qty)));
        Espresso.pressBack();

        // type price
        Espresso.onView(ViewMatchers.withId(R.id.receiptAddItemPriceEditText)).perform(
                ViewActions.typeText(Double.toString(price)));
        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.receiptAddItemAddButton))).perform(
                ViewActions.click());
    }

    /**
     * a system test for removing an item from receipt
     * @param name
     * @param qty
     * @param price
     */
    public void rmReceiptItem(String name, int qty, double price) {
        Log.d("System Test", "test helper adding item to receipt");

        // type name
        Espresso.onView(ViewMatchers.withId(R.id.receiptAddItemNameEditText)).perform(
                ViewActions.typeText(name));
        Espresso.pressBack();

        // type qty
        Espresso.onView(ViewMatchers.withId(R.id.receiptAddItemQtyEditText)).perform(
                ViewActions.typeText(Integer.toString(qty)));
        Espresso.pressBack();

        // type price
        Espresso.onView(ViewMatchers.withId(R.id.receiptAddItemPriceEditText)).perform(
                ViewActions.typeText(Double.toString(price)));
        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.receiptAddItemAddButton))).perform(
                ViewActions.click());
    }

    /**
     * A system test for navigating to chore list
     */
    public void goToChoreList() {
        Log.d("System Test", "test helper navigating to chore list");

        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.homeChoreListButton)).perform(
                ViewActions.click());
    }

    /**
     * A system test for adding a chore to chore list
     * @param chore
     * @param owner
     * @param assigner
     */
    public void addChore(String chore, String owner, String assigner) {
        Log.d("System Test", "test helper adding chore to chore list");

        Espresso.onView(ViewMatchers.withId(R.id.choreEditText))
                .perform(ViewActions.typeText(chore));

        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.ownerEditText))
                .perform(ViewActions.typeText(owner));

        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.assignerEditText))
                .perform(ViewActions.typeText(assigner));

        Espresso.pressBack();
        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.choreAddButton)))
                .perform(ViewActions.click());
    }

    /**
     * A system test for removing a chore from chore list
     * @param chore
     * @param owner
     * @param assigner
     */
    public void rmChore(String chore, String owner, String assigner) {
        Log.d("System Test", "test helper adding chore to chore list");

        Espresso.onView(ViewMatchers.withId(R.id.choreEditText))
                .perform(ViewActions.typeText(chore));

        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.ownerEditText))
                .perform(ViewActions.typeText(owner));

        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.assignerEditText))
                .perform(ViewActions.typeText(assigner));

        Espresso.pressBack();
        Espresso.pressBack();

        // press add item button
        Espresso.onView((ViewMatchers.withId(R.id.choreRmButton)))
                .perform(ViewActions.click());
    }

}
