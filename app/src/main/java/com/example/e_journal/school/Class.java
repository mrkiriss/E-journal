package com.example.e_journal.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Class extends Group {
    String number;
    HashMap<String, HashMap<String, String>> schedule = new HashMap<>(); // хранение расписания по дням недели {день недели: {предмет: учитель, ...}, ...}
    HashMap<String, HashMap<String, HashMap<String, String>>> journal= new HashMap<>(); // хранение страниц журнала { дата: {предмет: [{имя: оценка, имя: оценка, ... }, учитель ], ... }, ... }

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
    public  HashMap<String, HashMap<String, String>> getSchedule() {return this.schedule;}
    public void setSchedule(HashMap<String, HashMap<String, String>> schedule){this.schedule=schedule;}
    public HashMap<String, HashMap<String, HashMap<String, String>>> getJournal(){return this.journal;}
    public void setJournal (HashMap<String, HashMap<String, HashMap<String, String>>> journal) {this.journal=journal;}

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

    // преобразует String в journal для ввода в sql
    public void convertStringToJournal(String data){
        HashMap<String, HashMap<String, HashMap<String, String>>> journal = new HashMap<>();
        // парсим в lvl1 объекты на первом слое:  07-03-2021={Физика_Кошкина={Воскребенцев=5, Лесков=Н}, Математика_плетенёва={Воскребенцев=5, Лесков=Н}}
        ArrayList<String> lvl1 = parseToArrayList(data);

        for (String lvl1_item0: lvl1){
            String lvl1_item=lvl1_item0;
            // парсим и удаляем дату
            String lvl1_date = lvl1_item.substring(0,lvl1_item.indexOf("="));
            lvl1_item = lvl1_item.replaceFirst(lvl1_date+"=","");
            // парсим в lvl2 бъекты на втором слое:   Физика_Кошкина={Воскребенцев=5, Лесков=Н}
            ArrayList<String> lvl2 = parseToArrayList(lvl1_item);

            HashMap<String, HashMap<String, String>> teacher_lesson_learner_score = new HashMap<>();
            for (String lvl2_item0: lvl2){
                String lvl2_item=lvl2_item0;
                // парсим и удаляем урок_учитель
                String lvl2_lesson_teacher = lvl2_item.substring(0,lvl2_item.indexOf("="));
                lvl2_item = lvl2_item.replaceFirst(lvl2_lesson_teacher+"=","");
                // парсим в lvl3 бъекты на третьем слое:   Воскребенцев=5
                ArrayList<String> lvl3 = parseToArrayList(lvl2_item);
                // формируем hash для третьего слоя
                HashMap<String, String> learner_score = new HashMap<>();
                for (String lvl3_item0: lvl3){
                    String lvl3_item = lvl3_item0;
                    String item0 = lvl3_item.substring(0, lvl3_item.indexOf("="));
                    String item1 = lvl3_item.substring(lvl3_item.indexOf("=")+1, lvl3_item.length());
                    learner_score.put(item0, item1);
                }

                // формируем hash для второго слоя
                teacher_lesson_learner_score.put(lvl2_lesson_teacher, learner_score);
            }
            // формируем hash для первого слоя
            journal.put(lvl1_date, teacher_lesson_learner_score);
        }

        this.journal = journal;
    }
    // парсит из {obj, obj} ArrayList{obj, obj} с учётом { и }
    private ArrayList<String> parseToArrayList(String data){
        ArrayList<String> result= new ArrayList<>();
        int indicator = 0;
        int position_start=1;
        int i=0;
        data=data.substring(0,data.length()-1)+",";
        while (i<data.length()){
            switch (data.charAt(i)){
                case '{':
                    indicator++;
                    break;
                case '}':
                    indicator--;
                    break;
                case ',':
                    if (indicator==1){
                        String item="";
                        item=data.substring(position_start,i);
                        position_start=i+2;
                        result.add(item);
                    }
            }
            i++;
        }
        return result;
    }
    // преобразует String в schedule для ввода в sql
    public void convertStringToSchedule(String data){
        // {день недели={предмет=учитель, предмет=учитель, ...}, день недели={предмет=учитель, ...}, ...}

        // парсим в lvl1 объекты на первом слое
        ArrayList<String> lvl1 = parseToArrayList(data);

        HashMap<String, HashMap<String, String>> schedule = new HashMap<>();
        for (String lvl1_item0: lvl1){
            String lvl1_item = lvl1_item0;
            // парсим и удаляем день недели
            String lvl1_dayOfWeek =lvl1_item.substring(0,lvl1_item.indexOf("="));
            lvl1_item = lvl1_item.replaceFirst(lvl1_dayOfWeek+"=","");
            // парсим в lvl2 бъекты на втором слое
            ArrayList<String> lvl2 = parseToArrayList(lvl1_item);

            HashMap<String, String> lesson_teacher = new HashMap<>();
            for (String lvl2_item0: lvl2){
                String lvl2_item = lvl2_item0;
                String item0 = lvl2_item.substring(0, lvl2_item.indexOf("="));
                String item1 = lvl2_item.substring(lvl2_item.indexOf("=")+1, lvl2_item.length());
                lesson_teacher.put(item0, item1);
            }

            // формируем hash для первого слоя
            schedule.put(lvl1_dayOfWeek, lesson_teacher);
        }

        this.schedule=schedule;
    }
}
