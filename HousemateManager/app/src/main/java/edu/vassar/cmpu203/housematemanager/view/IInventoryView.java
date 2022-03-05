package edu.vassar.cmpu203.housematemanager.view;

import edu.vassar.cmpu203.housematemanager.model.Inventory;

public interface IInventoryView {

    interface Listener{
        void onInventoryAddedItem(String name, int qty, IInventoryView inventoryView);
        void onInventoryRemovedItem(String name, int qty, IInventoryView inventoryView);
        void onViewShoppingList();
    }
    void updateDisplay(Inventory inventory);
    void noSuchItemError();

}
