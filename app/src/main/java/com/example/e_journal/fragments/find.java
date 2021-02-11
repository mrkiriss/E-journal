package com.example.e_journal.fragments;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.e_journal.R;
import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.interfaces.FindPostman;
import com.example.e_journal.school.Employee;
import com.example.e_journal.school.Learner;
import com.example.e_journal.school.School;
import com.example.e_journal.school.Teacher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link find#newInstance} factory method to
 * create an instance of this fragment.
 */
public class find extends Fragment {

    // глобальные переменные
    private School school;

    // создание ссылку на главное Activity и трансформируем экземпляр интерфейса в ссылку на Activity
    private FindPostman post;
    private Activity activity;
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        if (c instanceof  Activity){
            activity=(Activity) c;
        }
        post= (FindPostman) activity;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public find() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment find.
     */
    // TODO: Rename and change types and number of parameters
    public static find newInstance(String param1, String param2) {
        find fragment = new find();
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
        View v =  inflater.inflate(R.layout.fragment_find, container, false);

        // получение актуального экземпляра класса School
        school = post.getSchool();

        // создание объектов необходимых элементов интерфейса
        Spinner spinner_parameter = v.findViewById(R.id.spinner_parameter);
        Button button_find_person = v.findViewById(R.id.button_find_person);
        EditText edit_value = v.findViewById(R.id.textedit_find);
        TableLayout table = v.findViewById(R.id.table_found);

        table.setGravity(Gravity.BOTTOM);

        // слушатель на кнопку НАЧАТЬ ПОИСК (парсит из вып. списка параметр для поиска, проводит поиск, добавляет все найденные персоны в таблицу)
        button_find_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // очищает пред. результаты
                table.removeAllViews();
                // парс параметра поиска
                String selected_parameter = spinner_parameter.getSelectedItem().toString();
                // парс введённого значения для поиска
                String value = edit_value.getText().toString();

                // поиск учеников
                for (Learner i: school.getLearners()){
                    // строка, которая будет занесена в таблицу в случае своей непустоты
                    String for_adding="";

                    switch (selected_parameter){
                        case "ФИО":
                            if (i.getFullName().equals(value)) for_adding=i.toString();
                            break;
                        case "ID":
                            if (i.getCardID().equals(value)) for_adding=i.toString();
                            break;
                        case "Номер телефона":
                            if (i.getPhone().equals(value)) for_adding=i.toString();
                            break;
                    }
                    // добавляем элемент при его наличии
                    if (!for_adding.equals("")){
                        TableRow row = new TableRow(getContext());

                        ImageView item0 = new ImageView(getContext());
                        item0.setImageResource(R.drawable.ic_learner);
                        item0.setPadding(5,5,5,5);
                        row.addView(item0);

                        TextView item1 = new TextView(getContext());
                        item1.setPadding(5,5,5,5);
                        item1.setText(for_adding);
                        row.addView(item1);

                        table.addView(row);
                    }
                }

                // поиск учителей
                for (Teacher i: school.getTeachers()){
                    // строка, которая будет занесена в таблицу в случае своей непустоты
                    String for_adding="";

                    switch (selected_parameter){
                        case "ФИО":
                            if (i.getFullName().equals(value)) for_adding=i.toString();
                            break;
                        case "ID":
                            if (i.getCardID().equals(value)) for_adding=i.toString();
                            break;
                        case "Номер телефона":
                            if (i.getPhone().equals(value)) for_adding=i.toString();
                            break;
                    }
                    // добавляем элемент при его наличии
                    if (!for_adding.equals("")){
                        TableRow row = new TableRow(getContext());

                        ImageView item0 = new ImageView(getContext());
                        item0.setImageResource(R.drawable.ic_teacher);
                        item0.setPadding(5,5,5,5);
                        row.addView(item0);

                        TextView item1 = new TextView(getContext());
                        item1.setText(for_adding);
                        item1.setPadding(5,5,5,5);
                        row.addView(item1);

                        table.addView(row);
                    }
                }

                // поиск работников
                for (Employee i: school.getEmployees()){
                    // строка, которая будет занесена в таблицу в случае своей непустоты
                    String for_adding="";

                    switch (selected_parameter){
                        case "ФИО":
                            if (i.getFullName().equals(value)) for_adding=i.toString();
                            break;
                        case "ID":
                            if (i.getCardID().equals(value)) for_adding=i.toString();
                            break;
                        case "Номер телефона":
                            if (i.getPhone().equals(value)) for_adding=i.toString();
                            break;
                    }
                    // добавляем элемент при его наличии
                    if (!for_adding.equals("")){
                        TableRow row = new TableRow(getContext());

                        ImageView item0 = new ImageView(getContext());
                        item0.setImageResource(R.drawable.ic_employee);
                        item0.setPadding(5,5,5,5);
                        row.addView(item0);

                        TextView item1 = new TextView(getContext());
                        item1.setText(for_adding);
                        item1.setPadding(5,5,5,5);
                        row.addView(item1);

                        table.addView(row);
                    }
                }

            }
        });

        return v;
    }
}