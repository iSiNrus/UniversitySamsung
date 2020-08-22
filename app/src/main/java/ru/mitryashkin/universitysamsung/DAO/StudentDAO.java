package ru.mitryashkin.universitysamsung.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.mitryashkin.universitysamsung.models.Student;

@Dao
public interface StudentDAO {
    @Query("SELECT * FROM students ORDER BY first_name DESC")
    LiveData<List<Student>> getAll();

    @Query("Select * FROM students where students.id = :id")
    LiveData<Student> getById(int id);

    @Query("SELECT * FROM students ORDER BY first_name DESC")
    List<Student> getAllSync();

    @Query("Select * FROM students where students.id = :id")
    Student getByIdSync(int id);

    @Insert
    void insert(Student stud);

    @Update
    void update(Student stud);

    @Delete
    void delete(Student stud);

    @Query("DELETE FROM students")
    void deleteAll();
}
