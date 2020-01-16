package com.bonfire.todo.datas;

public class TaskData {
  private String id = "";
  private String key = "";
  private String taskTitle = "";
  private String taskDescription = "";
  private String taskDate = "";
  private String taskTime = "";
  private String taskSubjectKey = "";
  private String taskCategoryKey = "";
  private String taskSubTask = "";

  public void set(
      String id,
      String key,
      String taskTitle,
      String taskDescription,
      String taskDate,
      String taskTime,
      String taskSubjectKey,
      String taskCategoryKey,
      String taskSubTask
  ) {
    this.id = id;
    this.key = key;
    this.taskTitle = taskTitle;
    this.taskDescription = taskDescription;
    this.taskDate = taskDate;
    this.taskTime = taskTime;
    this.taskSubjectKey = taskSubjectKey;
    this.taskCategoryKey = taskCategoryKey;
    this.taskSubTask = taskSubTask;
  }

  public String getTaskSubTask() {
    return taskSubTask;
  }

  public void setTaskSubTask(String taskSubTask) {
    this.taskSubTask = taskSubTask;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public String getId() {
    return id;
  }

  public String getTaskCategoryKey() {
    return taskCategoryKey;
  }

  public String getTaskDate() {
    return taskDate;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public String getTaskSubjectKey() {
    return taskSubjectKey;
  }

  public String getTaskTime() {
    return taskTime;
  }

  public String getTaskTitle() {
    return taskTitle;
  }

  public void setTaskCategoryKey(String taskCategoryKey) {
    this.taskCategoryKey = taskCategoryKey;
  }

  public void setTaskDate(String taskDate) {
    this.taskDate = taskDate;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }

  public void setTaskSubjectKey(String taskSubjectKey) {
    this.taskSubjectKey = taskSubjectKey;
  }

  public void setTaskTime(String taskTime) {
    this.taskTime = taskTime;
  }

  public void setTaskTitle(String taskTitle) {
    this.taskTitle = taskTitle;
  }
}
