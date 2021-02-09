package com.example.e_journal.fragments.journal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_journal.R;
import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.interfaces.JournalPostman;
import com.example.e_journal.school.Class;
import com.example.e_journal.school.Learner;
import com.example.e_journal.school.School;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class JournalFragment extends Fragment {

    // переменные
    private JournalPostman post;
    private Activity activity;
    private School school;
    private HashMap<String, HashMap<String, HashMap<String, String>>> journal = new HashMap<>();
    private Class selected_class; // выбранный пользователем не первом шаге класс
    private Date selected_date; // выбранная пользователем не первом шаге дата
    private String selected_date_format;
    private String selected_dayofweek;
    private String selection_lesson;
    private String selected_teacher;

    HashMap<String, String> learner_score;

    // создание ссылку на главное Activity и трансформируем экземпляр интерфейса в ссылку на Activity
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        if (c instanceof  Activity){
            activity=(Activity) c;
        }
        post= (JournalPostman) activity;
    }

    private JournalViewModel journalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        journalViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(JournalViewModel.class);
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        // получение объектов элементов во фрагменте
        LinearLayout container1 = v.findViewById(R.id.container_journal_choice1);
        Spinner spinner_classes = v.findViewById(R.id.spinner_journal_classes);
        TextView date = v.findViewById(R.id.textedit_date);
        Button button_accept1 = v.findViewById(R.id.button_journal_accept1);
        Button button_cancel1 = v.findViewById(R.id.button_journal_cancel1);

        LinearLayout container2 = v.findViewById(R.id.container_journal_choice2);
        Spinner spinner_lessons = v.findViewById(R.id.spinner_journal_lessons);
        Button button_accept2 = v.findViewById(R.id.button_journal_accept2);
        Button button_cancel2 = v.findViewById(R.id.button_journal_cancel2);

        LinearLayout container3 = v.findViewById(R.id.container_accept);
        TextView text_annotation = v.findViewById(R.id.text_journal_annotation);
        Button button_accept3 = v.findViewById(R.id.button_jouranl_accept3);

        LinearLayout container4 = v.findViewById(R.id.container_journal_result);
        TableLayout table = v.findViewById(R.id.table_journal);
        Button button_clear = v.findViewById(R.id.button_journal_clear);

        // получение актуального экземпляра school
        school = post.getSchool();

        // заполнение контейнера1 (выбор класса и даты)
         // заполнение вып. меню с номерами классов
        ArrayList<Class> classes = school.getClasses();
        String[] data = new String[classes.size()];
        for (int i=0;i<classes.size();i++) {
            data[i] = classes.get(i).getNumber();
        }
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_classes.setAdapter(adapter);

        // слушатель на ПОДТВЕРДИТЬ1 (по номеру класса получает его экземпляр, по выбранной дате получает расписание и объект журнала в экземпляре класса)
        button_accept1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // выход, если нет доступных классов
                if (spinner_classes.getCount() == 0){
                    Toast.makeText(getContext(), "Ошбика. Класс не выбран", Toast.LENGTH_SHORT).show();
                    return;
                }
                // получение экземпляр класса
                String selected_name = spinner_classes.getSelectedItem().toString();
                for (Class i: classes){
                    if (i.getNumber().equals(selected_name)){
                        selected_class = i;
                        break;
                    }
                }

                // работа с выбранной датой
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                selected_date_format = date.getText().toString();
                String regex = "\\d\\d\\D\\d\\d\\D\\d\\d\\d\\d";

                if ( !Pattern.matches(regex, selected_date_format) || selected_date_format.equals("")){
                    Toast.makeText(getContext(), "Дата не введена", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    selected_date = sdf.parse(selected_date_format);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(selected_date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;
                if (dayOfWeek==0) dayOfWeek=7;
                selected_dayofweek=String.valueOf(dayOfWeek);

                // заполнение вып. меню с уроками
                HashMap<String, HashMap<String, String>> schedule= selected_class.getSchedule();

                // выход, если нет расписаний ни на какой день
                if (schedule.size() == 0){
                    Toast.makeText(getContext(), "Ошбика. Расписания класса не существует", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> lessons_teachers = schedule.get(selected_dayofweek);

                // выход, если нет доступных уроков
                if (lessons_teachers == null){
                    Toast.makeText(getContext(), "Ошбика. У класса нет уроков в выбранный день", Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] data = new String[lessons_teachers.size()];
                int k=0;
                for (String i: lessons_teachers.keySet()) {
                    data[k++] = i;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_lessons.setAdapter(adapter);

                // блокировка и скрытие\открытие необходимых элементов
                spinner_classes.setEnabled(false);
                date.setEnabled(false);
                button_accept1.setEnabled(false);
                button_accept2.setEnabled(true);
                spinner_lessons.setEnabled(true);
                container2.setVisibility(View.VISIBLE);
            }
        });

        // слушатель на кнопку ОТМЕНА1
        button_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_classes.setEnabled(true);
                date.setEnabled(true);
                date.setText("");
                button_accept1.setEnabled(true);
                container2.setVisibility(View.GONE);
                container3.setVisibility(View.GONE);
            }
        });

        // слушатель на кнопку ПОДТВЕРДИТЬ2 (парсит название предмета и создаёт аннотацию)
        button_accept2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection_lesson = spinner_lessons.getSelectedItem().toString();
                String annotation = "Будет показана инфомрация о учениках\nкласса №"+selected_class.getNumber()
                        +"\nна "+selected_date_format+"\nпо предмету \""+selection_lesson+"\"";
                text_annotation.setText(annotation);

                // получение учителя выбранного предмета
                selected_teacher = selected_class.getSchedule().get(selected_dayofweek).get(selection_lesson);

                // блокировка и скрытие\открытие необходимых элементов
                spinner_lessons.setEnabled(false);
                button_accept2.setEnabled(false);
                container3.setVisibility(View.VISIBLE);
            }
        });

        // слушатель на кнопку ОТМЕНА2
        button_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_lessons.setEnabled(true);
                button_accept2.setEnabled(true);
                container3.setVisibility(View.GONE);
            }
        });

        // слушатель на кнопку ПОДТВЕРДИТЬ3 (создаёт таблицу с именами учеников и их оценками в вып. списках)
        button_accept3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, HashMap<String, HashMap<String, String>>> journal = selected_class.getJournal();

                // { дата: {предмет_учитель: {имя: оценка, имя: оценка, ... }, ... }, ... }

                // {имя: оценка, имя: оценка, ... }
                learner_score = new HashMap<>();
                for (Learner i : selected_class.getLearners()) {
                    learner_score.put(i.getFullName(), "-");
                }

                // информация по таблице
                String information = "Число: " + selected_date_format + "     Класс: №" + selected_class.getNumber() + "\nПредмет: " + selection_lesson + "     Учитель: " + selected_teacher;
                TextView inf = v.findViewById(R.id.text_journal_information);
                inf.setText(information);

                // составной ключ для второго слоя
                String lesson_teacher = selection_lesson+"_"+selected_teacher;

                // добавляем шапку таблице
                TableRow row = new TableRow(getContext());
                TextView item0 = new TextView(getContext());
                TextView item2 = new TextView(getContext());
                item0.setPadding(5, 0, 5, 0);
                item2.setPadding(5, 0, 5, 0);
                item0.setText("ФИО");
                row.addView(item0);
                item2.setText("Оценка");
                row.addView(item2);
                table.addView(row);

                // создаём адаптер с оценками
                ArrayList<String> data_score = new ArrayList<>();
                data_score.add("-");
                data_score.add("Н");
                data_score.add("2");
                data_score.add("3");
                data_score.add("4");
                data_score.add("5");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_score);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                if (!journal.containsKey(selected_date_format) || !journal.get(selected_date_format).containsKey(lesson_teacher)) { // нет даты или нет предмет_учитель

                    for (String i : learner_score.keySet()) {
                        row = new TableRow(getContext());
                        item0 = new TextView(getContext());
                        Spinner item1 = new Spinner(getContext());
                        item0.setPadding(5, 0, 5, 0);
                        item1.setPadding(5, 0, 5, 0);
                        item0.setText(i);
                        row.addView(item0);
                        item1.setAdapter(adapter);
                        row.addView(item1);

                        table.addView(row);
                    }
                }else{ // если существует и дата, и предмет_учитель

                    // проверяем каждого в learner_score на наличие в journal, при присутсвии добавляем ему оценку в список learner_score, который будет создавать таблицу
                    HashMap<String, String> journal_learners= new HashMap<>();
                    for (String class_fullName: learner_score.keySet()){
                        for (String journal_fullName: journal.get(selected_date_format).get(lesson_teacher).keySet()){
                            if (class_fullName.equals(journal_fullName)){
                                learner_score.put(class_fullName, journal.get(selected_date_format).get(lesson_teacher).get(class_fullName));
                                break;
                            }
                        }
                    }

                    ArrayList<String> rating_options = new ArrayList<>();
                    rating_options.add("-");rating_options.add("Н");rating_options.add("2");
                    rating_options.add("3");rating_options.add("4");rating_options.add("5");
                    // заполняем таблицу, ставя spinner каждого ученика в нужное положение
                    for (String i : learner_score.keySet()) {
                        row = new TableRow(getContext());
                        item0 = new TextView(getContext());
                        Spinner item1 = new Spinner(getContext());
                        item0.setPadding(5, 0, 5, 0);
                        item1.setPadding(5, 0, 5, 0);
                        item0.setText(i);
                        row.addView(item0);
                        item1.setAdapter(adapter);
                        item1.setSelection(rating_options.indexOf(learner_score.get(i)));
                        row.addView(item1);

                        table.addView(row);
                    }
                }
                // блокировка и скрытие\открытие необходимых элементов
                container1.setVisibility(View.GONE);
                container2.setVisibility(View.GONE);
                container3.setVisibility(View.GONE);
                container4.setVisibility(View.VISIBLE);
            }
        });

        // слушатель на кнопку ЗАВЕРШИТЬ ПРОСМОТР
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // получение оценок учеников из таблицы
                for (int i=1; i<table.getChildCount();i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TextView item0 = (TextView) row.getChildAt(0);
                    Spinner item1 = (Spinner) row.getChildAt(1);
                    learner_score.put(item0.getText().toString(), item1.getSelectedItem().toString()); // обновление словаря 3-его слоя
                }

                // предмет_учитель
                String lesson_teacher = selection_lesson+"_"+selected_teacher;

                // создание объекта для обновления journal в выбранном классе
                HashMap<String, HashMap<String, String>> journal_level1 = new HashMap<>();
                if (journal.containsKey(selected_date_format)) { // если дата уже существует, то получаем её для обновлениея
                    journal_level1 = journal.get(selected_date_format);
                }
                journal_level1.put(lesson_teacher, learner_score);
                journal.put(selected_date_format, journal_level1);
                // обновляет journal в классе
                selected_class.setJournal(journal);
                // обновляет класс в списке у экземляра school
                post.updateClasses(selected_class);
                // блокировка и скрытие\открытие необходимых элементов

                table.removeAllViews();
                container1.setVisibility(View.VISIBLE);
                container2.setVisibility(View.VISIBLE);
                container3.setVisibility(View.VISIBLE);
                container4.setVisibility(View.GONE);
            }
        });

        return v;
    }
}