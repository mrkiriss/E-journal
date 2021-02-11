package com.example.e_journal;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HandshakeCompletedEvent;

public class GroupEditorActivity extends AppCompatActivity {

    // глобальные переменные
    private String selected_dayOfWeek;
    private HashMap<String, HashMap<String, String>> schedule = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_editor);

        // создание объектов для элементов
        LinearLayout container_add=  findViewById(R.id.container_add_learner);
        LinearLayout container_delete=  findViewById(R.id.container_delete_learner);
        LinearLayout container_teacher=  findViewById(R.id.container_teacher);

         LinearLayout container_schedule =  findViewById(R.id.container_schedule);
         Spinner spinner_number_day = findViewById(R.id.spinner_number_day);
         Spinner spinner_number_lessons = findViewById(R.id.spinner_number_lessons);
         Button button_create_form = findViewById(R.id.button_create_schedule_form);
         TableLayout table_schedule = findViewById(R.id.table_schedule);
         Button button_edit_schedule = findViewById(R.id.button_edit_schedule);


        Intent i = getIntent();
        String[] current_learners = i.getStringArrayExtra("current_learners");
        String[] other_learners = i.getStringArrayExtra("other_learners");
        String current_teacher = i.getStringExtra("current_teacher");
        String[] teachers = i.getStringArrayExtra("teachers");

        if (i.getStringExtra("category").equals("Класс")){
            schedule=convertStringToSchedule(i.getStringExtra("schedule"));
            container_schedule.setVisibility(View.VISIBLE);
        }else{
            container_schedule.setVisibility(View.GONE);
        }


        // заполнение блока добавления
        if (other_learners.length==0){
            findViewById(R.id.button_accept_add).setEnabled(false); // выкл кнопки добавления, если нечего добавлять
        }else {
            findViewById(R.id.button_accept_add).setEnabled(true);

            Spinner spinner_add = findViewById(R.id.spinner_add);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, other_learners);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_add.setAdapter(adapter);
        }

        // заполнение блока удаления
        if (current_learners.length==0){
            findViewById(R.id.button_accept_delete).setEnabled(false); // выкл кнопки удаления, если нечего удалять
        }else {
            findViewById(R.id.button_accept_delete).setEnabled(true);

            Spinner spinner_add = findViewById(R.id.spinner_delete);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, current_learners);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_add.setAdapter(adapter);
        }

        // заполнение блока с учителями
        RadioGroup radio_group = findViewById(R.id.radio_group);
        int current_teacher_index = 0;
        for (int j=0; j<teachers.length;j++) {
            RadioButton radio = new RadioButton(this);
            radio.setText(teachers[j]);
            radio_group.addView(radio);

            if (current_teacher.equals(teachers[j])) current_teacher_index=j;
        }
        if (teachers.length!=0) {
            RadioButton radio0 = (RadioButton) radio_group.getChildAt(current_teacher_index);
            radio0.setChecked(true);
        }


        // установка слушателя на кнопку добавления ученика
        findViewById(R.id.button_accept_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // выгрузка выбранного ученика
                Spinner spinner_add = findViewById(R.id.spinner_add);
                String selected_learner = spinner_add.getSelectedItem().toString();
                // выгрузка выбранного учителя
                RadioGroup radio_group = findViewById(R.id.radio_group);
                RadioButton radio = findViewById(radio_group.getCheckedRadioButtonId());
                String selected_teacher = "";
                if (!current_teacher.equals("")){ selected_teacher = radio.getText().toString();}

                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                back.putExtra("selected_learner", selected_learner);
                back.putExtra("selected_teacher", selected_teacher);
                setResult(1, back);
                finish();

            }
        });

        // установка слушателя на кнопку удалить ученика
        findViewById(R.id.button_accept_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // выгрузка выбранного ученика
                Spinner spinner_add = findViewById(R.id.spinner_delete);
                String selected_learner = spinner_add.getSelectedItem().toString();
                // выгрузка выбранного учителя
                RadioGroup radio_group = findViewById(R.id.radio_group);
                RadioButton radio = findViewById(radio_group.getCheckedRadioButtonId());
                String selected_teacher = radio.getText().toString();

                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                back.putExtra("selected_learner", selected_learner);
                back.putExtra("selected_teacher", selected_teacher);
                setResult(-1, back);
                finish();

            }
        });

        // установка слушаетля на кнопку выхода (парсит учителя и возвращает Intent)
        findViewById(R.id.button_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // выгрузка выбранного учителя
                RadioGroup radio_group = findViewById(R.id.radio_group);
                RadioButton radio = findViewById(radio_group.getCheckedRadioButtonId());
                String selected_teacher = new String();
                if (radio_group.getChildCount()!=0){
                    selected_teacher = radio.getText().toString();
                }

                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                back.putExtra("selected_learner", "");
                if (selected_teacher==null) selected_teacher="There are no teachers. Add a teacher";
                back.putExtra("selected_teacher", selected_teacher);
                setResult(0, back);
                finish();
            }
        });

        // установка слушателя на кнопку генерации формы для расписания (парсит параметры из вып. списков, создаёт таблицу со строками: поле ввода текста\вып. список с именами учителей)
        findViewById(R.id.button_create_schedule_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // удаление старой таблицы
                table_schedule.removeAllViews();
                // получеине номера дня недели
                switch (spinner_number_day.getSelectedItem().toString()){
                    case "Понедельник":
                        selected_dayOfWeek="1";
                        break;
                    case "Вторник":
                        selected_dayOfWeek="2";
                        break;
                    case "Среда":
                        selected_dayOfWeek="3";
                        break;
                    case "Четверг":
                        selected_dayOfWeek="4";
                        break;
                    case "Пятница":
                        selected_dayOfWeek="5";
                        break;
                    case "Суббота":
                        selected_dayOfWeek="6";
                        break;
                    case "Воскресенье":
                        selected_dayOfWeek="7";
                        break;
                }
                // получение количества уроков
                int number_lessons=Integer.valueOf(spinner_number_lessons.getSelectedItem().toString());
                // адаптер для вып. списка учителей
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, teachers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // заполнение таблицы
                 // шапка таблицы
                TableRow row = new TableRow(getApplicationContext());
                TextView item2 = new TextView(getApplicationContext());
                item2.setPadding(5, 0, 5, 0);
                item2.setText("Название предмета");
                row.addView(item2);
                TextView item3 = new TextView(getApplicationContext());
                item3.setText("ФИО преподавателя");
                item3.setPadding(5, 0, 5, 0);
                row.addView(item3);

                table_schedule.addView(row);

                for (int i=0; i<number_lessons;i++){
                    row = new TableRow(getApplicationContext());
                    EditText item0 = new EditText(getApplicationContext());
                    row.addView(item0);
                    Spinner item1 = new Spinner(getApplicationContext());
                    item1.setAdapter(adapter);
                    row.addView(item1);

                    table_schedule.addView(row);
                }
                // сокрытие остальных контейнеров, для увеличения вместимости
                container_add.setVisibility(View.GONE);
                container_delete.setVisibility(View.GONE);
                container_teacher.setVisibility(View.GONE);
                // разблокирование кнопки парсинга результатов заполнения
                button_edit_schedule.setEnabled(true);
                // отключения выбора дня недели и количества уроков
                spinner_number_day.setEnabled(false);
                spinner_number_lessons.setEnabled(false);
            }

        });

        // парсит результаты ввода в таблицу, сохраняет результат в глобальную переменную
        findViewById(R.id.button_edit_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> lesson_teacher = new HashMap<>();
                for (int i=1;i<table_schedule.getChildCount();i++){
                    TableRow row= (TableRow) table_schedule.getChildAt(i);
                    EditText item0 = (EditText) row.getChildAt(0);
                    Spinner item1= (Spinner) row.getChildAt(1);
                    lesson_teacher.put(item0.getText().toString(), item1.getSelectedItem().toString());
                }
                // обновляем schedule
                schedule.put(selected_dayOfWeek, lesson_teacher);
                // выгрузка выбранного учителя
                RadioGroup radio_group = findViewById(R.id.radio_group);
                RadioButton radio = findViewById(radio_group.getCheckedRadioButtonId());
                String selected_teacher = radio.getText().toString();

                // выходим из Activity
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                back.putExtra("schedule", schedule.toString());
                back.putExtra("selected_teacher", selected_teacher);
                setResult(2, back);
                System.out.println("Редактирование расписания закончено");
                finish();
            }
        });
    }

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
    public HashMap<String, HashMap<String, String>> convertStringToSchedule(String data){

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

        return schedule;
    }
}