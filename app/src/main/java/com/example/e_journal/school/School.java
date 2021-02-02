package com.example.e_journal.school;

import java.util.ArrayList;

public class School {
    ArrayList<Employee> employees;
    ArrayList<Teacher> teachers;
    ArrayList<Learner> learners;
    ArrayList<Participant> participants;
    ArrayList<Class> classes;
    ArrayList<Elective> electives;
    ArrayList<Section> sections;
    String adress;
    String name;

    //  список вспомогательного (неучительского) состава
    void getListEmployees(){
        ArrayList<String> list = null;
        for(Employee i: employees){
            list.add(i.fullName);
        }
    }

    //  список преподавательского состава с указанием квалификации
    void getListTeachers(){
        ArrayList<String[]> list = null;
        String[] list0 = new String[2]; // вспомогательный список, [0]-ФИО, [1]-квалификация
        for(Teacher i: teachers){
            list0[0]=i.fullName;
            list0[1]=i.qualification;
            list.add(list0);
        }

    }

    // список школьников с указанием возраста
    void getListLearners(){
        ArrayList<String[]> list = null;
        String[] list0 = new String[2]; // вспомогательный список, [0]-ФИО, [1]-класс, [2]-возраст
        for(Learner i: learners){
            list0[0]=i.fullName;
            list0[1]=i.fullName;
            list0[2]= String.valueOf(i.age);
            list.add(list0);
        }

    }

    // список всех людей, имеющих доступ в школу, для выдачи магнитных карт
    void getListParticipants(){
        ArrayList<String[]> list = null;
        String[] list0 = new String[2]; // вспомогательный список, [0]-ID, [1]-ФИО
        for(Participant i: participants){
            list0[0]=String.valueOf(i.cardID);
            list0[1]= i.fullName;
            list.add(list0);
        }

    }

    // список учеников КЛАССА вместе с родителями для организации собраний
    void getListForClassMeeting(int number){
        Class obj = classes.get(number-1);
        ArrayList<ArrayList<String>> list = obj.getListParrents();
    }

    // список учеников ЭЛЕКТИВА вместе с родителями для организации собраний
    void getListForElectiveMeeting(int number){
        Elective obj = electives.get(number-1);
        ArrayList<ArrayList<String>> list = obj.getListParrents();
    }

    // список учеников СЕКЦИИ вместе с родителями для организации собраний
    void getListForSectionMeeting(int number){
        Section obj = sections.get(number-1);
        ArrayList<ArrayList<String>> list = obj.getListParrents();
    }
}
