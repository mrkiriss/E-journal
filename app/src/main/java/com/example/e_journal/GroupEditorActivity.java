package com.example.e_journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class GroupEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_editor);
        Intent i = getIntent();
        String[] current_learners = i.getStringArrayExtra("current_learners");
        String[] other_learners = i.getStringArrayExtra("other_learners");
        String current_teacher = i.getStringExtra("current_teacher");
        String[] teachers = i.getStringArrayExtra("teachers");

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
    }

}