package edu.vassar.cmpu203.housematemanager.view;

import edu.vassar.cmpu203.housematemanager.model.House;

public interface IHouseView {

    interface Listener{
        void onAddHousemate(String name, IHouseView houseView);
        void onRemovedHousemate(String name, IHouseView houseView);
    }
    void updateDisplay(House house);
    void noSuchHousemateError();
    void duplicateHousemateError();

}
