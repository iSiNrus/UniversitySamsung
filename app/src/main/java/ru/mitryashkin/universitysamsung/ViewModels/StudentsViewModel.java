package ru.mitryashkin.universitysamsung.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mitryashkin.universitysamsung.models.Student;
import ru.mitryashkin.universitysamsung.models.Teacher;
import ru.mitryashkin.universitysamsung.repos.StudentRepository;

public class StudentsViewModel extends AndroidViewModel {

    private StudentRepository repository;
    private LiveData<List<Student>> allStudents;
    
    public StudentsViewModel(@NonNull Application application)
    {
        super(application);
        repository = new StudentRepository(application);
        allStudents = repository.getAll();
    }

    public void insert(Student student)
    {
        repository.insert(student);
    }

    public void update(Student student)
    {
        repository.update(student);
    }

    public void delete(Student student)
    {
        repository.delete(student);
    }

    public void deleteAll()
    {
        repository.deleteAll();
    }

    public LiveData<List<Student>> getAllStudents()
    {
        return repository.getAll();
    }

    public LiveData<Student> getById(int studentID)
    {
        return repository.getById(studentID);
    }

}
