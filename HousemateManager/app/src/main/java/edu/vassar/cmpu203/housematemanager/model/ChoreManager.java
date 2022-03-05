package edu.vassar.cmpu203.housematemanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoreManager {
    List<Chore> ChoreList;
    Random rand = new Random();

    public ChoreManager() {
        this.ChoreList = new ArrayList<>();
    }

    public List<Chore> getChoreList() { return ChoreList; }
    public void setChoreList(List<Chore> choreList) { this.ChoreList = choreList; }

    public void assignChore(String name, String Assigner, String Owner) {
        /*
        Note: Unit tests don't work with the log.d's, so they have been commented out for now. If debug is needed, feel free to uncomment them.
         */
        Chore temp = new Chore(name);
        temp.setAssigner(Assigner); //their name
        temp.setOwner(Owner); // person responsible to perform task
        //Log.d("NextGenPos", "initialized");
        if (ChoreList.toString().contains(temp.toString())) {
            throw new RuntimeException("Duplicate chore cannot be added"); // not sure if appropriate logging method, change later if need be
        }
        //Log.d("NextGenPos", "went the distance");
        ChoreList.add(temp);
    }

    public boolean removeChore(String name) { // removes first instance of a chore with specified name
        // inefficient solution, but not sure how to optimize
        boolean removed = false;
        for (int i = 0; i < ChoreList.size(); i++) { // go through each item in ChoreList
            if (ChoreList.get(i).getName().equals(name)) { // if the chore at the index has the name specified
                ChoreList.remove(ChoreList.get(i));
                removed = true;
            }
        }
        return removed;
    }

    public boolean removeChore(String name, String Assigner, String Owner) { // removes first instance of chore, all vals specified
        boolean removed = false;
        for (Chore c : ChoreList){
            if(c.getName().equals(name)
            && c.getOwner().equals(Owner)
            && c.getAssigner().equals(Assigner)) {
                ChoreList.remove(c);
                removed = true;
                break;
            }
        }
        return removed;

    }

    public List<String> getChoreListNames(){
        ArrayList<String> a = new ArrayList<>();
        for (Chore c : ChoreList){
            a.add(c.getName());
        }
        return a;
    }

    @Override
    public String toString() { // make this a tostring override
        String a = "";
        for (int i = 0; i < ChoreList.size(); i++) {
            a = a.concat(ChoreList.get(i).toString() + "\n");
        }
        return a;
    }

    public boolean HousemateDNE(String owner, String assigner, List<Housemate> housemates) {
        boolean ownerExists = false;
        boolean assignerExists = false;
        for (Housemate h: housemates){
            if (h.toString().equals(owner)){
                 ownerExists = true;
            }
        }
        for (Housemate h: housemates){
            if (h.toString().equals(assigner)){
                assignerExists = true;
            }
        }
        // ignore this line used for testing/bug solving
        // Log.d("Housemate Manager", "assigner = " + assignerExists + "owner = " + ownerExists);
        return !(assignerExists && ownerExists);
    }

    public boolean choreAE(String name) {
        for (Chore c : ChoreList){
            if (c.getName().equals(name))
                return true;
        }
        return false;
    }
}
