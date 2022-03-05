package edu.vassar.cmpu203.housematemanager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptManager {

    private Map<String, String> pastReceipts;
    private Receipt r;

    public ReceiptManager() {
        this.pastReceipts = new HashMap<String, String>();
    }

    public void startReceipt(Housemate payer, List<Housemate> debtors) {
        this.r = new Receipt(debtors, payer);
    }

    public void endReceipt(Inventory inventory) {
        r.process(inventory);
        pastReceipts.put(r.getDate(), r.toString());
        r = null;
    }

    // will return null if there is no ongoing receipt
    public Receipt getReceipt(){
        return r;
    }

    public void setReceipt(Receipt r) { this.r = r; }

    public Map<String, String> getPastReceipts(){
        return this.pastReceipts;
    }

    public void setPastReceipts(Map<String,String> receipts) { this.pastReceipts = receipts;}

    /* unnecessary function, not deleting JIC
    @Override
    public String toString(){
        if(pastReceipts.isEmpty()){return "No Receipts have been logged!";}
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for(Receipt r : pastReceipts){
            sb.append("Receipt "+ i+": \n"+r.toString()+ "\n");
            i++;
        }
        return sb.toString();
    }
     */
}
