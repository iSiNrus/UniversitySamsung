package ru.mitryashkin.universitysamsung.DAO;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import ru.mitryashkin.universitysamsung.DB.DateConverter;
import ru.mitryashkin.universitysamsung.models.Student;

@SuppressWarnings("unchecked")
public final class StudentDAO_Impl implements StudentDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfStudent;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public StudentDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `students`(`id`,`grade_book_num`,`first_name`,`last_name`,`middle_name`,`birthday`,`group_id`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Student value) {
        stmt.bindLong(1, value.getID());
        stmt.bindLong(2, value.getGradeBookNumber());
        if (value.getFirstName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLastName());
        }
        if (value.getMiddleName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMiddleName());
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.getBirthday());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, _tmp);
        }
        stmt.bindLong(7, value.getGroupID());
      }
    };
    this.__deletionAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `students` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Student value) {
        stmt.bindLong(1, value.getID());
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `students` SET `id` = ?,`grade_book_num` = ?,`first_name` = ?,`last_name` = ?,`middle_name` = ?,`birthday` = ?,`group_id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Student value) {
        stmt.bindLong(1, value.getID());
        stmt.bindLong(2, value.getGradeBookNumber());
        if (value.getFirstName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLastName());
        }
        if (value.getMiddleName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMiddleName());
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.getBirthday());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, _tmp);
        }
        stmt.bindLong(7, value.getGroupID());
        stmt.bindLong(8, value.getID());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM students";
        return _query;
      }
    };
  }

  @Override
  public void insert(Student stud) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfStudent.insert(stud);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Student stud) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfStudent.handle(stud);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Student stud) {
    __db.beginTransaction();
    try {
      __updateAdapterOfStudent.handle(stud);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Student>> getAll() {
    final String _sql = "SELECT * FROM students ORDER BY first_name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Student>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Student> compute() {
        if (_observer == null) {
          _observer = new Observer("students") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfGradeBookNumber = _cursor.getColumnIndexOrThrow("grade_book_num");
          final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
          final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
          final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
          final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
          final int _cursorIndexOfGroupID = _cursor.getColumnIndexOrThrow("group_id");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Student _item;
            _item = new Student();
            final int _tmpID;
            _tmpID = _cursor.getInt(_cursorIndexOfID);
            _item.setID(_tmpID);
            final int _tmpGradeBookNumber;
            _tmpGradeBookNumber = _cursor.getInt(_cursorIndexOfGradeBookNumber);
            _item.setGradeBookNumber(_tmpGradeBookNumber);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            _item.setFirstName(_tmpFirstName);
            final String _tmpLastName;
            _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            _item.setLastName(_tmpLastName);
            final String _tmpMiddleName;
            _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            _item.setMiddleName(_tmpMiddleName);
            final Date _tmpBirthday;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfBirthday)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfBirthday);
            }
            _tmpBirthday = DateConverter.fromTimestamp(_tmp);
            _item.setBirthday(_tmpBirthday);
            final int _tmpGroupID;
            _tmpGroupID = _cursor.getInt(_cursorIndexOfGroupID);
            _item.setGroupID(_tmpGroupID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<Student> getById(int id) {
    final String _sql = "Select * FROM students where students.id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return new ComputableLiveData<Student>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected Student compute() {
        if (_observer == null) {
          _observer = new Observer("students") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfGradeBookNumber = _cursor.getColumnIndexOrThrow("grade_book_num");
          final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
          final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
          final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
          final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
          final int _cursorIndexOfGroupID = _cursor.getColumnIndexOrThrow("group_id");
          final Student _result;
          if(_cursor.moveToFirst()) {
            _result = new Student();
            final int _tmpID;
            _tmpID = _cursor.getInt(_cursorIndexOfID);
            _result.setID(_tmpID);
            final int _tmpGradeBookNumber;
            _tmpGradeBookNumber = _cursor.getInt(_cursorIndexOfGradeBookNumber);
            _result.setGradeBookNumber(_tmpGradeBookNumber);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            _result.setFirstName(_tmpFirstName);
            final String _tmpLastName;
            _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            _result.setLastName(_tmpLastName);
            final String _tmpMiddleName;
            _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            _result.setMiddleName(_tmpMiddleName);
            final Date _tmpBirthday;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfBirthday)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfBirthday);
            }
            _tmpBirthday = DateConverter.fromTimestamp(_tmp);
            _result.setBirthday(_tmpBirthday);
            final int _tmpGroupID;
            _tmpGroupID = _cursor.getInt(_cursorIndexOfGroupID);
            _result.setGroupID(_tmpGroupID);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public List<Student> getAllSync() {
    final String _sql = "SELECT * FROM students ORDER BY first_name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfGradeBookNumber = _cursor.getColumnIndexOrThrow("grade_book_num");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
      final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
      final int _cursorIndexOfGroupID = _cursor.getColumnIndexOrThrow("group_id");
      final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Student _item;
        _item = new Student();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final int _tmpGradeBookNumber;
        _tmpGradeBookNumber = _cursor.getInt(_cursorIndexOfGradeBookNumber);
        _item.setGradeBookNumber(_tmpGradeBookNumber);
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        _item.setFirstName(_tmpFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        _item.setLastName(_tmpLastName);
        final String _tmpMiddleName;
        _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
        _item.setMiddleName(_tmpMiddleName);
        final Date _tmpBirthday;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfBirthday)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfBirthday);
        }
        _tmpBirthday = DateConverter.fromTimestamp(_tmp);
        _item.setBirthday(_tmpBirthday);
        final int _tmpGroupID;
        _tmpGroupID = _cursor.getInt(_cursorIndexOfGroupID);
        _item.setGroupID(_tmpGroupID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Student getByIdSync(int id) {
    final String _sql = "Select * FROM students where students.id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfGradeBookNumber = _cursor.getColumnIndexOrThrow("grade_book_num");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
      final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
      final int _cursorIndexOfGroupID = _cursor.getColumnIndexOrThrow("group_id");
      final Student _result;
      if(_cursor.moveToFirst()) {
        _result = new Student();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _result.setID(_tmpID);
        final int _tmpGradeBookNumber;
        _tmpGradeBookNumber = _cursor.getInt(_cursorIndexOfGradeBookNumber);
        _result.setGradeBookNumber(_tmpGradeBookNumber);
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        _result.setFirstName(_tmpFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        _result.setLastName(_tmpLastName);
        final String _tmpMiddleName;
        _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
        _result.setMiddleName(_tmpMiddleName);
        final Date _tmpBirthday;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfBirthday)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfBirthday);
        }
        _tmpBirthday = DateConverter.fromTimestamp(_tmp);
        _result.setBirthday(_tmpBirthday);
        final int _tmpGroupID;
        _tmpGroupID = _cursor.getInt(_cursorIndexOfGroupID);
        _result.setGroupID(_tmpGroupID);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
