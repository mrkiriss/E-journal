package com.example.e_journal.fragments.documents;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.e_journal.R;
import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.interfaces.DocumentsPostman;
import com.example.e_journal.school.Class;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DocumentsFragment extends Fragment {

    // создание ссылку на главное Activity и трансформируем экземпляр интерфейса в ссылку на Activity
    private DocumentsPostman post;
    private Activity activity;
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        if (c instanceof  Activity){
            activity=(Activity) c;
        }
        post= (DocumentsPostman) activity;
    }
    // для хранения в общей области видимости
    ArrayList<Class> classes0;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GalleryViewModel.class);
        View v = inflater.inflate(R.layout.fragment_documents, container, false);

        // создаём объекты необходимых элементов
        Button gen = v.findViewById(R.id.button_generate);
        Spinner spin = v.findViewById(R.id.spinner_documents);
        TableLayout table = v.findViewById(R.id.table_documents);
        Spinner spinner_classes = v.findViewById(R.id.spinner_classes);

        // слушатель на spin, при выборе пунка с классами должна появлятся доп. область для выбора номера класса
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // стирание старой таблицы при смене выбраного элемента
                table.removeAllViews();

                if (position!=3){ // пропускать всё, кроме последнего пункта
                    v.findViewById(R.id.container_choose_class).setVisibility(View.GONE);
                    return;
                }

                classes0 = post.getClasses();
                // создаём адаптер для выдв. списка с номерами классов
                String[] data = new String[classes0.size()];
                for (int i=0;i<classes0.size();i++) {
                    data[i] = classes0.get(i).getNumber();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_classes.setAdapter(adapter);

                v.findViewById(R.id.container_choose_class).setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // слушатель на кнопку ОЧИСТИТЬ,стирает таблицу и скрывает доп. блок
        v.findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.removeAllViews();
                v.findViewById(R.id.container_choose_class).setVisibility(View.GONE);
                spinner_classes.setSelection(0);
            }
        });

        // добавление слушателя для кнопки СГЕНЕРИРОВАТЬ ДОКУМЕНТ
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = spin.getSelectedItem().toString();
                table.removeAllViews();
                ArrayList<String[]> list= new ArrayList<String[]>();

                TableRow row = new TableRow(getContext());
                TextView item0 = new TextView(getContext());
                TextView item1 = new TextView(getContext());
                item0.setPadding(5, 0, 5, 0);
                item1.setPadding(5, 0, 5, 0);
                row.setPadding(0, 2, 0, 2);

                switch (id) {
                    case "Список преподавателей с указанием квалификации":
                        // добавляем шапку таблице
                        row = new TableRow(getContext());
                        item0.setText("ФИО");
                        row.addView(item0);
                        item1.setText("Квалификация");
                        row.addView(item1);

                        table.addView(row);

                        list = post.getTeachers();

                        for (String[] i : list) {

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
                        break;
                    case "Список школьников с указанием возраста":
                        // добавляем шапку таблице
                        row = new TableRow(getContext());
                        item0 = new TextView(getContext());
                        item1 = new TextView(getContext());
                        item0.setPadding(5, 0, 5, 0);
                        item1.setPadding(5, 0, 5, 0);
                        item0.setText("ФИО");
                        row.addView(item0);
                        item1.setText("Возраст");
                        row.addView(item1);
                        table.addView(row);

                        list = post.getLearners();

                        for (String[] i : list) {
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
                        break;
                    case "Список всех людей, имеющих доступ в школу":
                        // добавляем шапку таблице
                        row = new TableRow(getContext());
                        item0 = new TextView(getContext());
                        item1 = new TextView(getContext());
                        item0.setPadding(5, 0, 5, 0);
                        item1.setPadding(5, 0, 5, 0);
                        item0.setText("ID");
                        row.addView(item0);
                        item1.setText("ФИО");
                        row.addView(item1);
                        table.addView(row);

                        list = post.getParticipants();

                        for (String[] i : list) {
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
                        break;
                    case "Список учеников класса вместе с родителями":
                        // получение экземляра класса, который был выбран
                        String selected_name = spinner_classes.getSelectedItem().toString();
                        Class c = null;
                        for (Class i: classes0){
                            if (i.getNumber().equals(selected_name)){
                                c=i;
                                break;
                            }
                        }
                        // формирование списка, которым будет заполнена таблица
                        list = c.getLearnersAndParents();
                        // создание таблицы
                        // добавляем шапку таблице
                        row = new TableRow(getContext());
                        item0 = new TextView(getContext());
                        item1 = new TextView(getContext());
                        TextView item2 = new TextView(getContext());
                        item0.setPadding(5, 0, 5, 0);
                        item1.setPadding(5, 0, 5, 0);
                        item2.setPadding(5, 0, 5, 0);
                        item0.setText("ФИО ученика");
                        row.addView(item0);
                        item1.setText("ФИО родителя1");
                        row.addView(item1);
                        item2.setText("ФИО родителя2");
                        row.addView(item2);
                        table.addView(row);

                        for (String[] i : list) {
                            row = new TableRow(getContext());
                            item0 = new TextView(getContext());
                            item1 = new TextView(getContext());
                            item2 = new TextView(getContext());
                            item0.setPadding(5, 0, 5, 0);
                            item1.setPadding(5, 0, 5, 0);
                            item2.setPadding(5, 0, 5, 0);
                            item0.setText(i[0]);
                            row.addView(item0);
                            item1.setText(i[1]);
                            row.addView(item1);
                            item2.setText(i[2]);
                            row.addView(item2);

                            table.addView(row);
                        }

                        break;

                }
            }
        });
        return v;
    }
}