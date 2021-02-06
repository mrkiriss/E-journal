package com.example.e_journal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.MenuItem;
import android.widget.Toast;

import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.interfaces.DocumentsPostman;
import com.example.e_journal.interfaces.GroupsPostman;
import com.example.e_journal.school.Class;
import com.example.e_journal.school.Elective;
import com.example.e_journal.school.Employee;
import com.example.e_journal.school.Learner;
import com.example.e_journal.school.Parent;
import com.example.e_journal.school.Participant;
import com.example.e_journal.school.School;
import com.example.e_journal.school.Section;
import com.example.e_journal.school.Teacher;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AddingPostman, DocumentsPostman, GroupsPostman {

    // экземпляр класса School
    School school= new School();
    // экземляр класса DBHelper
    DBHelper dbHelper = new DBHelper(this);
/*
    void addData(){
        Parent l1_p1 = new Parent("Воскребенцева.О.С", "+79127090525");
        Parent l1_p2 = new Parent("Воскребенцева.А.В", "+79127139099");
        ArrayList<Parent> prnts = new ArrayList<Parent>();
        prnts.add(l1_p1);prnts.add(l1_p2);
        Learner l1 = new Learner("Воскребенцев.К.А", "+7912718314", "175", prnts, "18");

        Parent l2_p1 = new Parent("Онегина.О.С", "+12349");
        Parent l2_p2 = new Parent("Сергеич.А.В", "+5644685888888");
        ArrayList<Parent> prnts2 = new ArrayList<Parent>();
        prnts2.add(l2_p1);prnts2.add(l2_p2);
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
*/
    // ---программне функции---
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //addData(); // начальные данный для проверки коректности программы

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
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_upload:
                Toast.makeText(getApplicationContext(), "Start upload", Toast.LENGTH_SHORT).show();
                uploadDatabase();
                Toast.makeText(getApplicationContext(), "Upload completed successful", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_download:
                Toast.makeText(getApplicationContext(), "Start download", Toast.LENGTH_SHORT).show();
                downloadDatabase();
                Toast.makeText(getApplicationContext(), "Download completed successful", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    // ----------авторские функции----------

    // функции ДЛЯ работы с БАЗОЙ sql
    void uploadDatabase(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // внесение в базу learners
        db.execSQL("DROP TABLE IF EXISTS " + "learners");
        db.execSQL("CREATE TABLE learners (" +"fullName STRING, phone STRING, cardID STRING, age STRING,"+
                "fullName_parent1 STRING, phone_parent1 STRING, fullName_parent2 STRING, phone_parent2 STRING, "+
                "consist_class STRING, consist_elective STRING, consist_section STRING)");
        for (Learner i: school.getLearners()){
            String data = "'"+i.getFullName()+"', '"+i.getPhone()+"', '"+i.getCardID()+"', '"+i.getAge()+"', '"+
                    i.getParentData(0)[0]+"', '"+i.getParentData(0)[1]+"', '"+i.getParentData(1)[0]+"', '"+i.getParentData(1)[1]+"', '"+
                    i.convertArrayToString("Класс")+"', '"+i.convertArrayToString("Электив")+"', '"+i.convertArrayToString("Секция")+"'";
            db.execSQL(" INSERT INTO learners" + "(fullName, phone, cardID, age, fullName_parent1, phone_parent1, fullName_parent2, phone_parent2, consist_class, consist_elective, consist_section)"+
                    " VALUES(" + data +")");
        }
        // внесение в базу teachers
        db.execSQL("DROP TABLE IF EXISTS " + "teachers");
        db.execSQL("CREATE TABLE teachers (fullName STRING, phone STRING, cardID STRING, position STRING, qvalification STRING)");
        for (Teacher i: school.getTeachers()){
            String data = "'"+i.getFullName()+"', '"+i.getPhone() +"', '"+ i.getCardID() + "', '" + i.getPosition() + "', '" + i.getQualification() +"'";
            db.execSQL(" INSERT INTO teachers " + "(fullName, phone, cardID, position, qvalification)" +
                    "VALUES (" + data + ")");
        }
        // внесение в базу employees
        db.execSQL("DROP TABLE IF EXISTS " + "employees");
        db.execSQL("CREATE TABLE employees (fullName STRING, phone STRING, cardID STRING, position STRING)");
        for (Employee i: school.getEmployees()){
            String data = "'"+i.getFullName()+"', '"+i.getPhone() +"', '"+ i.getCardID() + "', '" + i.getPosition()+"'";
            db.execSQL(" INSERT INTO employees " + "(fullName, phone, cardID, position)" +
                    "VALUES (" + data + ")");
        }
        // внесение в базу participants
        db.execSQL("DROP TABLE IF EXISTS " + "participants");
        db.execSQL("CREATE TABLE participants (fullName STRING, phone STRING, cardID STRING)");
        for (Participant i: school.getParticipants()){
            String data = "'"+i.getFullName()+"', '"+i.getPhone() +"', '"+ i.getCardID()+"'";
            db.execSQL(" INSERT INTO participants " + "(fullName, phone, cardID)" +
                    "VALUES (" + data + ")");
        }
        // внесение в базу classes
        db.execSQL("DROP TABLE IF EXISTS " + "classes");
        db.execSQL("CREATE TABLE classes (fullName STRING, teacher STRING)");
        for (Class i: school.getClasses()){
            String data = "'"+i.getNumber()+"', '"+i.getTeacher().getFullName()+"'";
            db.execSQL(" INSERT INTO classes " + "(fullName, teacher)" +
                    "VALUES (" + data + ")");
        }
        // внесение в базу electives
        db.execSQL("DROP TABLE IF EXISTS " + "electives");
        db.execSQL("CREATE TABLE electives (fullName STRING, teacher STRING)");
        for (Elective i: school.getElectives()){
            String data = "'"+i.getAcademicSubject()+"', '"+i.getTeacher().getFullName()+"'";
            db.execSQL(" INSERT INTO electives " + "(fullName, teacher)" +
                    "VALUES (" + data + ")");
        }
        // внесение в базу sections
        db.execSQL("DROP TABLE IF EXISTS " + "sections");
        db.execSQL("CREATE TABLE sections (fullName STRING, teacher STRING)");
        for (Section i: school.getSections()){
            String data = "'"+i.getName()+"', '"+i.getTeacher().getFullName()+"'";
            db.execSQL(" INSERT INTO sections " + "(fullName, teacher)" +
                    "VALUES (" + data + ")");
        }
        // закрытие базы данных
        db.close();
    }
    void downloadDatabase(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // считывание из базы teachers
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        Cursor cursor = db.rawQuery("select * from teachers", null);
        cursor.moveToFirst();
        while(cursor.getCount()!=0){
            Teacher t = new Teacher(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            teachers.add(t);

            cursor.moveToNext();
            if(cursor.isAfterLast()) break;
        }
        school.setTeachers(teachers);
        // считывание из базы classes
        ArrayList<Class> classes = new ArrayList<Class>();
        cursor = db.rawQuery("select * from classes", null);
        cursor.moveToFirst();
        while(cursor.getCount()!=0){
            Class c = new Class(cursor.getString(0), school.getTeacherByName(cursor.getString(1)), new ArrayList<Learner>());
            classes.add(c);

            cursor.moveToNext();
            if(cursor.isAfterLast()) break;
        }
        school.setClasses(classes);
        // считывание из базы electives
        ArrayList<Elective> electives = new ArrayList<Elective>();
        cursor = db.rawQuery("select * from electives", null);
        cursor.moveToFirst();
        while(cursor.getCount()!=0){
            Elective e = new Elective(cursor.getString(0), school.getTeacherByName(cursor.getString(1)), new ArrayList<Learner>());
            electives.add(e);

            cursor.moveToNext();
            if(cursor.isAfterLast()) break;
        }
        school.setElectives(electives);
        // считывание из базы sections
        ArrayList<Section> sections = new ArrayList<Section>();
        cursor = db.rawQuery("select * from sections", null);
        cursor.moveToFirst();
        while(cursor.getCount()!=0){
            Section s = new Section(cursor.getString(0), school.getTeacherByName(cursor.getString(1)), new ArrayList<Learner>());
            sections.add(s);

            cursor.moveToNext();
            if(cursor.isAfterLast()) break;
        }
        school.setSections(sections);
        // считывание из базы learners
        ArrayList<Learner> learners = new ArrayList<Learner>();
        cursor = db.rawQuery("select * from learners", null);
        cursor.moveToFirst();
        while(cursor.getCount()!=0){
            Parent p1 = new Parent(cursor.getString(4), cursor.getString(5));
            Parent p2 = new Parent(cursor.getString(6), cursor.getString(7));
            ArrayList<Parent> p = new ArrayList<Parent>();
            p.add(p1);p.add(p2);
            ArrayList<String> consist_class = Learner.convertStringToArray("Класс", cursor.getString(8));
            ArrayList<String> consist_elective = Learner.convertStringToArray("Электив", cursor.getString(9));
            ArrayList<String> consist_section = Learner.convertStringToArray("Секция", cursor.getString(10));
            Learner l = new Learner(cursor.getString(0), cursor.getString(1), cursor.getString(2), p, cursor.getString(3), consist_class, consist_elective, consist_section);
            learners.add(l);

            // добовляем ученика во все группы, в которых он состоит
            for (String i: consist_class){
                if (i=="") continue;
                school.getClasses().get(Integer.valueOf(i)).addLearner(l);
            }
            for (String i: consist_elective){
                if (i=="") continue;
                school.getElectives().get(Integer.valueOf(i)).addLearner(l);
            }
            for (String i: consist_section){
                if (i=="") continue;
                school.getSections().get(Integer.valueOf(i)).addLearner(l);
            }

            cursor.moveToNext();
            if(cursor.isAfterLast()) break;
        }
        school.setLearners(learners);
        // считывание из базы employees
        ArrayList<Employee> employees = new ArrayList<Employee>();
        cursor = db.rawQuery("select * from employees", null);
        cursor.moveToFirst();
        while(cursor.getCount()!=0){
            Employee e = new Employee(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            employees.add(e);

            cursor.moveToNext();
            if(cursor.isAfterLast()) break;
        }
        school.setEmployees(employees);
        // считывание из базы participants
        ArrayList<Participant> participants = new ArrayList<Participant>();
        cursor = db.rawQuery("select * from participants", null);
        cursor.moveToFirst();
        while(cursor.getCount()!=0){
            Participant p = new Participant(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            participants.add(p);

            cursor.moveToNext();
            if(cursor.isAfterLast()) break;
        }
        school.setParticipants(participants);
        // закрытие базы данных
        db.close();
    }


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
            if (bol && other_learners.length!=0){ // второе условие, т.к возникает ошибка java.lang.ArrayIndexOutOfBoundsException: length=0; index=0 на строку ниже
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
        if (data==null) System.out.println("NUll MF");
        String selected_learner=data.getStringExtra("selected_learner");
        String selected_teacher=data.getStringExtra("selected_teacher");
        Learner learner = school.getLearnerByName(selected_learner); // получаем объект ученика

        switch (resultCode){
            case 1: // запрос на добавление
                System.out.println("Добавление запущено");
                // добавляем в нужную категорию по индексу
                switch(selected_category0){
                    case "Класс":
                        Class c = school.getClasses().get(selected_index0);
                        c.addLearner(learner);
                        learner.addConsist("Класс", String.valueOf(c.getIndex()));
                        break;
                    case "Электив":
                        Elective e = school.getElectives().get(selected_index0);
                        e.addLearner(learner);
                        learner.addConsist("Электив", String.valueOf(e.getIndex()));
                        break;
                    case "Секция":
                        Section s = school.getSections().get(selected_index0);
                        s.addLearner(learner);
                        learner.addConsist("Секция", String.valueOf(s.getIndex()));
                        break;
                }
                break;
            case -1: // запрос на удаление
                System.out.println("Удаление запущено");
                // удаляем из нужной категории по индексу
                switch(selected_category0){
                    case "Класс":
                        Class c = school.getClasses().get(selected_index0);
                        c.deleteLearnerByName(selected_learner);
                        learner.deleteConsist("Класс", String.valueOf(c.getIndex()));
                        break;
                    case "Электив":
                        Elective e = school.getElectives().get(selected_index0);
                        e.deleteLearnerByName(selected_learner);
                        learner.deleteConsist("Электив", String.valueOf(e.getIndex()));
                        break;
                    case "Секция":
                        Section s = school.getSections().get(selected_index0);
                        s.deleteLearnerByName(selected_learner);
                        learner.deleteConsist("Секция", String.valueOf(s.getIndex()));
                        break;
                }
                break;
        }

        // обновления объекта ученика после изменений с индексами групп
        school.updateLearner(learner);

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


