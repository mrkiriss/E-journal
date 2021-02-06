package com.example.e_journal.school;

public class Employee extends Participant{
    String position;

    public Employee(String fullName, String phone, String cardID, String position) {
        super(fullName, phone, cardID);
        this.position=position;
    }

    public String getPosition(){return this.position;}
}
