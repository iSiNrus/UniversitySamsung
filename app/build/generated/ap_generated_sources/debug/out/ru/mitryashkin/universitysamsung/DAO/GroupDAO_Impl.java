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
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ru.mitryashkin.universitysamsung.models.Group;

@SuppressWarnings("unchecked")
public final class GroupDAO_Impl implements GroupDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGroup;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfGroup;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfGroup;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public GroupDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGroup = new EntityInsertionAdapter<Group>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `groups`(`ID`,`group_name`,`department`,`speciality`,`kurator_id`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Group value) {
        stmt.bindLong(1, value.getID());
        if (value.getGroupName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getGroupName());
        }
        if (value.getDepartment() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDepartment());
        }
        if (value.getSpeciality() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSpeciality());
        }
        stmt.bindLong(5, value.getKuratorID());
      }
    };
    this.__deletionAdapterOfGroup = new EntityDeletionOrUpdateAdapter<Group>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `groups` WHERE `ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Group value) {
        stmt.bindLong(1, value.getID());
      }
    };
    this.__updateAdapterOfGroup = new EntityDeletionOrUpdateAdapter<Group>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `groups` SET `ID` = ?,`group_name` = ?,`department` = ?,`speciality` = ?,`kurator_id` = ? WHERE `ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Group value) {
        stmt.bindLong(1, value.getID());
        if (value.getGroupName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getGroupName());
        }
        if (value.getDepartment() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDepartment());
        }
        if (value.getSpeciality() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSpeciality());
        }
        stmt.bindLong(5, value.getKuratorID());
        stmt.bindLong(6, value.getID());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM groups";
        return _query;
      }
    };
  }

  @Override
  public void insert(Group group) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGroup.insert(group);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Group group) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfGroup.handle(group);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Group group) {
    __db.beginTransaction();
    try {
      __updateAdapterOfGroup.handle(group);
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
  public LiveData<List<Group>> getAll() {
    final String _sql = "Select * from groups";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Group>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Group> compute() {
        if (_observer == null) {
          _observer = new Observer("groups") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("ID");
          final int _cursorIndexOfGroupName = _cursor.getColumnIndexOrThrow("group_name");
          final int _cursorIndexOfDepartment = _cursor.getColumnIndexOrThrow("department");
          final int _cursorIndexOfSpeciality = _cursor.getColumnIndexOrThrow("speciality");
          final int _cursorIndexOfKuratorID = _cursor.getColumnIndexOrThrow("kurator_id");
          final List<Group> _result = new ArrayList<Group>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Group _item;
            _item = new Group();
            final int _tmpID;
            _tmpID = _cursor.getInt(_cursorIndexOfID);
            _item.setID(_tmpID);
            final String _tmpGroupName;
            _tmpGroupName = _cursor.getString(_cursorIndexOfGroupName);
            _item.setGroupName(_tmpGroupName);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            _item.setDepartment(_tmpDepartment);
            final String _tmpSpeciality;
            _tmpSpeciality = _cursor.getString(_cursorIndexOfSpeciality);
            _item.setSpeciality(_tmpSpeciality);
            final int _tmpKuratorID;
            _tmpKuratorID = _cursor.getInt(_cursorIndexOfKuratorID);
            _item.setKuratorID(_tmpKuratorID);
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
  public List<Group> getAllSync() {
    final String _sql = "Select * from groups";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("ID");
      final int _cursorIndexOfGroupName = _cursor.getColumnIndexOrThrow("group_name");
      final int _cursorIndexOfDepartment = _cursor.getColumnIndexOrThrow("department");
      final int _cursorIndexOfSpeciality = _cursor.getColumnIndexOrThrow("speciality");
      final int _cursorIndexOfKuratorID = _cursor.getColumnIndexOrThrow("kurator_id");
      final List<Group> _result = new ArrayList<Group>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Group _item;
        _item = new Group();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpGroupName;
        _tmpGroupName = _cursor.getString(_cursorIndexOfGroupName);
        _item.setGroupName(_tmpGroupName);
        final String _tmpDepartment;
        _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        _item.setDepartment(_tmpDepartment);
        final String _tmpSpeciality;
        _tmpSpeciality = _cursor.getString(_cursorIndexOfSpeciality);
        _item.setSpeciality(_tmpSpeciality);
        final int _tmpKuratorID;
        _tmpKuratorID = _cursor.getInt(_cursorIndexOfKuratorID);
        _item.setKuratorID(_tmpKuratorID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Group> getById(Integer id) {
    final String _sql = "SELECT * FROM groups WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    return new ComputableLiveData<Group>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected Group compute() {
        if (_observer == null) {
          _observer = new Observer("groups") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("ID");
          final int _cursorIndexOfGroupName = _cursor.getColumnIndexOrThrow("group_name");
          final int _cursorIndexOfDepartment = _cursor.getColumnIndexOrThrow("department");
          final int _cursorIndexOfSpeciality = _cursor.getColumnIndexOrThrow("speciality");
          final int _cursorIndexOfKuratorID = _cursor.getColumnIndexOrThrow("kurator_id");
          final Group _result;
          if(_cursor.moveToFirst()) {
            _result = new Group();
            final int _tmpID;
            _tmpID = _cursor.getInt(_cursorIndexOfID);
            _result.setID(_tmpID);
            final String _tmpGroupName;
            _tmpGroupName = _cursor.getString(_cursorIndexOfGroupName);
            _result.setGroupName(_tmpGroupName);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            _result.setDepartment(_tmpDepartment);
            final String _tmpSpeciality;
            _tmpSpeciality = _cursor.getString(_cursorIndexOfSpeciality);
            _result.setSpeciality(_tmpSpeciality);
            final int _tmpKuratorID;
            _tmpKuratorID = _cursor.getInt(_cursorIndexOfKuratorID);
            _result.setKuratorID(_tmpKuratorID);
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
  public Group getByIdSync(Integer id) {
    final String _sql = "SELECT * FROM groups WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("ID");
      final int _cursorIndexOfGroupName = _cursor.getColumnIndexOrThrow("group_name");
      final int _cursorIndexOfDepartment = _cursor.getColumnIndexOrThrow("department");
      final int _cursorIndexOfSpeciality = _cursor.getColumnIndexOrThrow("speciality");
      final int _cursorIndexOfKuratorID = _cursor.getColumnIndexOrThrow("kurator_id");
      final Group _result;
      if(_cursor.moveToFirst()) {
        _result = new Group();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _result.setID(_tmpID);
        final String _tmpGroupName;
        _tmpGroupName = _cursor.getString(_cursorIndexOfGroupName);
        _result.setGroupName(_tmpGroupName);
        final String _tmpDepartment;
        _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        _result.setDepartment(_tmpDepartment);
        final String _tmpSpeciality;
        _tmpSpeciality = _cursor.getString(_cursorIndexOfSpeciality);
        _result.setSpeciality(_tmpSpeciality);
        final int _tmpKuratorID;
        _tmpKuratorID = _cursor.getInt(_cursorIndexOfKuratorID);
        _result.setKuratorID(_tmpKuratorID);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCount() {
    final String _sql = "SELECT Count(*) FROM groups";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
