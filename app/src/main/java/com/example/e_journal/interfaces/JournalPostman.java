package com.example.e_journal.interfaces;

import com.example.e_journal.school.Class;
import com.example.e_journal.school.School;

public interface JournalPostman {
    public School getSchool();
    public void updateClasses(Class c);
}
