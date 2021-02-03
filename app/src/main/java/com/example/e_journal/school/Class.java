package com.example.e_journal.school;

import java.util.ArrayList;

public class Class extends Group{
    String number;

    public Class(String number, Teacher classTeacher, ArrayList<Learner> learners){
        super(classTeacher, learners);
        this.number=number;
    }
    public Class(String number){
        this.number=number;
    }
}
