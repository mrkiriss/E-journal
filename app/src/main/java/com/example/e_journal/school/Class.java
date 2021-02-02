package com.example.e_journal.school;

public class Class extends Group{
    String number;

    Class(String number, Teacher classTeacher, Learner learners[]){
        super(classTeacher, learners);
        this.number=number;
    }
}
