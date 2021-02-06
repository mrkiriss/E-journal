package com.example.e_journal.school;

public class Person {
    String fullName;
    String phone;

    public Person(String fullName, String phone){
        this.fullName=fullName;
        this.phone=phone;
    }
    public String getFullName(){
        return this.fullName;
    }
    public String getPhone(){
        return this.phone;
    }

}
