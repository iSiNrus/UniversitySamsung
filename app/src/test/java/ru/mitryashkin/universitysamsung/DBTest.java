package ru.mitryashkin.universitysamsung;

import android.app.Application;

import androidx.room.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import ru.mitryashkin.universitysamsung.DAO.GroupDAO;
import ru.mitryashkin.universitysamsung.DB.UniversityDatabase;

public class DBTest {

        private UniversityDatabase db;
        private GroupDAO grDao;

        @Before
        public void createDb() throws Exception {

        }

        @After
        public void closeDb() throws Exception {
            db.close();
        }

}
