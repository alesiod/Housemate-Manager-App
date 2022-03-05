package edu.vassar.cmpu203.housematemanager.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.vassar.cmpu203.housematemanager.R;
import edu.vassar.cmpu203.housematemanager.model.ChoreManager;
import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Housemate;
import edu.vassar.cmpu203.housematemanager.model.Inventory;
import edu.vassar.cmpu203.housematemanager.model.Receipt;
import edu.vassar.cmpu203.housematemanager.model.ReceiptManager;
import edu.vassar.cmpu203.housematemanager.persistence.FirestoreFacade;
import edu.vassar.cmpu203.housematemanager.persistence.IPersistenceFacade;
import edu.vassar.cmpu203.housematemanager.persistence.IPersistenceFacade.BinaryResultListener;
import edu.vassar.cmpu203.housematemanager.view.AuthFragment;
import edu.vassar.cmpu203.housematemanager.view.ChoreListFragment;
import edu.vassar.cmpu203.housematemanager.view.DebtsFragment;
import edu.vassar.cmpu203.housematemanager.view.HomeFragment;
import edu.vassar.cmpu203.housematemanager.view.HouseFragment;
import edu.vassar.cmpu203.housematemanager.view.IAuthView;
import edu.vassar.cmpu203.housematemanager.view.IChoreListView;
import edu.vassar.cmpu203.housematemanager.view.IDebtsView;
import edu.vassar.cmpu203.housematemanager.view.IHomeView;
import edu.vassar.cmpu203.housematemanager.view.IHouseView;
import edu.vassar.cmpu203.housematemanager.view.IInventoryView;
import edu.vassar.cmpu203.housematemanager.view.IMainView;
import edu.vassar.cmpu203.housematemanager.view.IPastReceiptsView;
import edu.vassar.cmpu203.housematemanager.view.IReceiptAddItemView;
import edu.vassar.cmpu203.housematemanager.view.IReceiptCreateView;
import edu.vassar.cmpu203.housematemanager.view.IReceiptProcessView;
import edu.vassar.cmpu203.housematemanager.view.IShoppingListView;
import edu.vassar.cmpu203.housematemanager.view.InventoryFragment;
import edu.vassar.cmpu203.housematemanager.view.MainView;
import edu.vassar.cmpu203.housematemanager.view.PastReceiptsFragment;
import edu.vassar.cmpu203.housematemanager.view.ReceiptAddItemFragment;
import edu.vassar.cmpu203.housematemanager.view.ReceiptCreateFragment;
import edu.vassar.cmpu203.housematemanager.view.ReceiptProcessFragment;
import edu.vassar.cmpu203.housematemanager.view.ShoppingListFragment;


public class ControllerActivity extends AppCompatActivity implements IHomeView.Listener,
        IInventoryView.Listener, IShoppingListView.Listener, IReceiptAddItemView.Listener,
        IChoreListView.Listener, IHouseView.Listener, IReceiptCreateView.Listener,
        IReceiptProcessView.Listener, IDebtsView.Listener, IPastReceiptsView.Listener, IAuthView.Listener {

    private IMainView mainView;  // view logic class
    private Inventory inventory;
    private ChoreManager choreManager;
    private ReceiptManager receiptManager;
    private final IPersistenceFacade persistenceFacade = new FirestoreFacade();
    private House curHouse;
    private boolean isLoggedIn;

    private String test_username;
    private String test_password;
    private boolean isTesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.curHouse = new House();
        this.inventory = new Inventory();
        this.receiptManager = new ReceiptManager();
        this.choreManager = new ChoreManager();
        this.isLoggedIn = false;

        test_username = this.getString(R.string.test_username);
        test_password = this.getString(R.string.test_password);
        isTesting = false;
        super.onCreate(savedInstanceState);


        // set display
        this.mainView = new MainView(this);
        this.setContentView(this.mainView.getRootView());
        this.mainView.displayFragment(new AuthFragment(this));

    }

    // backpress to return to home menu
    @Override
    public void onBackPressed() {
        if (this.isLoggedIn)
            this.mainView.displayFragment(new HomeFragment(this));
    }

    @Override
    public void onViewInventory() {
        InventoryFragment inventoryView = new InventoryFragment(this);
        this.mainView.displayFragment(inventoryView);
        inventoryView.updateDisplay(this.inventory);
    }

    @Override
    public void onInventoryAddedItem(String name, int qty, IInventoryView inventoryView) {
        Log.d("Housemate Manager", "controller is handling inventory item addition");

        this.inventory.addItem(name, qty);
        inventoryView.updateDisplay(this.inventory);
        this.saveInven();
    }

    @Override
    public void onInventoryRemovedItem(String itemID, int qty, IInventoryView inventoryView) {
        Log.d("Housemate Manager", "controller is handling inventory item removal");

        if (this.inventory.useItem(itemID, qty)) {
            Log.d("Housemate Manager", "controller removed item from inventory");
            inventoryView.updateDisplay(this.inventory);
            this.saveInven();
        } else {
            Log.d("Housemate Manager", "controller failed to remove item from inventory");
            inventoryView.noSuchItemError();
        }
    }

    @Override
    public void onViewShoppingList() {
        ShoppingListFragment shoppingListView = new ShoppingListFragment(this);
        this.mainView.displayFragment(shoppingListView);
        shoppingListView.updateDisplay(this.inventory);
    }

    @Override
    public void onShoppingListAddedItem(String itemID, int qty, IShoppingListView shoppingListView) {
        Log.d("Housemate Manager", "controller is handling shopping list item addition");

        this.inventory.addDesired(itemID, qty);
        shoppingListView.updateDisplay(this.inventory);
        this.saveInven();
    }

    @Override
    public void onShoppingListRemovedItem(String itemID, int qty, IShoppingListView shoppingListView) {
        Log.d("Housemate Manager", "controller is handling shopping list item removal");

        if (this.inventory.subDesired(itemID, qty)) {
            Log.d("Housemate Manager", "controller removed item from inventory");
            shoppingListView.updateDisplay(this.inventory);
            this.saveInven();
        } else {
            Log.d("Housemate Manager", "controller failed to remove item from inventory");
            shoppingListView.noSuchItemError();
        }
    }

    @Override
    public void onViewReceipt() {
        Receipt r = this.receiptManager.getReceipt();
        if (r == null) {
            Log.d("Housemate Manager", "controller started receipt creation");
            List<Housemate> debtors = this.curHouse.getHousemates();
            ReceiptCreateFragment receiptCreateView = new ReceiptCreateFragment(debtors, this);
            this.mainView.displayFragment(receiptCreateView);
        } else if (!r.getItemsDone()) {
            Log.d("Housemate Manager", "controller started receipt item addition");
            ReceiptAddItemFragment receiptAddItemView = new ReceiptAddItemFragment(this);
            this.mainView.displayFragment(receiptAddItemView);
            receiptAddItemView.updateDisplay(r);
        } else {
            Log.d("Housemate Manager", "controller started receipt opt-in/out process");
            List<Housemate> debtors = r.getDebtors();
            ReceiptProcessFragment receiptProcessView = new ReceiptProcessFragment(debtors, this);
            this.mainView.displayFragment(receiptProcessView);
        }
    }

    @Override
    public void onSelectPayer(Housemate payer) {
        Log.d("Housemate Manager", "controller created new receipt");
        this.receiptManager.startReceipt(payer, curHouse.getHousemates());
        this.onViewReceipt();
    }

    @Override
    public void onReceiptAddedItem(String itemID, int qty, double price, IReceiptAddItemView receiptAddItemView) {
        Log.d("Housemate Manager", "controller adding receipt item");

        Receipt r = this.receiptManager.getReceipt();
        r.addItem(itemID, qty, price);
        receiptAddItemView.updateDisplay(r);
        this.saveReceipts();
    }

    @Override
    public void onReceiptRemovedItem(String itemID, int qty, IReceiptAddItemView receiptAddItemView) {
        Log.d("Housemate Manager", "controller removing receipt item");
        Receipt r = this.receiptManager.getReceipt();
        if (r.rmItem(itemID, qty)) {
            Log.d("Housemate Manager", "controller removed receipt item");
            receiptAddItemView.updateDisplay(r);
            this.saveReceipts();
        } else {
            Log.d("Housemate Manager", "controller failed to remove receipt item");
            receiptAddItemView.noSuchItemError();
        }
    }

    @Override
    public void onReceiptItemsDone(IReceiptAddItemView receiptAddItemView) {
        Receipt r = receiptManager.getReceipt();
        if (r.size() == 0) {
            Log.d("Housemate Manager", "controller found no item in receipt");
            receiptAddItemView.emptyReceiptError();
        } else {
            this.receiptManager.getReceipt().itemsDone();
            this.onViewReceipt();
            this.saveReceipts();
        }
    }

    @Override
    public void onReceiptDebtorSelected(IReceiptProcessView receiptProcessView) {
        receiptProcessView.updateDisplay(this.receiptManager.getReceipt());
    }

    @Override
    public void onReceiptItemOptedIn(String itemName, Housemate debtor, IReceiptProcessView receiptProcessView) {
        Receipt r = this.receiptManager.getReceipt();
        if (r.hasItem(itemName)) {
            Log.d("Housemate Manager", "controller opting user into item");
            r.optInItem(itemName, debtor);
            receiptProcessView.updateDisplay(r);
            this.saveReceipts();
        } else {
            Log.d("Housemate Manager", "controller detected user input error");
            receiptProcessView.noSuchItemError();
        }
    }

    @Override
    public void onReceiptItemOptedOut(String itemName, Housemate debtor, IReceiptProcessView receiptProcessView) {
        Receipt r = this.receiptManager.getReceipt();
        if (r.hasItem(itemName)) {
            Log.d("Housemate Manager", "controller opting user out of item");

            // If everyone opted out of an item, alert user and auto opt-in payer
            if (!r.optOutItem(itemName, debtor)) {
                Log.d("Housemate Manager", "controller auto opted-in payer");
                receiptProcessView.noDebtorError();
            }
            receiptProcessView.updateDisplay(r);
            this.saveReceipts();
        } else {
            Log.d("Housemate Manager", "controller detected user input error");
            receiptProcessView.noSuchItemError();
        }
    }

    @Override
    public void onReceiptConfirmed() {
        Log.d("Housemate Manager", "controller ending and processing receipt");
        receiptManager.endReceipt(this.inventory);
        this.saveAll();
        this.mainView.displayFragment(new HomeFragment(this));
    }

    @Override
    public void onViewChoreList() {
        ChoreListFragment choreListView = new ChoreListFragment(this);
        this.mainView.displayFragment(choreListView);
        choreListView.updateDisplay(choreManager, curHouse.getHousemates());
    }

    @Override
    public void onChoreListAddedItem(String name, String owner, String assigner, IChoreListView choreListView) {
        if (choreManager.HousemateDNE(owner, assigner, curHouse.getHousemates())) {
            Log.d("Housemate Manager", "assigned by or to a Housemate that DNE");
            choreListView.hmDNEerror();
        }
        else if (choreManager.choreAE(name)){ //discuss chore name already exists error
            Log.d("Housemate Manager", "tried to create a chore that already exists");
            choreListView.choreAEerror();
        }
        else {
            Log.d("Housemate Manager", "Controller is handling Chore List Addition");
            this.choreManager.assignChore(name, assigner, owner);
            Log.d("Housemate Manager", "Chore added");
            this.saveChores();
            choreListView.updateDisplay(choreManager, curHouse.getHousemates());
        }
    }

    public void onChoreListRemovedItem(String name, String owner, String assigner, IChoreListView choreListView) {
        Log.d("Housemate Manager", "Controller is handling Chore List Removal");
        boolean error = choreManager.removeChore(name, owner, assigner);
        if (!error){
            Log.d("Housemate Manager", "error removing chore");
            choreListView.removeError();
        }
        this.saveChores();
        choreListView.updateDisplay(choreManager, curHouse.getHousemates());
    }

    @Override
    public void onViewHousemates() {
        HouseFragment houseView = new HouseFragment(this);
        this.mainView.displayFragment(houseView);
        houseView.updateDisplay(curHouse);
    }


    @Override
    public void onAddHousemate(String name, IHouseView houseView) {
        Log.d("Housemate Manager", "controller adding new housemate");
        if (curHouse.addHousemate(name)) {
            Log.d("Housemate Manager", "controller added housemate");
            houseView.updateDisplay(this.curHouse);
            this.saveHouse();
        } else {
            Log.d("Housemate Manager", "controller failed to add housemate");
            houseView.duplicateHousemateError();
        }
    }

    @Override
    public void onRemovedHousemate(String name, IHouseView houseView) {
        Log.d("Housemate Manager", "controller removing housemate");
        if (this.curHouse.removeHousemate(name)) {
            Log.d("Housemate Manager", "controller removed housemate");
            houseView.updateDisplay(this.curHouse);
            this.saveHouse();
        } else {
            Log.d("Housemate Manager", "controller failed to remove housemate");
            houseView.noSuchHousemateError();
        }
    }

    @Override
    public void onViewDebts() {
        DebtsFragment debtView = new DebtsFragment(this.curHouse.getHousemates(), curHouse, this);
        this.mainView.displayFragment(debtView);
        debtView.updateDisplay(curHouse);
    }


    @Override
    public void onDebtorSelected(IDebtsView debtsView) {
        Log.d("Housemate Manager", "Viewing debts for Housemate");
        debtsView.updateDisplay(curHouse);
    }

    @Override
    public void onAddedDebt(Housemate debtor, Housemate creditor, double amount, IDebtsView debtsView) {
        if (debtor.equals(creditor)){ debtsView.samePersonError();}
        else if (amount <= 0) {
            debtsView.negativeAmountError();
        } else {
            this.curHouse.addDebt(debtor, creditor, amount);
            Log.d("Housemate Manager", "Debt Added");
            this.saveHouse();
        }
    }

    @Override
    public void onCreditorSelected(IDebtsView debtsView) {
        Log.d("Housemate Manager", "Viewing debts");
        debtsView.updateDisplay(curHouse);
    }

    @Override
    public void onRemovedDebt(Housemate debtor, Housemate creditor, double amount, IDebtsView debtsView) {
        //amount will always be negative !
        if (amount >= 0) {
            debtsView.negativeAmountError();
        } else {
            double tag = this.curHouse.removeDebt(debtor, creditor, -amount);
            Log.d("Housemate Manager", "tag = "+tag);
            if(tag==0.0){
                Log.d("Housemate Manager", "Debt Removed");
            } else if(tag==-1.0){
            debtsView.debtDNEError();
            } else
                debtsView.overdraftError(debtor, creditor, tag);
                this.saveHouse();
        }
    }


    @Override
    public void onViewPastReceipts() {
        PastReceiptsFragment pastReceiptsView = new PastReceiptsFragment(this.receiptManager.getPastReceipts(), this);
        this.mainView.displayFragment(pastReceiptsView);
        pastReceiptsView.updateDisplay();
    }


    @Override
    public void onPastReceiptSelected(IPastReceiptsView pastReceiptsView) {
        pastReceiptsView.updateDisplay();
    }

    // Authentication Screen stuff

    //@Override
    public void onRegister(String username, String password, IAuthView authView) {
        House house = new House(username, password); // our tentative user
        this.persistenceFacade.createHouseIfNotExists(house, inventory, choreManager, receiptManager, new BinaryResultListener() {
            @Override
            public void onYesResult() { authView.onRegisterSuccess(); }

            @Override
            public void onNoResult() { authView.onUserAlreadyExists(); }
        });
    }


    public void onSigninAttempt(String username, String password, IAuthView authView) {
        Log.d("Housemate Manager", "Sign in attempted");

        if (username.equals(test_username) && password.equals(test_password)) {
            Log.d("Housemate Manager", "System test detected");
            this.isTesting = true;
            this.isLoggedIn = true;
            this.mainView.displayFragment(new HomeFragment(this));
        } else {
            this.persistenceFacade.retrieveHouse(username, new IPersistenceFacade.DataListener<House>() {
                @Override
                public void onDataReceived(@NonNull House house) {
                    if (house.validatePassword(password)) { // password matches
                        Log.d("Housemate Manager", "Sign in successful");

                        // allow backpress-to-home privilege
                        ControllerActivity.this.isLoggedIn = true;

                        // bring cloud data to local
                        ControllerActivity.this.curHouse = house;
                        ControllerActivity.this.retrieveChores(username);
                        ControllerActivity.this.retrieveInven(username);
                        ControllerActivity.this.retrieveReceipt(username);

                        // navigate to home screen
                        ControllerActivity.this.mainView.displayFragment(new HomeFragment(ControllerActivity.this));
                    } else { // password does not match
                        Log.d("Housemate Manager", "Wrong password");
                        authView.onInvalidCredentials(); // let the view know things didn't work out
                    }
                }
                @Override
                public void onNoDataFound() { // username does not exist
                    Log.d("Housemate Manager", "Username does not exits");
                    authView.onInvalidCredentials(); // let the view know things didn't work out
                }
            });

        }

    }

    private void saveAll() {
        if (!this.isTesting) {
            this.persistenceFacade.set(this.curHouse, this.inventory, this.choreManager, this.receiptManager, new BinaryResultListener() {
                @Override
                public void onYesResult() {
                    Log.d("Housemate Manager", "Bulk data save successful");
                }

                @Override
                public void onNoResult() {
                    Log.d("Housemate Manager", "Bulk data save failed");
                }
            });
        }
    }

    private void saveHouse() {
        if (!this.isTesting) {
            this.persistenceFacade.setHouse(this.curHouse, new BinaryResultListener() {
                @Override
                public void onYesResult() {
                    Log.d("Housemate Manager", "Housemate data saved");
                }

                @Override
                public void onNoResult() {
                    Log.d("Housemate Manager", "Housemate data save failed");
                }
            });
        }
    }
    private void saveInven() {
        if (!this.isTesting) {
            this.persistenceFacade.setInven(this.inventory, this.curHouse, new BinaryResultListener() {
                @Override
                public void onYesResult() {
                    Log.d("Housemate Manager", "Inventory data saved");
                }

                @Override
                public void onNoResult() {
                    Log.d("Housemate Manager", "Inventory data save failed");
                }
            });
        }
    }

    private void retrieveInven(String username) {
        ControllerActivity.this.persistenceFacade.retrieveInven(username, new IPersistenceFacade.DataListener<Inventory>() {
            @Override
            public void onDataReceived(@NonNull Inventory data) {
                Log.d("Housemate Manager", "Inventory retrieved");
                ControllerActivity.this.inventory = data;
            }

            @Override
            public void onNoDataFound() {
                Log.d("Housemate Manager", "Inventory retrieval failed");
            }
        });
    }

    private void saveChores() {
        if (!this.isTesting) {
            this.persistenceFacade.setChore(this.choreManager, this.curHouse, new BinaryResultListener() {
                @Override
                public void onYesResult() {
                    Log.d("Housemate Manager", "Chore list saved");
                }

                @Override
                public void onNoResult() {
                    Log.d("Housemate Manager", "Chore list save failed");
                }
            });
        }
    }

    private void retrieveChores(String username) {
        ControllerActivity.this.persistenceFacade.retrieveChore(username, new IPersistenceFacade.DataListener<ChoreManager>() {
            @Override
            public void onDataReceived(@NonNull ChoreManager data) {
                Log.d("Housemate Manager", "Chore list retrieved");
                ControllerActivity.this.choreManager = data;
            }

            @Override
            public void onNoDataFound() {
                Log.d("Housemate Manager", "Chore list retrieval failed");
            }
        });
    }

    private void saveReceipts() {
        if (!this.isTesting) {
            this.persistenceFacade.setReceipts(this.receiptManager, this.curHouse, new BinaryResultListener() {
                @Override
                public void onYesResult() {
                    Log.d("Housemate Manager", "Receipt saved");
                }

                @Override
                public void onNoResult() {
                    Log.d("Housemate Manager", "Receipt save failed");
                }
            });
        }
    }

    private void retrieveReceipt(String username) {
        ControllerActivity.this.persistenceFacade.retrieveReceipts(username, new IPersistenceFacade.DataListener<ReceiptManager>() {
            @Override
            public void onDataReceived(@NonNull ReceiptManager data) {
                Log.d("Housemate Manager", "Receipt retrieved");
                ControllerActivity.this.receiptManager = data;
            }

            @Override
            public void onNoDataFound() {
                Log.d("Housemate Manager", "Receipt retrieval failed");
            }
        });
    }

}

