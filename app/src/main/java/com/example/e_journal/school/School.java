package com.example.e_journal.school;

import java.util.ArrayList;

public class School {
    ArrayList<Employee> employees=new ArrayList<Employee>();
    ArrayList<Teacher> teachers=new ArrayList<Teacher>();
    ArrayList<Learner> learners=new ArrayList<Learner>();
    ArrayList<Participant> participants=new ArrayList<Participant>();
    ArrayList<Class> classes=new ArrayList<Class>();
    ArrayList<Elective> electives=new ArrayList<Elective>();
    ArrayList<Section> sections=new  ArrayList<Section>();
    String adress;
    String name;

    /* Для меню ДОКУМЕНТЫ */
    //  список преподавательского состава с указанием квалификации
    public ArrayList<String[]> getListTeachers(){
        ArrayList<String[]> list = new ArrayList<String[]>();
        String[] list0 = new String[2]; // вспомогательный список, [0]-ФИО, [1]-квалификация
        for(Teacher i: teachers){
            list0[0]=i.fullName;
            list0[1]=i.qualification;
            list.add(list0.clone());
        }
        return list;
    }
    // список школьников с указанием возраста
    public ArrayList<String[]> getListLearners(){
        ArrayList<String[]> list = new ArrayList<String[]>();
        String[] list0 = new String[2]; // вспомогательный список, [0]-ФИО, [1]-возраст
        for(Learner i: learners){
            list0[0]=i.fullName;
            list0[1]= i.age;
            list.add(list0.clone());
        }
        return list;
    }
    // список всех людей, имеющих доступ в школу, для выдачи магнитных карт
    public ArrayList<String[]> getListParticipants(){
        ArrayList<String[]> list =new ArrayList<String[]>();
        String[] list0 = new String[2]; // вспомогательный список, [0]-ID, [1]-ФИО
        for(Participant i: participants){
            list0[0]=i.cardID;
            list0[1]= i.fullName;
            list.add(list0.clone());
        }
        return  list;
    }
    // список учеников КЛАССА вместе с родителями для организации собраний
    public ArrayList<String[]> getListForClassMeeting(int number){
        Class obj = classes.get(number-1); // сещение индекса, предполагается, что пользователь видит ....

        ArrayList<String[]> list =new ArrayList<String[]>();
        String[] list0 = new String[3]; // вспомогательный список, [0]-ФИО ученика, [1]-ФИО родителя1, [2]-ФИО родителя2
        for (Learner i:obj.learners){
            list0[0]=i.fullName;
            list0[1]=i.parents.get(0).fullName;
            list0[2]=i.parents.get(1).fullName;
            list.add(list0.clone());
        }
        return list;
    }

    /* Для меню ГРУППЫ */
    public ArrayList<Class> getClasses(){
        return this.classes;
    }
    public ArrayList<Elective> getElectives(){
        return this.electives;
    }
    public ArrayList<Section> getSections(){
        return this.sections;
    }

    /* Для меню ДОБАВЛЕНИЕ */
    private void addParticipant(String fullName, String phone, String cardID){
        Participant p = new Participant(fullName, phone, cardID);
        participants.add(p);
    }
    public void addLearner(Learner x){
        learners.add(x);
        addParticipant(x.fullName, x.phone, x.cardID);
    }
    public void addTeacher(Teacher x){
        teachers.add(x);
        addParticipant(x.fullName, x.phone, x.cardID);
    }
    public void addEmployee(Employee x){
        employees.add(x);
        addParticipant(x.fullName, x.phone, x.cardID);
    }
    public void addClass(Class x){
        classes.add(x);
    }
    public void addElective(Elective x){
        electives.add(x);
    }
    public void addSection(Section x){
        sections.add(x);
    }

    public int getCountClasses(){
        return this.classes.size();
    }
    public int getCountElectives(){
        return this.electives.size();
    }
    public int getCountSections(){
        return this.sections.size();
    }
}
