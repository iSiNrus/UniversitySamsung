package ru.mitryashkin.universitysamsung.DB;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.Date;

import ru.mitryashkin.universitysamsung.DAO.GroupDAO;
import ru.mitryashkin.universitysamsung.DAO.StudentDAO;
import ru.mitryashkin.universitysamsung.DAO.TeacherDAO;
import ru.mitryashkin.universitysamsung.models.Group;
import ru.mitryashkin.universitysamsung.models.Student;
import ru.mitryashkin.universitysamsung.models.Teacher;

@Database(entities = {Teacher.class, Group.class, Student.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class UniversityDatabase extends RoomDatabase {

    private static final String TAG = "UniversityDatabase";
    private static UniversityDatabase instance;

    private static final String DB_name = "UniversityDB";

    public abstract GroupDAO groupDAO();
    public abstract TeacherDAO teacherDAO();
    public abstract StudentDAO studentDAO();

    public static synchronized UniversityDatabase getInstance(Context context){
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UniversityDatabase.class,
                    DB_name)
                    .allowMainThreadQueries()
                    .build();
//            instance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
//                    UniversityDatabase.class)
//                    .addCallback(roomCallback)
//                    .allowMainThreadQueries()
//                    .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance.teacherDAO(), instance.groupDAO(), instance.studentDAO()).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private TeacherDAO teacherDAO;
        private GroupDAO groupDAO;
        private StudentDAO studentDAO;

        public PopulateDBAsyncTask(TeacherDAO teacherDAO, GroupDAO groupDAO, StudentDAO studentDAO) {
            this.teacherDAO = teacherDAO;
            this.groupDAO = groupDAO;
            this.studentDAO = studentDAO;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {
            teacherDAO.insert(new Teacher());
            teacherDAO.insert(new Teacher("Vasiliy", "Petrov", "Robertovich", new Date(), "Doctor"));
            teacherDAO.insert(new Teacher("Петр", "Петров", "Алексеевич", new Date(), "Kandidat"));
            teacherDAO.insert(new Teacher("Andrey", "Sidorov", "Ivanovich", new Date(), "Labor"));
            teacherDAO.insert(new Teacher("Ivan", "Serov", "Kuzmich", new Date(), "Docent"));

            groupDAO.insert(new Group());
            groupDAO.insert(new Group());
            groupDAO.insert(new Group());
            groupDAO.insert(new Group());
            Group g = new Group("MyGroup", "My spec", "<y Dep", 5);
            groupDAO.insert(g);
            Log.d(TAG, "After insert: groups count = " + (groupDAO.getCount()));

            Calendar cal = Calendar.getInstance();
            cal.set(1997, 2, 12);
            studentDAO.insert(new Student(1488, "Пушкин", "Алекс", "Сергеевич", new Date(cal.getTimeInMillis()), -1));
            studentDAO.insert(new Student(1488, "Пушкин", "Алекс", "Сергеевич", new Date(cal.getTimeInMillis()), -1));
            studentDAO.insert(new Student(1488, "Пушкин", "Алекс", "Сергеевич", new Date(cal.getTimeInMillis()), -1));
            return null;
        }
    }

}
