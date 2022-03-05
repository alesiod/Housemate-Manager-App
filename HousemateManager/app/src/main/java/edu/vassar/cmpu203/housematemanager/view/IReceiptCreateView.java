package edu.vassar.cmpu203.housematemanager.view;

import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Housemate;

public interface IReceiptCreateView {
    interface Listener {
        void onSelectPayer(Housemate mate);
    }
}
