package com.example.e_journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.interfaces.DocumentsPostman;
import com.example.e_journal.interfaces.GroupsPostman;
import com.example.e_journal.school.Class;
import com.example.e_journal.school.Elective;
import com.example.e_journal.school.Employee;
import com.example.e_journal.school.Learner;
import com.example.e_journal.school.Parent;
import com.example.e_journal.school.School;
import com.example.e_journal.school.Section;
import com.example.e_journal.school.Teacher;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AddingPostman, DocumentsPostman, GroupsPostman {

    // экземпляр класса School
    public School school= new School();

    void addData(){
        Parent l1_p1 = new Parent("Воскребенцева.О.С", "+79127090525");
        Parent l1_p2 = new Parent("Воскребенцева.А.В", "+79127139099");
        ArrayList<Parent> prnts = new ArrayList<Parent>();
        prnts.add(l1_p1);prnts.add(l1_p2);
        Learner l1 = new Learner("Воскребенцев.К.А", "+7912718314", "175", prnts, "18");

        Parent l2_p1 = new Parent("Онегина.О.С", "+12349");
        Parent l2_p2 = new Parent("Сергеич.А.В", "+5644685888888");
        ArrayList<Parent> prnts2 = new ArrayList<Parent>();
        prnts2.add(l1_p1);prnts2.add(l1_p2);
        Learner l2 = new Learner("Лесков.К.А", "+7777777777777", "666", prnts2, "17");

        ArrayList<Learner> lrnrs= new ArrayList<Learner>();
        lrnrs.add(l1);lrnrs.add(l2);

        Teacher t1 = new Teacher("КарлишунаЛА", "+7900000000000", "000", "учитель-бог", "нормалёк");
        Teacher t2 = new Teacher("ПлетенёваКК", "+123456789", "999", "учитель-математик", "ну так");

        Class сlass1 = new Class("1", t1, lrnrs);

        school.addLearner(l1);school.addLearner(l2);
        school.addTeacher(t1);school.addTeacher(t2);
        school.addClass(сlass1);
    }
    // ---программне функции---
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        addData(); // начальные данный для проверки коректности программы

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_journal, R.id.nav_documents, R.id.nav_adding, R.id.nav_groups, R.id.nav_find)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // ---авторские функции---
    // функции для ДОБАВЛЕНИЕ
    // перегрузки функции, которая добавляет выбранную категорию в подходящий список у school
    @Override
    public void fragmentMail(Learner x) {
        school.addLearner(x);
    }
    @Override
    public void fragmentMail(Teacher x) {
        school.addTeacher(x);
    }
    @Override
    public void fragmentMail(Employee x) {
        school.addEmployee(x);
    }
    @Override
    public void fragmentMail(Class x) {
        school.addClass(x);
    }
    @Override
    public void fragmentMail(Elective x) {
        school.addElective(x);
    }
    @Override
    public void fragmentMail(Section x) {
        school.addSection(x);
    }

    public int getCountClasses(){
        return school.getCountClasses();
    }
    public int getCountElectives(){
        return school.getCountElectives();
    }
    public int getCountSections(){
        return school.getCountSections();
    }

    // функции для ДОКУМЕНТЫ
    // фукнции, отвечающие за передачу свойств экземляра school во фрагмент documents
    public ArrayList<String[]> getTeachers(){
        return school.getListTeachers();
    }
    public ArrayList<String[]> getLearners(){
        return school.getListLearners();
    }
    public ArrayList<String[]> getParticipants(){
        return school.getListParticipants();
    }
    public ArrayList<String[]> getLearnersAndParents(int number){
        return school.getListForClassMeeting(number);
    }

    // функции для ГРУППЫ
    // возвращает все существующие классы в school
    public ArrayList<Class> getClasses(){
        return school.getClasses();
    }
    public ArrayList<Elective> getElectives(){
        return school.getElectives();
    }
    public ArrayList<Section> getSections(){
        return school.getSections();
    }

    // функции для Активности Редактирования
    // индикаторы общие
    String selected_category0="";
    int selected_index0=0;
    // запускает активность редактирования
    public void startEditActivity(String selected_category, int selected_index){
        //изменяет постоянные значения индикаторов
        selected_category0=selected_category;
        selected_index0=selected_index;

        Intent intent = new Intent(this, GroupEditorActivity.class);
        // создание списка имён всех школьников
        ArrayList<Learner> lrnrs = new ArrayList<Learner>();
        switch(selected_category){
            case "Класс":
                 lrnrs = school.getClasses().get(selected_index).getLearners();
                 break;
            case "Электив":
                lrnrs = school.getElectives().get(selected_index).getLearners();
                 break;
            case "Секция":
                lrnrs = school.getSections().get(selected_index).getLearners();
                break;
        }
        // получаем имена всех присутствующих в группе учеников
        String[] current_learners = new String[lrnrs.size()];
        for (int i=0; i<lrnrs.size();i++){
            current_learners[i] = lrnrs.get(i).getFullName();
        }
        // получение списка всех остальных учеников
        String[] other_learners = new String[school.getLearners().size()-current_learners.length];
        int k=0;
        for (Learner i: school.getLearners()){
            String fullName = i.getFullName();
            boolean bol = true;
            for (String j: current_learners){
                if (fullName == j){
                    bol = false;
                    break;
                }
            }
            if (bol){
                other_learners[k++]=fullName;
            }
        }
        // получаем нынещнего учителя
        String current_teacher = "";
        switch(selected_category){
            case "Класс":
                current_teacher = school.getClasses().get(selected_index).getTeacher().getFullName();
                break;
            case "Электив":
                current_teacher = school.getElectives().get(selected_index).getTeacher().getFullName();
                break;
            case "Секция":
                current_teacher = school.getSections().get(selected_index).getTeacher().getFullName();
                break;
        }
        // получаем список всех учителей
        String[] teachers = new String[school.getTeachers().size()];
        k=0;
        for (Teacher i: school.getTeachers()){
            teachers[k++]=i.getFullName();
        }
        // заполняем данные и посылаем в активити
        intent.putExtra("current_learners", current_learners);
        intent.putExtra("other_learners", other_learners);
        intent.putExtra("current_teacher", current_teacher);
        intent.putExtra("teachers", teachers);
        startActivityForResult(intent, 0);
    }
    // принимает значения из Активности Редактирования, заного вызывает её

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // взятие значение из возвращенного Intent data
        String selected_learner=data.getStringExtra("selected_learner");
        String selected_teacher=data.getStringExtra("selected_teacher");

        switch (resultCode){
            case 1: // запрос на добавление
                System.out.println("Добавление запущено");
                Learner learner = school.getLearnerByName(selected_learner); // получаем объект ученика
                // добавляем в нужную категорию по индексу
                switch(selected_category0){
                    case "Класс":
                        school.getClasses().get(selected_index0).addLearner(learner);
                        break;
                    case "Электив":
                        school.getElectives().get(selected_index0).addLearner(learner);
                        break;
                    case "Секция":
                        school.getSections().get(selected_index0).addLearner(learner);
                        break;
                }
                break;
            case -1: // запрос на удаление
                System.out.println("Удаление запущено");
                // удаляем из нужной категории по индексу
                switch(selected_category0){
                    case "Класс":
                        school.getClasses().get(selected_index0).deleteLearnerByName(selected_learner);
                        break;
                    case "Электив":
                        school.getElectives().get(selected_index0).deleteLearnerByName(selected_learner);
                        break;
                    case "Секция":
                        school.getSections().get(selected_index0).deleteLearnerByName(selected_learner);
                        break;
                }
                break;
        }
        // перезапись учителя
        Teacher teacher = school.getTeacherByName(selected_teacher);
         // изменяем нужную категорию по индексу
        switch(selected_category0){
            case "Класс":
                school.getClasses().get(selected_index0).setTeacher(teacher);
                break;
            case "Электив":
                school.getElectives().get(selected_index0).setTeacher(teacher);
                break;
            case "Секция":
                school.getSections().get(selected_index0).setTeacher(teacher);
                break;
        }

        if (resultCode==0){
            return; // прекращение циклицеского вызова
        }else {
            startEditActivity(selected_category0, selected_index0); // повторный вызов Активити Редактирования
        }
    }

}


