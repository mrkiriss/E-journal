package com.example.e_journal.school;

public class Employee extends Participant{
    String position;

    public Employee(String fullName, String phone, String cardID, String position) {
        super(fullName, phone, cardID);
        this.position=position;
    }

    public String getPosition(){return this.position;}

    public String toString(){
        String result="";
        result+="Категория: Работник"+"\n";
        result+="ФИО: "+fullName+"\n";
        result+="Норер телефона: "+phone+"\n";
        result+="ID: "+cardID+"\n";
        result+="Должность: "+position+"\n";
        return result;
    }
}
