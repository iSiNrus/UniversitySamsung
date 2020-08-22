package ru.mitryashkin.universitysamsung.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import ru.mitryashkin.universitysamsung.models.Group;

@Dao
public interface GroupDAO {

    @Query("Select * from groups")
    LiveData<List<Group>> getAll();

    @Query("Select * from groups")
    List<Group> getAllSync();

    @Query("SELECT * FROM groups WHERE id = :id")
    LiveData<Group> getById(Integer id);

    @Query("SELECT * FROM groups WHERE id = :id")
    Group getByIdSync(Integer id);

    @Insert
    void insert(Group group);

    @Update
    void update(Group group);

    @Delete
    void delete(Group group);

    @Query("DELETE FROM groups")
    void deleteAll();

    @Query("SELECT Count(*) FROM groups")
    int getCount();
}
