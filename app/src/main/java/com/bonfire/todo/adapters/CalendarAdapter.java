package com.bonfire.todo.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.utils.Color;
import com.bonfire.todo.utils.Font;
import com.bonfire.todo.utils.Size;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarAdapter extends RecyclerView.Adapter {
  private Calendar calendar;
  private Activity activity;

  private int maxCardDay = 5;
  private int selectedCardDay = 0;

  private int[] days = {
      R.string.sunday,
      R.string.monday,
      R.string.tuesday,
      R.string.wednesday,
      R.string.thursday,
      R.string.friday,
      R.string.saturday,
  };

  public CalendarAdapter(Activity activity) {
    this.activity = activity;
    this.calendar = Calendar.getInstance();

    this.a();
  }

  private ArrayList<String> dateStringArrayList = new ArrayList<>();
  private void a() {
    int currentMonth = this.calendar.get(Calendar.MONTH) + 1;
    int currentDayOfMonth = this.calendar.get(Calendar.DAY_OF_MONTH);
    int maxDayOfMonth = this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    Log.d("current month", "" + currentMonth);
    Log.d("currentDayOfMonth", "" + currentDayOfMonth);
    Log.d("maxDayOfMonth", "" + maxDayOfMonth);

    if (this.dateStringArrayList.size() > 0) {
      this.dateStringArrayList.clear();
    }

    if (currentDayOfMonth + (this.maxCardDay - 1) <= maxDayOfMonth) {
      // show 4 day later
      for (int i = 0; i < this.maxCardDay; i++) {
        Date date = this.calendar.getTime();
        String dateString = String.valueOf(date.getTime());
        this.dateStringArrayList.add(dateString);

        this.calendar.set(
            Calendar.DAY_OF_MONTH,
            this.calendar.get(Calendar.DAY_OF_MONTH) + 1
        );
      }
    } else {

    }
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((Holder) holder).bind(position, this.dateStringArrayList.get(position));
    ((Holder) holder).frameLayout.setOnClickListener(view -> {
      this.selectedCardDay = position;
      this.notifyDataSetChanged();
    });
  }

  @Override
  public int getItemCount() {
    return this.dateStringArrayList.size();
  }

  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_date)
    TextView textViewDate;
    @BindView(R.id.tv_day)
    TextView textViewDay;
    @BindView(R.id.card_day)
    CardView cardViewDay;
    @BindView(R.id.fr)
    FrameLayout frameLayout;
    @BindView(R.id.ln_card_day)
    LinearLayout linearLayoutCardDay;

    public Holder(@NonNull View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
      this.a();
    }

    private void bind(int position, String dateString) {
      this.b(position);

      if (dateString.trim().length() > 0) {
        Date date = new Date(Long.parseLong(dateString));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        this.textViewDate.setText(String.valueOf(
            calendar.get(Calendar.DAY_OF_MONTH)
        ));
        this.textViewDay.setText(
            days[calendar.get(Calendar.DAY_OF_WEEK) - 1]
        );
      }
    }

    private void a() {
      int totalDiv = maxCardDay + 1;
      int widthPixels = Size.getWidthPixels(activity);
      int divWidth = (int) Size.dpToPx(8, activity);
      int widthCardDay = (widthPixels - (totalDiv * divWidth)) / maxCardDay;
      int heightCardDay = (int) Math.floor(widthCardDay * 1.5);
      if (this.cardViewDay != null) {
        this.cardViewDay.setLayoutParams(new LinearLayout.LayoutParams(
            widthCardDay, heightCardDay
        ));
      }

      Font.setGilroyBold(this.textViewDate);
    }

    private void b(int position) {
      int cardDayColor = 0;
      int cardDayTextColor = 0;
      if (position == selectedCardDay) {
        cardDayColor = R.color.aws_green;
        cardDayTextColor = 0xffffffff;
      } else {
        cardDayColor = android.R.color.white;
        cardDayTextColor = Color.blueGrey("500");
      }
      this.linearLayoutCardDay.setBackgroundResource(cardDayColor);
      this.textViewDate.setTextColor(cardDayTextColor);
      this.textViewDay.setTextColor(cardDayTextColor);
    }
  }
}
