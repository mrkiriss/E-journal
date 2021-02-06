package com.example.e_journal.school;

import java.io.Serializable;
import java.util.ArrayList;

public class Class extends Group implements Serializable {
    String number;

    public Class(String number, Teacher classTeacher, ArrayList<Learner> learners){
        super(classTeacher, learners);
        this.number=number;
    }
    public Class(String number){
        this.number=number;
        this.index=index;
    }
    public Class(){
        this.number="";
        this.classTeacher = new Teacher();
        this.learners= new ArrayList<Learner>();
    }

    public String getNumber(){
        return this.number;
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
