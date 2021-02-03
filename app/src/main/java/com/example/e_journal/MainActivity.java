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
import com.example.e_journal.school.Class;
import com.example.e_journal.school.Elective;
import com.example.e_journal.school.Employee;
import com.example.e_journal.school.Learner;
import com.example.e_journal.school.School;
import com.example.e_journal.school.Section;
import com.example.e_journal.school.Teacher;


public class MainActivity extends AppCompatActivity implements AddingPostman {

    // экземпляр класса School
    public School school= new School();

    // ---программне функции---
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

}


