package com.example.e_journal.school;

import java.util.ArrayList;
import java.util.Map;

public class Group {
    Teacher classTeacher;
    ArrayList<Learner> learners;

    Group(Teacher classTeacher, ArrayList<Learner> learners){
        this.classTeacher=classTeacher;
        this.learners=learners;
    }

    //получение списка учеников
    ArrayList<String> getList(){
        ArrayList<String> list = null;
        for (Learner l: learners){
            list.add(l.fullName);
        }
        return list;
    }

    //получение списка учеников с их родителями
    ArrayList<ArrayList<String>> getListParrents(){
        ArrayList<ArrayList<String>> list = null;
        ArrayList<String> list0=null;// промежуточный список, содержаший ФИО ученика и ФИО родителей
        for (Learner l: learners){
            list0.add(l.fullName); // добавление ФИО ученика

            for (Parent p:l.parents){ // обход родителей внутри ученика, добавление их ФИО
                list0.add(p.fullName);
            }

            list.add(list0);
            list0.clear();
        }
        return list;
    }

    // добавление ученика в группу
    void addLearner(String fullName, int phone, int cardID, ArrayList<Parent> parents, int age){
        Learner l= new Learner(fullName, phone, cardID, parents, age);
        learners.add(l);
    }
}
