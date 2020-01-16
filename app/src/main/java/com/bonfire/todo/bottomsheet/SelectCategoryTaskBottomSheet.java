package com.bonfire.todo.bottomsheet;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.activities.AddTaskActivity;
import com.bonfire.todo.databases.TaskCategoryDatabase;
import com.bonfire.todo.datas.TaskCategoryData;
import com.bonfire.todo.utils.Font;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCategoryTaskBottomSheet extends BottomSheetDialogFragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (this.getDialog() != null && this.getDialog().getWindow() != null) {
      Window window = this.getDialog().getWindow();
      if (window != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          window.getDecorView().setSystemUiVisibility(
              window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
          );
        }
      }
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.bottom_sheet_select_category, container, false);

    ButterKnife.bind(this, view);
    this.a();
    this.b();
    this.c();

    return view;
  }

  @BindView(R.id.tv_title)
  TextView textViewTitle;
  @BindView(R.id.tv_done)
  TextView textViewDone;

  @BindView(R.id.fr_done)
  FrameLayout frameLayoutDone;

  @BindView(R.id.rv_category)
  RecyclerView recyclerViewCategory;

  private ArrayList<TaskCategoryData> taskCategoryDataArrayList = new ArrayList<>();
  private Adapter adapter;

  private TaskCategoryData selectedTaskCategoryData = null;

  public interface listener {
    void onCategorySelected(TaskCategoryData data);
  }
  private listener listener;
  public void setListener(listener listener) {
    this.listener = listener;
  }

  private void a() {
    Font.setGilroyBold(this.textViewTitle);
    Font.setGilroyLight(this.textViewDone);
    Font.setBoldStyle(this.textViewDone);

    this.adapter = new Adapter(AddTaskActivity.selectedCategoryDataKey);
  }

  private void b() {
    if (this.adapter != null) {
      this.recyclerViewCategory.setAdapter(this.adapter);
      this.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
      this.adapter.notifyDataSetChanged();
    }
  }

  private void c() {
    if (this.taskCategoryDataArrayList.size() == 0) {
      new aiLoad(this.getContext()).setListener((taskCategoryKeyArrayList, taskCategoryDataArrayList) -> {
        this.taskCategoryDataArrayList = taskCategoryDataArrayList;
        this.adapter.notifyDataSetChanged();
      }).execute();
    }

    this.frameLayoutDone.setOnClickListener(view -> {
      if (this.listener != null && this.selectedTaskCategoryData != null) {
        this.listener.onCategorySelected(this.selectedTaskCategoryData);
      }
      this.dismissBottomSheet();
    });
  }

  private void dismissBottomSheet() {
    if (this.getDialog() != null && this.getDialog().isShowing())
      this.getDialog().dismiss();
  }

  private static class aiLoad extends AsyncTask<Void, Void, Void> {
    private TaskCategoryDatabase taskCategoryDatabase;
    private ArrayList<String> taskCategoryKeyArrayList = new ArrayList<>();
    private ArrayList<TaskCategoryData> taskCategoryDataArrayList = new ArrayList<>();

    private interface listener {
      void onLoadCompleted(ArrayList<String> taskCategoryKeyArrayList,
                           ArrayList<TaskCategoryData> taskCategoryDataArrayList);
    }
    private listener listener;
    private aiLoad setListener(listener listener) {
      this.listener = listener;
      return this;
    }

    aiLoad(Context context) {
      this.taskCategoryDatabase = new TaskCategoryDatabase(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
      String sql = "select * from " + TaskCategoryDatabase.databaseTable;
      Cursor cursor = this.taskCategoryDatabase.getWritableDatabase().rawQuery(sql, null);
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
          if (!this.taskCategoryKeyArrayList.contains(data.getKey()) &&
              !data.getKey().equals("0")) {
            this.taskCategoryKeyArrayList.add(data.getKey());
            this.taskCategoryDataArrayList.add(data);
          }
        }
        cursor.close();
      }

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      if (this.listener != null) {
        this.listener.onLoadCompleted(this.taskCategoryKeyArrayList, this.taskCategoryDataArrayList);
      }
    }
  }

  public class Adapter extends RecyclerView.Adapter {
    // private int selectedPosition = -1;
    private String selectedCategoryKey = "";

    private Adapter(String selectedCategoryKey) {
      this.selectedCategoryKey = selectedCategoryKey;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_select_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      ((Holder) holder).bind(position, taskCategoryDataArrayList.get(position));
      ((Holder) holder).frameLayout.setOnClickListener(view -> {
        // this.selectedPosition = position;
        selectedTaskCategoryData = taskCategoryDataArrayList.get(position);
        this.selectedCategoryKey = taskCategoryDataArrayList.get(position).getKey();
        new Handler().postDelayed(this::notifyDataSetChanged, 250);
      });
    }

    @Override
    public int getItemCount() {
      return taskCategoryDataArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
      @BindView(R.id.fr)
      FrameLayout frameLayout;

      @BindView(R.id.img_mark)
      ImageView imageViewMark;
      @BindView(R.id.tv_title)
      TextView textViewTitle;
      @BindView(R.id.tv_subtitle)
      TextView textViewSubtitle;

      public Holder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
      }

      private void bind(int position, TaskCategoryData data) {
        int res = 0;
        if (selectedCategoryKey.equals(data.getKey())) {
          res = R.drawable.mark_done;
        } else {
          res = R.drawable.mark_undone;
        }
        this.imageViewMark.setImageResource(res);

        this.textViewTitle.setText(data.getCategoryName());
      }
    }
  }
}
