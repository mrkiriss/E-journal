package com.example.e_journal.school;

public class Participant extends Person{
    int cardID;

    Participant(String fullName, int phone, int cardID) {
        super(fullName, phone);
        this.cardID=cardID;
    }
}
