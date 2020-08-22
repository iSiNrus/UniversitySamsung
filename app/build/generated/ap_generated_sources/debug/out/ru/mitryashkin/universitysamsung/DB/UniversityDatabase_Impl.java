package ru.mitryashkin.universitysamsung.DB;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import ru.mitryashkin.universitysamsung.DAO.GroupDAO;
import ru.mitryashkin.universitysamsung.DAO.GroupDAO_Impl;
import ru.mitryashkin.universitysamsung.DAO.StudentDAO;
import ru.mitryashkin.universitysamsung.DAO.StudentDAO_Impl;
import ru.mitryashkin.universitysamsung.DAO.TeacherDAO;
import ru.mitryashkin.universitysamsung.DAO.TeacherDAO_Impl;

@SuppressWarnings("unchecked")
public final class UniversityDatabase_Impl extends UniversityDatabase {
  private volatile GroupDAO _groupDAO;

  private volatile TeacherDAO _teacherDAO;

  private volatile StudentDAO _studentDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `teachers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `first_name` TEXT, `last_name` TEXT, `middle_name` TEXT, `birthday` INTEGER, `grade` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `groups` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `group_name` TEXT, `department` TEXT, `speciality` TEXT, `kurator_id` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `students` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `grade_book_num` INTEGER NOT NULL, `first_name` TEXT, `last_name` TEXT, `middle_name` TEXT, `birthday` INTEGER, `group_id` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"b6d27f33d87173f1a2097cb1f25effda\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `teachers`");
        _db.execSQL("DROP TABLE IF EXISTS `groups`");
        _db.execSQL("DROP TABLE IF EXISTS `students`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTeachers = new HashMap<String, TableInfo.Column>(6);
        _columnsTeachers.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsTeachers.put("first_name", new TableInfo.Column("first_name", "TEXT", false, 0));
        _columnsTeachers.put("last_name", new TableInfo.Column("last_name", "TEXT", false, 0));
        _columnsTeachers.put("middle_name", new TableInfo.Column("middle_name", "TEXT", false, 0));
        _columnsTeachers.put("birthday", new TableInfo.Column("birthday", "INTEGER", false, 0));
        _columnsTeachers.put("grade", new TableInfo.Column("grade", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTeachers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTeachers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTeachers = new TableInfo("teachers", _columnsTeachers, _foreignKeysTeachers, _indicesTeachers);
        final TableInfo _existingTeachers = TableInfo.read(_db, "teachers");
        if (! _infoTeachers.equals(_existingTeachers)) {
          throw new IllegalStateException("Migration didn't properly handle teachers(ru.mitryashkin.universitysamsung.models.Teacher).\n"
                  + " Expected:\n" + _infoTeachers + "\n"
                  + " Found:\n" + _existingTeachers);
        }
        final HashMap<String, TableInfo.Column> _columnsGroups = new HashMap<String, TableInfo.Column>(5);
        _columnsGroups.put("ID", new TableInfo.Column("ID", "INTEGER", true, 1));
        _columnsGroups.put("group_name", new TableInfo.Column("group_name", "TEXT", false, 0));
        _columnsGroups.put("department", new TableInfo.Column("department", "TEXT", false, 0));
        _columnsGroups.put("speciality", new TableInfo.Column("speciality", "TEXT", false, 0));
        _columnsGroups.put("kurator_id", new TableInfo.Column("kurator_id", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGroups = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGroups = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGroups = new TableInfo("groups", _columnsGroups, _foreignKeysGroups, _indicesGroups);
        final TableInfo _existingGroups = TableInfo.read(_db, "groups");
        if (! _infoGroups.equals(_existingGroups)) {
          throw new IllegalStateException("Migration didn't properly handle groups(ru.mitryashkin.universitysamsung.models.Group).\n"
                  + " Expected:\n" + _infoGroups + "\n"
                  + " Found:\n" + _existingGroups);
        }
        final HashMap<String, TableInfo.Column> _columnsStudents = new HashMap<String, TableInfo.Column>(7);
        _columnsStudents.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsStudents.put("grade_book_num", new TableInfo.Column("grade_book_num", "INTEGER", true, 0));
        _columnsStudents.put("first_name", new TableInfo.Column("first_name", "TEXT", false, 0));
        _columnsStudents.put("last_name", new TableInfo.Column("last_name", "TEXT", false, 0));
        _columnsStudents.put("middle_name", new TableInfo.Column("middle_name", "TEXT", false, 0));
        _columnsStudents.put("birthday", new TableInfo.Column("birthday", "INTEGER", false, 0));
        _columnsStudents.put("group_id", new TableInfo.Column("group_id", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudents = new TableInfo("students", _columnsStudents, _foreignKeysStudents, _indicesStudents);
        final TableInfo _existingStudents = TableInfo.read(_db, "students");
        if (! _infoStudents.equals(_existingStudents)) {
          throw new IllegalStateException("Migration didn't properly handle students(ru.mitryashkin.universitysamsung.models.Student).\n"
                  + " Expected:\n" + _infoStudents + "\n"
                  + " Found:\n" + _existingStudents);
        }
      }
    }, "b6d27f33d87173f1a2097cb1f25effda", "e3d0a3ba2011f45415c49703a1550965");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "teachers","groups","students");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `teachers`");
      _db.execSQL("DELETE FROM `groups`");
      _db.execSQL("DELETE FROM `students`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public GroupDAO groupDAO() {
    if (_groupDAO != null) {
      return _groupDAO;
    } else {
      synchronized(this) {
        if(_groupDAO == null) {
          _groupDAO = new GroupDAO_Impl(this);
        }
        return _groupDAO;
      }
    }
  }

  @Override
  public TeacherDAO teacherDAO() {
    if (_teacherDAO != null) {
      return _teacherDAO;
    } else {
      synchronized(this) {
        if(_teacherDAO == null) {
          _teacherDAO = new TeacherDAO_Impl(this);
        }
        return _teacherDAO;
      }
    }
  }

  @Override
  public StudentDAO studentDAO() {
    if (_studentDAO != null) {
      return _studentDAO;
    } else {
      synchronized(this) {
        if(_studentDAO == null) {
          _studentDAO = new StudentDAO_Impl(this);
        }
        return _studentDAO;
      }
    }
  }
}
