package edu.vassar.cmpu203.housematemanager.persistence;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.housematemanager.model.Chore;
import edu.vassar.cmpu203.housematemanager.model.ChoreManager;
import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Housemate;
import edu.vassar.cmpu203.housematemanager.model.Inventory;
import edu.vassar.cmpu203.housematemanager.model.Item;
import edu.vassar.cmpu203.housematemanager.model.Receipt;
import edu.vassar.cmpu203.housematemanager.model.ReceiptManager;

public interface IPersistenceFacade {

    // authentication
    void retrieveHouse(String username, DataListener<House> houseDataListener);
    void retrieveInven(String username, DataListener<Inventory> listener);
    void retrieveChore(String username, DataListener<ChoreManager> listener);
    void retrieveReceipts(String username, DataListener<ReceiptManager> listener);

    void createHouseIfNotExists(House h, Inventory i, ChoreManager cm, ReceiptManager rm, BinaryResultListener listener);

    void setHouse(House house, BinaryResultListener listener);
    void setInven(Inventory i, House house, BinaryResultListener listener);
    void setChore(ChoreManager cm, House house, BinaryResultListener listener);
    void setReceipts(ReceiptManager rm, House house, BinaryResultListener listener);
    void set(House h, Inventory i, ChoreManager cm, ReceiptManager rm, BinaryResultListener b);

        interface InventoryListener {
        void onInventoryReceived(Inventory inventory);
    }

    interface HouseListener {
        void onHouseReceived(House house);
    }

    interface ChoreManagerListener {
        void onChoreListReceived(ChoreManager ChoreManager);
    }

    interface ReceiptManagerListener {
        void onChoreListReceived(ReceiptManager ReceiptManager);
    }

    interface DataListener<T>{
        void onDataReceived(@NonNull T data);
        void onNoDataFound();
    }

    interface BinaryResultListener {
        void onYesResult();
        void onNoResult();
    }


}