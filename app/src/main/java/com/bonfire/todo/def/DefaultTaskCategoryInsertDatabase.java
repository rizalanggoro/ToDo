package com.bonfire.todo.def;

import android.app.Activity;
import android.util.Log;

import com.bonfire.todo.databases.TaskCategoryDatabase;
import com.bonfire.todo.datas.TaskCategoryData;

import java.util.ArrayList;

public class DefaultTaskCategoryInsertDatabase {
  private Activity activity;
  private TaskCategoryDatabase taskCategoryDatabase;

  public DefaultTaskCategoryInsertDatabase(Activity activity) {
    this.activity = activity;
    this.taskCategoryDatabase = new TaskCategoryDatabase(this.activity);
  }

  public void initialize() {
    ArrayList<TaskCategoryData> taskCategoryDataArrayList =
        new DefaultTaskCategory().getDefaultTaskCategoryData(this.activity);
    for (int i = 0; i < taskCategoryDataArrayList.size(); i++) {
      TaskCategoryData data = taskCategoryDataArrayList.get(i);
      data.setKey(String.valueOf(i));
      data.setCategoryTotalTask("0");
      if (this.taskCategoryDatabase.addTaskCategoryData(data))
        Log.d("insertDefaultCategory", "success");
      else
        Log.d("insertDefaultCategory", "failed");
    }
  }
}
