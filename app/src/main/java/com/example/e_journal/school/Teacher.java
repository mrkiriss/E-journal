package com.example.e_journal.school;

public class Teacher extends Participant{
    String position;
    String qualification;

    Teacher(String fullName, int phone,int cardId, String position, String qualification) {
        super(fullName, phone, cardId);
        this.position=position;
        this.qualification=qualification;
    }
}
