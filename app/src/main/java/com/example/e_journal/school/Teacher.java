package com.example.e_journal.school;

import java.util.ArrayList;

public class Teacher extends Participant{
    String position;
    String qualification;

    public Teacher(String fullName, String phone,String cardID, String position, String qualification) {
        super(fullName, phone, cardID);
        this.position=position;
        this.qualification=qualification;
    }
    public Teacher() {
        super("", "", "");
        this.position="";
        this.qualification="";
    }

    public String getPosition(){
        return this.position;
    }
    public String getQualification(){
        return this.qualification;
    }

    public String toString(){
        String result="";
        result+="Категория: Учитель"+"\n";
        result+="ФИО: "+fullName+"\n";
        result+="Норер телефона: "+phone+"\n";
        result+="ID: "+cardID+"\n";
        result+="Должность: "+position+"\n";
        result+="Квалификация: "+qualification+"\n";
        return result;
    }
}
