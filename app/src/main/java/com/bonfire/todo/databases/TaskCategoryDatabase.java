package com.bonfire.todo.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bonfire.todo.datas.TaskCategoryData;

import java.util.ArrayList;

public class TaskCategoryDatabase extends SQLiteOpenHelper {
  public static String databaseTable = "task_category_table";

  private String id = "_id";                              // 0
  private String key = "_key";                            // 1
  private String categoryName = "_categoryName";          // 2
  private String categoryIcon = "_categoryIcon";          // 3
  private String categoryIconColor = "_categoryIconColor";// 4
  private String categoryTotalTask = "_categoryTotalTask";// 5
  private String categoryIconBackgroundColor = "_categoryIconBackgroundColor"; // 6

  public TaskCategoryDatabase(@Nullable Context context) {
    super(context, "task_category.db", null, 2);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String sql = "create table if not exists " + databaseTable + " (" +
        this.id + " integer primary key autoincrement," +
        this.key + " text," +
        this.categoryName + " text," +
        this.categoryIcon + " text," +
        this.categoryIconColor + " text," +
        this.categoryTotalTask + " text," +
        this.categoryIconBackgroundColor + " text" +
        ")";
    sqLiteDatabase.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    // ==> backup data
    this.backupDatabase();

    String sql = "drop table if exists " + databaseTable;
    sqLiteDatabase.execSQL(sql);

    // ==> restore data
    this.restoreDatabase();
  }

  public interface loadListener {
    void onAdded(TaskCategoryData taskCategoryData);
  }

  private loadListener loadListener;
  public TaskCategoryDatabase setListener(loadListener listener) {
    if (listener != null) this.loadListener = listener;
    return this;
  }

  public void load() {
    String sql = "select * from " + databaseTable;
    Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        TaskCategoryData data = new TaskCategoryData();
        data.set(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6)
        );
        if (this.loadListener != null)
          this.loadListener.onAdded(data);
      }
      cursor.close();
    }
  }

  public TaskCategoryData getTaskCategoryData(String taskCategoryKey) {
    if (taskCategoryKey.trim().length() > 0) {
      String sql = "select * from " + databaseTable + " where " + this.key + " = '" + taskCategoryKey + "'";
      Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
      if (cursor != null && cursor.getCount() > 0) {
        TaskCategoryData data = null;
        while (cursor.moveToNext()) {
          data = new TaskCategoryData();
          data.set(cursor.getString(0),
              cursor.getString(1),
              cursor.getString(2),
              cursor.getString(3),
              cursor.getString(4),
              cursor.getString(5),
              cursor.getString(6));
        }
        cursor.close();
        return data;
      }
    }
    return null;
  }

  public boolean addTaskCategoryData(TaskCategoryData taskCategoryData) {
    return this.getWritableDatabase()
        .insert(databaseTable, null,
            this.putData(taskCategoryData)) != -1;
  }

  public boolean updateTaskCategoryData(TaskCategoryData taskCategoryData) {
    return this.getWritableDatabase()
        .update(databaseTable,
            this.putData(taskCategoryData),
            this.key + "=?",
            new String[]{taskCategoryData.getKey()}) != -1;
  }

  public boolean deleteTaskCategoryData(String taskCategoryDataKey) {
    return this.getWritableDatabase()
        .delete(databaseTable,
            this.key + "=?",
            new String[]{taskCategoryDataKey}) != -1;
  }

  private ArrayList<TaskCategoryData> backupTaskCategoryDataArrayList = new ArrayList<>();
  private void backupDatabase() {
    if (this.backupTaskCategoryDataArrayList.size() > 0) {
      this.backupTaskCategoryDataArrayList.clear();
    }
    String sql = "select * from " + databaseTable;
    Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        TaskCategoryData data = new TaskCategoryData();
        data.set(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6)
        );
        this.backupTaskCategoryDataArrayList.add(data);
      }
      cursor.close();
    }
  }

  private void restoreDatabase() {
    if (this.backupTaskCategoryDataArrayList.size() > 0) {
      for (int i = 0; i < this.backupTaskCategoryDataArrayList.size(); i++) {
        this.getWritableDatabase()
            .insert(
                databaseTable,
                null,
                this.putData(
                    this.backupTaskCategoryDataArrayList.get(i)
                )
            );
      }
    }
  }

  private ContentValues putData(TaskCategoryData data) {
    ContentValues values = new ContentValues();
    values.put(this.key, data.getKey());
    values.put(this.categoryName, data.getCategoryName());
    values.put(this.categoryIcon, data.getCategoryIcon());
    values.put(this.categoryIconColor, data.getCategoryIconColor());
    values.put(this.categoryTotalTask, data.getCategoryTotalTask());
    values.put(this.categoryIconBackgroundColor, data.getCategoryIconBackgroundColor());
    return values;
  }
}
