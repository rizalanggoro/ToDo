package com.bonfire.todo.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.MainActivity;
import com.bonfire.todo.R;
import com.bonfire.todo.adapters.MyTaskCategoryAdapter;
import com.bonfire.todo.adapters.TaskHomeAdapter;
import com.bonfire.todo.databases.TaskCategoryDatabase;
import com.bonfire.todo.datas.TaskCategoryData;
import com.bonfire.todo.utils.Font;
import com.bonfire.todo.utils.FullscreenTheme;
import com.bonfire.todo.utils.Size;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMenu extends Fragment {
  @BindView(R.id.tv_title)
  TextView textViewTitle;
  @BindView(R.id.img_close_menu)
  ImageView imageViewCloseMenu;
  @BindView(R.id.rv_my_task_category)
  RecyclerView recyclerViewMyTaskCategory;

  @BindView(R.id.insetTop)
  View insetTop;
  @BindView(R.id.div_top_nested_scroll_view)
  View divTopNestedScrollView;
  @BindView(R.id.div_bottom_nested_scroll_view)
  View divBottomNestedScrollView;

  @BindView(R.id.constraintLayoutAppBar)
  ConstraintLayout constraintLayoutAppBar;

  private MyTaskCategoryAdapter myTaskCategoryAdapter;
  private TaskCategoryDatabase taskCategoryDatabase;

  private ArrayList<String> taskCategoryKeyArrayList = new ArrayList<>();
  private ArrayList<TaskCategoryData> taskCategoryDataArrayList = new ArrayList<>();

  public interface onCategoryListener {
    void onCategoryChanged(TaskCategoryData data);
  }

  public interface onCategoryListenerFragmentHome {
    void onCategoryChanged(TaskCategoryData data);
  }

  private onCategoryListenerFragmentHome onCategoryListenerFragmentHome;
  public FragmentMenu setOnCategoryListenerFragmentHome(onCategoryListenerFragmentHome listener) {
    this.onCategoryListenerFragmentHome = listener;
    return this;
  }

  private onCategoryListener onCategoryListener;
  public FragmentMenu setOnCategoryListener(onCategoryListener listener) {
    if (listener != null) this.onCategoryListener = listener;
    return this;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_menu, container, false);

    ButterKnife.bind(this, view);
    this.a();
    this.aa();
    this.ab();
    this.b();

    return view;
  }

  private void a() {
    this.myTaskCategoryAdapter = new MyTaskCategoryAdapter(getActivity());
    this.taskCategoryDatabase = new TaskCategoryDatabase(getActivity());
    if (this.taskCategoryDataArrayList.size() == 0)
      this.taskCategoryDatabase.setListener(taskCategoryData -> {
        if (!this.taskCategoryKeyArrayList.contains(taskCategoryData.getKey())) {
          this.taskCategoryKeyArrayList.add(taskCategoryData.getKey());
          this.taskCategoryDataArrayList.add(taskCategoryData);
          this.myTaskCategoryAdapter.notifyDataSetChanged();
        }
      }).load();

    this.myTaskCategoryAdapter.setTaskCategoryDataArrayList(this.taskCategoryDataArrayList);

    Font.setGilroyBold(this.textViewTitle);

    this.constraintLayoutAppBar.setBackgroundColor(Color.parseColor(
        FullscreenTheme.getStatusBarColorLight()));

    this.insetTop.setLayoutParams(new ConstraintLayout.LayoutParams(
        0, MainActivity.insetTop));
  }

  private void aa() {
    int appBarHeight = 72;
    int insetTopDp = (int) Size.pxToDp(MainActivity.insetTop, getActivity());
    int divTopNestedScrollViewHeight = insetTopDp + appBarHeight;
    int divTopNestedScrollViewHeightPx = (int) Size.dpToPx(divTopNestedScrollViewHeight, getActivity());
    this.divTopNestedScrollView.setLayoutParams(new LinearLayout.LayoutParams(0, divTopNestedScrollViewHeightPx));

    int divBottom = 120;
    int divBottomPx = (int) Size.dpToPx(divBottom, getActivity());
    int divBottomNestedScrollViewHeightPx = MainActivity.insetBottom + divBottomPx;
    this.divBottomNestedScrollView.setLayoutParams(new LinearLayout.LayoutParams(0, divBottomNestedScrollViewHeightPx));

    this.imageViewCloseMenu.setColorFilter(com.bonfire.todo.utils.Color.blueGrey("300"));
  }

  private void ab() {
    this.recyclerViewMyTaskCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    this.recyclerViewMyTaskCategory.setAdapter(this.myTaskCategoryAdapter);
    this.myTaskCategoryAdapter.notifyDataSetChanged();
  }

  private void b() {
    this.imageViewCloseMenu.setOnClickListener(view -> {
      if (getActivity() != null) {
        getActivity().getSupportFragmentManager().popBackStack("menu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        MainActivity.isMenuOpened = false;
      } else {
        Log.d("fragmentMenu", "getActivity == null");
      }
    });

    this.myTaskCategoryAdapter.setOnCategoryListener(data -> {
      if (this.onCategoryListener != null)
        this.onCategoryListener.onCategoryChanged(data);
      if (this.onCategoryListenerFragmentHome != null)
        this.onCategoryListenerFragmentHome.onCategoryChanged(data);
    });
  }
}
