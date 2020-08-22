package ru.mitryashkin.universitysamsung.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;

import java.io.Serializable;

import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName = "groups")
public class Group implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "group_name")
    private String GroupName;
    @ColumnInfo(name = "department")
    private String Department;
    @ColumnInfo(name = "speciality")
    private String Speciality;
    @ColumnInfo(name = "kurator_id")
    private int KuratorID;

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public Group() {
        GroupName = "DefaultName";
        Department = "DefaultDepartment";
        Speciality = "Speciality";
        KuratorID = -1;
    }

    public Group(String groupName, String speciality, String department, int kuratorID) {
        GroupName = groupName;
        Speciality = speciality;
        Department = department;
        KuratorID = kuratorID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getKuratorID() {
        return KuratorID;
    }

    public void setKuratorID(int kuratorID) {
        KuratorID = kuratorID;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Group gr = (Group) obj;
        return gr.getID() == this.getID();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getGroupName();
    }
}
