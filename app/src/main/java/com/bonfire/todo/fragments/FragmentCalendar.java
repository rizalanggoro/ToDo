package com.bonfire.todo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.adapters.CalendarAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCalendar extends Fragment {
  @BindView(R.id.rv_calendar)
  RecyclerView recyclerViewCalendar;

  private CalendarAdapter calendarAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_calendar, container, false);


    ButterKnife.bind(this, view);
    this.a();
    this.b();

    return view;
  }

  private void a() {
    this.calendarAdapter = new CalendarAdapter(getActivity());
  }

  private void b() {
    if (this.calendarAdapter != null) {
      this.recyclerViewCalendar.setLayoutManager(
          new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
      this.recyclerViewCalendar.setAdapter(this.calendarAdapter);
      this.calendarAdapter.notifyDataSetChanged();
    }
  }
}
