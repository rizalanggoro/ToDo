package com.bonfire.todo.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bonfire.todo.datas.TaskData;

import java.util.ArrayList;

public class TaskDatabase extends SQLiteOpenHelper {
  private Context context;
  private String databaseTable = "task_table";

  private String id = "_id";
  private String key = "_key";
  private String taskTitle = "_taskTitle";
  private String taskDescription = "_taskDescription";
  private String taskDate = "_taskDate";
  private String taskTime = "_taskTime";
  private String taskSubjectKey = "_taskSubjectKey";
  private String taskCategoryKey = "_taskCategoryKey";
  private String taskSubTask = "_taskSubTask";

  public TaskDatabase(@Nullable Context context) {
    super(context, "task_database.db", null, 2);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String sql = "create table if not exists " + this.databaseTable + " (" +
        this.id + " integer primary key autoincrement," +
        this.key + " text," +
        this.taskTitle + " text," +
        this.taskDescription + " text," +
        this.taskDate + " text," +
        this.taskTime + " text," +
        this.taskSubjectKey + " text," +
        this.taskCategoryKey + " text," +
        this.taskSubTask + " text" +
        ")";
    sqLiteDatabase.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    // ==> backup data
    this.backupDatabase();

    String sql = "drop table if exists " + this.databaseTable;
    sqLiteDatabase.execSQL(sql);

    // ==> restore data
    this.restoreDatabase();
  }

  public boolean addTaskData(TaskData taskData) {
    return this.getWritableDatabase().insert(this.databaseTable, null, this.putData(taskData)) != -1;
  }

  private ArrayList<TaskData> backupTaskDataArrayList = new ArrayList<>();
  private void backupDatabase() {
    if (this.backupTaskDataArrayList.size() > 0) {
      this.backupTaskDataArrayList.clear();
    }
    String sql = "select * from " + this.databaseTable;
    Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        TaskData data = new TaskData();
        data.set(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(7),
            cursor.getString(8)
        );
        this.backupTaskDataArrayList.add(data);
      }
      cursor.close();
    }
  }

  private void restoreDatabase() {
    if (this.backupTaskDataArrayList.size() > 0) {
      for (int i = 0; i < this.backupTaskDataArrayList.size(); i++) {
        this.getWritableDatabase()
            .insert(
                this.databaseTable,
                null,
                this.putData(
                    this.backupTaskDataArrayList.get(i)
                )
            );
      }
    }
  }

  private ContentValues putData(TaskData data) {
    ContentValues values = new ContentValues();
    values.put(this.key, data.getKey());
    values.put(this.taskTitle, data.getTaskTitle());
    values.put(this.taskDescription, data.getTaskDescription());
    values.put(this.taskDate, data.getTaskDate());
    values.put(this.taskTime, data.getTaskTime());
    values.put(this.taskSubjectKey, data.getTaskSubjectKey());
    values.put(this.taskCategoryKey, data.getTaskCategoryKey());
    values.put(this.taskSubTask, data.getTaskSubTask());
    return values;
  }
}
