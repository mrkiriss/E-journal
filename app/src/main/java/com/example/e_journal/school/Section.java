package com.example.e_journal.school;

import java.util.ArrayList;

public class Section extends Group{
    String name;

    Section(String name,Teacher classTeacher, ArrayList<Learner> learners) {
        super(classTeacher, learners);
        this.name=name;
    }
    public Section(String name) {
        super();
        this.name=name;
    }
}
