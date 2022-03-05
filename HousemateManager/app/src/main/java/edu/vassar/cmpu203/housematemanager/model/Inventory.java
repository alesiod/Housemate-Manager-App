package edu.vassar.cmpu203.housematemanager.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.*;
import java.lang.StringBuilder;

public class Inventory {

    HashMap<String, Item> items;
    public static final String INVENT_DEFAULT = "Nothing in inventory";
    public static final String SHOP_LIST_DEFAULT = "Fully stocked!";

    // create a new inventory. will only need to be done once (at the creation of a new house)
    // initializes invent to be a completely empty inventory
    // Hashmap with String Keys and Item Values
    public Inventory() {
        items = new HashMap<>();
    }

    // getItemNames
    // returns item names in a list
    public HashMap<String, Item> getItems() { return items; }

    public void setItems(HashMap<String, Item> items) { this.items = items; }

    // addItem:
    // takes a string itemID as an input and an integer quantity
    // if there is already an Item with that itemID in the inventory,
    // it just updates the current quantity
    // if there is no Item with that itemID in inventory, it adds item

    public void addItem(String itemID, int quantity) {
        Item item;
        if (this.items.containsKey(itemID)) {
            item = items.get(itemID);
            assert item != null;
            item.setCurrent(item.getCurrent() + quantity);
        } else {
            item = new Item(itemID, quantity);
            items.put(itemID, item);
        }
    }


    // useItem:
    // takes a string itemID as an input and an integer quantity
    // if there is an Item with that itemID in the inventory,
    // it just updates the current quantity with the amount used
    // if there is no Item with that itemID in inventory, or if
    // user says they are using more of an item then they currently have,
    // it throws an exception

    public boolean useItem(String itemID, int quantity) {
        if (this.items.containsKey(itemID)) {
            Item item = items.get(itemID);
            assert item != null;
            if (item.getCurrent() > quantity) {
                item.setCurrent(item.getCurrent() - quantity);
            } else if (item.getDesired() > 0) {
                item.setCurrent(0);
            } else {
                items.remove(itemID);
            }
            return true;
        } else {
            return false;
        }
    }


    public void addDesired(String itemID, int quantity) {
        Item item;
        if (items.containsKey(itemID)) {
            item = items.get(itemID);
            assert item != null;
            item.setDesired(item.getDesired() + quantity);
        } else {
            item = new Item(itemID, 0);
            item.setDesired(quantity);
            items.put(itemID, item);
        }
    }

    public boolean subDesired(String itemID, int quantity) {
        if (this.items.containsKey(itemID)) {
            Item item = this.items.get(itemID);
            assert item != null;
            if (item.getDesired() > quantity) {
                item.setDesired(item.getDesired() - quantity);
            } else if (item.getCurrent() > 0){
                item.setDesired(0);
            } else {
                items.remove(itemID);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean hasItem(String itemID) {
        return items.containsKey(itemID);
    }

    // toStringShoppingList:
    // creates a textual shopping list based on what's in the inventory
    // iterates through the inventory and for every item for which
    // the current quantity is less than the desired, it adds to shopping list

    @SuppressLint("DefaultLocale")
    public String toStringShoppingList() {
        List<Item> shoppingList = new ArrayList<>();
        for (Item i : items.values()) {
            if (i.getCurrent() < i.getDesired())
                shoppingList.add(i);
        }

        if (shoppingList.size() == 0) {
            return SHOP_LIST_DEFAULT;
        } else {
            StringBuilder sb = new StringBuilder();
            for (Item itm : shoppingList) {
                String itemID = itm.getItemID();
                int needed = itm.getDesired() - itm.getCurrent();
                sb.append(String.format("%4d units of %s\n", needed, itemID));
            }
            return sb.toString();
        }
    }

    @NonNull
    @SuppressLint("DefaultLocale")
    public String toString() {
        if(items.isEmpty()){
            return INVENT_DEFAULT;
        }
        StringBuilder sb = new StringBuilder();
        for(Item item : this.items.values()) {
            if (item.getCurrent() > 0) {
                String itemID = item.getItemID();
                int current = item.getCurrent();
                sb.append(String.format("%4d units of %s\n", current, itemID));
            }
        }

        return sb.toString();
    }

}


