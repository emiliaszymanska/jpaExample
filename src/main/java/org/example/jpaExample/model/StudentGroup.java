package org.example.jpaExample.model;

import java.util.HashSet;
import java.util.Set;

public class StudentGroup {

    private String name;
    private Set<Student> students = new HashSet<>();

    public StudentGroup() {
    }

    public StudentGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }
}
