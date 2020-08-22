package ru.mitryashkin.universitysamsung.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.PrimaryKey;

import java.util.List;

import ru.mitryashkin.universitysamsung.models.Group;
import ru.mitryashkin.universitysamsung.models.Teacher;
import ru.mitryashkin.universitysamsung.repos.GroupRepository;

public class GroupsViewModel extends AndroidViewModel {
    
    private GroupRepository repository;
    private LiveData<List<Group>> allGroups;

    public GroupsViewModel(@NonNull Application application) {
        super(application);
        repository = new GroupRepository(application);
        allGroups = repository.getAll();
    }

    public void insert(Group group)
    {
        repository.insert(group);
    }

    public void update(Group group)
    {
        repository.update(group);
    }

    public void delete(Group group)
    {
        repository.delete(group);
    }

    public void deleteAll()
    {
        repository.deleteAll();
    }

    public LiveData<List<Group>> getAllGroups()
    {
        return repository.getAll();
    }

    public LiveData<Group> getById(int groupId)
    {
        return repository.getById(groupId);
    }
}
