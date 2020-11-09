## JPA Example Project

Follow the instructions **point by point**, run the code and **check the results** in the database. Check the states of in-memory objects as well (use the debugger, logging, or `System.out`). Read about all annotations used -> [Hibernate Documentation](http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html)

### Setup

1. Create a PostgreSQL database `jpaexampleDB` and modify DB username and password in `resources/META-INF/persistence.xml`.
2. Start `JPAExample`. `Student` and `Address` are annotated with `@Entity`, so if you check the database you should see two tables created by Hibernate.

### Exercises

#### Constraints and Transient

3. Use the `@Column` annotation to modify the default O-R mapping! Change the column name for attribute `zipcode` to `Zip`, limit its length to 4, and set the `email` field to `UNIQUE` and `NOT NULL`!
4. It is not needed to persist `age` of students since it is calculated from `dateOfBirth` - exclude it from the table by marking it `@Transient`.
5. Why `dateOfBirth` has been annotated with `@Temporal`?
    --> The type Date contains both date and time information. It doesn't directly relate to any SQL type so we need to specify the desired SQL type (TemporalType.DATE, TemporalType.TIME or TemporalType.TIMESTAMP)

#### Relationships

6. Currently students have an address (OneToOne relationship). What happens when an address has a student as well? Set a symmetric `@OneToOne` relationship and see what happens! Student table has `address_id` column and address table has `student_id`. What a waste of memory!
7. We wouldn't want this... Fix the issue by adding a `mappedBy` attribute to the annotation! What has changed?
    --> student_id column has disappeared from the address table

8. Annotate POJO `StudentGroup` and convert it to an entity! The corresponding table should be called `Student_group`. Try to run the example.
9. Fix the issues on your own. Use `@OneToMany` relationship between StudentGroup and Students! What happened to the database?
    --> student table has a new column - studentgroup_id

10. Again, use `mappedBy` to make the association bidirectional! (You have to drop a table manually if it's not needed anymore.)
11. **IMPORTANT!** When using bidirectional relationships it is your responsibility to set the relationships both ways to have the in-memory objects in sync with the database!

#### CascadeType

12. Now try to remove a student group at the end of the program (`em.remove(studentGroupBp2)`). What happened? How to fix it?
    --> ERROR: update or delete on table "student_group" violates foreign key constraint on table "student"
13. Read about `CascadeType`. Use the one that will remove all the students after removing a student group which they belong to.
    --> CascadeType.REMOVE

#### Element Collection

14. Add a `List<String> phoneNumbers` to students, and add it to the constructor! What happened? Why does this cause an error?
    --> 'Basic' attribute type should not be a container error. JPA wants to know what is this list of objects
15. You can fix the problem by `@ElementCollection`. Set the name of the auxiliary table to `Phone`.
16. Why cannot we use OneToMany annotation?
    --> @OneToMany is used to map entities. @ElementCollection is mainly for mapping non-entities

#### Enums

17. Create a new enumerated class called `CCLocation` with values `MISKOLC`, `BUDAPEST`, and `KRAKOW`! Add it as an attribute to `StudentGroup` and set it in its constructor! How does it get represented in the database?
    --> column cclocation with a cell value of 1

Use the `@Enumerated` annotation to change this default behaviour! Locations should be stored as varchars in the db.

#### FetchTypes

18. Uncomment `loadStudentGroup(em);` in `JPAExample` and run the application. Check the generated sql statement.
19. Add `fetch = FetchType.LAZY` to @OneToMany in `StudentGroup`. Run the application again and compare the sql statement.
20. Change the FetchType to EAGER. Check again.
21. Think which fetch type is better? Why?
    --> We generally should use FetchType.LAZY. It is not efficient to load all students together (FetchType.EAGER)