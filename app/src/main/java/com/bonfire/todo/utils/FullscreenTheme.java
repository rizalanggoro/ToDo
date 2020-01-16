package com.bonfire.todo.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

public class FullscreenTheme {
  private static String statusBarColorLight = "#F2FFFFFF";

  public static String getStatusBarColorLight() {
    return statusBarColorLight;
  }

  public static interface insetListener {
    void onInset(int insetTop, int insetBottom);
  }

  private static insetListener insetListener;
  public static void setInsetListener(insetListener listener) {
    if (listener != null) {
      insetListener = listener;
    }
  }

  public static void initializeConstraint(
      ConstraintLayout constraintLayoutAppBar,
      int constraintAppBarHeight,
      View fab,
      View[] divs,
      View navBar
  ) {
    if (constraintLayoutAppBar != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        constraintLayoutAppBar.setBackgroundColor(Color.parseColor(statusBarColorLight));
        constraintLayoutAppBar.setOnApplyWindowInsetsListener((view, windowInsets) -> {
          int insetTop = windowInsets.getSystemWindowInsetTop();
          int insetBottom = windowInsets.getSystemWindowInsetBottom();

          if (insetListener != null) {
            insetListener.onInset(insetTop, insetBottom);
          }

          ViewGroup.MarginLayoutParams marginAppBar = (ViewGroup.MarginLayoutParams) constraintLayoutAppBar.getLayoutParams();
          marginAppBar.setMargins(0, insetTop, 0, 0);
          constraintLayoutAppBar.setLayoutParams(marginAppBar);

          if (fab != null) {
            int marginBottom = (int) Size.dpToPx(32, fab.getContext()) + insetBottom;
            int marginRightLeft = (int) Size.dpToPx(32, fab.getContext());
            if (marginBottom > 0) {
              ViewGroup.MarginLayoutParams fabMargin = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
              fabMargin.setMargins(marginRightLeft, 0, marginRightLeft, marginBottom);
            }
          }

          if (divs[0] != null) {
            int heightDivNestedScrollView = (int) Size.dpToPx((float) constraintAppBarHeight, divs[0].getContext()) + insetTop;
            if (heightDivNestedScrollView > 0) {
              divs[0].setLayoutParams(
                  new LinearLayout.LayoutParams(
                      ViewGroup.LayoutParams.MATCH_PARENT, heightDivNestedScrollView
                  )
              );
            }
          }

          if (divs[1] != null) {
            int heightDivBottomNestedScrollView = (int) Size.dpToPx(120, divs[1].getContext()) + insetBottom;
            if (heightDivBottomNestedScrollView > 0) {
              divs[1].setLayoutParams(
                  new LinearLayout.LayoutParams(
                      ViewGroup.LayoutParams.MATCH_PARENT, heightDivBottomNestedScrollView
                  )
              );
            }
          }

          if (divs.length > 2) {
            if (divs[2] != null) {
              int heightDivBottomNestedScrollView = (Size.getHeightPixels(divs[2].getContext()) / 3) + insetBottom;
              if (heightDivBottomNestedScrollView > 0) {
                divs[2].setLayoutParams(
                    new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, heightDivBottomNestedScrollView
                    )
                );
              }
            }
          }

          if (navBar != null) {
            if (insetBottom > 0) {
              ViewGroup.MarginLayoutParams fabMargin = (ViewGroup.MarginLayoutParams) navBar.getLayoutParams();
              fabMargin.setMargins(0, 0, 0, insetBottom);
            }
          }
          return windowInsets;
        });
      }
    }
  }

  public static void initializeAppBar(
      LinearLayout linearLayoutAppBar,
      int appBarHeightDp,
      View divTopNestedScrollView,
      View divBottomNestedScrollView,
      View fab
  ) {
    if (linearLayoutAppBar != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        linearLayoutAppBar.setBackgroundColor(Color.parseColor(statusBarColorLight));
        linearLayoutAppBar.setOnApplyWindowInsetsListener((view, windowInsets) -> {
          int insetTop = windowInsets.getSystemWindowInsetTop();
          int insetBottom = windowInsets.getSystemWindowInsetBottom();

          ViewGroup.MarginLayoutParams marginAppBar = (ViewGroup.MarginLayoutParams) linearLayoutAppBar.getLayoutParams();
          marginAppBar.setMargins(0, insetTop, 0, 0);
          linearLayoutAppBar.setLayoutParams(marginAppBar);

          if (divTopNestedScrollView != null) {
            int heightDivNestedScrollView = (int) Size.dpToPx((float) appBarHeightDp, divTopNestedScrollView.getContext()) + insetTop;
            if (heightDivNestedScrollView > 0) {
              divTopNestedScrollView.setLayoutParams(
                  new LinearLayout.LayoutParams(
                      ViewGroup.LayoutParams.MATCH_PARENT, heightDivNestedScrollView
                  )
              );
            }
          }

          if (divBottomNestedScrollView != null) {
            int heightDivBottomNestedScrollView = (int) Size.dpToPx(120, divBottomNestedScrollView.getContext()) + insetBottom;
            if (heightDivBottomNestedScrollView > 0) {
              divBottomNestedScrollView.setLayoutParams(
                  new LinearLayout.LayoutParams(
                      ViewGroup.LayoutParams.MATCH_PARENT, heightDivBottomNestedScrollView
                  )
              );
            }
          }

          if (fab != null) {
            int marginBottom = (int) Size.dpToPx(32, fab.getContext()) + insetBottom;
            int marginRightLeft = (int) Size.dpToPx(32, fab.getContext());
            if (marginBottom > 0) {
              ViewGroup.MarginLayoutParams fabMargin = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
              fabMargin.setMargins(marginRightLeft, 0, marginRightLeft, marginBottom);
            }
          }

          return windowInsets;
        });
      }
    }
  }

  public static void initializeAppBar2(
      LinearLayout linearLayoutAppBar,
      int appBarHeightDp,
      View[] divTop,
      View[] divBottom,
      View fab) {
    if (linearLayoutAppBar != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        linearLayoutAppBar.setBackgroundColor(Color.parseColor(statusBarColorLight));
        linearLayoutAppBar.setOnApplyWindowInsetsListener((view, windowInsets) -> {
          int insetTop = windowInsets.getSystemWindowInsetTop();
          int insetBottom = windowInsets.getSystemWindowInsetBottom();

          ViewGroup.MarginLayoutParams marginAppBar = (ViewGroup.MarginLayoutParams) linearLayoutAppBar.getLayoutParams();
          marginAppBar.setMargins(0, insetTop, 0, 0);
          linearLayoutAppBar.setLayoutParams(marginAppBar);

          if (divTop != null) {
            for (View divTopNestedScrollView : divTop) {
              int heightDivNestedScrollView = (int) Size.dpToPx((float) appBarHeightDp, divTopNestedScrollView.getContext()) + insetTop;
              if (heightDivNestedScrollView > 0) {
                divTopNestedScrollView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, heightDivNestedScrollView
                    )
                );
              }
            }
          }

          if (divBottom != null) {
            for(View divBottomNestedScrollView : divBottom) {
              int heightDivBottomNestedScrollView = (int) Size.dpToPx(120, divBottomNestedScrollView.getContext()) + insetBottom;
              if (heightDivBottomNestedScrollView > 0) {
                divBottomNestedScrollView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, heightDivBottomNestedScrollView
                    )
                );
              }
            }
          }

          if (fab != null) {
            int marginBottom = (int) Size.dpToPx(32, fab.getContext()) + insetBottom;
            int marginRightLeft = (int) Size.dpToPx(32, fab.getContext());
            if (marginBottom > 0) {
              ViewGroup.MarginLayoutParams fabMargin = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
              fabMargin.setMargins(marginRightLeft, 0, marginRightLeft, marginBottom);
            }
          }

          return windowInsets;
        });
      }
    }
  }

  public static void initialize(Activity activity) {
    if (activity != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // setup status bar only
        activity.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        activity.getWindow().setStatusBarColor(Color.parseColor(statusBarColorLight));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          // setup status bar
          // then setup navigation bar
          int a = activity.getWindow().getDecorView().getSystemUiVisibility();
          activity.getWindow().getDecorView().setSystemUiVisibility(
              a |
                  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                  View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
          );
          activity.getWindow().setNavigationBarColor(Color.parseColor(statusBarColorLight));
        }
      }
    }
  }
}
