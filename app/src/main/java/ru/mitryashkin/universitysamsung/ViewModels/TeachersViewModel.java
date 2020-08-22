package ru.mitryashkin.universitysamsung.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mitryashkin.universitysamsung.models.Teacher;
import ru.mitryashkin.universitysamsung.repos.TeacherRepository;

public class TeachersViewModel extends AndroidViewModel {

    private TeacherRepository repository;
    private LiveData<List<Teacher>> allTeachers;

    public TeachersViewModel(@NonNull Application application) {
        super(application);
        repository = new TeacherRepository(application);
        allTeachers = repository.getAll();
    }

    public void insert(Teacher teacher)
    {
        repository.insert(teacher);
    }

    public void update(Teacher teacher)
    {
        repository.update(teacher);
    }

    public void delete(Teacher teacher)
    {
        repository.delete(teacher);
    }

    public void deleteAll()
    {
        repository.deleteAll();
    }

    public LiveData<List<Teacher>> getAllTeachers()
    {
        return repository.getAll();
    }

    public LiveData<Teacher> getById(int teacherID)
    {
        return repository.getById(teacherID);
    }
}
