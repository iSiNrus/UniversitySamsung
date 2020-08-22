package ru.mitryashkin.universitysamsung.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;

@Entity(tableName = "students")
public class Student{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int ID;

    @ColumnInfo(name = "grade_book_num")
    private int GradeBookNumber;
    @ColumnInfo(name = "first_name")
    private String FirstName;
    @ColumnInfo(name = "last_name")
    private String LastName;
    @ColumnInfo(name = "middle_name")
    private String MiddleName;
    @ColumnInfo(name = "birthday")
    private Date Birthday;
    @ColumnInfo(name = "group_id")
    private int GroupID;

    public Student() {
        FirstName = "Vasya";
        LastName = "Pupkin";
        MiddleName = "Studenovich";
        Birthday = new Date();
        this.GroupID = -1;
    }

    public Student(int gradeBookNumber, String firstName, String lastName, String middleName, Date birthday, int groupID) {
        this.GradeBookNumber = gradeBookNumber;
        FirstName = firstName;
        LastName = lastName;
        MiddleName = middleName;
        Birthday = birthday;
        this.GroupID = groupID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public int getGradeBookNumber() {
        return GradeBookNumber;
    }

    public void setGradeBookNumber(int gradeBookNumber) {
        GradeBookNumber = gradeBookNumber;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    
    public String getFullName()
    {
        return this.getLastName() + " " + this.getFirstName() + " " + this.getMiddleName();
    }
}
