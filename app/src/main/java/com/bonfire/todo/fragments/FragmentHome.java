package com.bonfire.todo.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.AppConfiguration;
import com.bonfire.todo.MainActivity;
import com.bonfire.todo.R;
import com.bonfire.todo.adapters.TaskHomeAdapter;
import com.bonfire.todo.databases.TaskDatabase;
import com.bonfire.todo.datas.TaskData;
import com.bonfire.todo.utils.Font;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHome extends Fragment {
  @BindView(R.id.tv_dont_have_task)
  TextView textViewDontHaveTask;
  @BindView(R.id.tv_btn_add_task)
  TextView textViewButtonAddTask;
  @BindView(R.id.tv_today_task)
  TextView textViewTodayTask;
  @BindView(R.id.rv_today_task)
  RecyclerView recyclerViewTodayTask;

  private TaskHomeAdapter taskHomeAdapter;
  private TaskDatabase taskDatabase;
  private AppConfiguration appConfiguration;

  private ArrayList<String> todayTaskKeyArrayList = new ArrayList<>();
  private ArrayList<TaskData> todayTaskDataArrayList = new ArrayList<>();
  private ArrayList<String> incomingTaskKeyArrayList = new ArrayList<>();
  private ArrayList<TaskData> incomingTaskDataArrayList = new ArrayList<>();

  private String currentSelectedTaskCategoryKey = "";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    ButterKnife.bind(this, view);
    this.a();
    this.aa();
    this.b();
    this.c();

    Log.d("fragment home", "onCreateView");

    return view;
  }

  private void a() {
    this.taskHomeAdapter = new TaskHomeAdapter(
        getActivity(),
        this.todayTaskKeyArrayList,
        this.todayTaskDataArrayList
    );
    this.taskDatabase = new TaskDatabase(getActivity());
    if (getActivity() != null)
      this.appConfiguration = new AppConfiguration(getActivity());

    Font.setGilroyBold(
        this.textViewDontHaveTask,
        this.textViewButtonAddTask,
        this.textViewTodayTask
    );
  }

  private void aa() {
    if (this.appConfiguration != null) {
      this.currentSelectedTaskCategoryKey = this.appConfiguration.getSelectedTaskCategoryKey();
    }
  }

  private void b() {
    if (this.recyclerViewTodayTask != null) {
      this.recyclerViewTodayTask.setLayoutManager(new LinearLayoutManager(getActivity()));
      this.recyclerViewTodayTask.setAdapter(this.taskHomeAdapter);
      this.taskHomeAdapter.notifyDataSetChanged();
    }

    MainActivity.fragmentMenu.setOnCategoryListenerFragmentHome(data -> {
      if (!this.currentSelectedTaskCategoryKey.equals(data.getKey())) {
        // reload data from database
        this.currentSelectedTaskCategoryKey = data.getKey();
        this.c();
      }
    });
  }

  private void c() {
    new aiLoadTaskDatabase(
        this.appConfiguration,
        this.taskDatabase,
        this.todayTaskKeyArrayList,
        this.todayTaskDataArrayList,
        this.taskHomeAdapter
    ).execute();
  }

  private static class aiLoadTaskDatabase extends AsyncTask<Void, Void, Void> {
    private AppConfiguration appConfiguration;
    private TaskDatabase taskDatabase;
    private ArrayList<String> taskKeyArrayList;
    private ArrayList<TaskData> taskDataArrayList;
    private TaskHomeAdapter taskHomeAdapter;

    private aiLoadTaskDatabase(
        AppConfiguration appConfiguration,
        TaskDatabase taskDatabase,
        ArrayList<String> taskKeyArrayList,
        ArrayList<TaskData> taskDataArrayList,
        TaskHomeAdapter taskHomeAdapter
        ) {
      this.appConfiguration = appConfiguration;
      this.taskDatabase = taskDatabase;
      this.taskKeyArrayList = taskKeyArrayList;
      this.taskDataArrayList = taskDataArrayList;
      this.taskHomeAdapter = taskHomeAdapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      String selectedTaskCategory = this.appConfiguration.getSelectedTaskCategoryKey();
      String databaseTable = "task_table";
      String sql = "";
      if (selectedTaskCategory.equals("0")) {
        sql  = "select * from " + databaseTable;
      } else {
        sql = "select * from " + databaseTable + " where _taskCategoryKey = '" + selectedTaskCategory + "'";
      }
      if (sql.length() > 0) {
        Cursor cursor = this.taskDatabase.getWritableDatabase().rawQuery(sql, null);
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
            if (!this.taskKeyArrayList.contains(data.getKey())) {
              this.taskKeyArrayList.add(data.getKey());
              this.taskDataArrayList.add(data);
            }
          }
          cursor.close();
        }
      }

      return null;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      if (this.taskKeyArrayList.size() > 0) {
        this.taskKeyArrayList.clear();
        this.taskDataArrayList.clear();
      }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      if (this.taskHomeAdapter != null) {
        this.taskHomeAdapter.notifyDataSetChanged();
      }
    }
  }
}
