package com.example.e_journal.school;

import java.util.ArrayList;

public class Elective extends Group{
    String academicSubject;

    Elective(String academicSubject, ArrayList<Learner> learners, Teacher classTeacher){
        super(classTeacher, learners);
        this.academicSubject=academicSubject;
    }
    public Elective(String academicSubject){
        this.academicSubject=academicSubject;
    }
}
