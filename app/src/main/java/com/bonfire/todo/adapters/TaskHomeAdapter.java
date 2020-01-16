package com.bonfire.todo.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.databases.TaskCategoryDatabase;
import com.bonfire.todo.datas.TaskCategoryData;
import com.bonfire.todo.datas.TaskData;
import com.bonfire.todo.utils.Color;
import com.bonfire.todo.utils.FeatherIcon;
import com.bonfire.todo.utils.Font;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskHomeAdapter extends RecyclerView.Adapter {
  private Activity activity;
  private ArrayList<String> taskKeyArrayList;
  private ArrayList<TaskData> taskDataArrayList;
  private TaskCategoryDatabase taskCategoryDatabase;
  private FeatherIcon featherIcon;

  public TaskHomeAdapter(
      Activity activity,
      ArrayList<String> taskKeyArrayList,
      ArrayList<TaskData> taskDataArrayList
  ) {
    this.activity = activity;
    this.taskKeyArrayList = taskKeyArrayList;
    this.taskDataArrayList = taskDataArrayList;
    this.taskCategoryDatabase = new TaskCategoryDatabase(this.activity);
    this.featherIcon = new FeatherIcon(this.activity);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_task, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((Holder) holder).bind(this.taskDataArrayList.get(position));
  }

  @Override
  public int getItemCount() {
    return this.taskDataArrayList.size();
  }

  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    TextView textViewTitle;
    @BindView(R.id.tv_subtitle)
    TextView textViewSubtitle;
    @BindView(R.id.tv_date)
    TextView textViewDate;
    @BindView(R.id.tv_time)
    TextView textViewTime;
    @BindView(R.id.tv_subject)
    TextView textViewSubject;

    @BindView(R.id.img_calendar)
    ImageView imageViewCalendar;
    @BindView(R.id.img_subject_color)
    ImageView imageViewSubjectColor;
    @BindView(R.id.img_icon)
    ImageView imageViewIcon;
    @BindView(R.id.img_background_icon)
    ImageView imageViewBackgroundIcon;

    public Holder(@NonNull View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
      this.a();
    }

    @SuppressLint("ResourceType")
    private void a() {
      Font.setSofiaProLight(this.textViewTitle);
      Font.setBoldStyle(this.textViewTitle);

      String awsGreen = activity.getString(R.color.aws_green);
      String detailTextColor = "400";

      this.textViewTitle.setTextColor(Color.blueGrey("900"));
      this.textViewSubtitle.setTextColor(Color.blueGrey("700"));
      this.textViewDate.setTextColor(Color.blueGrey(detailTextColor));
      this.textViewTime.setTextColor(Color.blueGrey(detailTextColor));
      this.textViewSubject.setTextColor(Color.blueGrey(detailTextColor));

      this.imageViewCalendar.setColorFilter(android.graphics.Color.parseColor(awsGreen));
      this.imageViewSubjectColor.setColorFilter(android.graphics.Color.parseColor(awsGreen));

      this.imageViewCalendar.setLayoutParams(new LinearLayout.LayoutParams(
          (int) this.textViewDate.getTextSize(), (int) this.textViewDate.getTextSize()
      ));

      this.imageViewSubjectColor.setLayoutParams(new LinearLayout.LayoutParams(
          (int) this.textViewSubject.getTextSize(), (int) this.textViewSubject.getTextSize()
      ));
    }

    private void bind(TaskData data) {
      this.textViewTitle.setText(data.getTaskTitle());
      this.textViewSubtitle.setText(data.getTaskDescription());

      String taskCategoryKey = data.getTaskCategoryKey();
      if (taskCategoryKey.trim().length() > 0) {
        TaskCategoryData categoryData = taskCategoryDatabase.getTaskCategoryData(taskCategoryKey);
        this.imageViewIcon.setImageResource(featherIcon.getResource(categoryData.getCategoryIcon()));
        this.imageViewIcon.setColorFilter(Integer.parseInt(categoryData.getCategoryIconColor()));
        this.imageViewBackgroundIcon.setColorFilter(Integer.parseInt(categoryData.getCategoryIconBackgroundColor()));
      }
    }
  }
}
