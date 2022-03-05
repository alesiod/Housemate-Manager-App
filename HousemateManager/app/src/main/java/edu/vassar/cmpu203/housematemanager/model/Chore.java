package edu.vassar.cmpu203.housematemanager.model;

public class Chore {
    String Name;
    String Assigner;
    String Owner;

    public Chore(String name) {
        this.Name = name;
    }

    public Chore(String name, String Assigner, String Owner) {
        this.Name = name;
        this.Assigner = Assigner;
        this.Owner = Owner;
    }

    public Chore() {}


    public void setName(String name) {
        Name = name;
    }

    public void setAssigner(String a) { // no try catch for invalid person because assuming UI has a list, update use case docs
        Assigner = a;
    }


    public void setOwner(String b) {
        Owner = b;
    }

    public String getName() {
        return Name;
    }

    public String getOwner() {
        return Owner;
    }

    public String getAssigner() {
        return Assigner;
    }


    @Override
    public String toString(){
        return (Name + " assigned by " + Assigner + " to " + Owner);
    }

    // One person can't own two chores with same name
    public boolean equals(Chore chore) {
        boolean sameName = chore.getName().equals(this.getName());
        boolean sameOwner = chore.getOwner().equals(this.getOwner());
        return sameName && sameOwner;
    }
}
