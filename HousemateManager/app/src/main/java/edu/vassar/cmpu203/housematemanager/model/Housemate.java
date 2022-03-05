package edu.vassar.cmpu203.housematemanager.model;

import java.util.HashMap;

public class Housemate {

    private String name;
    private HashMap<String, Double> debts;
    
    public Housemate() {
        this.debts = new HashMap<>();
    }

    public Housemate(String name) {
        this();
        this.name = name;
    }

    // getters and setters for serialization
    public void setName(String name){ this.name = name; }
    public String getName() { return name;}
    public void setDebts(HashMap<String, Double> debts) { this.debts = debts; }
    public HashMap<String, Double> getDebts() { return debts;}

    // implemented with hashmap
    public void addDebt(Housemate creditor, double debt) {
        String creditorName = creditor.getName();
        if (debts.containsKey(creditor)) {
            debts.put(creditorName, debts.get(creditor) + debt);
        } else {
            debts.put(creditorName, debt);
        }
    }

    public String getDebtsStringForm() {
        if(this.debts.isEmpty()){return "All Debts are Paid!";}
        StringBuilder sb = new StringBuilder();

        debts.forEach((k,v) -> sb.append(this.toString() + " owes " + k.toString() +" " + v + "\n"));

        return sb.toString();
    }

    public boolean equals(Housemate mate) {
        return mate.toString().equals(this.toString());
    }

    // toString should display debts
    public String toString() { return name;}


}
