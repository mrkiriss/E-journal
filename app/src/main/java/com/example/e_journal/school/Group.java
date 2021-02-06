package com.example.e_journal.school;

import java.util.ArrayList;
import java.util.Map;

public class Group {
    Teacher classTeacher;
    ArrayList<Learner> learners;
    int index;

    Group(Teacher classTeacher, ArrayList<Learner> learners){
        this.classTeacher=classTeacher;
        this.learners=learners;
    }
    //пустой конструктор (использутеся в меня ДОБАВЛЕНИЕ)
    Group(){
        this.classTeacher=new Teacher();
        this.learners=new ArrayList<Learner>();
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

    // получение индекса
    public int getIndex(){
        return this.index;
    }
    // присвоение индекса
    public void setIndex(int index){
        this.index = index;
    }

    // формирование дин. списка с масивами строк [ID, ФИО] из  динам. списка со школьниками learners
    public ArrayList<String[]> getListLearners(){
        ArrayList<String[]> list = new ArrayList<String[]>();
        String[] list0 = new String[2];
        for (Learner i: learners){
            list0[0]=i.cardID;
            list0[1]=i.fullName;
            list.add(list0.clone());
        }
        // добавление учетеля
        list0[0]=classTeacher.cardID+" (учитель)";
        list0[1]=classTeacher.fullName;
        list.add(list0.clone());

        return  list;
    }

    public ArrayList<Learner> getLearners(){
        return this.learners;
    }
    public Teacher getTeacher(){
        return this.classTeacher;
    }

    public void addLearner(Learner l){
        this.learners.add(l);
    }
    public void setTeacher(Teacher t){
        this.classTeacher=t;
    }
    public void deleteLearnerByName(String fullName){
        System.out.println("Удаляемое имя"+fullName);
        ArrayList<Learner> learners_new = new ArrayList<Learner>();
        for (Learner i: this.learners){
            if (!fullName.equals(i.getFullName())){
                learners_new.add(i); // добавляем в новый массимв всех учеников, кроме того, которого нужно убрать
            }
            System.out.println(i.getFullName());
        }
        this.learners=learners_new;
        System.out.println("Количество учеников в новом массиве после удалеиня"+learners_new.size());
    }

}
