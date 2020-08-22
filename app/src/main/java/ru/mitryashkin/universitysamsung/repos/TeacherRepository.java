package ru.mitryashkin.universitysamsung.repos;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.PrimaryKey;

import java.util.List;

import ru.mitryashkin.universitysamsung.DAO.TeacherDAO;
import ru.mitryashkin.universitysamsung.DB.UniversityDatabase;
import ru.mitryashkin.universitysamsung.models.Teacher;

public class TeacherRepository {

    private TeacherDAO teacherDAO;
    private LiveData<List<Teacher>> allTeachers;

    public TeacherRepository(Application application)
    {
        UniversityDatabase db = UniversityDatabase.getInstance(application);
        teacherDAO = db.teacherDAO();
        allTeachers = teacherDAO.getAll();
    }

    public void insert(Teacher teacher){
        new InsertTeacherAsyncTask(teacherDAO).execute(teacher);
    }
    public void update(Teacher teacher){
        new UpdateTeacherAsyncTask(teacherDAO).execute(teacher);
    }
    public void delete(Teacher teacher){
        new DeleteTeacherAsyncTask(teacherDAO).execute(teacher);
    }
    public void deleteAll(){
        new DeleteAllTeachersAsyncTask(teacherDAO).execute();
    }
    public LiveData<List<Teacher>> getAll(){
        return allTeachers;
    }
    public List<Teacher> getAllSync()
    {
        return teacherDAO.getAllSync();
    }
    public LiveData<Teacher> getById(int teacherId) {
       return teacherDAO.getById(teacherId);
    }
    public Teacher getByIdSync(int teacherId)
    {
        return teacherDAO.getByIdSync(teacherId);
    }

    private static class GetTeacherByIdAsyncTask extends AsyncTask<Integer, Void, LiveData<Teacher>>{
        private TeacherDAO teacherDAO;

        public GetTeacherByIdAsyncTask(TeacherDAO teacherDAO) {
            this.teacherDAO = teacherDAO;
        }

        @Override
        protected LiveData<Teacher> doInBackground(Integer... integers) {
            LiveData<Teacher> liveData = teacherDAO.getById(integers[0]);
            return teacherDAO.getById(integers[0]);
        }
    }

    private static class InsertTeacherAsyncTask extends AsyncTask<Teacher, Void, Void>{
        private TeacherDAO teacherDAO;

        public InsertTeacherAsyncTask(TeacherDAO teacherDAO) {
            this.teacherDAO = teacherDAO;
        }

        @Override
        protected Void doInBackground(Teacher... teachers) {
            teacherDAO.insert(teachers[0]);
            return null;
        }
    }

    private static class UpdateTeacherAsyncTask extends AsyncTask<Teacher, Void, Void>{
        private TeacherDAO teacherDAO;

        public UpdateTeacherAsyncTask(TeacherDAO teacherDAO)
        {
            this.teacherDAO = teacherDAO;
        }

        @Override
        protected Void doInBackground(Teacher... teachers) {
            teacherDAO.update(teachers[0]);
            return null;
        }
    }

    private static class DeleteAllTeachersAsyncTask extends AsyncTask<Void, Void, Void>{
        private TeacherDAO teacherDAO;

        public DeleteAllTeachersAsyncTask(TeacherDAO teacherDAO)
        {
            this.teacherDAO = teacherDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            teacherDAO.deleteAll();
            return null;
        }
    }

    private static class DeleteTeacherAsyncTask extends AsyncTask<Teacher, Void, Void>{
        private TeacherDAO teacherDAO;

        public DeleteTeacherAsyncTask(TeacherDAO teacherDAO)
        {
            this.teacherDAO = teacherDAO;
        }

        @Override
        protected Void doInBackground(Teacher... teachers) {
            teacherDAO.delete(teachers[0]);
            return null;
        }
    }

}
