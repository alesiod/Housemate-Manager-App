package edu.vassar.cmpu203.housematemanager.view;

import java.util.List;

import edu.vassar.cmpu203.housematemanager.model.ChoreManager;
import edu.vassar.cmpu203.housematemanager.model.Housemate;
import edu.vassar.cmpu203.housematemanager.model.Inventory;


public interface IChoreListView {



    interface Listener{
        void onChoreListAddedItem(String name, String owner, String assigner, IChoreListView choreListView);
        void onChoreListRemovedItem(String name, String owner, String assigner, IChoreListView choreListView);
        void onViewInventory(); // keep this? idk navigation
    }
    void updateDisplay(ChoreManager manager, List<Housemate> housemates);
    void hmDNEerror();
    void choreAEerror();
    void removeError();


}
