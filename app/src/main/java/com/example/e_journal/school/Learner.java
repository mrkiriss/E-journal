package com.example.e_journal.school;

import java.util.ArrayList;
import java.util.LinkedList;

public class Learner extends Participant{
    ArrayList<Parent> parents=null;
    String age;
    //массивы индексов групп, в которой состоит ученик
    ArrayList<String> consist_class= new  ArrayList<String>();
    ArrayList<String> consist_elective= new  ArrayList<String>();
    ArrayList<String> consist_section= new  ArrayList<String>();

    public Learner(String fullName, String phone, String cardID, ArrayList<Parent> parents, String age) {
        super(fullName, phone, cardID);
        this.parents=parents;
        this.age=age;
    }
    public Learner(String fullName, String phone, String cardID, ArrayList<Parent> parents, String age, ArrayList<String> consist_class, ArrayList<String> consist_elective, ArrayList<String> consist_section) {
        super(fullName, phone, cardID);
        this.parents=parents;
        this.age=age;
        this.consist_class=consist_class;
        this.consist_elective=consist_elective;
        this.consist_section=consist_section;
    }
    public Learner() {
        super("", "", "");
        this.parents = new ArrayList<Parent>();
        this.age="";
    }

    public String getAge(){
        return this.age;
    }
    public String[] getParentData(int index){
        String[] data = new String[2];
        data[0]=parents.get(index).getFullName();
        data[1]=parents.get(index).getPhone();
        return data;
    }
    // добавляет индекс определённой группы, в которую был добавлен ученик
    public void addConsist(String indicator, String index){
        switch (indicator){
            case "Класс":
                consist_class.add(index);
                break;
            case "Электив":
                consist_elective.add(index);
                break;
            case "Секция":
                consist_section.add(index);
                break;
        }
    }
    // удаляет индексопределённой группы, из которой был удалён ученик
    public void deleteConsist(String indicator, String index){
        ArrayList<String> consist0 = new ArrayList<String>();
        switch (indicator){
            case "Класс":
                for (String i: consist_class) {
                    if (i!=index){
                        consist0.add(i);
                    }
                }
                consist_class=consist0;
                break;
            case "Электив":
                for (String i: consist_elective) {
                    if (i!=index){
                        consist0.add(i);
                    }
                }
                consist_elective=consist0;
                break;
            case "Секция":
                for (String i: consist_section) {
                    if (i!=index){
                        consist0.add(i);
                    }
                }
                consist_section=consist0;
                break;
        }
    }
    // преобразовывает массив consist... в строку для хранения в sql
    public String convertArrayToString(String indicator){
        String result="";
        switch (indicator){
            case "Класс":
                for (String i: consist_class){
                    result+=i+" ";
                }
                break;
            case "Электив":
                for (String i: consist_elective){
                    result+=i+" ";
                }
                break;
            case "Секция":
                for (String i: consist_section){
                    result+=i+" ";
                }
                break;
        }
        if (result==""){
            return "";
        }else {
            return result.substring(0, result.length() - 1);
        }
    }
    public static ArrayList<String> convertStringToArray(String indicator, String data){
        ArrayList<String> result= new ArrayList<String>();
        String[] result0 = data.split(" ");
        for (String i: result0){
            result.add(i);
        }
        return result;
    }
}
