package edu.vassar.cmpu203.housematemanager.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import edu.vassar.cmpu203.housematemanager.model.ChoreManager;
import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Inventory;
import edu.vassar.cmpu203.housematemanager.model.ReceiptManager;

public class FirestoreFacade implements IPersistenceFacade {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String HOUSES = "houses";

    @Override
    public void createHouseIfNotExists(House h, Inventory i, ChoreManager cm, ReceiptManager rm, BinaryResultListener listener) {
        this.retrieveHouse(h.getUsername(), new DataListener<House>() {
                    @Override
                    public void onDataReceived(@NonNull House house) { // there's data there, so no go
                        listener.onNoResult();
                    }

                    @Override
                    public void onNoDataFound() { // there's no data there, so we can add the user
                        FirestoreFacade.this.set(h, i, cm, rm, listener);
                        //FirestoreFacade.this.setHouseTest(house);
                    }
                }
        );
    }

    public void set(House h, Inventory i, ChoreManager cm, ReceiptManager rm, BinaryResultListener b) {
        Log.d("Firestone", "saving everything");
        setHouse(h, b);
        setInven(i, h, b);
        setChore(cm, h, b);
        setReceipts(rm, h, b);
    }

    public void setHouse(@NonNull House house, @NonNull BinaryResultListener listener){
        this.db.collection(HOUSES)
                .document(house.getUsername())
                .set(house)
                .addOnSuccessListener( avoid -> listener.onYesResult())
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error adding house",e));
    }

    public void setInven(@NonNull Inventory inventory, @NonNull House house, @NonNull BinaryResultListener listener) {
        this.db.collection(HOUSES)
                .document(house.getUsername() + "_inven")
                .set(inventory)
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error adding inventory"));
    }

    public void setChore(@NonNull ChoreManager cm, @NonNull House house, @NonNull BinaryResultListener listener) {
        this.db.collection(HOUSES)
                .document(house.getUsername() + "_chore")
                .set(cm)
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error adding chores"));
    }

    public void setReceipts(@NonNull ReceiptManager rm, @NonNull House house, @NonNull BinaryResultListener listener) {
        this.db.collection(HOUSES)
                .document(house.getUsername() + "_receipts")
                .set(rm)
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error adding receipts"));
    }

    @Override
    public void retrieveHouse(@NonNull String username, @NonNull DataListener<House> listener) {
        this.db.collection(HOUSES).document(username).get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        House house = dsnap.toObject(House.class);
                        assert (house != null);
                        listener.onDataReceived(house);
                    } else listener.onNoDataFound();  // no username match
                })
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error retrieving user from database",e));
    }
    @Override
    public void retrieveInven(String username, DataListener<Inventory> listener) {
        this.db.collection(HOUSES).document(username + "_inven").get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        Inventory inventory = dsnap.toObject(Inventory.class);
                        assert (inventory != null);
                        listener.onDataReceived(inventory);
                    } else listener.onNoDataFound();  // no username match
                })
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error retrieving user from database",e));
    }
    @Override
    public void retrieveChore(String username, DataListener<ChoreManager> listener) {
        this.db.collection(HOUSES).document(username + "_chore").get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        ChoreManager cm = dsnap.toObject(ChoreManager.class);
                        assert (cm != null);
                        listener.onDataReceived(cm);
                    } else listener.onNoDataFound();  // no username match
                })
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error retrieving user from database",e));
    }
    @Override
    public void retrieveReceipts(String username, DataListener<ReceiptManager> listener) {
        this.db.collection(HOUSES).document(username + "_receipts").get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        ReceiptManager rm = dsnap.toObject(ReceiptManager.class);
                        assert (rm != null);
                        listener.onDataReceived(rm);
                    } else listener.onNoDataFound();  // no username match
                })
                .addOnFailureListener(e ->
                        Log.w("HousemateManager", "Error retrieving user from database",e));
    }
}
