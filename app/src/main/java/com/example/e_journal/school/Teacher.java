package com.example.e_journal.school;

public class Teacher extends Participant{
    String position;
    String qualification;

    public Teacher(String fullName, String phone,String cardId, String position, String qualification) {
        super(fullName, phone, cardId);
        this.position=position;
        this.qualification=qualification;
    }
    public Teacher() {
        super("", "", "");
        this.position="";
        this.qualification="";
    }
    public String getName(){
        return this.fullName;
    }
}
