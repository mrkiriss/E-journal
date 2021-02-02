package com.example.e_journal.school;

import java.util.ArrayList;

public class Learner extends Participant{
    ArrayList<Parent> parents=null;
    int age;

    Learner(String fullName, int phone, int cardID, ArrayList<Parent> parents, int age) {
        super(fullName, phone, cardID);
        this.parents=parents;
        this.age=age;
    }
}
