package edu.vassar.cmpu203.housematemanager.view;

import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.ReceiptManager;

public interface IPastReceiptsView {

    interface Listener{
        void onPastReceiptSelected(IPastReceiptsView pastReceiptsView);
    }
    void updateDisplay();
}
