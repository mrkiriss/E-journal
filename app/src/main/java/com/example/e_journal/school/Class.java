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
        this.index=index;
    }

    public String getNumber(){
        return this.number;
    }
    public int getIndex(){
        return this.index;
    }
    public Teacher getTeacher(){
        return this.classTeacher;
    }

    // создаёт динам. массив из массивов строк [ФИО ученика, ФИО родителя1, ФИО родителя2]
    public ArrayList<String[]> getLearnersAndParents(){
        ArrayList<String[]> list = new ArrayList<String[]>();
        String[] list0 = new String[3];
        for (Learner i: learners){
            list0[0]=i.fullName;
            list0[1]=i.parents.get(0).fullName;
            list0[2]=i.parents.get(1).fullName;
            list.add(list0.clone());
        }

        return  list;
    }
}
