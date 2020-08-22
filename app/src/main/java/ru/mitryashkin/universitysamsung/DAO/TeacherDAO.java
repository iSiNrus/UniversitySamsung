package ru.mitryashkin.universitysamsung.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.mitryashkin.universitysamsung.models.Teacher;

@Dao
public interface TeacherDAO {

    @Query("SELECT * FROM teachers ORDER BY first_name DESC")
    LiveData<List<Teacher>> getAll();

    @Query("Select * from teachers where id = :id")
    LiveData<Teacher> getById(int id);

    @Query("SELECT * FROM TEACHERS ORDER BY first_name DESC")
    List<Teacher> getAllSync();

    @Insert
    void insert(Teacher teacher);

    @Update
    void update(Teacher teacher);

    @Delete
    void delete(Teacher teacher);

    @Query("DELETE FROM teachers")
    void deleteAll();

    @Query("Select * from teachers where id = :id")
    Teacher getByIdSync(int id);
}
