package com.example.e_journal.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.R;
import com.example.e_journal.school.Class;
import com.example.e_journal.school.Elective;
import com.example.e_journal.school.Employee;
import com.example.e_journal.school.Learner;
import com.example.e_journal.school.Parent;
import com.example.e_journal.school.Section;
import com.example.e_journal.school.Teacher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link adding#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adding extends Fragment {

    // создание ссылку на главное Activity и трансформируем экземпляр интерфейса в ссылку на Activity
    private AddingPostman post;
    private Activity activity;
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        if (c instanceof  Activity){
            activity=(Activity) c;
        }
        post= (AddingPostman) activity;
    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public adding() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment adding.
     */
    // TODO: Rename and change types and number of parameters
    public static adding newInstance(String param1, String param2) {
        adding fragment = new adding();
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
        View v = inflater.inflate(R.layout.fragment_adding, container, false);
        // создаём объекты необходимых элементов
        Spinner sp = v.findViewById(R.id.spinner);
        TextView fullName = v.findViewById(R.id.text_fullName);
        TextView phone = v.findViewById(R.id.text_phone);
        TextView cardID = v.findViewById(R.id.text_cardID);
        TextView age = v.findViewById(R.id.text_age);
        TextView position= v.findViewById(R.id.text_position);
        TextView qualification= v.findViewById(R.id.text_qualification);
        View parents = v.findViewById(R.id.container_parents);
        TextView parent_fio1= v.findViewById(R.id.text_parent_fio1);
        TextView parent_phone1 = v.findViewById(R.id.text_parent_phone1);
        TextView parent_fio2 = v.findViewById(R.id.text_parent_fio2);
        TextView parent_phone2 = v.findViewById(R.id.text_parent_phone2);

        //добавление слушателя для кнопки ПРОДОЛЖИТЬ
        v.findViewById(R.id.button_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = sp.getSelectedItem().toString();

                switch(id){
                    case "Ученик":
                        //System.out.println("Гусь");
                        fullName.setVisibility(View.VISIBLE);
                        fullName.setHint("ФИО");
                        phone.setVisibility(View.VISIBLE);
                        cardID.setVisibility(View.VISIBLE);
                        age.setVisibility(View.VISIBLE);
                        View parents=v.findViewById(R.id.container_parents);
                        parents.setVisibility(View.VISIBLE);
                        break;
                    case "Учитель":
                        fullName.setVisibility(View.VISIBLE);
                        phone.setVisibility(View.VISIBLE);
                        cardID.setVisibility(View.VISIBLE);
                        position.setVisibility(View.VISIBLE);
                        qualification.setVisibility(View.VISIBLE);
                        break;
                    case "Работник":
                        fullName.setVisibility(View.VISIBLE);
                        phone.setVisibility(View.VISIBLE);
                        cardID.setVisibility(View.VISIBLE);
                        position.setVisibility(View.VISIBLE);
                        break;
                    case "Класс":
                        fullName.setVisibility(View.VISIBLE);
                        fullName.setHint("Номер класса");
                        break;
                    case "Электив":
                        fullName.setVisibility(View.VISIBLE);
                        fullName.setHint("Изучаемый предмет");
                        break;
                    case "Секция":
                        fullName.setVisibility(View.VISIBLE);
                        fullName.setHint("Название секции");
                        break;
                }

                // выключить выдвиг. меню и кнопку
                v.findViewById(R.id.spinner).setEnabled(false);
                v.findViewById(R.id.button_continue).setEnabled(false);
                // включить кнопки ПОДТВЕРДИТЬ и ОТМЕНА
                v.findViewById(R.id.button_cancel).setEnabled(true);
                v.findViewById(R.id.button_accept).setEnabled(true);
            }
        });

        // добавление слушателя для кнопки ПОДТВЕРДИТЬ
        v.findViewById(R.id.button_accept).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String id=sp.getSelectedItem().toString();

                switch(id){
                    case "Ученик":
                        // проверка на заполнение всех полей
                        if (fullName.getText().toString().equals("") || phone.getText().toString().equals("") ||cardID.getText().toString().equals("") ||age.getText().toString().equals("") ||
                                parent_fio1.getText().toString().equals("") ||parent_phone1.getText().toString().equals("") ||parent_fio2.getText().toString().equals("") ||parent_phone2.getText().toString().equals("")){
                            Toast.makeText(getContext(), "Не все поля заполнены. Ученик не был добавлен", Toast.LENGTH_SHORT).show();
                        }else {
                            Parent p1 = new Parent(parent_fio1.getText().toString(), parent_phone1.getText().toString());
                            Parent p2 = new Parent(parent_fio2.getText().toString(), parent_phone2.getText().toString());
                            ArrayList<Parent> p = new ArrayList<Parent>();
                            p.add(p1);
                            p.add(p2);
                            Learner l = new Learner(fullName.getText().toString(), phone.getText().toString(), cardID.getText().toString(), p, age.getText().toString());
                            // отправка созданного экземляра в MainActivity
                            post.fragmentMail(l);
                        }
                        fullName.setVisibility(View.GONE);
                        phone.setVisibility(View.GONE);
                        cardID.setVisibility(View.GONE);
                        age.setVisibility(View.GONE);
                        View parents=v.findViewById(R.id.container_parents);
                        parents.setVisibility(View.GONE);

                        break;
                    case "Учитель":
                        // проверка на заполнение всех полей
                        if (fullName.getText().toString().equals("") || phone.getText().toString().equals("") ||cardID.getText().toString().equals("")  ||
                                position.getText().toString().equals("") ||qualification.getText().toString().equals("")){
                            Toast.makeText(getContext(), "Не все поля заполнены. Учитель не был добавлен", Toast.LENGTH_SHORT).show();
                        }else {
                            Teacher t = new Teacher(fullName.getText().toString(), phone.getText().toString(), cardID.getText().toString(), position.getText().toString(), qualification.getText().toString());
                            // отправка созданного экземляра в MainActivity
                            post.fragmentMail(t);
                        }
                        fullName.setVisibility(View.GONE);
                        phone.setVisibility(View.GONE);
                        cardID.setVisibility(View.GONE);
                        position.setVisibility(View.GONE);
                        qualification.setVisibility(View.GONE);
                        break;
                    case "Работник":
                        // проверка на заполнение всех полей
                        if (fullName.getText().toString().equals("") || phone.getText().toString().equals("") ||cardID.getText().toString().equals("")  ||
                                position.getText().toString().equals("")){
                            Toast.makeText(getContext(), "Не все поля заполнены. Работник не был добавлен", Toast.LENGTH_SHORT).show();
                        }else {
                            Employee e = new Employee(fullName.getText().toString(), phone.getText().toString(), cardID.getText().toString(), position.getText().toString());
                            // отправка созданного экземляра в MainActivity
                            post.fragmentMail(e);
                        }
                        fullName.setVisibility(View.GONE);
                        phone.setVisibility(View.GONE);
                        cardID.setVisibility(View.GONE);
                        position.setVisibility(View.GONE);
                    case "Класс":
                        // проверка на заполнение всех полей
                        if (fullName.getText().toString().equals("") ){
                            Toast.makeText(getContext(), "Не все поля заполнены. Класс не был добавлен", Toast.LENGTH_SHORT).show();
                        }else {
                            Class c = new Class(fullName.getText().toString());
                            c.setIndex(post.getCountClasses());
                            // отправка созданного экземляра в MainActivity
                            post.fragmentMail(c);
                        }
                        fullName.setVisibility(View.GONE);
                        break;
                    case "Электив":
                        // проверка на заполнение всех полей
                        if (fullName.getText().toString().equals("") ){
                            Toast.makeText(getContext(), "Не все поля заполнены. Электив не был добавлен", Toast.LENGTH_SHORT).show();
                        }else {
                            Elective el = new Elective(fullName.getText().toString());
                            el.setIndex(post.getCountElectives());
                            // отправка созданного экземляра в MainActivity
                            post.fragmentMail(el);
                        }
                        fullName.setVisibility(View.GONE);
                        break;
                    case "Секция":
                        // проверка на заполнение всех полей
                        if (fullName.getText().toString().equals("") ){
                            Toast.makeText(getContext(), "Не все поля заполнены. Секция не былы добавлена", Toast.LENGTH_SHORT).show();
                        }else {
                            Section s = new Section(fullName.getText().toString());
                            s.setIndex(post.getCountSections());
                            // отправка созданного экземляра в MainActivity
                            post.fragmentMail(s);
                        }
                        fullName.setVisibility(View.GONE);
                        break;
                }

                // включить выдвиг. меню и кнопку
                v.findViewById(R.id.spinner).setEnabled(true);
                v.findViewById(R.id.button_continue).setEnabled(true);
                // очистка всех лементов от текста
                fullName.setText("");
                phone.setText("");
                cardID.setText("");
                age.setText("");
                position.setText("");
                qualification.setText("");
                parent_fio1.setText("");
                parent_phone1.setText("");
                parent_fio2.setText("");
                parent_phone2.setText("");
                // выключить кнопки ПОДТВЕРДИТЬ и ОТМЕНА
                v.findViewById(R.id.button_cancel).setEnabled(false);
                v.findViewById(R.id.button_accept).setEnabled(false);
            }
        });

        // добавление слушателя для кнопки ОТМЕНА
        v.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // скрыть все поля ввода текста
                fullName.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
                cardID.setVisibility(View.GONE);
                age.setVisibility(View.GONE);
                position.setVisibility(View.GONE);
                qualification.setVisibility(View.GONE);
                parents.setVisibility(View.GONE);
                // очистка всех лементов от текста
                fullName.setText("");
                phone.setText("");
                cardID.setText("");
                age.setText("");
                position.setText("");
                qualification.setText("");
                parent_fio1.setText("");
                parent_phone1.setText("");
                parent_fio2.setText("");
                parent_phone2.setText("");
                // включить выдвиг. меню и кнопку
                v.findViewById(R.id.spinner).setEnabled(true);
                v.findViewById(R.id.button_continue).setEnabled(true);
                // выключить кнопки ПОДТВЕРДИТЬ и ОТМЕНА
                v.findViewById(R.id.button_cancel).setEnabled(false);
                v.findViewById(R.id.button_accept).setEnabled(false);
            }
        });
        return v;
    }
}