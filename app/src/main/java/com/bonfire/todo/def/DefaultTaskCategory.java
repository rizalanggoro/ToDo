package com.bonfire.todo.def;

import android.app.Activity;

import com.bonfire.todo.R;
import com.bonfire.todo.datas.TaskCategoryData;
import com.bonfire.todo.utils.Color;
import com.bonfire.todo.utils.FeatherIcon;
import com.bonfire.todo.utils.FeatherIcons;

import java.util.ArrayList;

public class DefaultTaskCategory {
  // default task category
  private String backgroundColor = "50";
  private int[] taskCategoryTitles = {
      R.string.all_task,
      R.string.personal_task,
      R.string.work_task,
  };
  private FeatherIcons[] taskCategoryIcons = {
      FeatherIcons.more_horizontal,
      FeatherIcons.user,
      FeatherIcons.briefcase,
  };
  private int[] taskCategoryIconColors = {
      Color.lightBlue("500"),
      Color.lightGreen("500"),
      Color.pink("500"),
  };
  private int[] taskCategoryIconBackgroundColors = {
      Color.lightBlue(this.backgroundColor),
      Color.lightGreen(this.backgroundColor),
      Color.pink(this.backgroundColor),
  };

  public ArrayList<TaskCategoryData> getDefaultTaskCategoryData(Activity activity) {
    ArrayList<TaskCategoryData> taskCategoryDataArrayList = new ArrayList<>();
    for (int i = 0; i < this.taskCategoryTitles.length; i++) {
      TaskCategoryData data = new TaskCategoryData();
      data.setCategoryName(activity.getString(this.taskCategoryTitles[i]));
      data.setCategoryIcon(this.taskCategoryIcons[i].toString());
      data.setCategoryIconColor(String.valueOf(this.taskCategoryIconColors[i]));
      data.setCategoryIconBackgroundColor(String.valueOf(this.taskCategoryIconBackgroundColors[i]));
      taskCategoryDataArrayList.add(data);
    }
    return taskCategoryDataArrayList;
  }
}
