package com.bonfire.todo.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.bonfire.todo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class ToDoDate {
  private ArrayList<String> dateArrayList28 = new ArrayList<>();
  private ArrayList<String> dateArrayList29 = new ArrayList<>();
  private ArrayList<String> dateArrayList30 = new ArrayList<>();
  private ArrayList<String> dateArrayList31 = new ArrayList<>();
  private String nullDate = "null";

  private int currentYear = 0;
  private int currentMonth = 0;

  public ToDoDate() {
    if (this.calendar == null) {
      this.calendar = Calendar.getInstance();
      this.currentYear = this.calendar.get(Calendar.YEAR);
      this.currentMonth = this.calendar.get(Calendar.MONTH);
    }
    this.a();
  }

  public int getCurrentYear() {
    return this.currentYear;
  }

  public int getCurrentMonth() {
    return this.currentMonth;
  }

  private int[] monthsText = {
      R.string.january,
      R.string.february,
      R.string.march,
      R.string.april,
      R.string.may,
      R.string.june,
      R.string.july,
      R.string.august,
      R.string.september,
      R.string.october,
      R.string.november,
      R.string.december,
  };

  private static int[] monthsText2 = {
      R.string.january,
      R.string.february,
      R.string.march,
      R.string.april,
      R.string.may,
      R.string.june,
      R.string.july,
      R.string.august,
      R.string.september,
      R.string.october,
      R.string.november,
      R.string.december,
  };

  private static int[] daysText = {
      R.string.sunday,
      R.string.monday,
      R.string.tuesday,
      R.string.wednesday,
      R.string.thursday,
      R.string.friday,
      R.string.saturday
  };

  public int getMonthText(int month) {
    return month != -1 ? this.monthsText[month] : 0;
  }

  public static int getStaticMonthText(int month) {
    return month != -1 ? monthsText2[month] : 0;
  }

  public static int getStaticDayText(int day) {
    return day != -1 ? daysText[day] : 0;
  }

  private void a() {
    String[] a28 = {
        "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21",
        "22", "23", "24", "25", "26", "27", "28",
    };
    String[] a29 = {
        "29",
    };
    String[] a30 = {
        "29", "30",
    };
    String[] a31 = {
        "29", "30", "31"
    };
    // --> clear all data from list
    this.d();
    // --> add 28
    if (this.dateArrayList28.size() == 0) {
      Collections.addAll(dateArrayList28, a28);
    }
    // --> add 29
    if (this.dateArrayList29.size() == 0) {
      Collections.addAll(dateArrayList29, a28);
      Collections.addAll(dateArrayList29, a29);
    }
    // --> add 30
    if (this.dateArrayList30.size() == 0) {
      Collections.addAll(dateArrayList30, a28);
      Collections.addAll(dateArrayList30, a30);
    }
    // --> add 31
    if (this.dateArrayList31.size() == 0) {
      Collections.addAll(dateArrayList31, a28);
      Collections.addAll(dateArrayList31, a31);
    }
    this.b();
  }

  public interface listener {
    void onLoadCompleted(ArrayList<String> resultDateArrayList);
  }

  private listener listener;
  public ToDoDate setListener(listener listener) {
    if (listener != null) this.listener = listener;
    return this;
  }

  public void load() {
    // load data
    // parse data from calendar
    this.a();
  }

  public void aiLoad() {
    new aiLoad(this.listener, this.calendar).execute();
  }

  private class aiLoad extends AsyncTask<Void, Void, Void> {
    private listener listener;
    private String[] a28 = {
        "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21",
        "22", "23", "24", "25", "26", "27", "28",
    };
    private String[] a29 = {
        "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21",
        "22", "23", "24", "25", "26", "27", "28",
        "29",
    };
    private String[] a30 = {
        "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21",
        "22", "23", "24", "25", "26", "27", "28",
        "29", "30",
    };
    private String[] a31 = {
        "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21",
        "22", "23", "24", "25", "26", "27", "28",
        "29", "30", "31"
    };
    private ArrayList<String> aiDateArrayList = new ArrayList<>();
    private Calendar calendar;
    private String nullDate = "null";

    private aiLoad(listener listener, Calendar calendar) {
      this.listener = listener;
      this.calendar = calendar;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      this.calendar.set(Calendar.DAY_OF_MONTH, 1);
      int actualMaximumDayOfMonth = this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      switch (actualMaximumDayOfMonth) {
        case 28 :
          Collections.addAll(this.aiDateArrayList, a28);
          break;

        case 29 :
          Collections.addAll(this.aiDateArrayList, a29);
          break;

        case 30 :
          Collections.addAll(this.aiDateArrayList, a30);
          break;

        case 31 :
          Collections.addAll(this.aiDateArrayList, a31);
          break;
      }

      int firstDayOfTheMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;
      for (int a = 0; a < firstDayOfTheMonth; a++) {
        this.aiDateArrayList.add(0, this.nullDate);
      }

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      Log.d("onPostExecute", "called");
      if (this.listener != null) {
        this.listener.onLoadCompleted(this.aiDateArrayList);
      } else {
        Log.d("listener", "null");
      }
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      Log.d("onPreExecute", "called");
      if (this.aiDateArrayList.size() > 0) {
        this.aiDateArrayList.clear();
      }
    }
  }

  private Calendar calendar;
  public void nextMonth() {
    if (this.calendar != null) {
      this.calendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH) + 1);
      this.currentYear = this.calendar.get(Calendar.YEAR);
      this.currentMonth = this.calendar.get(Calendar.MONTH);
      this.aiLoad();
    }
  }

  public void prevMonth() {
    if (this.calendar != null) {
      this.calendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH) - 1);
      this.currentYear = this.calendar.get(Calendar.YEAR);
      this.currentMonth = this.calendar.get(Calendar.MONTH);
      this.aiLoad();
    }
  }

  private void b() {
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    int actualMaximumDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    switch (actualMaximumDayOfMonth) {
      case 28 :
        this.c(this.dateArrayList28, calendar);
        break;

      case 29 :
        this.c(this.dateArrayList29, calendar);
        break;

      case 30 :
        this.c(this.dateArrayList30, calendar);
        break;

      case 31 :
        this.c(this.dateArrayList31, calendar);
        break;
    }
  }

  private void c(ArrayList<String> dateArrayList, Calendar calendar) {
    if (dateArrayList != null && calendar != null) {
      int firstDayOfTheMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;
      for (int a = 0; a < firstDayOfTheMonth; a++) {
        dateArrayList.add(0, this.nullDate);
      }
      if (this.listener != null) {
        this.listener.onLoadCompleted(dateArrayList);
      } else {
        Log.d("listener", "null");
      }
    }
  }

  private void d() {
    // reset all null from date array list
    this.dateArrayList28.removeAll(Collections.singleton(this.nullDate));
    this.dateArrayList29.removeAll(Collections.singleton(this.nullDate));
    this.dateArrayList30.removeAll(Collections.singleton(this.nullDate));
    this.dateArrayList31.removeAll(Collections.singleton(this.nullDate));
  }
}
