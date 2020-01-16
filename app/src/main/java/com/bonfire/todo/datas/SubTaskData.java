package com.bonfire.todo.datas;

public class SubTaskData {
  private String id = "";
  private String key = "";
  private String title = "";
  private String done = "";

  public void set(String id, String key, String title, String done) {
    this.id = id;
    this.key = key;
    this.title = title;
    this.done = done;
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

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public String getDone() {
    return done;
  }

  public void setDone(String done) {
    this.done = done;
  }
}
