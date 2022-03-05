package edu.vassar.cmpu203.housematemanager.view;

import edu.vassar.cmpu203.housematemanager.model.Receipt;

public interface IReceiptAddItemView {

     interface Listener {
        void onReceiptAddedItem(String name, int qty, double price, IReceiptAddItemView receiptAddItemView);
        void onReceiptRemovedItem(String name, int qty, IReceiptAddItemView receiptAddItemView);
        void onReceiptItemsDone(IReceiptAddItemView receiptAddItemView);
    }

    void updateDisplay(Receipt receipt);
    void noSuchItemError();
    void emptyReceiptError();
}
