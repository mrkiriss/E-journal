package com.example.e_journal;

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

        // начальные данный для проверки коректности программы
        addData();

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

}


