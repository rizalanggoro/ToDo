package com.bonfire.todo.utils;

import android.content.Context;

public class FeatherIcon {
  private Context context;

  public FeatherIcon(Context context) {
    this.context = context;
  }

  public int getResource(FeatherIcons icons) {
    String icon = icons.toString();
    return this.getResource(icon);
  }

  public int getResource(String name) {
    return this.context.getResources()
        .getIdentifier(name, "drawable", this.context.getPackageName());
  }
}
