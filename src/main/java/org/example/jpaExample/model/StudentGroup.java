package org.example.jpaExample.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Student_group")
public class StudentGroup {

    private String name;

    @OneToMany(mappedBy = "studentGroup", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private CCLocation ccLocation;

    public StudentGroup() {
    }

    public StudentGroup(String name, CCLocation ccLocation) {
        this.name = name;
        this.ccLocation = ccLocation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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
