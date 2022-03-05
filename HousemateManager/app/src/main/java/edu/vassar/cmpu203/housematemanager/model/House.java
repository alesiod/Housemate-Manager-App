package edu.vassar.cmpu203.housematemanager.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class House {

    public static final String DEFAULT_TEXT = "No Housemates";
    private final List<Housemate> housemates;
    private String username;
    private AuthKey authKey;


    public House(){
        this.housemates = new ArrayList<>();
    }

    public House(String username, String password){
        this.housemates = new ArrayList<>();
        this.username = username;
        this.authKey = new AuthKey(password);
    }

    public String getUsername(){ return this.username; }
    public AuthKey getAuthKey(){ return this.authKey; }

    public boolean validatePassword(String password){
        return this.authKey.validatePassword(password);
    }

    public boolean addHousemate(String name){ // changed from void to boolean to aid controller/prevent duplicates
        if (getHousemate(name)==null) {
            Housemate mate = new Housemate(name);
            housemates.add(mate);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean removeHousemate(String name) { // changed from void to boolean to aid controller
        if (this.getHousemate(name)==null) {
            return false;
        } else {
            Housemate mate = this.getHousemate(name);
            this.housemates.remove(mate);
            return true;
        }
    }

    public int size(){
        return housemates.size();
    }

    // Returns a copy of members (for opt-in/out function of receipt)
    public List<Housemate> getHousemates() {
        return new ArrayList<>(housemates);
    }

    public Housemate getHousemate(String s) {
        for (Housemate member : housemates) {
            String name = member.toString();
            if (s.equals(name)) {
                return member;
            }
        }
        return null;
    }

    public String toString() {
        if(housemates.isEmpty())
            return DEFAULT_TEXT;
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (Housemate member : housemates) {
            sb.append(prefix);
            prefix = "\n";
            sb.append(member.toString());
        }
        return sb.toString();
    }

    public double removeDebt(Housemate debtor, Housemate creditor, double amount) {
        //returns double to indicate removal type:
        // 0 = normal removal, -1 = no removal, other = overdraft
        String creditorName = creditor.getName();
        HashMap<String, Double> debts = debtor.getDebts();
        if(debts.containsKey(creditorName)){
            double currentAmount = debts.get(creditorName);
            if(currentAmount < amount){
                Log.d("Housemate Manager", "check?");
                debts.remove(creditorName);
                this.addDebt(creditor, debtor, amount-currentAmount);
                return amount-currentAmount;
            }
            debts.replace(creditorName, currentAmount-amount);
            if(debts.get(creditorName)==0) {debts.remove(creditorName);}
            return 0;}
        return -1;
    }

    public void addDebt(Housemate debtor, Housemate creditor, double amount) {
        String creditorName = creditor.getName();
        HashMap<String, Double> debts = debtor.getDebts();
        if(debts.containsKey(creditorName)) {
            double currentAmount = debts.get(creditorName);
            debts.replace(creditorName, currentAmount + amount);
        }
        else{
            debts.put(creditorName, amount);
        }
    }
}
