package com.example.e_journal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "school.db"; // название бд
    private static final int VERSION = 1; // версия базы данных
    // переменные для Participants
//    static final String PARTICIPANTS_TNAME = "participants";
//    static final String PARTICIPANTS_C_NAME = "name";
//    static final String PARTICIPANTS_C_PHONE = "phone";
//    static final String PARTICIPANTS_C_ID = "id";
//    // переменные для Learner
//    static final String LEARNER_TNAME = "participants";
//    static final String LEARNER_C_NAME = "name";
//    static final String LEARNER_C_PHONE = "phone";
//    static final String LEARNER_C_ID = "id";
//    // переменные для Teacher
//    static final String TEACHER_TNAME = "participants";
//    static final String TEACHER_C_NAME = "name";
//    static final String TEACHER_C_PHONE = "phone";
//    static final String TEACHER_C_ID = "id";
//    // переменные для Employee
//    static final String EMPLOYEE_TNAME = "participants";
//    static final String EMPLOYEE_C_NAME = "name";
//    static final String EMPLOYEE_C_PHONE = "phone";
//    static final String EMPLOYEE_C_ID = "id";


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
