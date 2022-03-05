package edu.vassar.cmpu203.housematemanager.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receipt {
    private String DEFAULT_TEXT;
    private List<Housemate> debtors;
    private Housemate payer;
    private Map<String, ReceiptItem> items;
    private boolean itemsDone;
    private String date;

    public Receipt() {}

    public Receipt(List<Housemate> debtors, Housemate payer) {
        this.DEFAULT_TEXT = "Add items paid by " + payer.toString();
        this.debtors = new ArrayList<>(debtors);
        this.payer = payer;
        this.items = new HashMap<>();
        this.itemsDone = false;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        Date date = new Date();
        this.date = dateFormat.format(date);
    }

    public int size() { return items.size(); }

    public List<Housemate> getDebtors() { return debtors; }

    public void setDebtors(List<Housemate> debtors) { this.debtors = debtors; }

    public Housemate getPayer() { return payer; }

    public void setPayer(Housemate payer) { this.payer = payer; }

    public Map<String, ReceiptItem> getItems() { return items; }

    public void setItems(Map<String, ReceiptItem> items) { this.items = items; }

    public boolean getItemsDone() { return itemsDone; }

    public void setItemsDone(boolean bool) { this.itemsDone = bool; }

    public void itemsDone() { this.setItemsDone(true); }

    public String getDate() { return date; }

    public void setDate() { this.date = date; }

    public void addItem(String itemID, int quantity, double price) {
        ReceiptItem item;
        if (this.items.containsKey(itemID)) {
            item = items.get(itemID);
            assert item != null;

            item.setQuantity(item.getQuantity() + quantity);
            item.setPrice(price);
        } else {
            item = new ReceiptItem(itemID, quantity, price, this.debtors);
            items.put(itemID, item);
        }
    }

    public boolean rmItem(String itemID, int qty) {

        if (this.items.containsKey(itemID)) {
            ReceiptItem item = items.get(itemID);

            if (qty >= item.getQuantity() || qty < 0)
                // remove item if qty is impossible to subtract
                items.remove(itemID);
            else
                // otherwise reduce qty
                item.setQuantity(item.getQuantity() - qty);
            return true;

        } else {
            return false;
        }
    }

    public boolean hasItem(String itemID) { return items.containsKey(itemID); }

    public void optInItem(String itemID, Housemate debtor) {
        ReceiptItem item = items.get(itemID);
        assert item != null;
        if (!item.paidBy(debtor)) {
            item.optIn(debtor);
        }
    }

    public boolean optOutItem(String itemID, Housemate debtor) {
        ReceiptItem item = items.get(itemID);
        assert item != null;
        item.optOut(debtor);
        if (item.getDebtorCount() == 0) {
            item.optIn(this.payer);
            return false;
        }
        else
            return true;
    }

    public void process(Inventory i) {
        // inventory stocking
        for (ReceiptItem itm : this.items.values()) {
            String id = itm.getItemID();
            int qty = itm.getQuantity();
            i.addItem(id, qty);
        }
        // charging housemates
        for (Housemate debtor : debtors) {
            if (!debtor.equals(payer))
                debtor.addDebt(payer, this.getTotal(debtor));
        }
    }

    public double getTotal() {
        double total = 0;
        for (ReceiptItem itm : items.values()) {
            total += itm.getPrice();
        }
        return total;
    }

    public double getTotal(Housemate debtor) {
        double total = 0;
        for (ReceiptItem itm : items.values()) {
            if (itm.paidBy(debtor)) {
                total += itm.eachPays();
            }
        }
        return total;
    }

    @NonNull
    @SuppressLint("DefaultLocale")
    public String toString() {
        if (this.items.size() == 0) {
            return DEFAULT_TEXT;
        } else {
            StringBuilder sb = new StringBuilder();
            for (ReceiptItem item : this.items.values()) {
                sb.append(String.format("%d units of %s for %.2f%n", item.getQuantity(), item.getItemID(), item.getPrice()));
            }
            sb.append(String.format("%n%s paid total of %.2f", this.payer.toString(), getTotal()));
            return sb.toString();
        }
    }

    @SuppressLint("DefaultLocale")
    public String toString(Housemate debtor) {
        StringBuilder sb = new StringBuilder();
        StringBuilder optInSB = new StringBuilder();
        StringBuilder optOutSB = new StringBuilder();
        for (ReceiptItem item : this.items.values()) {
            if (item.paidBy(debtor)) {
                optInSB.append(String.format("[%s] each pays %.2f%n", item.getItemID(), item.eachPays()));
            } else {
                optOutSB.append(String.format("[%s] each pays %.2f%n", item.getItemID(), item.eachPays()));
            }
        }
        sb.append("---Paying Items---\n").append(optInSB).append("\n---Unselected Items---\n").append(optOutSB);
        sb.append(String.format("%n%s will owe %s %.2f", debtor.toString(), this.payer.toString(), this.getTotal(debtor)));
        return sb.toString();
    }
}
