package com.example.e_journal.interfaces;

import com.example.e_journal.school.Class;
import com.example.e_journal.school.Teacher;

import java.util.ArrayList;

public interface DocumentsPostman {
    public ArrayList<String[]> getTeachers();
    public ArrayList<String[]> getLearners();
    public ArrayList<String[]> getParticipants();
    public ArrayList<String[]> getLearnersAndParents(int number);
    public ArrayList<Class> getClasses();
}
