package com.bonfire.todo.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.bonfire.todo.R;
import com.bonfire.todo.bottomsheet.SelectCategoryTaskBottomSheet;
import com.bonfire.todo.bottomsheet.SelectDateBottomSheet;
import com.bonfire.todo.databases.TaskDatabase;
import com.bonfire.todo.datas.SubTaskData;
import com.bonfire.todo.datas.TaskCategoryData;
import com.bonfire.todo.datas.TaskData;
import com.bonfire.todo.utils.AlphaAnimation;
import com.bonfire.todo.utils.BonfireAnimation;
import com.bonfire.todo.utils.Color;
import com.bonfire.todo.utils.Font;
import com.bonfire.todo.utils.FullscreenTheme;
import com.bonfire.todo.utils.Size;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTaskActivity extends AppCompatActivity implements NestedScrollView.OnScrollChangeListener {
  @BindView(R.id.constraintLayoutAppBar)
  ConstraintLayout constraintLayoutAppBar;
  @BindView(R.id.nested_scroll_view)
  NestedScrollView nestedScrollView;
  @BindView(R.id.fr_add_sub_task)
  FrameLayout frameLayoutAddSubTask;

  @BindView(R.id.tv_title)
  TextView textViewTitle;
  @BindView(R.id.edt_task_title)
  EditText editTextTaskTitle;
  @BindView(R.id.tv1)
  TextView textView1;
  @BindView(R.id.tv2)
  TextView textView2;
  @BindView(R.id.tv3)
  TextView textView3;
  @BindView(R.id.tv_sub_task)
  TextView textViewSubTask;
  @BindView(R.id.tv_btn_add_sub_task)
  TextView textViewButtonAddSubTask;
  @BindView(R.id.tv_date_task)
  TextView textViewDateTask;
  @BindView(R.id.tv_category)
  TextView textViewCategory;
  @BindView(R.id.edt_description)
  EditText editTextDescription;

  @BindView(R.id.div_top_nested_scroll_view)
  View divTopNestedScrollView;
  @BindView(R.id.div_bottom_nested_scroll_view)
  View divBottomNestedScrollView;
  @BindView(R.id.material_elevation)
  View materialElevation;

  @BindView(R.id.card_date)
  CardView cardViewDate;
  @BindView(R.id.card_category)
  CardView cardViewCategory;
  @BindView(R.id.card_subject)
  CardView cardViewSubject;

  @BindView(R.id.ln_card_date)
  LinearLayout linearLayoutCardDate;
  @BindView(R.id.ln_card_category)
  LinearLayout linearLayoutCardCategory;
  @BindView(R.id.ln_card_subject)
  LinearLayout linearLayoutCardSubject;
  @BindView(R.id.ln_list_sub_task)
  LinearLayout linearLayoutListSubTask;

  @BindView(R.id.fr_card_date)
  FrameLayout frameLayoutCardDate;
  @BindView(R.id.fr_card_category)
  FrameLayout frameLayoutCardCategory;
  @BindView(R.id.fr_card_subject)
  FrameLayout frameLayoutCardSubject;

  @BindView(R.id.img_card_date)
  ImageView imageViewCardDate;
  @BindView(R.id.img_card_category)
  ImageView imageViewCardCategory;
  @BindView(R.id.img_back)
  ImageView imageViewBack;
  @BindView(R.id.img_done)
  ImageView imageViewDone;

  private int[] iconColors = {
      Color.blue("400"),
      Color.pink("400"),
  };
  private ArrayList<SubTaskData> subTaskDataArrayList = new ArrayList<>();
  private SelectDateBottomSheet selectDateBottomSheet;
  private SelectCategoryTaskBottomSheet selectCategoryTaskBottomSheet;
  private Calendar selectedCalendar;
  private BonfireAnimation bonfireAnimation;
  private TaskDatabase taskDatabase;

  // configuration
  public static String selectedLongDate = "";
  public static String selectedCategoryDataKey = "";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_add_task);

    this.a();
    this.aa();
    this.ab();
    this.b();
    this.c();
  }

  private void a() {
    ButterKnife.bind(this);
    FullscreenTheme.initialize(this);
    FullscreenTheme.initializeConstraint(
        this.constraintLayoutAppBar,
        72,
        null,
        new View[]{this.divTopNestedScrollView, null, this.divBottomNestedScrollView},
        null
    );
    Font.setGilroyBold(
        this.textViewTitle,
        this.editTextTaskTitle,
        this.textView1,
        this.textView2,
        this.textView3,
        this.textViewSubTask
    );
    Font.setGilroyLight(this.textViewButtonAddSubTask);
    Font.setBoldStyle(this.textViewButtonAddSubTask);

    selectedLongDate = "";
    selectedCategoryDataKey = "";
    this.selectDateBottomSheet = new SelectDateBottomSheet();
    this.selectCategoryTaskBottomSheet = new SelectCategoryTaskBottomSheet();
    this.selectDateBottomSheet.setListener(this::onDateTaskChanged);
    this.selectCategoryTaskBottomSheet.setListener(this::onCategoryDataChanged);
    this.bonfireAnimation = new BonfireAnimation();
    this.taskDatabase = new TaskDatabase(this);
  }

  private void aa() {
    int widthPx = Size.getWidthPixels(this);
    int divWidthPx = (int) Size.dpToPx(16, this);
    int totalDivWidthPx = divWidthPx * 3;
    int cardWidth = (widthPx - totalDivWidthPx) / 2;
    this.linearLayoutCardDate.setLayoutParams(new CardView.LayoutParams(cardWidth, cardWidth));
    this.linearLayoutCardCategory.setLayoutParams(new CardView.LayoutParams(cardWidth, cardWidth));
    this.linearLayoutCardSubject.setLayoutParams(new CardView.LayoutParams(cardWidth, cardWidth));
    this.frameLayoutCardDate.setLayoutParams(new CardView.LayoutParams(cardWidth, cardWidth));
    this.frameLayoutCardCategory.setLayoutParams(new CardView.LayoutParams(cardWidth, cardWidth));
    this.frameLayoutCardSubject.setLayoutParams(new CardView.LayoutParams(cardWidth, cardWidth));

    this.imageViewCardDate.setColorFilter(this.iconColors[0]);
    this.imageViewCardCategory.setColorFilter(this.iconColors[1]);
    this.imageViewBack.setColorFilter(Color.blueGrey("300"));
    this.editTextTaskTitle.setHintTextColor(Color.blueGrey("900"));
    this.editTextTaskTitle.setTextColor(Color.blueGrey("900"));
    this.editTextDescription.setHintTextColor(Color.blueGrey("800"));
    this.editTextDescription.setTextColor(Color.blueGrey("800"));
  }

  private void ab() {
    this.selectedCalendar = Calendar.getInstance();
    this.onDateTaskChanged(String.valueOf(this.selectedCalendar.getTime().getTime()));
  }

  private void b() {
    this.nestedScrollView.setOnScrollChangeListener(this);
  }

  private void c() {
    this.frameLayoutAddSubTask.setOnClickListener(view -> this.addSubTask());

    this.frameLayoutCardDate.setOnClickListener(view -> {
      if (this.selectDateBottomSheet.getDialog() == null)
        this.selectDateBottomSheet.show(this.getSupportFragmentManager(), "selectDateBottomSheet");
    });

    this.frameLayoutCardCategory.setOnClickListener(view -> {
      if (this.selectCategoryTaskBottomSheet.getDialog() == null) {
        this.selectCategoryTaskBottomSheet.show(this.getSupportFragmentManager(), "selectCategoryTaskBottomSheet");
      }
    });

    this.imageViewBack.setOnClickListener(view -> super.onBackPressed());

    this.imageViewDone.setOnClickListener(view -> {
      String taskKey = String.valueOf(System.currentTimeMillis());
      String taskTitle = this.editTextTaskTitle.getText().toString();
      String taskDescription = this.editTextDescription.getText().toString();
      String taskLongDateString = selectedLongDate;
      String taskCategoryKey = selectedCategoryDataKey;
      String taskSubTask = "";

      if (taskTitle.trim().length() > 0) {
        if (this.subTaskDataArrayList.size() > 0) {
          taskSubTask = new Gson().toJson(this.subTaskDataArrayList);
        }

        TaskData data = new TaskData();
        data.setKey(taskKey);
        data.setTaskTitle(taskTitle);
        data.setTaskDescription(taskDescription);
        data.setTaskDate(taskLongDateString);
        data.setTaskCategoryKey(taskCategoryKey);
        data.setTaskSubTask(taskSubTask);

        if (this.taskDatabase.addTaskData(data)) {
          String newTaskJson = new Gson().toJson(data);
          Intent intent = new Intent();
          intent.putExtra("newTask", newTaskJson);
          setResult(RESULT_OK, intent);
          this.finish();
          Log.d("json", newTaskJson);
        }
      }
    });
  }

  @SuppressLint("SimpleDateFormat")
  private void onDateTaskChanged(String longDateString) {
    selectedLongDate = longDateString;
    Date date = new Date(Long.parseLong(longDateString));
    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    String dateTask = format.format(calendar.getTime());
    if (dateTask.trim().length() > 0) {
      this.textViewDateTask.setText(dateTask);
      this.selectedCalendar = calendar;
    }
  }

  private void onCategoryDataChanged(TaskCategoryData data) {
    selectedCategoryDataKey = data.getKey();
    this.textViewCategory.setText(data.getCategoryName());
  }

  private void addSubTask() {
    SubTaskData data = new SubTaskData();
    data.set(
        "",
        String.valueOf(System.currentTimeMillis()),
        "",
        "false"
    );
    this.subTaskDataArrayList.add(data);
    this.as();
  }

  private void as() {
    View view = LayoutInflater.from(this).inflate(R.layout.list_item_sub_task, null, false);
    if (view != null) {
      this.linearLayoutListSubTask.addView(view);
      this.bonfireAnimation.expand(view);
      this.as(view);
    }
  }

  private void as(View view) {
    EditText editTextTitleSubTask = (EditText) view.findViewById(R.id.edt_title);
    ImageView imageViewMarkSubTask = (ImageView) view.findViewById(R.id.img_mark);
    ImageView imageViewDeleteSubTask = (ImageView) view.findViewById(R.id.img_delete);

    editTextTitleSubTask.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int index = linearLayoutListSubTask.indexOfChild(view);
        if (index != -1) {
          SubTaskData data = subTaskDataArrayList.get(index);
          if (data != null) {
            data.setTitle(charSequence.toString());
            subTaskDataArrayList.remove(index);
            subTaskDataArrayList.add(index, data);
          }
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    imageViewMarkSubTask.setOnClickListener(view1 -> {
      int index = this.linearLayoutListSubTask.indexOfChild(view);
      if (index != -1) {
        SubTaskData data = this.subTaskDataArrayList.get(index);
        int res = 0;
        if (data.getDone().equals("false")) {
          res = R.drawable.mark_done;
          data.setDone("true");
        } else {
          res = R.drawable.mark_undone;
          data.setDone("false");
        }
        imageViewMarkSubTask.setImageResource(res);
        this.subTaskDataArrayList.remove(index);
        this.subTaskDataArrayList.add(index, data);
      }
    });

    imageViewDeleteSubTask.setOnClickListener(view1 -> {
      int index = this.linearLayoutListSubTask.indexOfChild(view);
      if (index != -1) {
        this.bonfireAnimation.setCollapseListener(() -> {
          this.linearLayoutListSubTask.removeViewAt(index);
          this.subTaskDataArrayList.remove(index);
        }).collapse(view);
      }
    });
  }


  @Override
  public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    if (scrollY > oldScrollY) {
      // scroll down
      new AlphaAnimation().animateAlpha(materialElevation, true);
      // new AlphaAnimation().animateAlpha(fabAddPayment, false);
    } else {
      // scroll up
      // new AlphaAnimation().animateAlpha(fabAddPayment, true);
    }
    if (scrollY == 0) {
      // reach top
      new AlphaAnimation().animateAlpha(materialElevation, false);
    }
  }
}
