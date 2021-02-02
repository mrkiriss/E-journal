package com.example.e_journal.school;

public class Section extends Group{
    String name;

    Section(String name,Teacher classTeacher, Learner[] learners) {
        super(classTeacher, learners);
        this.name=name;
    }
}
