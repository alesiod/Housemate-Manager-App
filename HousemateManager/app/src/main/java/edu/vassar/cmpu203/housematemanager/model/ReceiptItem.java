package edu.vassar.cmpu203.housematemanager.model;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.stream.Collectors;

public class ReceiptItem {
    // vars
    private String itemID;
    private int quantity;
    private double price;
    private List<String> debtors; //

    public ReceiptItem() {}

    public ReceiptItem(String itemID, int quantity, double price, List<Housemate> debtors) {
        this.itemID = itemID;
        this.quantity = quantity;
        this.price = Math.round(price * 100) / 100.0;
        this.debtors = debtors.stream().map(Housemate::getName).collect(Collectors.toList()); //NOTE! Not sure yet if this works, test and remove comment
    }

    public Boolean paidBy(Housemate debtor) {
        return debtors.contains(debtor.toString());
    }

    public void optOut(Housemate debtor) { debtors.remove(debtor.toString()); }
    public void optIn(Housemate debtor) { debtors.add(debtor.toString()); }

    public String getItemID() {return itemID;}
    public void setItemID(String itemID) { this.itemID = itemID;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() {return price;}
    public void setPrice(double price) { this.price = Math.round(price * 100) / 100.0; }
    public List<String> getDebtors() { return this.debtors; }
    public void setDebtors(List<String> debtors) { this.debtors = debtors; }

    public double eachPays() { return Math.round(price / debtors.size() * 100) / 100.0; }
    public int getDebtorCount() {return debtors.size();}

    public boolean equals(ReceiptItem item) { return item.getItemID().equals(this.getItemID()); }
    public String toString() {return getItemID();}
}
