package edu.vassar.cmpu203.housematemanager.view;

import edu.vassar.cmpu203.housematemanager.model.Housemate;
import edu.vassar.cmpu203.housematemanager.model.Receipt;

public interface IReceiptProcessView {
    interface Listener {
        void onReceiptDebtorSelected(IReceiptProcessView receiptProcessView);
        void onReceiptItemOptedIn(String itemName, Housemate debtor, IReceiptProcessView receiptProcessView);
        void onReceiptItemOptedOut(String itemName, Housemate debtor, IReceiptProcessView receiptProcessView);
        void onReceiptConfirmed();
    }
    void updateDisplay(Receipt receipt);
    void noSuchItemError();
    void noDebtorError();
}

