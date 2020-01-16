package com.bonfire.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bonfire.todo.activities.AddTaskActivity;
import com.bonfire.todo.activities.SelectIconActivity;
import com.bonfire.todo.activities.TestActivity;
import com.bonfire.todo.databases.TaskCategoryDatabase;
import com.bonfire.todo.datas.TaskCategoryData;
import com.bonfire.todo.def.DefaultTaskCategoryInsertDatabase;
import com.bonfire.todo.fragments.FragmentCalendar;
import com.bonfire.todo.fragments.FragmentHome;
import com.bonfire.todo.fragments.FragmentMenu;
import com.bonfire.todo.utils.Color;
import com.bonfire.todo.utils.Font;
import com.bonfire.todo.utils.FullscreenTheme;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.constraintLayoutAppBar)
  ConstraintLayout constraintLayoutAppBar;
  @BindView(R.id.tv_title)
  TextView textViewTitle;
  @BindView(R.id.ln_nav_bar)
  LinearLayout linearLayoutNavBar;
  @BindView(R.id.img_menu)
  ImageView imageViewMenu;
  @BindView(R.id.img_add_task)
  ImageView imageViewAddTask;

  @BindView(R.id.div_top_nested_scroll_view)
  View divTopNestedScrollView;
  @BindView(R.id.div_bottom_nested_scroll_view)
  View divBottomNestedScrollView;
  @BindView(R.id.elevationNavigationBar)
  View elevationNavigationBar;

  @BindView(R.id.ln_home)
  LinearLayout linearLayoutHome;
  @BindView(R.id.ln_calendar)
  LinearLayout linearLayoutCalendar;
  @BindView(R.id.ln_settings)
  LinearLayout linearLayoutSettings;

  public static FragmentMenu fragmentMenu;

  private int currentNavigation = 0;
  private Fragment[] fragments = {
      new FragmentHome(), new FragmentCalendar(), new FragmentHome()
  };

  public static boolean isMenuOpened = false;
  public static int insetTop = 0;
  public static int insetBottom = 0;

  private AppConfiguration appConfiguration;
  private DefaultTaskCategoryInsertDatabase defaultTaskCategoryInsertDatabase;
  private TaskCategoryDatabase taskCategoryDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.def();
    this.test();
    this.a();
    this.b();
    this.c();
    this.d();
    this.e();
  }

  private void def() {
    this.appConfiguration = new AppConfiguration(this);

    if (this.appConfiguration.getNeedInsertDefaultTaskCategory()) {
      // insert def task category to database
      this.defaultTaskCategoryInsertDatabase = new DefaultTaskCategoryInsertDatabase(this);
      this.defaultTaskCategoryInsertDatabase.initialize();
      this.appConfiguration.setNeedInsertDefaultTaskCategory("false");
    }
  }

  private void test() {
    /* startActivity(new Intent(this, AddTaskActivity.class));
    TaskCategoryData taskCategoryData = new TaskCategoryData();
    taskCategoryData.set(
        "ini id",
        "ini key",
        "ini name",
        "ini icon",
        "ini color",
        "ini total",
        ""
    );
    for (int i = 0; i < 5; i++) {
      Log.d("taskCategoryData", "v: " + taskCategoryData.getId());
    }*/
    // Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
  }

  private void a() {
    ButterKnife.bind(this);
    FullscreenTheme.initialize(this);
    FullscreenTheme.setInsetListener((a, b) -> {
      insetTop = a;
      insetBottom = b;
      Log.d("insetTop", "v : " + insetTop);
      Log.d("insetBottom", "v : " + insetBottom);
    });
    FullscreenTheme.initializeConstraint(
        this.constraintLayoutAppBar,
        72,
        null,
        new View[]{
            this.divTopNestedScrollView,
            this.divBottomNestedScrollView,
        },
        this.linearLayoutNavBar
    );
    fragmentMenu = new FragmentMenu();
    this.taskCategoryDatabase = new TaskCategoryDatabase(this);
  }

  private void b() {
    this.imageViewMenu.setColorFilter(Color.blueGrey("300"));

    Font.setGilroyBold(this.textViewTitle);
  }

  private void c() {
    // default fragment configuration
    this.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.frame_content, this.fragments[this.currentNavigation])
        .commit();

    LinearLayout[] linearLayoutNavBarItems = {
        this.linearLayoutHome,
        this.linearLayoutCalendar,
        this.linearLayoutSettings,
    };

    for (int i = 0; i < linearLayoutNavBarItems.length; i++) {
      int navIconColor = 0;
      int navTextColor = 0;
      int awsBlueColor = 0xff4092FF;
      if (i == this.currentNavigation) {
        navIconColor = awsBlueColor;
        navTextColor = awsBlueColor;
      } else {
        navIconColor = Color.blueGrey("300");
        navTextColor = Color.blueGrey("300");
      }
      LinearLayout linearLayoutNavBarItem = linearLayoutNavBarItems[i];
      if (linearLayoutNavBarItem != null) {
        Log.d("nav bar item", "position " + i);
        ((ImageView) linearLayoutNavBarItem.getChildAt(0)).setColorFilter(navIconColor);
        ((TextView) linearLayoutNavBarItem.getChildAt(1)).setTextColor(navTextColor);
      } else {
        Log.d("nav bar item", "null");
      }
    }
  }

  private void d() {
    this.linearLayoutHome.setOnClickListener(view -> {
      this.currentNavigation = 0;
      this.c();
    });

    this.linearLayoutCalendar.setOnClickListener(view -> {
      this.currentNavigation = 1;
      this.c();
    });

    this.linearLayoutSettings.setOnClickListener(view -> {
      this.currentNavigation = 2;
      this.c();
    });

    this.imageViewMenu.setOnClickListener(view -> this.showMenu());

    this.imageViewAddTask.setOnClickListener(view -> {
      this.startActivityForResult(new Intent(this, AddTaskActivity.class), 1);
    });

    // refresh app title
    fragmentMenu.setOnCategoryListener(data -> {
      this.e();
    });
  }

  private void e() {
    String selectedCategoryKey = this.appConfiguration.getSelectedTaskCategoryKey();
    String title = "";
    if (selectedCategoryKey.equals("") || selectedCategoryKey.equals("0")) {
      title = this.getString(R.string.app_name);
    } else {
      TaskCategoryData data = this.taskCategoryDatabase.getTaskCategoryData(selectedCategoryKey);
      if (data != null) {
        title = data.getCategoryName();
      } else {
        title = this.getString(R.string.app_name);
      }
    }
    this.textViewTitle.setText(title);
  }

  private void showMenu() {
    this.getSupportFragmentManager()
        .beginTransaction()
        .addToBackStack("menu")
        .replace(R.id.frame_menu, fragmentMenu, "menu")
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .commit();
    isMenuOpened = true;
  }

  public void hideMenu() {
    this.getSupportFragmentManager().popBackStack("menu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    isMenuOpened = false;
  }

  @Override
  public void onBackPressed() {
    if (isMenuOpened) {
      // close menu
      this.hideMenu();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case 1 :
        break;
    }
  }
}
