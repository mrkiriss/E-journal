package com.example.e_journal.school;

import java.util.ArrayList;

public class Learner extends Participant{
    ArrayList<Parent> parents=null;
    String age;

    public Learner(String fullName, String phone, String cardID, ArrayList<Parent> parents, String age) {
        super(fullName, phone, cardID);
        this.parents=parents;
        this.age=age;
    }

    public String getPhone(){
        return this.phone;
    }
}
