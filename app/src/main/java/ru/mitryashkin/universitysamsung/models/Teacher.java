package ru.mitryashkin.universitysamsung.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "teachers")
public class Teacher {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int ID;

    @ColumnInfo(name = "first_name")
    private String FirstName;
    @ColumnInfo(name = "last_name")
    private String LastName;
    @ColumnInfo(name = "middle_name")
    private String MiddleName;
    @ColumnInfo(name = "birthday")
    private Date Birthday;
    @ColumnInfo(name = "grade")
    private String Grade;

    public Teacher(String firstName, String lastName, String middleName, Date birthday, String grade) {
        FirstName = firstName;
        LastName = lastName;
        MiddleName = middleName;
        Birthday = birthday;
        Grade = grade;
    }

    public Teacher() {
        this.FirstName = "Conor";
        this.LastName = "John";
        this.MiddleName = "Kayle";
        this.Birthday = new Date();
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

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    @NonNull
    @Override
    public String toString() {
        return (Grade!=null? Grade : "")  + " " + LastName +" " + FirstName.toUpperCase().toCharArray()[0] + "."+
                MiddleName.toUpperCase().toCharArray()[0] + ".";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Teacher t = (Teacher) obj;
        return t.ID == this.ID;
    }

    public String getFullName() {
        return this.getLastName() + " " + this.getFirstName() + " " + this.getMiddleName();
    }
}
