package com.bonfire.todo.bottomsheet;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.utils.Color;
import com.bonfire.todo.utils.Font;
import com.bonfire.todo.utils.Size;
import com.bonfire.todo.utils.ToDoDate;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectDateBottomSheet extends BottomSheetDialogFragment {

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
    View view = inflater.inflate(R.layout.bottom_sheet_select_date, container, false);

    ButterKnife.bind(this, view);
    this.a();
    this.aa();
    this.b();
    this.c();

    return view;
  }

  @BindView(R.id.rv_date)
  RecyclerView recyclerViewDate;

  @BindView(R.id.tv_month)
  TextView textViewMonth;
  @BindView(R.id.tv1)
  TextView textView1;
  @BindView(R.id.tv2)
  TextView textView2;
  @BindView(R.id.tv3)
  TextView textView3;
  @BindView(R.id.tv4)
  TextView textView4;
  @BindView(R.id.tv5)
  TextView textView5;
  @BindView(R.id.tv6)
  TextView textView6;
  @BindView(R.id.tv7)
  TextView textView7;
  @BindView(R.id.tv_select_date)
  TextView textViewSelectDate;

  @BindView(R.id.img_prev_month)
  ImageView imageViewPrevMonth;
  @BindView(R.id.img_next_month)
  ImageView imageViewNextMonth;

  @BindView(R.id.fr_select_date)
  FrameLayout frameLayoutSelectDate;

  private ToDoDate toDoDate;
  private ArrayList<String> dateArrayList = new ArrayList<>();
  private Adapter adapter;

  private int selectedDate = 0;
  private int selectedMonth = 0;
  private int selectedYear = 0;

  private int currentYear = 0;
  private int currentMonth = 0;

  public interface listener {
    void onDateSelected(String longDateString);
  }
  private listener listener;
  public void setListener(listener listener) {
    if (listener != null) this.listener = listener;
  }

  private void a() {
    Font.setGilroyBold(this.textViewMonth);
    Font.setSofiaProLight(
        this.textView1,
        this.textView2,
        this.textView3,
        this.textView4,
        this.textView5,
        this.textView6,
        this.textView7
    );
    Font.setGilroyLight(this.textViewSelectDate);
    Font.setBoldStyle(this.textViewSelectDate);

    this.adapter = new Adapter();
    this.toDoDate = new ToDoDate();
  }

  private void aa() {
    this.imageViewPrevMonth.setColorFilter(Color.blueGrey("300"));
    this.imageViewNextMonth.setColorFilter(Color.blueGrey("300"));
  }

  private void b() {
    if (this.adapter != null) {
      this.recyclerViewDate.setAdapter(this.adapter);
      this.recyclerViewDate.setLayoutManager(new GridLayoutManager(getContext(), 7));
      this.adapter.notifyDataSetChanged();
    }

    // --> first selected is today
    // default
    Calendar calendar = Calendar.getInstance();
    this.selectedDate = calendar.get(Calendar.DATE);
    this.selectedMonth = calendar.get(Calendar.MONTH);
    this.selectedYear = calendar.get(Calendar.YEAR);

    if (this.toDoDate != null) {
      this.toDoDate.setListener(resultDateArrayList -> {
        this.currentYear = this.toDoDate.getCurrentYear();
        this.currentMonth = this.toDoDate.getCurrentMonth();

        String monthAndYear = this.getString(this.toDoDate.getMonthText(currentMonth)) + " " + currentYear;
        this.textViewMonth.setText(monthAndYear);

        this.dateArrayList = resultDateArrayList;
        if (this.adapter != null) this.adapter.notifyDataSetChanged();
      }).aiLoad();
    }
  }

  private void c() {
    this.imageViewNextMonth.setOnClickListener(view -> this.toDoDate.nextMonth());

    this.imageViewPrevMonth.setOnClickListener(view -> this.toDoDate.prevMonth());

    this.frameLayoutSelectDate.setOnClickListener(view -> {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, this.selectedYear);
      calendar.set(Calendar.MONTH, this.selectedMonth);
      calendar.set(Calendar.DAY_OF_MONTH, this.selectedDate);
      String longCalendarString = String.valueOf(calendar.getTime().getTime());
      if (longCalendarString.trim().length() > 0) {
        if (this.listener != null) this.listener.onDateSelected(longCalendarString);
        this.dismissBottomSheet();
      }
    });
  }

  private void dismissBottomSheet() {
    if (this.getDialog() != null && this.getDialog().isShowing()) {
      this.getDialog().dismiss();
    }
  }

  public class Adapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      ((Holder) holder).bind(position, dateArrayList.get(position));
      ((Holder) holder).frameLayoutCardDate.setOnClickListener(view -> {
        if (!((Holder) holder).textViewDate.getText().toString().equals("")) {
          selectedYear = currentYear;
          selectedMonth = currentMonth;
          selectedDate = Integer.parseInt(((Holder) holder).textViewDate.getText().toString());
          notifyDataSetChanged();
        }
      });
    }

    @Override
    public int getItemCount() {
      return dateArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
      @BindView(R.id.tv_date)
      TextView textViewDate;

      @BindView(R.id.img_background_selected_date)
      ImageView imageViewBackgroundSelectedDate;

      @BindView(R.id.fr_card_date)
      FrameLayout frameLayoutCardDate;
      @BindView(R.id.ln_card_date)
      LinearLayout linearLayoutCardDate;
      @BindView(R.id.card_date)
      CardView cardViewDate;

      public Holder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        this.a();
        this.b();
      }

      private void a() {
        int totalColumn = 7;
        int widthPx = Size.getWidthPixels(getContext());
        int marginPx = (int) Size.dpToPx(16, getContext());
        int totalMarginPx = marginPx * 2;
        int divWidthPx = (int) Size.dpToPx(8, getContext());
        int totalDivPx = divWidthPx * (totalColumn + 1);
        int widthCardDatePx = (widthPx - (totalDivPx + totalMarginPx)) / totalColumn;

        this.linearLayoutCardDate.setLayoutParams(new CardView.LayoutParams(widthCardDatePx,
            widthCardDatePx));
        this.frameLayoutCardDate.setLayoutParams(new CardView.LayoutParams(widthCardDatePx,
            widthCardDatePx));
      }

      private void b() {
        Font.setGilroyLight(this.textViewDate);
        Font.setBoldStyle(this.textViewDate);
      }

      private void bind(int position, String date) {
        if (date.trim().length() > 0) {
          this.frameLayoutCardDate.setClickable(!date.equals("null"));
          this.frameLayoutCardDate.setFocusable(!date.equals("null"));
          if (date.equals("null")) {
            date = "";
          }
          this.textViewDate.setText(date);

          if (selectedMonth == currentMonth && selectedYear == currentYear &&
              date.equals(String.valueOf(selectedDate))) {
            this.setSelectedDate();
          } else {
            this.setUnSelectedDate(position);
          }
        }
      }

      private void setSelectedDate() {
        this.imageViewBackgroundSelectedDate.setVisibility(View.VISIBLE);
        this.textViewDate.setTextColor(0xffffffff);
      }

      private void setUnSelectedDate(int position) {
        this.imageViewBackgroundSelectedDate.setVisibility(View.GONE);
        if (position == 0 || position == 7 || position == 14 || position == 21 || position == 28 || position == 35) {
          this.textViewDate.setTextColor(Color.red("400"));
        } else {
          this.textViewDate.setTextColor(Color.blueGrey("900"));
        }
      }
    }
  }
}
