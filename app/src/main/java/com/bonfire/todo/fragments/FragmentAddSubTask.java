package com.bonfire.todo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bonfire.todo.MainActivity;
import com.bonfire.todo.R;
import com.bonfire.todo.datas.SubTaskData;
import com.bonfire.todo.utils.Color;
import com.bonfire.todo.utils.Font;
import com.bonfire.todo.utils.Size;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAddSubTask extends Fragment {
  @BindView(R.id.insetTop)
  View insetTop;
  @BindView(R.id.div_top_nested_scroll_view)
  View divTopNestedScrollView;
  @BindView(R.id.div_bottom_nested_scroll_view)
  View divBottomNestedScrollView;

  @BindView(R.id.tv_title)
  TextView textViewTitle;

  @BindView(R.id.img_close)
  ImageView imageViewClose;
  @BindView(R.id.img_mark_done)
  ImageView imageViewMarkDone;

  private boolean isDone = false;

  public interface subTaskListener {
    void onSubTaskAdded(SubTaskData data);
  }

  private subTaskListener subTaskListener;
  public void setSubTaskListener(subTaskListener listener) {
    if (listener != null)
      this.subTaskListener = listener;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_add_sub_task, container, false);

    ButterKnife.bind(this, view);
    this.a();
    this.b();

    return view;
  }

  private void a() {
    Font.setGilroyBold(this.textViewTitle);

    this.imageViewClose.setColorFilter(Color.blueGrey("300"));

    this.insetTop.setLayoutParams(new ConstraintLayout.LayoutParams(0,
        MainActivity.insetTop));

    int appBarHeight = 72;
    int insetTopDp = (int) Size.pxToDp(MainActivity.insetTop, getActivity());
    int divTopNestedScrollViewHeight = insetTopDp + appBarHeight;
    int divTopNestedScrollViewHeightPx = (int) Size.dpToPx(divTopNestedScrollViewHeight, getActivity());
    this.divTopNestedScrollView.setLayoutParams(new LinearLayout.LayoutParams(0, divTopNestedScrollViewHeightPx));

    int divBottom = 120;
    int divBottomPx = (int) Size.dpToPx(divBottom, getActivity());
    int divBottomNestedScrollViewHeightPx = MainActivity.insetBottom + divBottomPx;
    this.divBottomNestedScrollView.setLayoutParams(new LinearLayout.LayoutParams(0, divBottomNestedScrollViewHeightPx));
  }

  private void b() {
    this.imageViewMarkDone.setOnClickListener(view -> {
      int res = 0;
      if (this.isDone) {
        res = R.drawable.mark_undone;
      } else {
        res = R.drawable.mark_done;
      }
      this.imageViewMarkDone.setImageResource(res);
      this.isDone = !isDone;
    });
  }
}
