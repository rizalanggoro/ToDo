package com.bonfire.todo.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

public class ViewPressedAnimation {
  private static int duration = 250;

  @SuppressLint("ClickableViewAccessibility")
  public static void animate(View view1, View view2) {
    if (view1 != null && view2 != null) {
      view1.setOnTouchListener((view3, motionEvent) -> {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
          ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view2, "scaleX", 0.9f);
          ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view2, "scaleY", 0.9f);
          scaleDownX.setDuration(duration);
          scaleDownY.setDuration(duration);
          AnimatorSet scaleDown = new AnimatorSet();
          scaleDown.play(scaleDownX).with(scaleDownY);
          scaleDown.start();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
          ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view2, "scaleX", 1.0f);
          ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view2, "scaleY", 1.0f);
          scaleUpX.setDuration(duration);
          scaleUpY.setDuration(duration);
          AnimatorSet scaleUp = new AnimatorSet();
          scaleUp.play(scaleUpX).with(scaleUpY);
          scaleUp.start();
        }
        return true;
      });
    }
  }
}
