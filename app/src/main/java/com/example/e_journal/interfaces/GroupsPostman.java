package com.example.e_journal.interfaces;

import com.example.e_journal.school.Class;
import com.example.e_journal.school.Elective;
import com.example.e_journal.school.Section;

import java.util.ArrayList;

public interface GroupsPostman {
    public ArrayList<Class> getClasses();
    public ArrayList<Elective> getElectives();
    public ArrayList<Section> getSections();
    public void startEditActivity(String selected_category, int selected_index);
}
