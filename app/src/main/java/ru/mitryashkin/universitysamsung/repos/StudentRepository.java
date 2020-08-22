package ru.mitryashkin.universitysamsung.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mitryashkin.universitysamsung.DAO.GroupDAO;
import ru.mitryashkin.universitysamsung.DAO.StudentDAO;
import ru.mitryashkin.universitysamsung.DAO.TeacherDAO;
import ru.mitryashkin.universitysamsung.DB.UniversityDatabase;
import ru.mitryashkin.universitysamsung.models.Group;
import ru.mitryashkin.universitysamsung.models.Student;
import ru.mitryashkin.universitysamsung.models.Teacher;

public class StudentRepository {

    private StudentDAO studentDAO;
    private GroupDAO groupDAO;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application)
    {
        UniversityDatabase db = UniversityDatabase.getInstance(application);
        studentDAO = db.studentDAO();
        groupDAO = db.groupDAO();
        allStudents = studentDAO.getAll();
    }

    public void insert(Student student){
        new StudentRepository.InsertStudentAsyncTask(studentDAO).execute(student);
    }
    public void update(Student student){
        new StudentRepository.UpdateStudentAsyncTask(studentDAO).execute(student);
    }
    public void delete(Student student){
        new StudentRepository.DeleteStudentAsyncTask(studentDAO).execute();
    }
    public void deleteAll(){
        new StudentRepository.DeleteAllStudentsAsyncTask(studentDAO).execute();
    }
    public LiveData<List<Student>> getAll(){
        return allStudents;
    }
    public List<Student> getAllSync()
    {
        return studentDAO.getAllSync();
    }
    public LiveData<Student> getById(int studentId) {
        return studentDAO.getById(studentId);
    }
    public Student getByIdSync(int studentId)
    {
        return studentDAO.getByIdSync(studentId);
    }
    
    private static class GetStudentByIdAsyncTask extends AsyncTask<Integer, Void, LiveData<Student>> {
        private StudentDAO studentDAO;

        public GetStudentByIdAsyncTask(StudentDAO studentDAO) {
            this.studentDAO = studentDAO;
        }

        @Override
        protected LiveData<Student> doInBackground(Integer... integers) {
            LiveData<Student> liveData = studentDAO.getById(integers[0]);
            return studentDAO.getById(integers[0]);
        }
    }

    private static class InsertStudentAsyncTask extends AsyncTask<Student, Void, Void>{
        private StudentDAO studentDAO;

        public InsertStudentAsyncTask(StudentDAO studentDAO) {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDAO.insert(students[0]);
            return null;
        }
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<Student, Void, Void>{
        private StudentDAO studentDAO;

        public UpdateStudentAsyncTask(StudentDAO studentDAO)
        {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDAO.update(students[0]);
            return null;
        }
    }

    private static class DeleteAllStudentsAsyncTask extends AsyncTask<Void, Void, Void>{
        private StudentDAO studentDAO;

        public DeleteAllStudentsAsyncTask(StudentDAO studentDAO)
        {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDAO.deleteAll();
            return null;
        }
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<Student, Void, Void>{
        private StudentDAO studentDAO;

        public DeleteStudentAsyncTask(StudentDAO studentDAO)
        {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDAO.delete(students[0]);
            return null;
        }
    }



}
