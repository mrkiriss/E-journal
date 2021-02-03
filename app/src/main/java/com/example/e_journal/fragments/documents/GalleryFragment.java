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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.e_journal.R;
import com.example.e_journal.interfaces.AddingPostman;
import com.example.e_journal.interfaces.DocumentsPostman;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

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
                        item0.setText("ФИО");
                        row.addView(item0);
                        item1.setText("Возраст");
                        row.addView(item1);
                        table.addView(row);

                        list = post.getLearners();

                        for (String[] i : list) {
                            row = new TableRow(getContext());
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
                        item0.setText("ID");
                        row.addView(item0);
                        item1.setText("ФИО");
                        row.addView(item1);
                        table.addView(row);

                        list = post.getParticipants();

                        for (String[] i : list) {
                            row = new TableRow(getContext());
                            item0.setText(i[0]);
                            row.addView(item0);
                            item1.setText(i[1]);
                            row.addView(item1);

                            table.addView(row);
                        }
                        break;
                    case "Список учеников класса вместе с родителями":
                        /* прописать обработку нового выдв. меню: поиск классов по названию для вычисления индекса класса, чтобы дальше работать именно с индексом класса */
                        break;

                }
            }
        });
        return v;
    }
}