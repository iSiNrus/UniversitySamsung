package ru.mitryashkin.universitysamsung.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mitryashkin.universitysamsung.DAO.GroupDAO;
import ru.mitryashkin.universitysamsung.DB.UniversityDatabase;
import ru.mitryashkin.universitysamsung.models.Group;

public class GroupRepository {

    private GroupDAO groupDAO;
    private LiveData<List<Group>> allGroups;

    public GroupRepository(Application application)
    {
        UniversityDatabase db = UniversityDatabase.getInstance(application);
        groupDAO = db.groupDAO();
        allGroups = groupDAO.getAll();
    }

    public void insert(Group group)
    {
        new InsertGroupAsyncTask(groupDAO).execute(group);
    }
    public void update(Group group)
    {
        new UpdateGroupAsyncTask(groupDAO).execute(group);
    }
    public void delete(Group group)
    {
        new DeleteGroupAsyncTask(groupDAO).execute(group);
    }
    public void deleteAll()
    {
        new DeleteAllGroupAsyncTask(groupDAO).execute();
    }
    public LiveData<Group> getById(int groupId){
        return groupDAO.getById(groupId);
    }

    public LiveData<List<Group>> getAll(){
        return allGroups;
    }

    public Group getByIdSync(int grId) {
        return groupDAO.getByIdSync(grId);
    }

    public List<Group> getAllSync() {
        return groupDAO.getAllSync();
    }

    private static class InsertGroupAsyncTask extends AsyncTask<Group, Void, Void>{
        private GroupDAO groupDAO;

        public InsertGroupAsyncTask(GroupDAO groupDAO) {
            this.groupDAO = groupDAO;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDAO.insert(groups[0]);
            return null;
        }
    }

    private static class UpdateGroupAsyncTask extends AsyncTask<Group, Void, Void>{
        private GroupDAO groupDAO;

        public UpdateGroupAsyncTask(GroupDAO groupDAO) {
            this.groupDAO = groupDAO;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDAO.update(groups[0]);
            return null;
        }
    }

    private static class DeleteGroupAsyncTask extends AsyncTask<Group, Void, Void>{
        private GroupDAO groupDAO;

        public DeleteGroupAsyncTask(GroupDAO groupDAO) {
            this.groupDAO = groupDAO;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDAO.delete(groups[0]);
            return null;
        }
    }

    private static class DeleteAllGroupAsyncTask extends AsyncTask<Void, Void, Void>{
        private GroupDAO groupDAO;

        public DeleteAllGroupAsyncTask(GroupDAO groupDAO) {
            this.groupDAO = groupDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            groupDAO.deleteAll();
            return null;
        }
    }
}
