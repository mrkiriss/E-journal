package com.example.e_journal.school;

public class Participant extends Person{
    String cardID;

    public Participant(String fullName, String phone, String cardID) {
        super(fullName, phone);
        this.cardID=cardID;
    }
}
