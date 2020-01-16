package com.bonfire.todo.datas;

public class TaskCategoryData {
  private String id = "";
  private String key = "";
  private String categoryName = "";
  private String categoryIcon = "";
  private String categoryIconColor = "";
  private String categoryTotalTask = "";
  private String categoryIconBackgroundColor = "";

  public void set(
      String id,
      String key,
      String categoryName,
      String categoryIcon,
      String categoryIconColor,
      String categoryTotalTask,
      String categoryIconBackgroundColor
  ) {
    this.id = id;
    this.key = key;
    this.categoryName = categoryName;
    this.categoryIcon = categoryIcon;
    this.categoryIconColor = categoryIconColor;
    this.categoryTotalTask = categoryTotalTask;
    this.categoryIconBackgroundColor = categoryIconBackgroundColor;
  }

  public String getCategoryIconBackgroundColor() {
    return categoryIconBackgroundColor;
  }

  public void setCategoryIconBackgroundColor(String categoryIconBackgroundColor) {
    this.categoryIconBackgroundColor = categoryIconBackgroundColor;
  }

  public String getId() {
    return id;
  }

  public String getKey() {
    return key;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getCategoryIcon() {
    return categoryIcon;
  }

  public String getCategoryIconColor() {
    return categoryIconColor;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCategoryTotalTask() {
    return categoryTotalTask;
  }

  public void setCategoryIcon(String categoryIcon) {
    this.categoryIcon = categoryIcon;
  }

  public void setCategoryIconColor(String categoryIconColor) {
    this.categoryIconColor = categoryIconColor;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public void setCategoryTotalTask(String categoryTotalTask) {
    this.categoryTotalTask = categoryTotalTask;
  }
}
