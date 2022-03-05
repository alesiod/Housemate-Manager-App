package edu.vassar.cmpu203.housematemanager.view;

import android.view.View;
import edu.vassar.cmpu203.housematemanager.model.Inventory;

public interface IShoppingListView {

    interface Listener{
        void onShoppingListAddedItem(String name, int qty, IShoppingListView shoppingListView);
        void onShoppingListRemovedItem(String name, int qty, IShoppingListView shoppingListView);
        void onViewInventory();
    }
    void updateDisplay(Inventory inventory);
    void noSuchItemError();
}
