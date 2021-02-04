package com.example.e_journal.interfaces;

import com.example.e_journal.school.Class;
import com.example.e_journal.school.Elective;
import com.example.e_journal.school.Employee;
import com.example.e_journal.school.Learner;
import com.example.e_journal.school.Section;
import com.example.e_journal.school.Teacher;

public interface AddingPostman {
    public void fragmentMail(Learner x);
    public void fragmentMail(Teacher x);
    public void fragmentMail(Employee x);
    public void fragmentMail(Class x);
    public void fragmentMail(Elective x);
    public void fragmentMail(Section x);
    public int getCountClasses();
    public int getCountElectives();
    public int getCountSections();
}
