## JPA Example Project

Follow the instructions **point by point**, run the code and **check the results** in the database. Check the states of in-memory objects as well (use the debugger, logging, or `System.out`). Read about all annotations used -> [Hibernate Documentation](http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html)

### Setup

1. Create a PostgreSQL database `jpaexampleDB` and modify DB username and password in `resources/META-INF/persistence.xml`.
1. Start `JPAExample`. `Student` and `Address` are annotated with `@Entity`, so if you check the database you should see two tables created by Hibernate.

### Exercises

#### Constraints and Transient

1. Use the `@Column` annotation to modify the default O-R mapping! Change the column name for attribute `zipcode` to `Zip`, limit its length to 4, and set the `email` field to `UNIQUE` and `NOT NULL`!
1. It is not needed to persist `age` of students since it is calculated from `dateOfBirth` - exclude it from the table by marking it `@Transient`.
1. Why `dateOfBirth` has been annotated with `@Temporal`?

#### Relationships

1. Currently students have an address (OneToOne relationship). What happens when an address has a student as well? Set a symmetric `@OneToOne` relationship and see what happens! Student table has `address_id` column and address table has `student_id`. What a waste of memory!
1. We wouldn't want this... Fix the issue by adding a `mappedBy` attribute to the annotation! What has changed?


1. Annotate POJO `StudentGroup` and convert it to an entity! The corresponding table should be called `student_group`. Try to run the example.
1. Fix the issues on your own. Use `@OneToMany` relationship between StudentGroup and Students! What happened to the database?
1. Again, use `mappedBy` to make the association bidirectional! (You have to drop a table manually if it's not needed anymore.)
1. **IMPORTANT!** When using bidirectional relationships it is your responsibility to set the relationships both ways to have the in-memory objects in sync with the database!

#### CascadeType

1. Now try to remove a student group at the end of the program (`em.remove(studentGroupBp2)`). What happened? How to fix it?
1. Read about `CascadeType`. Use the one that will remove all the students after removing a student group which they belong to.

#### Element Collection

1. Add a `List<String> phoneNumbers` to students, and add it to the constructor! What happened? Why does this cause an error?
1. You can fix the problem by `@ElementCollection`. Set the name of the auxiliary table to `Phone`.
1. Why cannot we use OneToMany annotation?

#### Enums

1. Create a new enumerated class called `CCLocation` with values `MISKOLC`, `BUDAPEST`, and `KRAKOW`! Add it as an attribute to `StudentGroup` and set it in its constructor! How does it get represented in the database? Use the `@Enumerated` annotation to change this default behaviour! Locations should be stored as varchars in the db.

#### FetchTypes

1. Uncomment `loadStudentGroup(em);` in `JPAExample` and run the application. Check the generated sql statement.
1. Add `fetch = FetchType.LAZY` to @OneToMany in `StudentGroup`. Run the application again and compare the sql statement.
1. Change the FetchType to EAGER. Check again.
1. Think which fetch type is better? Why?