package edu.vassar.cmpu203.housematemanager.model;

public class Item {
    // vars
    private String itemID;
    private int desiredQuantity;
    private int currentQuantity;

    public Item() {}

    public Item(String itemID, int desiredQuantity, int currentQuantity) {
        this.itemID = itemID;
        this.desiredQuantity = desiredQuantity;
        this.currentQuantity = currentQuantity;
    }

    //if new item is added without a stated desired quantity (e.g. if new item when shopping)
    // then desired is set to the current (# you bought)
    public Item(String itemID, int currentQuantity){
        this.itemID = itemID;
        this.desiredQuantity = currentQuantity;
        this.currentQuantity = currentQuantity;
    }

    public int getDesired() {
        return desiredQuantity;
    }
    public void setDesired(int d) {
        desiredQuantity = d;
    }
    public int getCurrent() {
        return currentQuantity;
    }
    public void setCurrent(int c) {
        currentQuantity = c;
    }
    public String getItemID() {return itemID;}
    public void setItemID(String itemID) { this.itemID = itemID; }

    public boolean equals(Item item) {
        return item.getItemID().equals(this.getItemID());
    }
    public String toString() {return getItemID();}
}
