package com.example.e_journal.school;

import java.io.Serializable;
import java.util.ArrayList;

public class Elective extends Group implements Serializable {
    String academicSubject;

    Elective(String academicSubject, ArrayList<Learner> learners, Teacher classTeacher){
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
