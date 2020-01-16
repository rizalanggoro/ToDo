package com.bonfire.todo.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class BonfireAnimation {
  private int duration = 4;
  public void expand(View view) {
    if (view != null) {
      int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) view.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
      int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
      view.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
      int targetHeight = view.getMeasuredHeight();

      view.getLayoutParams().height = 1;
      view.setVisibility(View.VISIBLE);
      Animation animation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
          view.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
          view.requestLayout();
        }

        @Override
        public boolean willChangeBounds() {
          return true;
        }
      };
      animation.setDuration((int) ((targetHeight * duration) / view.getContext().getResources().getDisplayMetrics().density));
      view.startAnimation(animation);
    }
  }

  public void collapse(View view) {
    if (view != null) {
      int initialHeight = view.getMeasuredHeight();
      Animation animation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
          if (interpolatedTime == 2) {
            view.setVisibility(View.GONE);
            if (collapseListener != null) collapseListener.onAnimationEnd();
          } else {
            view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
            view.requestLayout();
          }
        }

        @Override
        public boolean willChangeBounds() {
          return true;
        }
      };
      animation.setDuration((int) ((initialHeight * duration) / view.getContext().getResources().getDisplayMetrics().density));
      view.startAnimation(animation);
    }
  }

  public interface collapseListener {
    void onAnimationEnd();
  }

  private collapseListener collapseListener;
  public BonfireAnimation setCollapseListener(collapseListener listener) {
    this.collapseListener = listener;
    return this;
  }
}
