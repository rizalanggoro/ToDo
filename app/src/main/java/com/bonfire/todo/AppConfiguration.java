package com.bonfire.todo;

import android.app.Activity;
import android.content.SharedPreferences;

public class AppConfiguration {
  private String selectedTaskCategoryKey = "_selectedTaskCategoryKey"; // default To-Do ==> all task
  private String needInsertDefaultTaskCategory = "_needInsertDefaultTaskCategory"; // default "" ==> true

  private SharedPreferences sharedPreferences;
  public AppConfiguration(Activity activity) {
    this.sharedPreferences = activity.getSharedPreferences("app_configurations", Activity.MODE_PRIVATE);
  }

  public void setNeedInsertDefaultTaskCategory(String needInsertDefaultTaskCategory) {
    this.a(this.needInsertDefaultTaskCategory, needInsertDefaultTaskCategory);
  }

  public boolean getNeedInsertDefaultTaskCategory() {
    return this.a(this.needInsertDefaultTaskCategory).equals("");
  }

  public void setSelectedTaskCategoryKey(String selectedTaskCategoryKey) {
    this.a(this.selectedTaskCategoryKey, selectedTaskCategoryKey);
  }

  public String getSelectedTaskCategoryKey() {
    return this.a(this.selectedTaskCategoryKey);
  }

  private void a(String k, String v) {
    this.sharedPreferences
        .edit()
        .putString(k, v)
        .apply();
  }

  private String a(String k) {
    if (this.sharedPreferences != null)
      return this.sharedPreferences.getString(k, "");
    else
      return "";
  }
}
