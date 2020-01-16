package com.bonfire.todo.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.adapters.IconAdapter;
import com.bonfire.todo.utils.FullscreenTheme;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectIconActivity extends AppCompatActivity {
  @BindView(R.id.constraintLayoutAppBar)
  ConstraintLayout constraintLayoutAppBar;
  @BindView(R.id.rv_icons)
  RecyclerView recyclerViewIcon;

  @BindView(R.id.div_top_nested_scroll_view)
  View divTopNestedScrollView;
  @BindView(R.id.div_bottom_nested_scroll_view)
  View divBottomNestedScrollView;

  private IconAdapter iconAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_select_icon);

    this.a();
    this.b();
  }

  private void a() {
    ButterKnife.bind(this);
    FullscreenTheme.initialize(this);
    FullscreenTheme.initializeConstraint(
        this.constraintLayoutAppBar,
        72,
        null,
        new View[]{this.divTopNestedScrollView, this.divBottomNestedScrollView},
        null
    );

    this.iconAdapter = new IconAdapter(this);
  }

  private void b() {
    if (this.iconAdapter != null) {
      this.recyclerViewIcon.setLayoutManager(new GridLayoutManager(this, 3));
      this.recyclerViewIcon.setAdapter(this.iconAdapter);
      this.iconAdapter.notifyDataSetChanged();
    }
  }
}
