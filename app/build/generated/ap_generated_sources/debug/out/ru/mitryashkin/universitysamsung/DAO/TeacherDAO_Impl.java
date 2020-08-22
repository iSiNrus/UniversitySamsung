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
import ru.mitryashkin.universitysamsung.models.Teacher;

@SuppressWarnings("unchecked")
public final class TeacherDAO_Impl implements TeacherDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTeacher;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTeacher;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTeacher;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TeacherDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTeacher = new EntityInsertionAdapter<Teacher>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `teachers`(`id`,`first_name`,`last_name`,`middle_name`,`birthday`,`grade`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Teacher value) {
        stmt.bindLong(1, value.getID());
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
        if (value.getMiddleName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMiddleName());
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.getBirthday());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.getGrade() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getGrade());
        }
      }
    };
    this.__deletionAdapterOfTeacher = new EntityDeletionOrUpdateAdapter<Teacher>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `teachers` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Teacher value) {
        stmt.bindLong(1, value.getID());
      }
    };
    this.__updateAdapterOfTeacher = new EntityDeletionOrUpdateAdapter<Teacher>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `teachers` SET `id` = ?,`first_name` = ?,`last_name` = ?,`middle_name` = ?,`birthday` = ?,`grade` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Teacher value) {
        stmt.bindLong(1, value.getID());
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
        if (value.getMiddleName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMiddleName());
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.getBirthday());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.getGrade() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getGrade());
        }
        stmt.bindLong(7, value.getID());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM teachers";
        return _query;
      }
    };
  }

  @Override
  public void insert(Teacher teacher) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTeacher.insert(teacher);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Teacher teacher) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfTeacher.handle(teacher);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Teacher teacher) {
    __db.beginTransaction();
    try {
      __updateAdapterOfTeacher.handle(teacher);
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
  public LiveData<List<Teacher>> getAll() {
    final String _sql = "SELECT * FROM teachers ORDER BY first_name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Teacher>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Teacher> compute() {
        if (_observer == null) {
          _observer = new Observer("teachers") {
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
          final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
          final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
          final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
          final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
          final int _cursorIndexOfGrade = _cursor.getColumnIndexOrThrow("grade");
          final List<Teacher> _result = new ArrayList<Teacher>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Teacher _item;
            _item = new Teacher();
            final int _tmpID;
            _tmpID = _cursor.getInt(_cursorIndexOfID);
            _item.setID(_tmpID);
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
            final String _tmpGrade;
            _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            _item.setGrade(_tmpGrade);
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
  public LiveData<Teacher> getById(int id) {
    final String _sql = "Select * from teachers where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return new ComputableLiveData<Teacher>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected Teacher compute() {
        if (_observer == null) {
          _observer = new Observer("teachers") {
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
          final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
          final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
          final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
          final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
          final int _cursorIndexOfGrade = _cursor.getColumnIndexOrThrow("grade");
          final Teacher _result;
          if(_cursor.moveToFirst()) {
            _result = new Teacher();
            final int _tmpID;
            _tmpID = _cursor.getInt(_cursorIndexOfID);
            _result.setID(_tmpID);
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
            final String _tmpGrade;
            _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            _result.setGrade(_tmpGrade);
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
  public List<Teacher> getAllSync() {
    final String _sql = "SELECT * FROM TEACHERS ORDER BY first_name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
      final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
      final int _cursorIndexOfGrade = _cursor.getColumnIndexOrThrow("grade");
      final List<Teacher> _result = new ArrayList<Teacher>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Teacher _item;
        _item = new Teacher();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
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
        final String _tmpGrade;
        _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
        _item.setGrade(_tmpGrade);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Teacher getByIdSync(int id) {
    final String _sql = "Select * from teachers where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfMiddleName = _cursor.getColumnIndexOrThrow("middle_name");
      final int _cursorIndexOfBirthday = _cursor.getColumnIndexOrThrow("birthday");
      final int _cursorIndexOfGrade = _cursor.getColumnIndexOrThrow("grade");
      final Teacher _result;
      if(_cursor.moveToFirst()) {
        _result = new Teacher();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _result.setID(_tmpID);
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
        final String _tmpGrade;
        _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
        _result.setGrade(_tmpGrade);
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
