package com.example.e_journal.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.e_journal.R;
import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.interfaces.DocumentsPostman;
import com.example.e_journal.interfaces.GroupsPostman;
import com.example.e_journal.school.Class;
import com.example.e_journal.school.Elective;
import com.example.e_journal.school.Section;
import com.example.e_journal.school.Teacher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link groups#newInstance} factory method to
 * create an instance of this fragment.
 */
public class groups extends Fragment {

    // создание ссылку на главное Activity и трансформируем экземпляр интерфейса в ссылку на Activity
    private GroupsPostman post;
    private Activity activity;
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        if (c instanceof  Activity){
            activity=(Activity) c;
        }
        post= (GroupsPostman) activity;
    }

    // авторские переменные
    String selected_category;
    ArrayList<Class> classes0 = new ArrayList<Class>();
    ArrayList<Elective> electives0 = new ArrayList<Elective>();
    ArrayList<Section> sections0 = new ArrayList<Section>();
    Class class0;
    Elective elective0;
    Section section0;
    int selected_index;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public groups() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment groups.
     */
    // TODO: Rename and change types and number of parameters
    public static groups newInstance(String param1, String param2) {
        groups fragment = new groups();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_groups, container, false);

        // листенер на нажатие ПРОДОЛЖИТЬ1 (формирует выдвигающийся список с выбранным типом групп)
        v.findViewById(R.id.button_continue1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner type = v.findViewById(R.id.spinner_group_category);
                String id = type.getSelectedItem().toString();
                Spinner groups = v.findViewById(R.id.spinner_group);
                TextView text = v.findViewById(R.id.text_group);
                LinearLayout container_group = v.findViewById(R.id.container_choose_group);

                String[] data;
                ArrayAdapter<String> adapter;

                switch (id){
                    case "Класс":
                        ArrayList<Class> classes = post.getClasses();
                        // создаём адаптер для выдв. списка с номерами классов
                        data = new String[classes.size()];
                        for (int i=0;i<classes.size();i++) {
                            data[i] = classes.get(i).getNumber();
                        }
                        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        groups.setAdapter(adapter);
                        // новая инструкция на скрытый label
                        text.setText("Выберите номер класса");
                        // установка идентификатора
                        selected_category="Класс";
                        classes0=classes;
                        break;
                    case "Электив":
                        ArrayList<Elective> electives = post.getElectives();
                        // создаём адаптер для выдв. списка с номерами классов
                        data = new String[electives.size()];
                        for (int i=0;i<electives.size();i++) {
                            data[i] = electives.get(i).getAcademicSubject();
                        }
                        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        groups.setAdapter(adapter);
                        // новая инструкция на скрытый label
                        text.setText("Выберите предмет электива");
                        // установка идентификатора
                        selected_category="Электив";
                        electives0=electives;

                        break;
                    case "Секция":
                        ArrayList<Section> sections = post.getSections();
                        // создаём адаптер для выдв. списка с номерами классов
                        data = new String[sections.size()];
                        for (int i=0;i<sections.size();i++) {
                            data[i] = sections.get(i).getName();
                        }
                        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        groups.setAdapter(adapter);
                        // новая инструкция на скрытый label
                        text.setText("Выберите название секции");
                        // установка идентификатора
                        selected_category="Секция";
                        sections0=sections;
                        break;
                }
                // показываем новый блок
                container_group.setVisibility(View.VISIBLE);
                // блокируем доступ к первому выдв. списку
                type.setEnabled(false);
                // блокируем доступ к кнопке ПРОДОЛЖИТЬ1
                v.findViewById(R.id.button_continue1).setEnabled(false);
            }
        });

        // листенер на нажатие ОТМЕНА1 (скрывает блок с названиями групп, открывает доступ к первомы выдв. меню)
        v.findViewById(R.id.button_cancel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner type = v.findViewById(R.id.spinner_group_category);
                Spinner groups = v.findViewById(R.id.spinner_group);
                LinearLayout container_group = v.findViewById(R.id.container_choose_group);

                type.setEnabled(true);
                container_group.setVisibility(View.GONE);
                // даём доступ к кнопке ПРОДОЛЖИТЬ1
                v.findViewById(R.id.button_continue1).setEnabled(true);
            }
        });

        // листенер на нажатие ПРОДОЛЖИТЬ2 (в соответсвии с выбранным названием создаёт строки таблицы с ID и именами учащихся в выбранной группе)
        v.findViewById(R.id.button_continue2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner groups = v.findViewById(R.id.spinner_group);
                TableLayout table = v.findViewById(R.id.table_content);
                ArrayList<String[]> list_for_tabel = new ArrayList<String[]>(); // лист для добавления в таблицу

                // получение
                String selected_name = groups.getSelectedItem().toString();
                switch (selected_category){
                    case "Класс":
                        for (Class i: classes0){
                            if (i.getNumber().equals(selected_name)){
                                selected_index=i.getIndex();
                                class0=i;
                                break;
                            }
                        }
                        // получение списка для ввода в таблицу
                        list_for_tabel = class0.getListLearners();
                        break;
                    case "Электив":
                        for (Elective i: electives0){
                            if (i.getAcademicSubject().equals(selected_name)){
                                selected_index=i.getIndex();
                                elective0=i;
                                break;
                            }
                        }
                        // получение списка для ввода в таблицу
                        list_for_tabel = elective0.getListLearners();
                        break;
                    case "Секция":
                        for (Section i: sections0){
                            if (i.getName().equals(selected_name)){
                                selected_index=i.getIndex();
                                section0=i;
                                break;
                            }
                        }
                        // получение списка для ввода в таблицу
                        list_for_tabel = section0.getListLearners();
                        break;
                }

                // создание таблицы
                // добавляем шапку таблице
                TableRow row = new TableRow(getContext());
                TextView item0 = new TextView(getContext());
                TextView item1 = new TextView(getContext());
                item0.setPadding(5, 0, 5, 0);
                item1.setPadding(5, 0, 5, 0);
                item0.setText("ID");
                row.addView(item0);
                item1.setText("ФИО");
                row.addView(item1);
                table.addView(row);

                for (String[] i : list_for_tabel) {
                    row = new TableRow(getContext());
                    item0 = new TextView(getContext());
                    item1 = new TextView(getContext());
                    item0.setPadding(5, 0, 5, 0);
                    item1.setPadding(5, 0, 5, 0);
                    item0.setText(i[0]);
                    row.addView(item0);
                    item1.setText(i[1]);
                    row.addView(item1);

                    table.addView(row);
                }
                // выключает второй выпад. список и кнопку ПРОДОЛЖИТЬ2
                v.findViewById(R.id.button_continue2).setEnabled(false);
                groups.setEnabled(false);
            }
        });

        // листенер на нажатие ОТМЕНА2 (очищает таблицу и востанавливает доступ к кнопке ПРОДОЛЖИТЬ2)
        v.findViewById(R.id.button_cancel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableLayout table = v.findViewById(R.id.table_content);
                Spinner groups = v.findViewById(R.id.spinner_group);

                table.removeAllViews();
                v.findViewById(R.id.button_continue2).setEnabled(true);
                groups.setEnabled(true);

            }
        });

        return v;
    }
}