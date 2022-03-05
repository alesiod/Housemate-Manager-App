package edu.vassar.cmpu203.housematemanager.view;

import android.view.View;
import edu.vassar.cmpu203.housematemanager.model.Inventory;

public interface IHomeView {

    public interface Listener{
        void onViewInventory();
        void onViewShoppingList();
        void onViewReceipt();
        void onViewChoreList();
        void onViewHousemates();
        void onViewDebts();
        void onViewPastReceipts();
    }

}
