package edu.vassar.cmpu203.housematemanager.view;

import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Housemate;
import edu.vassar.cmpu203.housematemanager.model.ReceiptManager;
// Not Sure what this will be yet


public interface IDebtsView {



    interface Listener{
        void onDebtorSelected(IDebtsView debtsView);
        void onAddedDebt(Housemate debtor, Housemate creditor, double amount, IDebtsView debtsView);
        void onCreditorSelected(IDebtsView debtsView);
        void onRemovedDebt(Housemate debtor, Housemate creditor, double amount, IDebtsView debtsView);
    }
    void updateDisplay(House house);
    void negativeAmountError();
    void debtDNEError();
    void overdraftError(Housemate debtor, Housemate creditor, double tag);
    void samePersonError();

}
