package com.example.e_journal.school;

import java.io.Serializable;
import java.util.ArrayList;

public class Elective extends Group implements Serializable {
    String academicSubject;

    public Elective(String academicSubject, Teacher classTeacher, ArrayList<Learner> learners){
        super(classTeacher, learners);
        this.academicSubject=academicSubject;
    }
    public Elective(String academicSubject){
        this.academicSubject=academicSubject;
    }

    public String getAcademicSubject(){
        return this.academicSubject;
    }
}
