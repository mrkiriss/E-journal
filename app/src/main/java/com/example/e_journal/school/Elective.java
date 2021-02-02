package com.example.e_journal.school;

public class Elective extends Group{
    String academicSubject;

    Elective(String academicSubject, Learner learners[], Teacher classTeacher){
        super(classTeacher, learners);
        this.academicSubject=academicSubject;
    }
}
